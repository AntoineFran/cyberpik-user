package com.cda.cyberpik.controller;

import java.util.List;
import java.util.Map;

import com.cda.cyberpik.exception.ControllerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cda.cyberpik.dto.user.account.dto.UserAccountDto;
import com.cda.cyberpik.exception.ServiceException;
import com.cda.cyberpik.service.UserAccountService;

@RestController
@RequestMapping("/cyberpik/user_accounts")
public class UserAccountController {

	@Autowired
	UserAccountService userAccountService;

//    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@CrossOrigin
	@GetMapping(value = {"", "/"})
	public ResponseEntity<List<UserAccountDto>> findAllUserAccounts() {
		return new ResponseEntity(this.userAccountService.getAll(), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/{user_account_id}")
	public ResponseEntity<?> findUserAccountById(@PathVariable("user_account_id") Long userAccountId)
			throws ServiceException {
		return new ResponseEntity(this.userAccountService.getById(userAccountId), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = {"", "/"}, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createNewUserAccount(@RequestBody UserAccountDto userAccount) throws ControllerException {
//       String encodePassword = this.bCryptPasswordEncoder.encode(userAccount.getPassword());
//       userAccount.setPassword(encodePassword);
		boolean userNameAlreadyExisting = this.userAccountService.getByUserName(userAccount.getUserName());
		boolean emailAlreadyExisting = this.userAccountService.getByEmail(userAccount.getEmail());
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
	public ResponseEntity<UserAccountDto> findUserAccountByEmailAndPassword(@RequestBody Map<String, String> emailPassword) throws ControllerException {
		UserAccountDto userAccount;
		try {
			 userAccount = this.userAccountService.getByEmailAndPassword(emailPassword.get("email"), emailPassword.get("password"));
		} catch (ServiceException e) {
			throw new ControllerException(HttpStatus.UNAUTHORIZED, "wrong email and/or wrong password");
		}
		return new ResponseEntity(userAccount, HttpStatus.OK);
	}

	@CrossOrigin
    @PatchMapping(value = "/{user_account_id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUserAccountById(@PathVariable("user_account_id") Long userAccountId, @RequestBody UserAccountDto userAccountUpdated) throws ServiceException, ControllerException {
		UserAccountDto userAccount = this.userAccountService.getById(userAccountId);
		boolean userNameAlreadyExisting = this.userAccountService.getByUserName(userAccountUpdated.getUserName());
		boolean emailAlreadyExisting = this.userAccountService.getByEmail(userAccountUpdated.getEmail());
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
				userAccount.setPassword(userAccountUpdated.getPassword());
			}
			if (userAccountUpdated.getLocation() != null) {
				userAccount.setLocation(userAccountUpdated.getLocation());
			}
			if (userAccountUpdated.isEnableNewsletter() != userAccount.isEnableNewsletter()) {
				userAccount.setEnableNewsletter(userAccountUpdated.isEnableNewsletter());
			}
			if (userAccountUpdated.getProfilePhoto() != null) {
				userAccount.setProfilePhoto(userAccountUpdated.getProfilePhoto());
			}
			System.out.println(userAccount);
			this.userAccountService.update(userAccount);
			return new ResponseEntity(HttpStatus.OK);
		}
	}

	@CrossOrigin
	@PatchMapping(value = "/archive/{user_account_id}")
	public ResponseEntity<?> archiveUserAccountById(@PathVariable("user_account_id") Long userAccountId) throws ServiceException {
		UserAccountDto userAccount = this.userAccountService.getById(userAccountId);
		userAccount.setArchived(true);
		this.userAccountService.update(userAccount);
		return new ResponseEntity(HttpStatus.OK);
	}


	@CrossOrigin
	@DeleteMapping(value = "/{user_account_id}")
	public ResponseEntity<?> deleteUserAccountById(@PathVariable("user_account_id") Long userAccountId)
			throws ServiceException {
		this.userAccountService.deleteById(userAccountId);
		return new ResponseEntity(HttpStatus.OK);
	}

}
