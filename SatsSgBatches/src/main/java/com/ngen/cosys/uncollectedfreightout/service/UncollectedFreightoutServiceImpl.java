package com.ngen.cosys.uncollectedfreightout.service;

import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.TracingBatchJobDAO;
import com.ngen.cosys.application.model.TracingShipmentModel;
import com.ngen.cosys.application.service.TracingBatchJobService;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.uncollectedfreightout.dao.UncollectedFreightoutDAO;
import com.ngen.cosys.uncollectedfreightout.model.IRPNotificationDetails;
import com.ngen.cosys.uncollectedfreightout.model.ShipmentRemarksModel;
import com.ngen.cosys.uncollectedfreightout.model.UncollectedFreightoutShipmentModel;

import reactor.util.CollectionUtils;

@Service
public class UncollectedFreightoutServiceImpl implements UncollectedFreightoutService {

   @Autowired
	private UncollectedFreightoutDAO unCollectedFreightdao;

   @Autowired
   private SendEmailEventProducer publisher;

   @Autowired
   private TracingBatchJobDAO tracingBatchJobDAO;

   @Autowired
   private TracingBatchJobService service;

   @Override
   public void sendDateForUncollectedFreightout(UncollectedFreightoutShipmentModel requestModel)
           throws CustomException {
       DateTimeFormatter formatter1 = new DateTimeFormatterBuilder().parseCaseInsensitive()
               .appendPattern("dd MMM yyyy").toFormatter();
       requestModel.setFreightDate(requestModel.getDate().format(formatter1));
		List<UncollectedFreightoutShipmentModel> shipments = unCollectedFreightdao
				.sendDateForUncollectedFreightout(requestModel);
       if (!CollectionUtils.isEmpty(shipments)) {
			for (UncollectedFreightoutShipmentModel shipment : shipments) {
              List<String> customeremails = unCollectedFreightdao.getcustomercode(shipment);
				if (!CollectionUtils.isEmpty(customeremails)) {
              EMailEvent emailEvent = new EMailEvent();
              String[] mailIds = customeremails.toArray(new String[customeremails.size()]);
              emailEvent.setMailSubject("SHIPMENT IRREGULARITY REPORT - [DO NOT REPLY]");
                  emailEvent.setMailToAddress(mailIds);
                  TemplateBO template = new TemplateBO();
                  Map<String, String> mapParameters = new HashMap<>();
                  template.setTemplateName("UNCOLLECTED FREIGHT OUT TEMPLATE");
                  mapParameters.put("Agent", shipment.getAgentName());
					if (!StringUtils.isEmpty(shipment.getIrpRefNo())) {
                     mapParameters.put("ReferenceNo", shipment.getIrpRefNo());
					} else {
                	  insertTracingRecord(shipment);
						mapParameters.put("ReferenceNo", shipment.getIrpRefNo());
                  }
                  mapParameters.put("Notification Date", shipment.getCurrentDate());
                  mapParameters.put("FlightKey", shipment.getFlight());
                  mapParameters.put("Nature Of Goods", shipment.getNatureOfGoods());
                  mapParameters.put("ShipmentNumber", shipment.getShipmentNumber());
                  mapParameters.put("Origin", shipment.getOrigin());
					if (shipment.getIrrPieces() != null) {
                     mapParameters.put("Pieces", shipment.getIrrPieces().toString());
					} else {
                     mapParameters.put("Pieces", shipment.getPieces().toString());
                  }
					if (shipment.getIrrWeight() != null) {
                     mapParameters.put("Weight", shipment.getIrrWeight().toString());
					} else {
                     mapParameters.put("Weight", shipment.getWeight().toString());
                  }
                  mapParameters.put("Charge Code", shipment.getChargeCode());
                  mapParameters.put("Reminder", shipment.getCurrentReminder());
                  template.setTemplateParams(mapParameters);
                  emailEvent.setTemplate(template);
                  publisher.publish(emailEvent);
					// Inserting Email Notification Details
                  IRPNotificationDetails irpDetails = unCollectedFreightdao.getIrpNotification(shipment);

					if (irpDetails == null) {
                     IRPNotificationDetails newIRP = new IRPNotificationDetails();
                     newIRP.setShipmentId(shipment.getShipmentId());
                     newIRP.setFlightId(shipment.getFlightId());
               	   	newIRP.setReferenceNumber(shipment.getIrpRefNo());
               	   	shipment.setDate(requestModel.getDate());
               	   	newIRP.setShcHandlingGroupCode(shipment.getShcHandlingGroupCode());
						this.getIRPNoticationDetails(shipment, newIRP);
                     unCollectedFreightdao.createIrpNotification(newIRP);
					} else {
                	  irpDetails.setFlightId(shipment.getFlightId());
                  	  irpDetails.setReferenceNumber(shipment.getIrpRefNo());
                  	  irpDetails.setShcHandlingGroupCode(shipment.getShcHandlingGroupCode());
                  	  shipment.setDate(requestModel.getDate());
						this.getIRPNoticationDetails(shipment, irpDetails);
						unCollectedFreightdao.updateIrpNotification(irpDetails);
                  }
               // Update the NOA flag for shipments for which email event triggered
                  ShipmentRemarksModel remarks = new ShipmentRemarksModel();
                  remarks.setShipmentId(shipment.getShipmentId());
                  remarks.setFlightId(shipment.getFlightId());
                  remarks.setFlight(shipment.getFlight());
		          remarks.setShipmentDate(shipment.getShipmentDate().toLocalDate());
                  remarks.setRemarkType("IRP");
                  remarks.setShipmentNumber(shipment.getAwbNumber());
					remarks.setShipmentRemarks(shipment.getIrpRefNo() + " " + "ACTION" + " "
							+ shipment.getCurrentReminder() + " " + requestModel.getDate().format(formatter1));
                  remarks.setShipmentType("AWB");
                  this.unCollectedFreightdao.createShipmentRemarks(remarks);
				} else {
        	  ShipmentRemarksModel remarks = new ShipmentRemarksModel();
              remarks.setShipmentId(shipment.getShipmentId());
              remarks.setFlightId(shipment.getFlightId());
              remarks.setFlight(shipment.getFlight());
              remarks.setShipmentDate(shipment.getShipmentDate().toLocalDate());
              remarks.setRemarkType("IRP");
              remarks.setShipmentNumber(shipment.getAwbNumber());
					// Shipment remarks changed as per user request in issue 12157
					if (!StringUtils.isEmpty(shipment.getIrpRefNo())) {
						shipment.setIrpRefNo(shipment.getIrpRefNo());
					} else {
            	  insertTracingRecord(shipment);
              }
					remarks.setShipmentRemarks(
							shipment.getIrpRefNo() + " " + "ACTION" + " " + shipment.getCurrentReminder() + " "
									+ requestModel.getDate().format(formatter1) + " No Email Configuration");
              remarks.setShipmentType("AWB");
              this.unCollectedFreightdao.createShipmentRemarks(remarks);
          }
           }

       }
	}

