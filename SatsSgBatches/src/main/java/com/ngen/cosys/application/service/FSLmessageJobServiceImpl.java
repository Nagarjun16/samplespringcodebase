package com.ngen.cosys.application.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.job.LynMessageJob;
import com.ngen.cosys.events.payload.FreightStatusListEvent;
import com.ngen.cosys.events.producer.FreightStatusListEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.lyn.dao.LynMessageDao;
import com.ngen.cosys.message.lyn.model.LynMessage;
import com.ngen.cosys.message.lyn.model.LynMessageCustomerDetails;
import com.ngen.cosys.timezone.util.TenantZoneTime;

@Service
public class FSLmessageJobServiceImpl implements FSLMessageJobService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LynMessageJob.class);
	private static final String TENANT_ID = "SIN";

	@Autowired
	private LynMessageDao dao;

	@Autowired
	private FreightStatusListEventProducer producer;

	@Override
	public void getFSLMessageDefinition() throws CustomException {
		LOGGER.warn("inside Lyn message ");
		List<LynMessage> message = new ArrayList<LynMessage>();
		// get Local Time SGT for comparison
		String time = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), TENANT_ID)
				.format(DateTimeFormatter.ofPattern("HH:mm"));
		message = (List<LynMessage>) dao.getFSLMessageDefinition();
		System.err.println("------------------------------------------------>");
		List<String> days = null;
		if (message != null) {
			for (LynMessage lynMessage : message) {
				days = new ArrayList<String>();
				days.add(lynMessage.getDayToTrigger1());
				days.add(lynMessage.getDayToTrigger2());
				days.add(lynMessage.getDayToTrigger3());
				days.add(lynMessage.getDayToTrigger4());
				days.add(lynMessage.getDayToTrigger5());
				days.add(lynMessage.getDayToTrigger6());
				days.add(lynMessage.getDayToTrigger7());

				// message.getDayToTrigger().equalsIgnoreCase(LocalDate.now().getDayOfWeek().name())
				// LocalDate.now().getDayOfWeek().name()
				if (days.contains(
						TenantZoneTime.getZoneDateTime(LocalDateTime.now(), TENANT_ID).getDayOfWeek().name())) {
						// publish the event
						if(time.equals(lynMessage.getTimeToTrigger().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))) {
						for (LynMessageCustomerDetails lyn : lynMessage.getLynMessageCustomerDetails()) {
							FreightStatusListEvent event = new FreightStatusListEvent();
							event.setCarrier(lyn.getCarrier());
							event.setCarrier(lyn.getCarrier());
							event.setCountryCode(lyn.getCountryCode());
							event.setSegment(lyn.getSegment());
							event.setFlightKey(lyn.getFlightKey());
							// setting channel type
							if (lynMessage.getChannelType() != null) {
								event.setChannelType(lynMessage.getChannelType());
							} else {
								event.setChannelType(null);
							}
							event.setMessageFormat(lynMessage.getMessageFormat());
							producer.publish(event);
						}
					}
				}
			}
		}

	}

}
