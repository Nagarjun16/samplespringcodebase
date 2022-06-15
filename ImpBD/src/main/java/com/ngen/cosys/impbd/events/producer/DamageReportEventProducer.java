package com.ngen.cosys.impbd.events.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.base.BusinessEvent;
import com.ngen.cosys.impbd.events.payload.DamageReportEvent;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DamageReportEventProducer {
	private final ApplicationEventPublisher publisher;
	@Autowired
	public DamageReportEventProducer(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publish(DamageReportEvent damageReportEvent) {
		publisher.publishEvent(new BusinessEvent<>(damageReportEvent));
	}
}