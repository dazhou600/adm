package com.bgcode.adm.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.jws.soap.SOAPBinding.Use;
import javax.validation.ConstraintViolation;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.test.context.junit4.SpringRunner;


public class UserInfoTest {

	@Test
	public void userExtTest() throws Exception {
//		UserInfo a = new UserInfo() ;
//		a.setMobile("1234567890");
//		System.out.println(a);
	}
	

	

	//@Test
	public void buildTest() throws Exception {
//		User user = new User("eeeee", "eeee", AuthorityUtils.NO_AUTHORITIES) ;
//		user.eraseCredentials();
		UserBuilder build = User.withUsername("qqqqqqq");
//		build.accountExpired(true);
//		build.disabled(true);
//		build.accountLocked(true);
		build.authorities(AuthorityUtils.NO_AUTHORITIES);
		build.credentialsExpired(true);
		build.password("rrrrrrrrr");
		//build.roles("UU","BB");
		User user = (User) build.build() ;
		System.out.println(user);
	}

}
