package com.ngen.cosys.satssg.interfaces.singpost.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.base.BusinessEvent;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingPostIncomingMessagesProducer {

	private final ApplicationEventPublisher publisher;

	   @Autowired
	   public SingPostIncomingMessagesProducer(ApplicationEventPublisher publisher) {
	      this.publisher = publisher;
	   }

	   public void publish(PullMailBagResponseModel event) {
	      publisher.publishEvent(new BusinessEvent<>(event));
	   }
}
