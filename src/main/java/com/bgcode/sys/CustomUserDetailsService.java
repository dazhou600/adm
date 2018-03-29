package com.bgcode.sys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Component;

import com.bgcode.adm.dao.UserRepository;
import com.bgcode.adm.domain.UserInfo;
import com.bgcode.listener.LoginFailureListener;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	private JdbcTemplate jdbcTemplate;
	
	public static final Long LOCKTIME = 28800000L;

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	//@Autowired
   private HttpServletRequest request ;  
	
	public CustomUserDetailsService(JdbcTemplate jdbcTemplate,HttpServletRequest request) {
		this.jdbcTemplate = jdbcTemplate;
		this.request=request;
	}
    
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<UserInfo> users = jdbcTemplate
				.query("SELECT u_name , password , isactive, last_update , fails_count , accountNonLocked"
						+ " FROM duty where u_name=?", new String[] { userName }, new RowMapper<UserInfo>() {
							@Override
							public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
								String username = rs.getString(1);
								String password = rs.getString(2);
								boolean enabled = rs.getBoolean(3);
								Date last_update = rs.getTimestamp(4);
								int fails_count = rs.getInt(5);
								boolean accountNonLocked = rs.getBoolean(6);

								return new UserInfo(username, password, enabled, accountNonLocked,
										AuthorityUtils.NO_AUTHORITIES, last_update, fails_count);
							}

						});
		if (users.size() == 0) {
			throw new UsernameNotFoundException(this.messages.getMessage("JdbcDaoImpl.noAuthority",
					new Object[] { userName }, "User {0} has no GrantedAuthority"));
		}

		UserInfo user = users.get(0); // contains no GrantedAuthority[]

		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

		dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));

		dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));

		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

		if (dbAuths.size() == 0) {
			throw new UsernameNotFoundException("没有权限!");
		}
		boolean accountNonLocked=user.isAccountNonLocked() ;
		if((user.getLastDate().getTime() +LOCKTIME) <= System.currentTimeMillis()){
			user.setLogincount(0);
			accountNonLocked=true;
			System.out.println("*****超过8小时解锁、清零！*******");
		}
//		if(user.getLogincount()>4){
//			accountNonLocked=false ;
//		}
		//user.setLastDate(new Date());//更新最后登录时间
		//System.out.println("********"+user);
		//System.out.println("********"+request);
		request.getSession().setAttribute("failsCount", user.getLogincount());
		//request.getSession().setAttribute("lastUpdate", user.getLastDate());		
//		jdbcTemplate.update("UPDATE duty SET accountNonLocked=?, fails_count=?, last_update=? WHERE u_name=?"
//, accountNonLocked,user.getLogincount(),user.getLastDate(),user.getUsername());//更新数据库
		return new UserInfo(user.getUsername(), user.getPassword(), user.isEnabled(), accountNonLocked,
				dbAuths, user.getLastDate(), user.getLogincount());

	}

	private List<GrantedAuthority> loadUserAuthorities(String username) {
		return jdbcTemplate.query("SELECT u_name ,r_name FROM user_role where u_name=?", new String[] { username },
				new RowMapper<GrantedAuthority>() {
					@Override
					public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
						String roleName = rs.getString(2);

						return new SimpleGrantedAuthority(roleName);
					}
				});
	}

	private List<GrantedAuthority> loadGroupAuthorities(String username) {
		return jdbcTemplate.query("SELECT o_id,u_name,operat FROM operatiom where u_name=?", new String[] { username },
				new RowMapper<GrantedAuthority>() {
					@Override
					public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
						String roleName = rs.getString(3);
						return new SimpleGrantedAuthority(roleName);
					}
				});
	}
}
