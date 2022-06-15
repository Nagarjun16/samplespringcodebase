package com.ngen.cosys.impbd.events.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.base.BusinessEvent;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;

/**
 * This Producer class is used for produce ShipmentDiscrepancyEventProducer.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EAPShipmentsEventProducer {
	
	private final ApplicationEventPublisher publisher;

	@Autowired
	public EAPShipmentsEventProducer(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publish(EAPShipmentsEvent event) {
		publisher.publishEvent(new BusinessEvent<>(event));
	}

}
