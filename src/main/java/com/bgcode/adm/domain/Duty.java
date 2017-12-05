package com.bgcode.adm.domain;




import java.util.Date;
import java.util.Set;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Duty {

	private String id;
	private String name;
	private boolean isactive;
	private String mobile;
	private String photo;
	private java.util.Date logindate;
	private String remark;
	private String loginip;
	private String password;
	private java.util.Date expiredate;
	private java.util.Date lastupdate;
	private java.util.Date createdate;
	private String email;
	private Set<Role> rols;
	
	public Duty(){}

	public Duty(String id, String name,String email,String password,Set<Role> rols){
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.rols = rols;

	}
	
	public Duty(String id, String name, boolean isactive, String mobile, String photo, Date logindate, String remark,
			String loginip, String password, Date expiredate, Date lastupdate, Date createdate, String email) {
		super();
		this.id = id;
		this.name = name;
		this.isactive = isactive;
		this.mobile = mobile;
		this.photo = photo;
		this.logindate = logindate;
		this.remark = remark;
		this.loginip = loginip;
		this.password = password;
		this.expiredate = expiredate;
		this.lastupdate = lastupdate;
		this.createdate = createdate;
		this.email = email;
	}

	public Duty(String id, String name, boolean isactive, String mobile, String photo, Date logindate, String remark,
			String loginip, String password, Date expiredate, Date lastupdate, Date createdate, String email,
			Set<Role> rols) {
		super();
		this.id = id;
		this.name = name;
		this.isactive = isactive;
		this.mobile = mobile;
		this.photo = photo;
		this.logindate = logindate;
		this.remark = remark;
		this.loginip = loginip;
		this.password = password;
		this.expiredate = expiredate;
		this.lastupdate = lastupdate;
		this.createdate = createdate;
		this.email = email;
		this.rols = rols;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean getIsactive() {
		return isactive;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public java.util.Date getLogindate() {
		return logindate;
	}

	@Length(min = 5, max = 200, message = "备注长度必须介于 1 和 200 之间")
	public String getRemark() {
		return remark;
	}

	public String getLoginip() {
		return loginip;
	}

	@Length(min = 6, max = 200, message = "密码长度必须介于 6 和 16 之间")
	public String getPassword() {
		return password;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public java.util.Date getExpiredate() {
		return expiredate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public java.util.Date getLastupdate() {
		return lastupdate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public java.util.Date getCreatedate() {
		return createdate;
	}

	public String getEmail() {
		return email;
	}
	public Set<Role> getRols() {
		return rols;
	}

	public void setRols(Set<Role> rols) {
		this.rols = rols;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setLogindate(java.util.Date logindate) {
		this.logindate = logindate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setExpiredate(java.util.Date expiredate) {
		this.expiredate = expiredate;
	}

	public void setLastupdate(java.util.Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
