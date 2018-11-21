package com.bgcode.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bgcode.cms.entity.NavCtgr;

@Repository
public interface NavCtgrRepository extends JpaRepository<NavCtgr, Integer>, NavCtgrPrcdu {

	@Query(value = "SELECT bid,rname,href,CountLayer(bid) level,lft,rgt FROM nav_bar  order by lft ", nativeQuery = true)
	public List<NavCtgr> findNav();
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value="update nav_bar set rname=?1, href=?2 where bid = ?3",nativeQuery = true)
	public int updateNav(String rname,String href,Integer bid);

	// @Query(value="call addSubnode(?1,?2,?3)", nativeQuery = true)
	// public void addNav(int pid,int pst,String rname);

	// @Query(value="select f_delNode(?1)", nativeQuery = true)
	// public int delNav(int
	// bid);//调用存储过程没返回值ResultSetImpl.next报异常if(!reallyResult())

}
