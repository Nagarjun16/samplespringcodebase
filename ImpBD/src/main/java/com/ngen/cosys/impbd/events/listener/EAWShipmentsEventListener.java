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
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;

/**
 * This Listener class is used for store EAWShipmentsEventListener.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class EAWShipmentsEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(EAWShipmentsEventListener.class);

	@Autowired
	ImpBdInterfaceEventStreamRouter impBdInterfaceEventStreamRouterEventStreamRouter;

	@EventListener
	public void handle(BusinessEvent<EAWShipmentsEvent> event) {
		LOGGER.debug("EAW Shipment Event {}", event);

		// Route the message for ImpBdServiceInterface
		impBdInterfaceEventStreamRouterEventStreamRouter.route(event.getSource(),
				ImpBdEventTypes.Names.EAW_SHIPMENTS_EVENT);
	}
}
