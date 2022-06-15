package com.ngen.cosys.impbd.events.stream.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.ImpBdPayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.events.enums.ImpBdEventTypes;
import com.ngen.cosys.impbd.events.payload.DamageReportEvent;
import com.ngen.cosys.impbd.events.service.ImpBdEventService;
import com.ngen.cosys.impbd.service.mail.processor.MailProcessor;
import com.ngen.cosys.inward.dao.InwardServiceReportDao;
import com.ngen.cosys.inward.model.DamageModel;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Component(ImpBdEventTypes.Names.DAMAGE_SERVICE_EVENT)
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DamageReportEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(InwardReportEventStreamProcessorImpl.class);

	   @Autowired
	   private ConvertJSONToObject convertJSONToObject;
	   @Autowired
		InwardServiceReportDao dao;
	   @Autowired
	   private ImpBdEventService eventService;

	   @Autowired
	   @Qualifier("ImpBDMailProcessor")
	   private MailProcessor mailProcessor;
	   
	   private static final String EMPTY = "";
	      final String TIMEFORMAT = "ddMMMyyyy HHmm";
	@Override
	public void process(Message<?> payload) throws JsonGenerationException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
		ImpBdPayload impBdPayload = (ImpBdPayload) payload.getPayload();
		 

	      // Concert JSON to actual event object
		 DamageReportEvent event = (DamageReportEvent) convertJSONToObject.convertMapToObject(impBdPayload.getPayload(),
				 DamageReportEvent.class);

		 InwardServiceReportModel data= new InwardServiceReportModel(); 
		 
		 data.setFlightId(event.getFlightId());
		 data.setFlightNumber(event.getFlightKey());
		 data.setFlightDate(event.getFlightDate());
		 
		 try {
			 List<DamageModel> damagedata = dao.getDamageReportData(data);
			 event.setWeatherCondition(damagedata.get(0).getWeatherCondition());
			 String carrierCode = dao.getCarrierCode(data.getFlightId());
		      event.setCarrierCode(carrierCode);
       	       List<String> email = dao.fetchEmailAdrressesDamageConfigured(carrierCode);
		      
		      String emailarr [] = email.toArray(new String[0]);
		      event.setEmailAddress(emailarr);
		      if (event.getEmailAddress().length > 0) {
		    	  EMailEvent emailEvent = new EMailEvent();
                  // Set the email to
                  emailEvent.setMailToAddress(event.getEmailAddress());
                  // No CC address
                  emailEvent.setMailCC("");
                  // Body of the message
                  emailEvent.setMailBody("Create Damage report- [DO NOT REPLY]");
                  // Subject line of the message
                  emailEvent.setMailSubject("Create Damage report- [DO NOT REPLY]"); 
              	StringBuilder finalsb = new StringBuilder();
              	DateTimeFormatter formatters = MultiTenantUtility.getTenantDateFormat();
              	String flightDate = event.getFlightDate().format(formatters);
              	finalsb.append("<html>" );
               	finalsb.append("<head>");
               	finalsb.append("<style>");
               	finalsb.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                          + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
                          + "  border-bottom: 1px solid #ddd;\r\n" + "}");
               	finalsb.append("</style>");
               	finalsb.append("</head>");
               	finalsb.append("<body>");
            	finalsb.append("<div>"+"SATS CARGO"+"</div>");
            	finalsb.append("<div>"+"SATS AIRPORT SERVICES PTE LTD"+"</div>");
            	finalsb.append("<div>"+"REGISTRATION NO."+event.getRegistration()+"</div>");
            	finalsb.append("<div>"+"REPORT DATE:"+LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase()+"</div>");
            	finalsb.append("<div>"+"CARGO DAMAGE REPORT"+"</div>");
            	finalsb.append("<div>"+"FLIGHT NO/DATE:"+" "+event.getFlightKey()+"/"
            			+event.getFlightDate().format(formatters).toString().toUpperCase()+"     ."+"WEATHER CONDITION:"+event.getWeatherCondition()+"</div>");
               	/// table starts from here.........
               	StringBuilder sb = new StringBuilder();
               	sb.append("<html>");
                sb.append("<head>" );
                sb.append("<style>");
                sb.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                      + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
                      + "  border-bottom: 1px solid #ddd;\r\n" + "}");
                sb.append("</style>");
                sb.append("</head>");
                sb.append("<body>");
                sb.append("<div>");
                sb.append("<table>");
                sb.append("<thead>");
                sb.append("</thead>");
                
                sb.append("<thead>");
                sb.append("<tr>");
                sb.append("<th> S/N </th>");
                sb.append("<th> AIR WAYBILL </th>");
                sb.append("<th> PCS DAMAGE </th>");
                sb.append("<th> CONTENTS </th>");
                sb.append("<th> SEVERITY/OCCURRENCE </th>");
                sb.append("<th> DAMAGE NATURE(EXTERNAL PACK)  C/S/P/T/W/F/O</th>");
                sb.append("</tr>");
                sb.append("<tr>");
               	sb.append("<th> REMARK</th>");
               	sb.append("</tr>");
                sb.append("</thead>");
                BigInteger SID = BigInteger.ZERO;
               	for(DamageModel ddata : damagedata) {
               		SID = SID.add(BigInteger.ONE);
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td> " + SID+ " </td>");
                    sb.append("<td> " + ddata.getShipmentNumber()+ " </td>");
                    sb.append("<td> " + ddata.getDamagedPieces() +" </td>");
                    sb.append("<td> " +ddata.getContent() + " </td>");
                    sb.append("<td> " +(ddata.getSeverity()!=null ? ddata.getSeverity():EMPTY)+"/" +(ddata.getOccurence()!=null ? ddata.getOccurence():EMPTY)+ " </td>");
                    sb.append("<td> " +ddata.getNatureOfDamageBooleancrushed()+"   /" +ddata.getNatureOfDamageBooleanseam()+ "   /"+ddata.getNatureOfDamageBooleanpuncture()+
                    		"/"+ddata.getNatureOfDamageBooleantorn()+"   /"+ddata.getNatureOfDamageBooleanwet()+"   /"+ddata.getNatureOfDamageBooleanforeigntaping()+"   /"+ddata.getNatureOfDamageBooleanothers()+
                    		"</td>");
                    sb.append("</tr>");
                    sb.append("<tr>");
                    sb.append("<td>"+(ddata.getRemarks()!=null? ddata.getRemarks():EMPTY)+"</td> ");
                    sb.append("</tr>");
                    
               	}
               		sb.append("</tbody>");
               		sb.append("</table>");
               		sb.append("</div>");
               		sb.append("</body>");
               		sb.append("</html>");
               		finalsb.append(sb);
               		finalsb.append("<div>"+"THE INFORMATION ON THIS REPORT WAS BASED ON OBSERVATIONS MADE WHEN FREIGHT\r\n" + 
            		" WAS RECEIVED IN THE SATS AIR FREIGHT TERMINAL. THIS REPORT DOES NOT IMPLY\r\n" + 
            		" ANY ADMISSION OF LIABILITY ON SATS AIRPORT SERVICES PTE LTD OR ITS \r\n" + 
            		" EMPLOYEES. THIS REPORT IS ALSO NOT A CLAIM OR NOTIFICATION OF INTENT TO\r\n" + 
            		" FILE CLAIM."+"</div>");
               		finalsb.append("<div>"+""+"</div>");
               		finalsb.append("<div>"+""+"</div>");
               		finalsb.append("<div>"+"Legend"+"</div>");
               		finalsb.append("<div>"+"C - CRUSHED"+"</div>");
               		finalsb.append("<div>"+"S - SEAMS OPENED"+"</div>");
               		finalsb.append("<div>"+"P - PUNCTURED"+"</div>");
               		finalsb.append("<div>"+"T - TORN"+"</div>");
               		finalsb.append("<div>"+"W - WET/DAMP"+"</div>");
               		finalsb.append("<div>"+"F - FOREIGN TAPING"+"</div>");
               		finalsb.append("<div>"+"O - OTHERS"+"</div>");
               		finalsb.append("<div>"+""+"</div>");
               		finalsb.append("<div>"+""+"</div>");
               		finalsb.append("<div>"+"REPORT PREPARED BY :"+event.getCreatedBy()+LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase()+" "+"</div>");
               		finalsb.append("<div>"+""+"</div>");
               		finalsb.append("<div>"+ "THIS SERVE AS THE ORIGINAL DOCUMENT AND NO SIGNATURE/APPROVAL STAMP \r\n" + 
    				" IS REQUIRED."+ "</div>");
    		finalsb.append("<div>"+"END OF REPORT"+"</div>");
//    		
                  		finalsb.append("</body>");
   	              	finalsb.append("</html>"); 
               		
            emailEvent.setTemplate(null);

            emailEvent.setMailBody(finalsb.toString());
//
//            // Send email
            boolean mailSent = this.mailProcessor.sendEmail(emailEvent);
		 } 
		 }
		 
		 catch(CustomException e ) {
			 e.printStackTrace();
		 }
	}

}
