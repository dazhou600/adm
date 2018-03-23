package com.bgcode.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Digits;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;

public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Length(min = 4, max = 32, message = "长度必须介于 4 和 32 之间")
	@Column(name="rname")
	protected String name;

	@Digits(fraction = 0, integer = 2147483640)
	@Column(name="isactive")
	protected int stater;

	@Length(min = 3, max = 32, message = "长度必须介于 3 和 32 之间")
	protected String remark;

	@Length(min = 5, max = 32, message = "长度必须介于 5 和 32 之间")
	protected String uid;

	protected Date lastTime;

	public BaseEntity() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStater() {
		return stater;
	}

	public void setStater(int stater) {
		this.stater = stater;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this);

	}
}
