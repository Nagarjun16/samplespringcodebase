/**
 * This is a rest controller service component which has functionality for
 * handling break down summary tonnage on inbound flight
 */
package com.ngen.cosys.impbd.summary.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.producer.SendEmailEventProducer;
import com.ngen.cosys.events.template.model.TemplateBO;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.summary.model.BreakDownSummary;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.Email;
import com.ngen.cosys.impbd.summary.service.BreakDownSummaryService;
import com.ngen.cosys.impbd.summary.validator.BreakDownSummaryValidation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation(values = BreakDownSummaryValidation.class)
public class BreakDownSummaryController {

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private BreakDownSummaryService summaryService;

   @Autowired
   private SendEmailEventProducer sendMailEmailEventProducer;
   
   private static final Logger LOGGER = LoggerFactory.getLogger(BreakDownSummaryController.class);
   
  
   /**
    * Method to get break down summary info for an inbound flight
    * 
    * @return complete Summary List
    * 
    * @throws CustomException
    */
   @ApiOperation("fetch Breakdown Summary list")
   @PostRequest(value = "api/config/breakdown/summaryList", method = RequestMethod.POST)
   public BaseResponse<BreakDownSummaryModel> fetchSummaryDetails(
         @ApiParam(value = "fetchBreakdownSummaryList", required = true) @Validated(value = BreakDownSummaryValidation.class) @RequestBody BreakDownSummaryModel requestModel)
         throws CustomException {

      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownSummaryModel> breakdownSummaryDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();

      BreakDownSummaryModel response = summaryService.get(requestModel);
      breakdownSummaryDetails.setData(response);

      return breakdownSummaryDetails;
   }

   /**
    * Method to create break down summary for an inbound flight
    * 
    * @param list
    *           of ULD Summary and it's tonnage information
    * 
    * @return BaseResponse<BreakDownSummary>
    * 
    * @throws CustomException
    */
   
   @ApiOperation("insert Summary details")
   @PostRequest(value = "api/config/breakdown/insertSummaryList", method = RequestMethod.POST)
   public BaseResponse<BreakDownSummary> createBreakDownSummary(
         @ApiParam(value = "insertSummaryList", required = true) @Valid @Validated(value = BreakDownSummaryValidation.class) @RequestBody BreakDownSummary requestModel)
         throws CustomException {

      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownSummary> breakdownSummaryDetails = utilitiesModelConfiguration.getBaseResponseInstance();

      try {
			// Create Summary Information
			BreakDownSummary response = summaryService.createBreakDownSummary(requestModel);
			breakdownSummaryDetails.setData(response);
		} catch (CustomException e) {
			breakdownSummaryDetails.setData(requestModel);
			breakdownSummaryDetails.setSuccess(false);
		}


      return breakdownSummaryDetails;
   }

   /**
    * REST api to update FeedBack
    * 
    * @param list
    *           of Feedback updated
    * 
    * @return BaseResponse<BreakDownSummary> - Base Response Object
    * 
    * @throws CustomException
    */
   @ApiOperation("update FeedBack")
   @PostRequest(value = "api/config/breakdown/updateFeedBack", method = RequestMethod.POST)
   public BaseResponse<BreakDownSummary> updateFeedBack(
         @ApiParam(value = "updateFeedBack", required = true) @RequestBody BreakDownSummary requestModel)
         throws CustomException {

      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownSummary> breakdownSummaryDetails = utilitiesModelConfiguration.getBaseResponseInstance();

      summaryService.updateFeedBack(requestModel);

      return breakdownSummaryDetails;
   }

   /**
    * REST api to update FeedBack
    * 
    * @param list
    *           of Feedback updated
    * 
    * @return BaseResponse<BreakDownSummary> - Base Response Object
    * 
    * @throws CustomException
    */
   @ApiOperation("send email")
   @PostRequest(value = "api/config/breakdown/sendEmail", method = RequestMethod.POST)
   public BaseResponse<BreakDownSummary> sendEmail(
         @ApiParam(value = "sendEmail", required = true) @RequestBody BreakDownSummary requestModel)  throws CustomException{

      @SuppressWarnings("unchecked")
      BaseResponse<BreakDownSummary> breakdownSummaryDetails = utilitiesModelConfiguration.getBaseResponseInstance();
      
             List<Email> emailDetails= this.summaryService.fetchEmails(requestModel.getEmailGroup());

    
      // Send flight delay break down summary email
      for (Email email : emailDetails) {
    	  
    	  email.setFlightkey(requestModel.getFlightNumber());
    	  email.setFlightDate(requestModel.getFlightDate());
         EMailEvent emailRej = new EMailEvent();
         emailRej.setMailTo(email.getToAddress());
         emailRej.setSenderName(requestModel.getCreatedBy());
       
         emailRej.setMailSubject("Reason for Delay");
         
         String flightDate=null;
			try {
			String stringCreatedDate =email.getFlightDate();
			Date createdDateFormat = new SimpleDateFormat("yyyy-MM-dd").parse(stringCreatedDate);
			SimpleDateFormat formatter = new SimpleDateFormat("ddMMMYYYY");
			flightDate = formatter.format(createdDateFormat);
			}catch(ParseException e) {
				  LOGGER.error("Date Format Issue");
			}

         Map<String, String> has = new HashMap<>();
         has.put("FlightKey", email.getFlightkey());
         has.put("FlightDate", flightDate);
         has.put("Receiver Details", requestModel.getCreatedBy());
       
         if(!StringUtils.isEmpty(requestModel.getReasonForWaive()))
         {
        	 has.put("Reason For Waive",requestModel.getReasonForWaive());
         }
         else
         {
        	 has.put("Reason For Waive",":");
         }
        if(!StringUtils.isEmpty(requestModel.getReasonForDelay()))
        {
            has.put("Details",requestModel.getReasonForDelay());
        }   
        else
        {
        	has.put("Details",":");
        }
   
         TemplateBO template = new TemplateBO();
         template.setTemplateName("FLIGHTDELAY");
         template.setTemplateParams(has);
         emailRej.setTemplate(template);

         sendMailEmailEventProducer.publish(emailRej);
         
        // this.mailProcessor.sendEmail(emailRej);
         
      }
      breakdownSummaryDetails.setData(null);
      return breakdownSummaryDetails;
   }

}