package com.bgcode.common;

import org.hibernate.validator.constraints.Length;

public class PageParam {
	private int draw;
	private int start;
	private int length;
	@Length(min = 1, max = 32, message = "标题必须介于 2 和 32 之间")
	private String title;
	@Length(min = 1, max = 32, message = "  作者必须介于 2 和 32 之间")
	private String auth;
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
