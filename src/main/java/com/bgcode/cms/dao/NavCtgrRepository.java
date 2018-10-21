package com.bgcode.cms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bgcode.cms.entity.NavCtgr;

@Repository
public interface NavCtgrRepository extends JpaRepository<NavCtgr, Integer>, NavCtgrPrcdu {

	@Query(value = "SELECT bid,rname,href,CountLayer(bid) level,lft,rgt FROM nav_bar where lft<>1 order by lft ", nativeQuery = true)
	public List<NavCtgr> findNav();

	// @Query(value="call addSubnode(?1,?2,?3)", nativeQuery = true)
	// public void addNav(int pid,int pst,String rname);

	// @Query(value="select f_delNode(?1)", nativeQuery = true)
	// public int delNav(int
	// bid);//调用存储过程没返回值ResultSetImpl.next报异常if(!reallyResult())

}
