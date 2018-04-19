package com.bgcode.adm.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bgcode.websoketmsg.entity.Msg;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRepositoryTest {

	@Autowired
	MessageRepository repository;
	
	@Test
	public void test() {
		
	List<Msg> msgs=repository.findAll();
	assertEquals(msgs.size(),3);
	System.out.println(msgs);
	}

}
