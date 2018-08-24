package com.bgcode.listener;

import java.util.Date;

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

	private final Log logger = LogFactory.getLog(LoginFailureListener.class);

	private JdbcTemplate jdbcTemplate;

	private HttpServletRequest request;

	public LoginFailureListener(JdbcTemplate jdbcTemplate, HttpServletRequest request) {
		this.jdbcTemplate = jdbcTemplate;
		this.request = request;
	}

	@EventListener
	public void listenUserRegisterEvent(AuthenticationFailureBadCredentialsEvent event) {
		if (event.getAuthentication().getPrincipal() != null&&!org.springframework.security.core.userdetails.UsernameNotFoundException.class.isInstance(event.getException())) {
			int failsCount = (int) request.getSession().getAttribute("failsCount") + 1;
			request.getSession().setAttribute("failsCount", failsCount);
			if (1 == failsCount) {
				jdbcTemplate.update(
						"INSERT INTO login_log (u_name, last_update, fails_count, login_ip,userstatus) VALUES (?,?,?,?,?)",
						event.getAuthentication().getPrincipal(), request.getSession().getAttribute("lastUpdate"),
						failsCount, request.getRemoteAddr(), "normal");
			}else{
				jdbcTemplate.update(
						"UPDATE `bingge`.`duty` SET `accountNonLocked`= ?, `last_update`=?, `fails_count`=?, `login_ip`=? WHERE `u_name`=? ",
						failsCount>4?false:true,new Date(), failsCount, request.getRemoteAddr(), event.getAuthentication().getPrincipal());
			}
		} else {
			logger.warn("密码错误事件找不到主体！");
		}
	}

	@EventListener
	public void listenUserLock(AuthenticationFailureLockedEvent event) {
		// 锁定事件处理
		// ENUM('normal','disabled', 'accountLocked', 'accountExpired','credentialsExpired')
		int failsCount = (int) request.getSession().getAttribute("failsCount") + 1;
		if (failsCount < 7) {
			jdbcTemplate.update(
					"INSERT INTO login_log (u_name, last_update, fails_count, login_ip,userstatus) VALUES (?,?,?,?,?)",
					event.getAuthentication().getPrincipal(), new Date(),
					failsCount, request.getRemoteAddr(), "accountLocked");
		} else {
			jdbcTemplate.update(
					"UPDATE `bingge`.`duty` SET `last_update`=?, `fails_count`=?, `login_ip`=? WHERE `u_name`=? ",
					new Date(), failsCount, request.getRemoteAddr(), event.getAuthentication().getPrincipal());
		}
		request.getSession().setAttribute("failsCount", failsCount);
		logger.warn("用户: " + event.getAuthentication().getPrincipal() + " 被锁定！");
		// jdbcTemplate.update("UPDATE `bingge`.`login_log` SET `last_update`="
		// + "'"+new java.sql.Timestamp(new Date().getTime())+"',
		// `fails_count`='"+failsCount+"',`login_ip`='"+request.getRemoteAddr()+"',
		// `userstatus`='accountLocked' WHERE
		// `u_name`='"+event.getAuthentication().getPrincipal()+"'");
		//System.out.println("用户:  " + event.getAuthentication().getPrincipal() + " 被锁定！");
	}
}
