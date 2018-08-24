
package com.bgcode.adm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.bgcode.entity.BaseEntity;

@Entity
public class Duty extends BaseEntity {

	@Id
	@Length(min = 3, max = 32, message = "长度必须介于 3 和 32 之间")
	@Column(name="u_name")
	private String id;
	@Length(min = 6, max = 12, message = "请输入正确的电话号码")
	private String mobile;
	@Length(min = 3, max = 32, message = "长度必须介于 3 和 32 之间")
	private String photo;
	@Column(name="login_ip")
	private String loginip;
	@Length(min = 3, max = 100, message = "密码长度必须介于 3 和 100 之间")
	private String password;
	@Past
	@Column(name="create_date")
	private Date createdate;
	@Email
	private String email;
	@Digits(fraction = 0, integer = 2147483640)
	@Column(name="accountnonlocked")
	private int accountnonlocked;

	public Duty(){
	}
	
	public Duty(String id) {
		//super();
		this.id=id;
	}

	public Duty(String id,String password) {
		this(id);
		this.password=password;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public void setAccountnonlocked(int accountnonlocked) {
		this.accountnonlocked = accountnonlocked;
	}
}
