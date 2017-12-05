package com.bgcode.adm.domain;

import java.util.Date;


public class Menu {

	private Integer menuid;
	private int isactive;
	private String icon;
	private String permission;
	private Integer sort;
	private boolean isshow;
	private Date updatedate;
	private String createby;
	private String name;
	private String href;
	private Date createdate;
	private String updateby;
	private String remarks;

	public Menu(String name, String permission) {
		super();
		this.name = name;
		this.permission = permission;
	}
	
	public Menu() {
		super();
	}

	public Integer getMenuid() {
		return menuid;
	}

	public int getIsactive() {
		return isactive;
	}

	public String getIcon() {
		return icon;
	}

	public String getPermission() {
		return permission;
	}

	public Integer getSort() {
		return sort;
	}

	
	public boolean getIsshow() {
		return isshow;
	}

	
	public Date getUpdatedate() {
		return updatedate;
	}

	public String getCreateby() {
		return createby;
	}

	public String getName() {
		return name;
	}

	public String getHref() {
		return href;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public String getUpdateby() {
		return updateby;
	}

	public String getRemarks() {
		return remarks;
	}
	/*
	 * 
	 * set
	 * 
	 */

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public void setIsactive(int isactive) {
		this.isactive = isactive;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public void setCreateby(String createby) {
		this.createby = createby;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		return "Menu [menuid=" + menuid + ", isactive=" + isactive + ", icon=" + icon + ", permission=" + permission
				+ ", sort=" + sort + ", isshow=" + isshow + ", updatedate=" + updatedate + ", createby=" + createby
				+ ", name=" + name + ", href=" + href + ", createdate=" + createdate + ", updateby=" + updateby
				+ ", remarks=" + remarks + "]";
	}
}
