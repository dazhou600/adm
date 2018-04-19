package com.bgcode.websoketmsg.entity;

public class MsgForm {

	private String text;
	
	private String type;
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
