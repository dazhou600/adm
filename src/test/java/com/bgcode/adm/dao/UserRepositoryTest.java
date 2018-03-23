/**
 * 
 */
package com.bgcode.adm.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.bgcode.Sufiguration;
import com.bgcode.adm.domain.Duty;

import javax.sql.DataSource;

/**
 * @author dada
 *
 */
public class UserRepositoryTest {
	
	private JdbcTemplate jdbcTemplate;
	private static DataSource ds;//new DriverManagerDataSource() ;
	Sufiguration conf ;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 //ds = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		//Class.forName("com.mysql.jdbc.Driver");
//		((DriverManagerDataSource) ds).setDriverClassName("com.mysql.jdbc.Driver");
//		((AbstractDriverBasedDataSource) ds).setUrl("jdbc:mysql://localhost:3306/bingge?characterEncoding=utf8&useSSL=false");
//		((AbstractDriverBasedDataSource) ds).setUsername("root");
//		((AbstractDriverBasedDataSource) ds).setPassword("123456");
		ds = new Sufiguration().dataSourcea();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		jdbcTemplate=new JdbcTemplate(ds);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Duty user = new Duty() ;
		user.setId("13456");
		user.setPassword("123456");
		//UserRepository dao = new UserRepository(jdbcTemplate) ;
		//dao.addUser(user);
	}

}
