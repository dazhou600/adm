package com.bgcode.adm.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.bgcode.Sufiguration;
import com.mysql.jdbc.CallableStatement;

public class JdbcIvkProcedureTest {
	private JdbcTemplate jdbcTemplate;
	//new DriverManagerDataSource() ;
	private static DataSource ds=new Sufiguration().dataSourcea();

	@Before
	public void setUp() throws Exception {
		jdbcTemplate=new JdbcTemplate(ds);

	}
	//用JDBC操作存储函数
	@Test
	public void test() {
		final String procedure = "{CALL delSubNode(?,?)}";
		 List<SqlParameter> params = new ArrayList<SqlParameter>();  
		    params.add(new SqlParameter("type_id", Types.INTEGER));  
		    params.add(new SqlOutParameter("errnb", Types.INTEGER));  
		    Map<String, Object> outValues =	jdbcTemplate.call(
		    		new CallableStatementCreator() {  
		    	        @Override  
		    	        public CallableStatement createCallableStatement(Connection conn) throws SQLException {  
		    	          CallableStatement cstmt = (CallableStatement) conn.prepareCall(procedure);  
		    	          cstmt.registerOutParameter(2, Types.INTEGER);  
		    	          cstmt.setInt("type_id", 83); 
		    	          return cstmt;  
		    	    }}, params);
		    System.out.println(outValues.get("errnb")); 
	}

}
