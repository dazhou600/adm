package com.bgcode.common;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class PageParam {
	private int draw;
	private int start;
	private int length;
	@Length(min = 1, max = 32, message = "标题必须介于 2 和 32 之间")
	private String title;
	@Length(min = 1, max = 32, message = "  作者必须介于 2 和 32 之间")
	private String auth;
	@NotNull(message = "排序规则不能为空!")
	private String dir;
	@Min(value=0)
	@Max(value=20)
	private int column;
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

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	

}
