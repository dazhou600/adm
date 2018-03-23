package com.bgcode.listener;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.stereotype.Service;

@Service
public class LoginFailureListener {

	protected final Log logger = LogFactory.getLog(LoginFailureListener.class);

	private JdbcTemplate jdbcTemplate;
	
	private HttpServletRequest request;
      
	public static final Long LOCKTIME = 28800000L;

	public LoginFailureListener(JdbcTemplate jdbcTemplate,HttpServletRequest request) {
		this.jdbcTemplate = jdbcTemplate;
		this.request=request;
	}

	@EventListener
	public void listenUserRegisterEvent(AuthenticationFailureBadCredentialsEvent event) {
		//SimpleDateFormat spdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		
		jdbcTemplate.update("INSERT INTO login_log (u_name, last_update, fails_count, login_ip,userstatus) VALUES (?,?,?,?,?)",event.getAuthentication().getPrincipal(),request.getSession().getAttribute("lastUpdate"),
		request.getSession().getAttribute("failsCount"),request.getRemoteAddr(),"normal");
		
			//	jdbcTemplate.execute("INSERT INTO login_log (u_name, last_update, fails_count, login_ip) VALUES ("+event.getAuthentication().getPrincipal()+", 2017-11-22 , 4 , ip)");

			logger.debug("密码错误事件找不到主体！");
		
	
	}
	@EventListener
	public void listenUserLock(AuthenticationFailureLockedEvent event) {
//	ENUM('normal','disabled', 'accountLocked', 'accountExpired', 'credentialsExpired')
		
		jdbcTemplate.update("INSERT INTO login_log (u_name, last_update, fails_count, login_ip,userstatus) VALUES (?,?,?,?,?)",event.getAuthentication().getPrincipal(),request.getSession().getAttribute("lastUpdate"),
				request.getSession().getAttribute("failsCount"),request.getRemoteAddr(),"accountLocked");
		System.out.println("用户:  " + event.getAuthentication().getPrincipal() + " 被锁定！");

	}
}
