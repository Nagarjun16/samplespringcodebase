package com.ngen.cosys.impbd.delaystatus.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.delaystatus.dao.BreakDownDelayStatusDao;
import com.ngen.cosys.impbd.delaystatus.model.DelayStatusSearch;
import com.ngen.cosys.impbd.delaystatus.model.FlightTonnageDifference;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.Email;
import com.ngen.cosys.impbd.summary.service.BreakDownSummaryService;


@Service
public class BreakDownStatusServiceImpl implements BreakDownStatusService {

   @Autowired
   private BreakDownDelayStatusDao delayDao;

   @Autowired
   private SendEmailEventProducer sendMailEmailEventProducer;

   @Autowired
   private BreakDownSummaryService summaryService;

   @Override
   public List<BreakDownSummaryModel> fetchDelayList(DelayStatusSearch flightData) throws CustomException {
      if (!StringUtils.isEmpty(flightData.getFlight()) && flightData.getDate() == null) {
         flightData.addError("emptyFlightDateErr", "date", ErrorType.ERROR);
      }
      if (flightData.getDate() != null && StringUtils.isEmpty(flightData.getFlight())) {
         flightData.addError("emptyFlightkeyErr", "flight", ErrorType.ERROR);
      }
      if (flightData.getFromDate() != null && flightData.getToDate() == null) {
         flightData.addError("SURVEYE2", "toDate", ErrorType.ERROR);
      }
      if (flightData.getFromDate() == null && flightData.getToDate() != null) {
         flightData.addError("FROMDATE", "fromDate", ErrorType.ERROR);
      }
      List<BreakDownSummaryModel> flightDetails = delayDao.fetchDelayList(flightData);
      for (BreakDownSummaryModel flightInfo : flightDetails) {
         flightInfo.setDelayInMinutes(summaryService.calculateFlightDelay(flightInfo));
         if (flightInfo.getDelayInMinutes().signum() == -1) {
        	 flightInfo.setDelayInMinutes(BigInteger.ZERO);
 		}

        if(!ObjectUtils.isEmpty(flightInfo.getFirstUldTowInTime()))
        { 
         flightInfo.setFirstUldTowInTime(flightInfo.getFirstUldTowInTime().plusHours(8));
        }
        if(!ObjectUtils.isEmpty(flightInfo.getLastUldTowInTime()))
        {
         flightInfo.setLastUldTowInTime(flightInfo.getLastUldTowInTime().plusHours(8));
        }  
			
		}
      return flightDetails;
   }

   @Override
   public void closeFlight(DelayStatusSearch flightData) throws CustomException {

      // Check For Inbound ULD List Finalized
      boolean isInbouldULDListFinalised = this.delayDao.checkInboundULDFinalized(flightData);
      if (!isInbouldULDListFinalised) {
         flightData.addError("data.flight.inbound.uld.not.finalized", null, ErrorType.APP,
               new Object[] { flightData.getFlightNumber(), flightData.getFlightDate() });
      }

      // Check for Ramp Check-In Complete
      boolean isRampCheckInNotCompleted = this.delayDao.checkRampCheckInComplete(flightData);
      if (!isRampCheckInNotCompleted) {
         flightData.addError("data.flight.ramp.checkin.not.complete", null, ErrorType.APP,
               new Object[] { flightData.getFlightNumber(), flightData.getFlightDate() });
      }

      // Check for Flight Complete
      boolean isFlightCompleted = this.delayDao.checkFlightComplete(flightData);
      if (!isFlightCompleted) {
         flightData.addError("data.flight.not.complete", null, ErrorType.APP,
               new Object[] { flightData.getFlightNumber(), flightData.getFlightDate() });
      }

      if (Action.CREATE.toString().equalsIgnoreCase(flightData.getFlagCRUD())) {
         delayDao.closeFlight(flightData);
      } else if (Action.DELETE.toString().equalsIgnoreCase(flightData.getFlagCRUD())) {
         delayDao.reopenFlight(flightData);
      }

      FlightTonnageDifference tonnageData = delayDao.fetchFlightTonnageWeight(flightData);
      if (Objects.nonNull(tonnageData) && Action.CREATE.toString().equalsIgnoreCase(flightData.getFlagCRUD())
            && tonnageData.getTonnageDifference().compareTo(BigDecimal.ZERO) != 0) {
    	  DateTimeFormatter flightFormatter = DateTimeFormatter.ofPattern("ddMMMyy");
    	 
    	  List<Email> emailDetails= this.delayDao.fetchFlightCloseAdminEmail();
    	  for(Email email:emailDetails)
    	  {

       
         EMailEvent emailRej = new EMailEvent();
         emailRej.setMailTo(email.getToAddress());
         emailRej.setSenderName(flightData.getCreatedBy());
         emailRej.setMailBody("Flight Close");
         emailRej.setMailSubject("Flight close with Weight Discrepancies<"+tonnageData.getFlightKey()+"/"+flightFormatter.format(tonnageData.getFlightDate())+">");
         emailRej.setMailCC("");
         TemplateBO template = new TemplateBO();
         //template.setTemplateName("CLOSEFLIGHT");
         template.setTemplateName("CLOSE FLIGHT FOR INBOUND BREAKDOWN TONNAGE WITH DISCREPANCY");
     
         Map<String, String> has = new HashMap<>();
         has.put("FlightKey", tonnageData.getFlightKey());
         has.put("FlightDate",flightFormatter.format(tonnageData.getFlightDate()) );
         has.put("Weight", tonnageData.getTonnageDifference().toString());
         has.put("Manifested Weight", tonnageData.getManifestWeight().toString());
         template.setTemplateParams(has);
         emailRej.setTemplate(template);
         sendMailEmailEventProducer.publish(emailRej);
    	  }
      }
   }

}