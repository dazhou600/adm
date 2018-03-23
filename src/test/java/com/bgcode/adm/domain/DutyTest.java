package com.bgcode.adm.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
import org.springframework.test.context.junit4.SpringRunner;


public class DutyTest {

	private static Validator validator;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void test() {
		
	//	Duty bean =new Duty(null,null,"iiiiiiii",null);
		Duty bean =new Duty();
		bean.setId("11111111111");
		bean.setMobile("2344");
		bean.setEmail("q2djfom");
	     Calendar rightNow = Calendar.getInstance();
	     Date da = new Date(rightNow.getTimeInMillis()+1000000L);
		bean.setCreatedate(da);
		Set<ConstraintViolation<Duty>> constraintViolations = validator.validate(bean);
		for (ConstraintViolation<Duty> constraintViolation : constraintViolations) {
			System.out.println(constraintViolation +" : "+constraintViolation.getMessage()); 
			//this.thrown.expect(IllegalArgumentException.class);
			//this.thrown.expectMessage("Username must not be empty");

			}
		System.out.println("^^^^^^^^^"+bean);
		assertEquals(3, constraintViolations.size()); 
		//assertEquals("错误", constraintViolations.iterator().next().getMessage());
	}
	

	@Before
	public void setUp() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 validator = factory.getValidator();
		assertNotNull(validator);
	}

	@After
	public void tearDown() throws Exception {
	}

}
