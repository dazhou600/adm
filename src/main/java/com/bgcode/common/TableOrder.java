package com.bgcode.common;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TableOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull(message = "排序规则不能为空!")
	private String dir;
	@Min(value=0)
	private int column;
	private String columnStr;
	private String[] columnS = { "id", "title", "auth", "createBy", "createDate", "articleContents" };

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

	public String getColumnStr() {
		return (column > columnS.length) ? columnS[columnS.length] : columnS[column];
	}

	public void setColumnStr(String columnStr) {
		this.columnStr = columnStr;
	}

	public String[] getColumnS() {
		return columnS;
	}

}
