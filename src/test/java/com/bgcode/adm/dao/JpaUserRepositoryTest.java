package com.bgcode.adm.dao;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;


import com.bgcode.adm.domain.Duty;
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaUserRepositoryTest {
	
	@Autowired
	UserRepository repository;

	@Test
	public void findall() {
		Iterable<Duty> it =  repository.findAll();
		int count = 0 ;
		Iterator<Duty> users = it.iterator() ;
		while(users.hasNext()){
			System.out.println(users.next());
			count++;
		}
		//it.forEach(System.out::println);
		assertEquals(8,count);
	}
	
	//@Test
	public void createUser() throws Exception{
		Duty entity = new Duty("nihadddddddddddllllllo") ;
		entity.setEmail("kkdkdk@345.com");
		//entity.setLastTime(lastTime);
		Duty user = null ;
		try{
		 user = repository.save(entity);
		}catch(Exception e){
			System.out.println("***************************"+e);

		}
		
		System.out.println(user);
	}
	
	@Test
	public void DeleteUser(){
	//	try{
//		repository.delete("33");
//		}catch(Exception e){
//			System.out.println("***************************"+e);
//
//		}
	}

}
