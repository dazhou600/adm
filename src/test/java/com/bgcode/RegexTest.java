package com.bgcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	// <img.*src="[^"][a-zA-Z:/;0-9,+=]+\"[^>]*>
	public static void main(String args[]) {
		// 要验证的字符串
		String str = "bai有匹配正则ke.xsoftlab.jPg";
		// 正则表达式规则
		String regEx = "^([\u0391-\uFFE5A-Za-z0-9-_.]+)\\.(?:jpg|png|gif)$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);
		// 查找字符串中是否有匹配正则表达式的字符/字符串
		//boolean rs = matcher.find();
		//System.out.println(str);
		while(matcher.find()) {
			System.out.println(matcher.group());
		}
	}

}
