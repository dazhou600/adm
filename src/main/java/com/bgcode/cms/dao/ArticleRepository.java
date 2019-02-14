package com.bgcode.cms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bgcode.cms.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
	
	Page<Article> findAll(Specification<Article> spec, Pageable pageable);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="INSERT INTO cms_article(title,auth,create_by,contents,create_date, allowcomments, statuse) VALUES (?1, ?2, ?3, ?4, ?5, 1, 2)",nativeQuery = true)
	public int creatArticle(String title,String auth,String user,String contents,Date create_date);
	
	@Query(value="SELECT new Article(a.id,a.title,a.auth,a.createBy,a.createDate,a.articleContents) from Article a")
	public List<Article> findAll();
	
	@Query(value="SELECT new Article(a.id,a.title,a.auth,a.createBy,a.createDate,a.articleContents) from Article a where a.id=?1")
	public List<Article> findArticle(int aid);
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="UPDATE cms_article SET title=?1,auth=?2,create_by=?3,create_date=?4,contents=?5 WHERE aid=?6",nativeQuery = true)
	public int upArticle(String title,String auth,String createby,Date createdate,String contents,int id);
	
}

