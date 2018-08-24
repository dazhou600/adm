package com.bgcode.cms.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bgcode.cms.entity.NavCtgr;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NavCtgrRepositoryTest {

	@Autowired
	NavCtgrRepository navdao ;
	
	@Test
	public void findNavTest() {

		List<NavCtgr> navs = navdao.findNav();
//		List<NavCtgr> navbar = new ArrayList<NavCtgr>() ;
		System.out.println("第 " + navs);
//		for(int i=0;i<navs.size();i++){
//			if(navs.get(i).getLevel()==1){
//				int m = 0;
//				navbar.set(m, navs.get(i));
//				m++;
//			}else if(navbar.get(i).getLevel()==2){
//				for(int j=0;j<navbar.size();j++){
//					if(navbar.get(j).getPid()==navs.get(i).getPid()){
//						
//					}
//				}
//			//}else if(navs.get(i).getLevel()==3){
//				
//			}
//		//System.out.println("List第" + i + "位是：" + nav);
//		}	
	}

}