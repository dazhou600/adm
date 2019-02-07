package com.bgcode.cms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

//@Entity
//@Table(name = "cms_article")
public class ArticleInfo  implements Serializable {

	@Length(min = 10, max = 83886, message = "文章内容介于 10 和 83886 之间")
	private String articleContents;
	@Length(min = 5, max = 128, message = "文章来源介于 5 和 128 之间")
	private String sourcing;

	@Min(value = -1, message = "状态(-1:不通过,0未审核,1:通过)")
	@Max(value = 1, message = "状态(-1:不通过,0未审核,1:通过)")
	private Byte statuse;

	// 点击数量
	private int hits;

	//权重，越大越靠前
	private int weight;
	
	 //是否允许评论(0:不允许,1:允许)
	private Byte allowcomments;

	private String image;

	private String keywords;

	private String description;
}
