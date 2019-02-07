package com.bgcode.cms.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.util.HtmlUtils;
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
	@Length(min = 2, max = 32, message = "  作者必须介于 2 和 32 之间")
	private String auth ;
	@Column(name = "create_by")
	private String createBy ;
	@Past
	@Column(name = "create_date")
	private Date createDate;
	@Column(name="contents")
	@Length(min = 10, max = 83886, message = "文章内容介于 10 和 83886 之间")
	private String articleContents;
	
	public Article(){};
	public Article(Integer id, String title,String auth, String createBy, Date createDate,String articleContents) {
		super();
		this.id = id;
		this.title = title;
		this.auth=auth;
		this.createBy = createBy;
		this.createDate = createDate;
		this.articleContents=articleContents;
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

	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
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
	public String toString(){
		return "[\""+this.id+"\",\""+this.title+"\",\""+this.auth+"\",\""+this.createBy+"\",\""+this.createDate+"\",\""+HtmlUtils.htmlEscape(getArticleContents(), "UTF-8")+"\"]";
//		String contents = "无内容";
//		if(this.getArticleContents()!=null){
//		 contents = this.getArticleContents().indexOf("\"")!=-1?this.getArticleContents().replaceAll("\"", "\\\\\""):this.getArticleContents();
//		}
		//return "[\""+this.id+"\",\""+this.title+"\",\""+this.createBy+"\",\""+this.createDate+"\",\""+this.getArticleContents()+"\"]";
	}
}
