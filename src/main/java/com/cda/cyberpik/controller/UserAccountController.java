package com.cda.cyberpik.controller;


import com.cda.cyberpik.dto.FormatDto;
import com.cda.cyberpik.dto.PhotoDto;
import com.cda.cyberpik.dto.UploadPhotoDto;
import com.cda.cyberpik.dto.user.account.dto.PhotoForUserAccountDto;
import com.cda.cyberpik.exception.ControllerException;
import com.cda.cyberpik.exception.InvalidTokenException;
import com.cda.cyberpik.security.dto.MyUserDetails;
import com.cda.cyberpik.security.service.IJwtTokenService;
import com.cda.cyberpik.security.service.UserDetailsServiceImpl;
import com.cda.cyberpik.service.FormatService;
import com.cda.cyberpik.service.PhotoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.UserAccountService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Validated
@RestController
@RequestMapping("/user_accounts")
public class UserAccountController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IJwtTokenService jwtTokenService;

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private PhotoService photoService;

	@Autowired
	private FormatService formatService;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    /**
	@Secured({"ROLE_ADMIN"})
	@CrossOrigin
	@GetMapping(value = {"", "/"})
	public ResponseEntity<List<UserAccountDto>> findAllUserAccounts() {
		return new ResponseEntity(this.userAccountService.getAll(), HttpStatus.OK);
	}
	 **/

	@CrossOrigin
	@GetMapping(value = {"", "/"})
	public ResponseEntity<UserAccountDto> findUserAccountById(Authentication authentication) throws ServiceException, InvalidTokenException {
		Long userAccountId = checkAuthentication(authentication);
		return new ResponseEntity(this.userAccountService.getById(userAccountId), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewUserAccount(@RequestBody UserAccountDto userAccount) throws ControllerException {
		String encodePassword = this.bCryptPasswordEncoder.encode(userAccount.getPassword());
       	userAccount.setPassword(encodePassword);
		boolean userNameAlreadyExisting = this.userAccountService.verifyByUserName(userAccount.getUserName());
		boolean emailAlreadyExisting = this.userAccountService.verifyByEmail(userAccount.getEmail());

		if (userNameAlreadyExisting && emailAlreadyExisting){
			throw new ControllerException(HttpStatus.CONFLICT, "username & email already taken");
		} else if (userNameAlreadyExisting){
			throw new ControllerException(HttpStatus.CONFLICT, "username already taken");
		} else if (emailAlreadyExisting) {
			throw new ControllerException(HttpStatus.CONFLICT, "email already taken");
		} else {
			this.userAccountService.add(userAccount);
			return new ResponseEntity(HttpStatus.OK);
		}
	}


	@CrossOrigin
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> authenticate(@RequestBody MyUserDetails loginDetails) throws ControllerException {
		System.out.println(loginDetails);
		boolean doesUserAccountExist = userAccountService.verifyByUserName(loginDetails.getUsername());
	    if(!doesUserAccountExist){
            throw new ControllerException(HttpStatus.UNAUTHORIZED, "wrong username and/or wrong password");
        }
	    UsernamePasswordAuthenticationToken userNamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				loginDetails.getUsername(), loginDetails.getPassword());
		Authentication authentication;
		try {
			authentication = authenticationManager.authenticate(userNamePasswordAuthenticationToken);
			System.out.println("Auth :" + authentication);
		} catch(Exception e) {
			throw new ControllerException(HttpStatus.UNAUTHORIZED, "wrong username and/or wrong password");
		}

		if (authentication != null && authentication.isAuthenticated()) {
			String tokens = jwtTokenService.createTokens(authentication);
			return new ResponseEntity(tokens, HttpStatus.OK);
		}
		throw new ControllerException(HttpStatus.UNAUTHORIZED, "wrong username and/or wrong password");
	}


	@CrossOrigin
    @PatchMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserAccountById(Authentication authentication, @RequestBody UserAccountDto userAccountUpdated) throws ServiceException, ControllerException, InvalidTokenException {
		Long userAccountId = checkAuthentication(authentication);

		UserAccountDto userAccount = this.userAccountService.getById(userAccountId);
		boolean userNameAlreadyExisting = this.userAccountService.verifyByUserName(userAccountUpdated.getUserName());
		boolean emailAlreadyExisting = this.userAccountService.verifyByEmail(userAccountUpdated.getEmail());
		if (userNameAlreadyExisting && emailAlreadyExisting){
			throw new ControllerException(HttpStatus.CONFLICT, "username & email already taken");
		} else if (userNameAlreadyExisting){
			throw new ControllerException(HttpStatus.CONFLICT, "username already taken");
		} else if (emailAlreadyExisting) {
			throw new ControllerException(HttpStatus.CONFLICT, "email already taken");
		} else {

			if (userAccountUpdated.getUserName() != null && !userAccountUpdated.getUserName().equals("")) {
				userAccount.setUserName(userAccountUpdated.getUserName());
			}
			if (userAccountUpdated.getEmail() != null && !userAccountUpdated.getEmail().equals("")) {
				userAccount.setEmail(userAccountUpdated.getEmail());
			}
			if (userAccountUpdated.getPassword() != null && !userAccountUpdated.getPassword().equals("")) {
                String encodePassword = this.bCryptPasswordEncoder.encode(userAccountUpdated.getPassword());
                userAccount.setPassword(encodePassword);
			}
			if (userAccountUpdated.getLocation() != null) {
				userAccount.setLocation(userAccountUpdated.getLocation());
			}

			this.userAccountService.update(userAccount);
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@CrossOrigin
	@PatchMapping(value = "/profile_picture")
	public ResponseEntity<?> setProfilePicture(Authentication authentication, @RequestParam("file") MultipartFile file) throws IOException, ServiceException, InvalidTokenException {
		Long userAccountId = checkAuthentication(authentication);

		if (file.isEmpty()) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("File is empty");
		}

		String filename = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(filename);
		String filenameWithoutExtension = FilenameUtils.removeExtension(filename);

		FormatDto format = new FormatDto();
		try {
			format = formatService.getFormatByName(extension);
		} catch (ServiceException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Format not supported");
		}

		PhotoForUserAccountDto photo = new PhotoForUserAccountDto();
		photo.setFormat(format);
		photo.setTitle(filenameWithoutExtension);
		photo.setPhotoBytes(file.getBytes());

		UserAccountDto userAccountDto = userAccountService.getById(userAccountId);
		if(userAccountDto.getProfilePhoto() != null) {
			Long profilePictureId = userAccountDto.getProfilePhoto().getPhotoId();
			userAccountService.deleteByProfilePicture(userAccountId, profilePictureId);
		}
		userAccountDto.setProfilePhoto(photo);

		userAccountService.update(userAccountDto);
		return new ResponseEntity(HttpStatus.OK);
	}

	@CrossOrigin
	@PatchMapping(value = "/archive")
	public ResponseEntity<?> archiveUserAccountById(Authentication authentication) throws ServiceException, InvalidTokenException {
		Long userAccountId = checkAuthentication(authentication);

		UserAccountDto userAccount = this.userAccountService.getById(userAccountId);
		userAccount.setArchived(true);
		this.userAccountService.update(userAccount);
		return new ResponseEntity(HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(value = "/")
	public ResponseEntity<?> deleteUserAccountById(Authentication authentication) throws ServiceException, InvalidTokenException {
		Long userAccountId = checkAuthentication(authentication);

		this.userAccountService.deleteById(userAccountId);
		return new ResponseEntity(HttpStatus.OK);
	}


	public Long checkAuthentication(Authentication authentication) throws InvalidTokenException {
		if(authentication == null){
			throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		Long userAccountId = ((MyUserDetails) userDetails).getUserDetailsId();
		return userAccountId;
	}

	public Long checkAuthenticationAndPhoto(Authentication authentication, Long id) throws InvalidTokenException, ServiceException {
		Long userAccountId = checkAuthentication(authentication);
		List<Long> userPhotosIdList= new ArrayList<>();

		userAccountService.getById(userAccountId).getPhotos().forEach(photo -> userPhotosIdList.add(photo.getPhotoId()));

		if(!userPhotosIdList.contains(id)) {
			throw new InvalidTokenException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		return userAccountId;
	}


}
