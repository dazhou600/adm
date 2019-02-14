package com.bgcode.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bgcode.adm.domain.UserInfo;
import com.bgcode.cms.dao.ArticleRepository;
import com.bgcode.cms.entity.Article;
import com.bgcode.common.PageParam;
@Service
public class ArticleSevice {
	@Autowired
	private ArticleRepository repo;
	
	public Page<Article> findArticlePage(PageParam param) {
        //规格定义
        Specification<Article> specification = new Specification<Article>() {

            /**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>(); //所有的断言
                if(StringUtils.isNotBlank(param.getTitle())){ //添加断言
                    Predicate likeTitle = cb.like(root.get("title").as(String.class),param.getTitle()+"%");
                    predicates.add(likeTitle);
                }
                if(StringUtils.isNotBlank(param.getAuth())){ //添加断言
                    Predicate likeAuth = cb.like(root.get("auth").as(String.class),param.getAuth()+"%");
                    predicates.add(likeAuth);
                }
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };
        //分页信息
        Pageable pageable = new PageRequest(param.getStart()/param.getLength(),param.getLength()); //页码：前端从1开始，jpa从0开始，做个转换
        //查询
        return this.repo.findAll(specification,pageable);
    }
	
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
