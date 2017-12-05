package com.bgcode.adm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bgcode.adm.domain.Menu;


@Repository
public class MenuRepository {
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public MenuRepository(JdbcTemplate jdbcTemplate){
		
		this.jdbcTemplate= jdbcTemplate ;
		
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
