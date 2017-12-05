package com.bgcode.adm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bgcode.adm.domain.Duty;

@Repository
public class UserRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate){
		
		this.jdbcTemplate= jdbcTemplate ;
		
	}
	
	public Duty findByNamePswd(){
		String sql="SELECT u_id , u_name FROM product.duty where u_name='姓名3' and password = '密码3' ";
		return null;
	}
	
	public List<Duty> findAll(){
		String sql="SELECT u_id id,u_name name,password,isactive,photo,email,mobile,"
				+ "remark,login_ip,login_date,expire_date,create_date,last_update "
				+ "FROM product.duty";
		return 	jdbcTemplate.query(sql, new DutyRowMapper());
		
	}
	
	private static final class DutyRowMapper implements RowMapper<Duty>{

		@Override
		public Duty mapRow(ResultSet rs, int rowNum) throws SQLException {

			Duty user = new Duty(rs.getString("id"),rs.getString("name"),rs.getBoolean("isactive"), rs.getString("mobile"),rs.getString("photo"),rs.getDate("login_date"), rs.getString("remark"),
					rs.getString("login_ip"),rs.getString("password"),rs.getDate("expire_date"),rs.getDate("last_update"),rs.getDate("create_date"),rs.getString("email"));
			return user;
		}
		
	}
}
