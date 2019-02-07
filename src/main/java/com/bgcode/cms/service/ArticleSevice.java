package com.bgcode.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bgcode.adm.domain.Duty;
import com.bgcode.adm.domain.UserInfo;
import com.bgcode.cms.dao.ArticleRepository;
import com.bgcode.cms.dao.NavCtgrRepository;
import com.bgcode.cms.entity.Article;
import com.bgcode.cms.entity.NavCtgr;
@Service
public class ArticleSevice {
	@Autowired
	private ArticleRepository repo;
	
	//增加或修改文章
	public int saveArticle(Article article){
		UserInfo user = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		article.setCreateBy(user.getUsername());
		int count=0;
		if (article.getId() == null) {
			article.setCreateDate(new Date());
			count = repo.creatArticle(article.getTitle(),article.getAuth(),article.getCreateBy(),article.getArticleContents(),article.getCreateDate());
		}else{
			count = repo.upArticle(article.getTitle(),article.getAuth(),article.getCreateBy(),article.getCreateDate(),article.getArticleContents(),article.getId());
		}
		return count ;
		
	}
}
