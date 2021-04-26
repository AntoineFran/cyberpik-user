package com.cda.cyberpik;

import com.cda.cyberpik.dao.IRepositoryPhoto;
import com.cda.cyberpik.dao.IRepositoryUserAccount;
import com.cda.cyberpik.entity.Photo;
import com.cda.cyberpik.entity.UserAccount;
import com.cda.cyberpik.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class CyberpikApplicationTests {
	@Autowired
	private IRepositoryPhoto photoDao;

	@Autowired
	private IRepositoryUserAccount userAccountDao;

/**
	@Test
	void contextLoads() {
		Optional<Photo> photo1 = photoDao.findById(5L);
		if (photo1.isPresent()){
			System.out.println(photo1.get().getPhotoId());
		}
	}

	@Test
	void test2() {

		Optional<UserAccount> op = this.userAccountDao.findById(5L);

		if(op.isPresent()){
			System.out.println(op.get().getUserAccountId());
		}
	}
**/


}
