package com.bgcode.websoketmsg.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bgcode.websoketmsg.entity.Shout;
import com.bgcode.websoketmsg.service.WebSocketService;

//@Controller
public class MessageTestController {

	  private static final Logger logger = LoggerFactory
		      .getLogger(MessageTestController.class);
	  
		//@RequestMapping(value = {"/infos"}, method = RequestMethod.GET)
		public String websocket(HttpServletRequest request) {
			return "/wskt" ;
		}

		 // @MessageMapping("/marco")
		  public Shout handleShout(Shout incoming) {
		    logger.info("Received message: " + incoming.getMessage());

		    try { Thread.sleep(2000); } catch (InterruptedException e) {}
		    
		    Shout outgoing = new Shout();
		    outgoing.setMessage("Polo!");
		    
		    return outgoing;
		  }
}
