package com.bgcode.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

import com.bgcode.entity.BaseEntity;

@Entity
@Table(name = "nav_bar")
public class NavCtgr  {
	@Id
	@Column(name = "bid")
	protected int bid;

	@Length(min=5,max=256,message="地址超过默认长度5-256之间")
	@Column(name = "href")
	protected String href;

	@Column(name = "level")
	protected int level;
	
	@Digits(fraction = 0, integer = 2147483640)
	@Column(name="lft")
	protected int lft;
	
	@Digits(fraction = 0, integer = 2147483640)
	@Column(name="rgt")
	protected int rgt;
	
	@Length(min = 4, max = 32, message = "长度必须介于 4 和 32 之间")
	@Column(name="rname")
	protected String rname;

	public NavCtgr() {
		super();
	}
	
	public NavCtgr(int bid,String name) {
		super();
		this.rname=name;
		this.bid = bid;
	}
	public NavCtgr(int bid,String name, String href, int level,int lft,int rgt) {
		super();
		this.rname=name;
		this.bid = bid;
		this.href = href;
		this.level = level;
		this.lft=lft;
		this.rgt=rgt;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLft() {
		return lft;
	}

	public void setLft(int lft) {
		this.lft = lft;
	}

	public int getRgt() {
		return rgt;
	}

	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
