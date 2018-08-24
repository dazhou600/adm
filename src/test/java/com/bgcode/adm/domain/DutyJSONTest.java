package com.bgcode.adm.domain;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DutyJSONTest {

	private static Validator validator;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	//@Test
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
	

	//@Before
	public void setUp() throws Exception {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		 validator = factory.getValidator();
		assertNotNull(validator);
	}

	@Test
	public void JSONTest() throws Exception {
		Duty bean =new Duty();
		bean.setId("11111111111");
		bean.setMobile("2344");
		bean.setEmail("q2djfom");
	     Calendar rightNow = Calendar.getInstance();
	     Date da = new Date(rightNow.getTimeInMillis()+1000000L);
		bean.setCreatedate(da);
		
		Duty b2 =new Duty();
		b2.setId("11111111111");
		b2.setMobile("2344");
		b2.setEmail("q2djfom");
	     Calendar cc = Calendar.getInstance();
	     Date d = new Date(cc.getTimeInMillis()+1000000L);
		bean.setCreatedate(d);
		List<Duty> users = new ArrayList<Duty>();
		users.add(bean);
		users.add(b2);
		final ObjectMapper mapper = new ObjectMapper();
		//mapper.readValue(json, clazz); * 将json转化为对象
		//mapper.writeValueAsString(obj); * 将对象转化为json
       // Collection<T> collection =  mapper.readValue(json, getCollectionType(collectionClazz,clazz));
        System.out.println( mapper.writeValueAsString(users));
	}
	

//	 static ObjectMapper mapper = new ObjectMapper();
//
//	 @Test
//	public void JsonToObject() {
//		ArrayList<Duty> users=(ArrayList<Duty>) this.fromJson("[{\"name\":null,\"stater\":0,\"remark\":null,\"uid\":null,\"lastTime\":null,\"id\":\"11111111111\",\"mobile\":\"2344\",\"photo\":null,\"loginip\":null,\"password\":null,\"createdate\":1534418411616,\"email\":\"q2djfom\"},{\"name\":null,\"stater\":0,\"remark\":null,\"uid\":null,\"lastTime\":null,\"id\":\"11111111111\",\"mobile\":\"2344\",\"photo\":null,\"loginip\":null,\"password\":null,\"createdate\":null,\"email\":\"q2djfom\"}]", ArrayList.class, Duty.class);
//	System.out.println(users);
//	 }
/**
 * 将json对象转化为集合类型
 * @author yangwenkui
 * @time 2017年3月16日 下午2:57:15
 * @param json json对象
 * @param collectionClazz 具体的集合类的class，如：ArrayList.class
 * @param clazz 集合内存放的对象的class
 * @return
 * [{"name":null,"stater":0,"remark":null,"uid":null,"lastTime":null,"id":"11111111111","mobile":"2344","photo":null,"loginip":null,"password":null,"createdate":1534418411616,"email":"q2djfom"},{"name":null,"stater":0,"remark":null,"uid":null,"lastTime":null,"id":"11111111111","mobile":"2344","photo":null,"loginip":null,"password":null,"createdate":null,"email":"q2djfom"}]
 */
//@SuppressWarnings("rawtypes")
//public static <T> Collection<T> fromJson(String json,Class<? extends Collection> collectionClazz,Class<T> clazz){
//	if(json == null){
//        return null;
//    }
//    try {
//        Collection<T> collection =  mapper.readValue(json, getCollectionType(collectionClazz,clazz));
//        return collection;
//    } catch (Exception e) {
//    	System.out.println(e);
//        //logger.error(String.format("json=[%s]", json), e);
//    }
//    return null;
//}
//
//private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {   
//    return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
//}

}
