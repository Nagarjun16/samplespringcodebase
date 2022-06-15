package com.ngen.cosys.impbd.events.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ngen.cosys.events.base.BusinessEvent;
import com.ngen.cosys.events.stream.router.ImpBdInterfaceEventStreamRouter;
import com.ngen.cosys.impbd.events.enums.ImpBdEventTypes;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;

/**
 * This Listener class is used for store EAPShipmentsEventListener.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EAPShipmentsEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(EAPShipmentsEventListener.class);

	@Autowired
	ImpBdInterfaceEventStreamRouter impBdInterfaceEventStreamRouterEventStreamRouter;

	@EventListener
	public void handle(BusinessEvent<EAPShipmentsEvent> event) {
		LOGGER.debug("EAP Shipment Event {}", event);

		// Route the message ImpBd Service Interface
		impBdInterfaceEventStreamRouterEventStreamRouter.route(event.getSource(),
				ImpBdEventTypes.Names.EAP_SHIPMENTS_EVENT);
	}

}
