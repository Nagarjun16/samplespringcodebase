package com.ngen.cosys.satssg.interfaces.singpost.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.base.BusinessEvent;
import com.ngen.cosys.events.listener.AirmailStatusStoreEventListener;
import com.ngen.cosys.events.stream.router.SatsSgInterfaceEventStreamRouter;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingPostIncomingMessagesListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(AirmailStatusStoreEventListener.class);

	@Autowired
	SatsSgInterfaceEventStreamRouter satssgInterfaceEventStreamRouter;

	@EventListener
	public void handle(BusinessEvent<PullMailBagResponseModel> event) {
		LOGGER.debug("Handle SingPost Message Event {}", event);

		// Route the message for cargo messaging
		// TODO create ENUM
		satssgInterfaceEventStreamRouter.route(event.getSource(), "SingPostIncomingMessageEvent");
	}
}
