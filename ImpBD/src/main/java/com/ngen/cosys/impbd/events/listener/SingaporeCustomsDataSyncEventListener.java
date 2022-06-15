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
import com.ngen.cosys.events.tenants.ApplicationTenants;
import com.ngen.cosys.impbd.events.enums.ImpBdEventTypes;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.multitenancy.context.TenantContext;

/**
 * This Listener class is used for store SingaporeCustomsDataSyncEventListener.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SingaporeCustomsDataSyncEventListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SingaporeCustomsDataSyncEventListener.class);

	@Autowired
	ImpBdInterfaceEventStreamRouter impBdInterfaceEventStreamRouterEventStreamRouter;

	@EventListener
	public void handle(BusinessEvent<SingaporeCustomsDataSyncEvent> event) {
		LOGGER.debug("Singapore Customs Event {}", event);
		switch (TenantContext.getTenantId()) {
		case ApplicationTenants.Names.AISATS:
		case ApplicationTenants.Names.AAT:
			// DO Nothing
			break;
		default:
			// Route the message for ImpBd Service Interface
			impBdInterfaceEventStreamRouterEventStreamRouter.route(event.getSource(),
					ImpBdEventTypes.Names.SINGAPORE_CUSTOMS_EVENT);
			break;
		}

	}
}
