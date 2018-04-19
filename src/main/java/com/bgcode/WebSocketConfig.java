package com.bgcode;
 
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

	  @Override
	  public void registerStompEndpoints(StompEndpointRegistry registry) {
	  // registry.addEndpoint("/marcopolo","/spittr").withSockJS();
	  registry.addEndpoint("/mesgs").withSockJS();

	  }

	  @Override
	  public void configureMessageBroker(MessageBrokerRegistry registry) {
		  //registry.enableStompBrokerRelay("/queue", "/topic");
	    //服务端发送消息给客户端的域,多个用逗号隔开
	    registry.enableSimpleBroker("/queue", "/topic");
        //定义controll处理前缀
	    registry.setApplicationDestinationPrefixes("/app");
	  }
}