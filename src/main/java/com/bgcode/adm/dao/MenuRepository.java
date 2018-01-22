package com.bgcode.adm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.bgcode.adm.domain.Menu;


@Repository
//@CacheConfig(cacheNames = "menus")
public class MenuRepository {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public MenuRepository(JdbcTemplate jdbcTemplate){
		
		this.jdbcTemplate= jdbcTemplate ;
		
	}
	//获得菜单位置
	@Cacheable(value="sort")
	public int findSortByMenuId(String id){
		SqlRowSet set = jdbcTemplate.queryForRowSet("SELECT sort FROM menu where menu_id='"+id+"'");
		if(set.next()){
		int l = set.getInt("sort");
		//System.out .println("^^^^^^^^^^^^^^^^"+l);
		return l;
		}
		return 0 ;
	}
	
	public List<Menu> findByUser(String name){
		String sql="SELECT distinct u.u_name uname, m.permission permission "
				+ "FROM duty u, menu m, role r, role_menu rm, user_role ur "
				+ "where u.u_name=? and u.u_id=ur.user_id and ur.role_id=r.r_id "
				+ "and r.r_id=rm.role_id and rm.menu_id=m.menu_id";
		
		return 	jdbcTemplate.query(sql, new DutyRowMapper(),name);
		
	}
	
	private static final class DutyRowMapper implements RowMapper<Menu>{

		@Override
		public Menu mapRow(ResultSet rs, int rowNum) throws SQLException {

			Menu menu =  new Menu(rs.getString("name"),rs.getString("permission"));
			return menu;
		}
		
	}
}