	public IRPNotificationDetails getIRPNoticationDetails(UncollectedFreightoutShipmentModel requestModel,
			IRPNotificationDetails irpNotificationDetails) {
		if (requestModel.getCurrentReminder().equalsIgnoreCase("1st REMINDER ON NON-COLLECTION OF CARGO")) {
		      irpNotificationDetails.setEmailNotification1(requestModel.getDate());
		} else if (requestModel.getCurrentReminder().equalsIgnoreCase("2nd REMINDER ON NON-COLLECTION OF CARGO")) {
	        irpNotificationDetails.setEmailNotification2(requestModel.getDate());
		} else if (requestModel.getCurrentReminder().equalsIgnoreCase("3rd REMINDER ON NON-COLLECTION OF CARGO")) {
	        irpNotificationDetails.setEmailNotification3(requestModel.getDate());
		} else if (requestModel.getCurrentReminder().equalsIgnoreCase("4th REMINDER ON NON-COLLECTION OF CARGO")) {
	        irpNotificationDetails.setEmailNotification4(requestModel.getDate());
		} else if (requestModel.getCurrentReminder()
				.equalsIgnoreCase("REMINDER ON NON-COLLECTION OF CARGO MOVED TO ABANDON")) {
	        irpNotificationDetails.setEmailNotificationAbondon(requestModel.getDate());
	     }

		return irpNotificationDetails;
    }

	@Override
	public List<String> getEmailAddress() throws CustomException {
		return unCollectedFreightdao.getEmailAddresses();
	}

	public void insertTracingRecord(UncollectedFreightoutShipmentModel shipment) throws CustomException {
		TracingShipmentModel tracingrecord = new TracingShipmentModel();
  	  BigInteger lastCaseNumber = tracingBatchJobDAO.getMaxCaseNumber();
  	  lastCaseNumber = lastCaseNumber.add(BigInteger.ONE);
        String caseNumber = "CT" + StringUtils.leftPad(String.valueOf(lastCaseNumber.intValue()), 5, '0');
        tracingrecord.setTracingShipmentInfoInsertIndicator("Y");
        tracingrecord.setTracingShipmentInfoUpdateIndicator("N");
        tracingrecord.setIrregularityTypeCode("UNDELIVEREDSHIPMENT");
        tracingrecord.setBoardPoint(shipment.getBoardpoint());
        tracingrecord.setOffPoint(shipment.getDestination());
        tracingrecord.setFlightKey(shipment.getFlightKey());
        tracingrecord.setShipmentNumber(shipment.getAwbNumber());
        tracingrecord.setShipmentDate(shipment.getShipmentDate());
        tracingrecord.setShipmentType(shipment.getShipmentType());
        tracingrecord.setShpPieces(shipment.getPieces());
        tracingrecord.setShpWeight(shipment.getWeight());
        tracingrecord.setInvPieces(shipment.getIrrPieces());
        tracingrecord.setInvWeight(shipment.getIrrWeight());
        tracingrecord.setSta(shipment.getFlightDate());
        tracingrecord.setHouseNumber(shipment.getHouseNumber());
		tracingrecord.setOrigin(shipment.getBoardpoint());
		tracingrecord.setDestination(shipment.getDestination());
		tracingrecord.setNatureOfGoods(shipment.getNatureOfGoods());
		tracingrecord.setAgentCode(shipment.getCustomerCode());
		tracingrecord.setCarrierCode(shipment.getCarrierCode());
		if (shipment.getCurrentReminder().contains("MOVETOABANDON")) {
      	  tracingrecord.setCurrentSlab("MOVETOABANDON");
        }
		if (shipment.getCurrentReminder().contains("4")) {
      	  tracingrecord.setCurrentSlab("SLAB4");
        }
        if(shipment.getLastCycle().equals("3") && shipment.getCurrentReminder().contains("3")) {
        	  tracingrecord.setCurrentSlab("SLAB3");
          }
        
  	 this.service.createTracing(tracingrecord);
       shipment.setIrpRefNo(caseNumber);
	}

}
