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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
@Entity
@Table(name = "cms_article")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "aid")
	private Integer id; 
	
	@Column(name = "category_id")
	private int categoryId;
	@NotNull(message = "没有标题!")
	@Length(min = 2, max = 32, message = "标题必须介于 2 和 32 之间")
	private String title;
	@NotNull(message = "作者为空!")
	@Length(min = 3, max = 32, message = "  作者必须介于 3 和 32 之间")
	@Column(name = "create_by")
	private String createBy ;
	@Past
	@Column(name = "create_date")
	private Date createDate;
	
	@Length(min = 10, max = 83886, message = "文章内容介于 10 和 83886 之间")
	private String articleContents;
	@Length(min = 5, max = 128, message = "文章来源介于 5 和 128 之间")
	private String sourcing;

	@Min(value = -1, message = "状态(-1:不通过,0未审核,1:通过)")
	@Max(value = 1, message = "状态(-1:不通过,0未审核,1:通过)")
	private Byte statuse;

	/**
	 * 点击数量
	 */

	private int hits;

	/**
	 * 权重，越大越靠前
	 */
	private int weight;

	/**
	 * 是否允许评论(0:不允许,1:允许)
	 */
	private Byte allowcomments;

	private String image;

	private String keywords;

	private String description;
	public Article(){};
	public Article(Integer id, String title, String createBy, Date createDate) {
		super();
		this.id = id;
		this.title = title;
		this.createBy = createBy;
		this.createDate = createDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer articleId) {
		this.id = articleId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getArticleContents() {
		return articleContents;
	}

	public void setArticleContents(String articleContents) {
		this.articleContents = articleContents;
	}

	public String getSourcing() {
		return sourcing;
	}

	public void setSourcing(String sourcing) {
		this.sourcing = sourcing;
	}

	public Byte getStatuse() {
		return statuse;
	}

	public void setStatuse(Byte statuse) {
		this.statuse = statuse;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Byte getAllowcomments() {
		return allowcomments;
	}

	public void setAllowcomments(Byte allowcomments) {
		this.allowcomments = allowcomments;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
