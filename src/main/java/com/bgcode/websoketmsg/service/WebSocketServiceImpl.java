package com.bgcode.websoketmsg.service;

import java.security.Principal;
import java.util.List;
import java.util.regex.Matcher;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.bgcode.adm.dao.MessageRepository;
import com.bgcode.websoketmsg.entity.Msg;
import com.bgcode.websoketmsg.entity.MsgForm;
import com.bgcode.websoketmsg.entity.Notification;

@Service
public class WebSocketServiceImpl implements WebSocketService {

	private SimpMessagingTemplate messaging;
	private Pattern pattern = Pattern.compile("\\@(\\S+)");
	private MessageRepository dao;
	@Autowired
	public WebSocketServiceImpl(SimpMessagingTemplate messaging,MessageRepository dao) {
		this.messaging = messaging;
		this.dao=dao;
	}

	public void sendMsg(Principal principal,MsgForm form) {
		switch (form.getType()) {
		case "messager":
			getMessage();
			break;
		case "notif":
			getNotif(principal,form.getText());
			break;
		default:
			getMessage();
			getNotif(principal,form.getText());
			break;
		}
	}

	public void getMessage() {
		List<Msg> msgs=dao.findAll();
		messaging.convertAndSend("/topic/msg", msgs);
	}

	public void getNotif(Principal principal,String text) {
		Matcher matcher = pattern.matcher(text);
		System.out.println("***********");
		if (matcher.find()) {
			String username = matcher.group(1);
			//System.out.println("******" + username);
			messaging.convertAndSendToUser(username, "/queue/notifications",
					new Notification("n你被提到!：" +principal.getName() ));
		}
	}
}