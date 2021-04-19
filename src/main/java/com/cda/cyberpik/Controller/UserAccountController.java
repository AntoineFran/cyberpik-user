package com.cda.cyberpik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	@PostMapping(value = {"", "/"})
	public ResponseEntity<?> createNewUserAccount(@RequestBody UserAccountDto userAccount) {
//       String encodePassword = this.bCryptPasswordEncoder.encode(userAccount.getPassword());
//       userAccount.setPassword(encodePassword);
		this.userAccountService.add(userAccount);
		return new ResponseEntity(HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/{user_account_id}")
	public ResponseEntity<?> findUserAccountById(@PathVariable("user_account_id") Long userAccountId)
			throws ServiceException {
		return new ResponseEntity(this.userAccountService.getById(userAccountId), HttpStatus.OK);
	}

	@CrossOrigin
    @PatchMapping(value = "/{user_account_id}")
    public ResponseEntity<?> updateUserAccountById(@PathVariable("user_account_id") Long userAccountId, @RequestBody UserAccountDto userAccountUpdated) throws ServiceException {
		UserAccountDto userAccount;
		userAccount = this.userAccountService.getById(userAccountId);
		
		if (userAccountUpdated.getEmail() != null && !userAccountUpdated.getEmail().equals("")) {
			userAccount.setEmail(userAccountUpdated.getEmail());
		}
		if (userAccountUpdated.getPassword() != null && !userAccountUpdated.getPassword().equals("")) {
			userAccount.setPassword(userAccountUpdated.getPassword());
		}
		if (userAccountUpdated.getUserName() != null && !userAccountUpdated.getUserName().equals("")) {
			userAccount.setUserName(userAccountUpdated.getUserName());
		} 
		if (userAccountUpdated.getCity() != null) {
			userAccount.setCity(userAccountUpdated.getCity());
		}

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
