package com.bgcode.websoketmsg.service;

import java.security.Principal;

import com.bgcode.websoketmsg.entity.Msg;
import com.bgcode.websoketmsg.entity.MsgForm;

public interface WebSocketService {
	void sendMsg(Principal principal,MsgForm form);
}
