package com.bgcode.adm.domain;


public class Syslog {

	private String exception;
	private Integer syslog_id;
	private String u_id;
	private String method;
	private String remark;
	private String params;
	private String uip;
	private String request_uri;
	private String user_agent;

	public String getException() {
		return exception;
	}

	public Integer getSyslog_id() {
		return syslog_id;
	}

	public String getU_id() {
		return u_id;
	}

	public String getMethod() {
		return method;
	}

	public String getRemark() {
		return remark;
	}

	public String getParams() {
		return params;
	}

	public String getUip() {
		return uip;
	}

	public String getRequest_uri() {
		return request_uri;
	}

	public String getUser_agent() {
		return user_agent;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
