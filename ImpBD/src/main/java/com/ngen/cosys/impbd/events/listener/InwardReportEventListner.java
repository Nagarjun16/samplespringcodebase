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
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InwardReportEventListner {
	private static final Logger LOGGER = LoggerFactory.getLogger(SingaporeCustomsDataSyncEventListener.class);

	@Autowired
	ImpBdInterfaceEventStreamRouter impBdInterfaceEventStreamRouterEventStreamRouter;

	@EventListener
	public void handle(BusinessEvent<InwardReportEvent> event) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Inward Service Report Customs Event {}", event);
		}

		// Route the message for ImpBd Service Interface
		impBdInterfaceEventStreamRouterEventStreamRouter.route(event.getSource(),
				ImpBdEventTypes.Names.INWARD_SERVICE_EVENT);

	}
}
