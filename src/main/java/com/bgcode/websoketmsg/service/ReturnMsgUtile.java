package com.bgcode.websoketmsg.service;

import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReturnMsgUtile {
/**
 * 绑定值错误提示信息
 * @return null 没有错误
 */	
 public static String extrMsg(BindingResult result){
	 if (result!=null&&result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			StringBuilder sb = new StringBuilder();
			sb.append("有" + errors.size() + "错误：");
			for (int i = 0; i < errors.size(); i++) {
				sb.append(i + 1);
				sb.append(errors.get(i).getDefaultMessage());
				if (i + 1 < errors.size()) {
					sb.append(",");
				}
			}
			return sb.toString();
		}
	 return null;
 }
 public static String toJSONMsg(Map<String, String> msgMap){
	ObjectMapper mapper = new ObjectMapper();
	try {
		return mapper.writeValueAsString(msgMap);
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
	 return null; 
 }
}
