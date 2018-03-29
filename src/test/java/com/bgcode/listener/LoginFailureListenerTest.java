package com.bgcode.listener;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class LoginFailureListenerTest {
	//自定义jdbc3***1521648000000*********
	//自定义jdbc3***1521704371204*********
	//public static long LOCKTIME = 28800000l ;
	@Test
	public void digistTest(){
		if(NumberUtils.isDigits("\\u005")){
			System.out.println("是数字");

		}else {
			System.out.println("不是数字");

		}
	}
	//@Test
	public void compareTest() throws ParseException{
		SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		//Date b = sd.parse("2018-03-22 13:33:45");
		Date d = new Date(1521648000000l);
		Date c = new Date(1521704371204l);
		System.out.println(spdf.format(d));
		System.out.println(spdf.format(c));

		//long h= System.currentTimeMillis() ;
		//boolean  bb = d.getTime()+8l*3600*1000<System.currentTimeMillis();
		//boolean  ff = f.getTime()+LOCKTIME<System.currentTimeMillis();

		//Date c=new Date(System.currentTimeMillis());
		//System.out.println(f.getTime()+LOCKTIME);
		//System.out.println(System.currentTimeMillis());
		//System.out.println(sd.format(c));
		//System.out.println(8l*3600*1000);

	}
	
	//1521578146605
	//1521606946603
	//@Test
	public void currentTimeMillis() {
		 //System.out.println( System.currentTimeMillis());
		//System.out.println( new Date(System.currentTimeMillis()-8*3600*1000).getTime());
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		Date d1 = new Date(1521578146605l) ;

		Date d2 = new Date(1521606946603l) ;
		
		String str1 =sd.format(d1);
		String str2 =sd.format(d2);

		System.out.println(str1);
		System.out.println(str2);
		System.out.println(d2);
		System.out.println(8*3600*1000);
		Long lo=8l*3600*1000;
		if((lo&(lo-1l))==0)  //使用与运算判断一个数是否是2的幂次方  
			System.out.println("%d不是2的幂次方！\n");  
	    else  
	    	System.out.println("%d是2的%d次方！\n");  

	}
}
