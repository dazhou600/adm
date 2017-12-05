package com.bgcode.adm.domain;

import java.util.Date;
import java.util.Set;

public class Role {
	private Integer rid;
	private String rname;
	private boolean isactive;
	private Integer datascope;
	private String rtype;
	private String remarks;
	private String createuser;
	private Date createdate;
	private String updateuser;
	private Date updatedate;
	private Set<Menu> menus;

	public Role() {
		super();
	}



	public Role(Integer rid, String rname) {
		super();
		this.rid = rid;
		this.rname = rname;
	}



	public Role(String rname, boolean isactive, Integer datascope, String rtype, String remarks, String createuser,
			Date createdate, String updateuser, Date updatedate) {
		super();
		this.rname = rname;
		this.isactive = isactive;
		this.datascope = datascope;
		this.rtype = rtype;
		this.remarks = remarks;
		this.createuser = createuser;
		this.createdate = createdate;
		this.updateuser = updateuser;
		this.updatedate = updatedate;
	}


	public Integer getRid() {
		return rid;
	}

	public boolean getIsactive() {
		return isactive;
	}

	public String getRname() {
		return rname;
	}

	public Integer getDatascope() {
		return datascope;
	}

	public String getRtype() {
		return rtype;
	}

	public String getRemarks() {
		return remarks;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public String getUpdateuser() {
		return updateuser;
	}

	public String getCreateuser() {
		return createuser;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	/*
	 * 
	 * 
	 * set
	 * 
	 * 
	 */
	
	public void setMenu(Set<Menu> menus) {
		this.menus = menus;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public void setDatascope(Integer datascope) {
		this.datascope = datascope;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setUpdatedate(java.util.Date updatedate) {
		this.updatedate = updatedate;
	}

	public void setUpdateuser(String updateuser) {
		this.updateuser = updateuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

}
