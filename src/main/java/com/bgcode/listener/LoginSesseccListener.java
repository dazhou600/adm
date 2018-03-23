package com.bgcode.listener;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;
@Service
public class LoginSesseccListener {

	   @EventListener
	    public void listenUserRegisterEvent(AuthenticationSuccessEvent successEvent) {
	        System.out.println("邮件服务接到通知，给 " + successEvent.getSource() + " 发送邮件...");
	    }
}
