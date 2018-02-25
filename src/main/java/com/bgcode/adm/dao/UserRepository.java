package com.bgcode.adm.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bgcode.adm.domain.Duty;

@Repository
public class UserRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	private static final String SQL_INSERTE_USER = "INSERT INTO duty (u_name, password) VALUES (?,?)" ;
	
	@Autowired
	public UserRepository(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate= jdbcTemplate ;
	}
	
	public void addUser(Duty user){
//		Map<String,Object> paramMap = new HashMap<String,Object>() ;
//		paramMap.put("u_name", user.getId());
//		paramMap.put("password", user.getPassword());
		jdbcTemplate.update(SQL_INSERTE_USER, user.getId(),user.getPassword());
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

	public void save(Duty user) {
		// TODO Auto-generated method stub
		
	}
}
