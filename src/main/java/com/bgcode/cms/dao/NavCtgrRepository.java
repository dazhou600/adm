package com.bgcode.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bgcode.cms.entity.NavCtgr;

@Repository
public interface NavCtgrRepository extends JpaRepository<NavCtgr,Long>{

	@Query(value="SELECT bid,rname,href,CountLayer(bid) level,lft,rgt FROM nav_bar where lft<>1 order by lft ", nativeQuery = true)
	public List<NavCtgr> findNav();
	
}
