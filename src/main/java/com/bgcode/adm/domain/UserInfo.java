package com.bgcode.adm.domain;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UserInfo extends User {

	private static final long serialVersionUID = 1L;

	private int logincount;

	private Date lastDate ;
	
	private String mobile;
	
	private  String email;
	

	public UserInfo(String username, String password,boolean enabled,boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,Date lastDate, int logincount) {
		super(username, password,enabled ,true ,true ,accountNonLocked ,authorities);
		this.lastDate = lastDate ;
		this.logincount=logincount;
		//this.email = email ;

	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	public String getEmail() {
		return email;
	}
	
	public int getLogincount() {
		return logincount;
	}
	

	public void setLogincount(int logincount) {
		this.logincount = logincount;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Date getLastDate() {
		return lastDate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append(": ");
		sb.append(this.isAccountNonLocked()).append(" 时间: ").append(this.getLastDate())
		.append(" 次数 ：").append(this.getLogincount());
		return sb.toString() ;
	}
}
