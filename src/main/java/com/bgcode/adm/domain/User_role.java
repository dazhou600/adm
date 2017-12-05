package com.bgcode.adm.domain;

import java.sql.*;
import java.util.*;

public class User_role {

	private String user_id;
	private Integer role_id;


	public String getUser_id(){
		return user_id;
	}
	public Integer getRole_id(){
		return role_id;
	}
	public void setUser_id(String user_id){
		this.user_id=user_id;
	}
	public void setRole_id(Integer role_id){
		this.role_id=role_id;
	}
}
