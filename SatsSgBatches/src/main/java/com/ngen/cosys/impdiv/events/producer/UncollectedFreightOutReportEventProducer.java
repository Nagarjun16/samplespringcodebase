package com.ngen.cosys.impdiv.events.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.base.BusinessEvent;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightOutEvent;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UncollectedFreightOutReportEventProducer {
	
	private final ApplicationEventPublisher publisher;
	@Autowired
	public UncollectedFreightOutReportEventProducer(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publish(UncollectedFreightOutEvent reportEvent) {
		publisher.publishEvent(new BusinessEvent<>(reportEvent));
	}

}
