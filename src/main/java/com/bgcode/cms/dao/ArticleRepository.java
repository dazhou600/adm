package com.bgcode.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bgcode.cms.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	@Query(value="INSERT INTO cms_article(category_id, title, create_by, articleContents, allowcomments, statuse) VALUES (?1, ?2, ?3, ?4, 1, 2)",nativeQuery = true)
	public Article creatArticle(int categoruid,String title,String user,String contents);
}
