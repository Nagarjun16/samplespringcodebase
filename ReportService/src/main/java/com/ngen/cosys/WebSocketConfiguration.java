/**
 * Web Socket Configuration
 */
package com.ngen.cosys;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.ngen.cosys.common.ConfigurationConstant;

/**
 * Web Socket Configuration
 */
@EnableAutoConfiguration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

	/**
	 * @see org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer#configureClientInboundChannel(org.springframework.messaging.simp.config.ChannelRegistration)
	 */
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		// Configure Interceptors
		registration.setInterceptors(new ChannelInterceptorAdapter() {

			/**
			 * @see org.springframework.messaging.support.ChannelInterceptorAdapter#preSend(org.springframework.messaging.Message,
			 *      org.springframework.messaging.MessageChannel)
			 */
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				//
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
				}
				return message;
			}
		});
	}

	/**
	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#configureMessageBroker(org.springframework.messaging.simp.config.MessageBrokerRegistry)
	 */
	@Override
	public void configureMessageBroker(MessageBrokerRegistry brokerRegistry) {
		brokerRegistry.enableSimpleBroker(ConfigurationConstant.NOTIFICATION_TOPIC, ConfigurationConstant.CHAT_TOPIC);
		brokerRegistry.setApplicationDestinationPrefixes(ConfigurationConstant.APPLICATION_DESTINATION_PREFIX);
	}

	/**
	 * @see org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer#registerStompEndpoints(org.springframework.web.socket.config.annotation.StompEndpointRegistry)
	 */
	@Override
	public void registerStompEndpoints(StompEndpointRegistry stompRegistry) {
		stompRegistry.addEndpoint(ConfigurationConstant.MESSAGE_ENDPOINT).setAllowedOrigins("*");
	}

}