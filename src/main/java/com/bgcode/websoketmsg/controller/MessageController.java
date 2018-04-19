package com.bgcode.websoketmsg.controller;

import java.security.Principal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.bgcode.websoketmsg.entity.Msg;
import com.bgcode.websoketmsg.entity.MsgForm;
import com.bgcode.websoketmsg.entity.Notification;
import com.bgcode.websoketmsg.service.WebSocketService;

@Controller
public class MessageController {

 // private SpittleRepository spittleRepo;
  private WebSocketService socketService;

  @Autowired
  public MessageController(WebSocketService socketService) {
	this.socketService = socketService;
  }
  
  @MessageMapping("/msg")
  @SendToUser("/queue/notifications")
  public void handleForm(Principal principal, MsgForm form ) {
	 // Msg message = new Msg(principal.getName(),form.getText(), new Date());
	  socketService.sendMsg(principal,form);
	  
	  
	  
	 // return new Notification("自动消息 :  " + principal.getName());
  }
  
}
