package com.bgcode.listener;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;

@Service
public class LoginSuccessListener {
	private final Log logger = LogFactory.getLog(LoginSuccessListener.class);
	private JdbcTemplate jdbcTemplate;

	public LoginSuccessListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@EventListener
	public void listenUserRegisterEvent(AuthenticationSuccessEvent event) {
		// int failscount = (int) request.getAttribute("failsCount");
		int i = jdbcTemplate.update(
				"UPDATE `bingge`.`duty` SET `last_update`=?, `fails_count`=?, `accountNonLocked`=? WHERE `u_name`=? ",
				new Date(), 0, true, event.getAuthentication().getName());
		logger.debug(event.getAuthentication().getName() + ": 登录成功");
		System.out.println("登录成功:" + i);
	}
}
