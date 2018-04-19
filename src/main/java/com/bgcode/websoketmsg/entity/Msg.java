package com.bgcode.websoketmsg.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
@Entity
@Table(name="messagee")
public class Msg implements Serializable{

	private static final long serialVersionUID = 1701282076189694918L;

	@Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
	private Long id;
	
	@Column(name="uname")
	private String user;
	
	@Column(name="content")
	private String message;
	
	@Column(name="createtime")
	private Date timestamp;
	
	public Msg(){}

	public Msg(String user, String message, Date timestamp) {
		this.user = user;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);

	}
	
}
