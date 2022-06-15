package com.ngen.cosys.impbd.events.stream.processor.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ngen.cosys.events.payload.EMailEvent;
import com.ngen.cosys.events.stream.business.processor.BaseBusinessEventStreamProcessor;
import com.ngen.cosys.events.stream.payload.ImpBdPayload;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.events.enums.ImpBdEventTypes;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;
import com.ngen.cosys.impbd.events.service.ImpBdEventService;
import com.ngen.cosys.impbd.service.mail.processor.MailProcessor;
import com.ngen.cosys.inward.dao.InwardServiceReportDao;
import com.ngen.cosys.inward.model.InwardSegmentConcatenate;
import com.ngen.cosys.inward.model.InwardSegmentModel;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportOtherDiscrepancyModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.jsonhelper.ConvertJSONToObject;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Component(ImpBdEventTypes.Names.INWARD_SERVICE_EVENT)
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InwardReportEventStreamProcessorImpl implements BaseBusinessEventStreamProcessor{

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
		 InwardReportEvent event = (InwardReportEvent) convertJSONToObject.convertMapToObject(impBdPayload.getPayload(),
				 InwardReportEvent.class);

		 InwardServiceReportModel data= new InwardServiceReportModel(); 
		 
		 data.setFlightId(event.getFlightId());
	         try {
	        	 List<InwardServiceReportModel> data1 =dao.fetchDiscrepancy(data);
	        	 InwardReportEvent carrierCode = this.eventService.getCarrierCode(event.getFlightId());
			      event.setCarrierCode(carrierCode.getCarrierCode());
	        	 List<String> email = this.eventService.fetchEmailAdrressesConfigured(event.getCarrierCode());
			      
			      String emailarr [] = email.toArray(new String[0]);
			      event.setEmailAddress(emailarr);
			      
			      if (event.getEmailAddress().length > 0) {
			    	  // Set the base email template data
	                  EMailEvent emailEvent = new EMailEvent();
	                  // Set the email to
	                  emailEvent.setMailToAddress(event.getEmailAddress());
	                  // No CC address
	                  emailEvent.setMailCC("");
	                  // Body of the message
	                  emailEvent.setMailBody("Inward Service Report- [DO NOT REPLY]");
	                  // Subject line of the message
	                  DateTimeFormatter formatters = DateTimeFormatter.ofPattern("ddMMMyyyy");
	                  emailEvent.setMailSubject("Inward Service Report"+"-" +event.getFlightKey() + "/"+
	                  		            	event.getFlightDate().format(formatters).toString().toUpperCase()+"-[DO NOT REPLY]"); 
	                  
	              	String flightDate = event.getFlightDate().format(formatters);
	              	
	              	StringBuilder finalsb = new StringBuilder();
               	 DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive()
                            .appendPattern("ddMMMyyyy").toFormatter();
               	finalsb.append("<html>" );
               	finalsb.append("<head>");
               	finalsb.append("<style>");
               	finalsb.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                          + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
                          + "  border-bottom: 1px solid #ddd;\r\n" + "}");
               	finalsb.append("</style>");
               	finalsb.append("</head>");
               	finalsb.append("<body>");
               	finalsb.append("<div>"+"INWARD SERVICE REPORT"+"\t"+LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase()+" "+event.getCreatedBy()+"</div>");
               	finalsb.append("<div>"+"REGISTRATION NO."+event.getRegistration()+"</div>");
               	finalsb.append("<div>"+"FLIGHT NO/DATE:"+" "+event.getFlightKey()+"/"
               	+event.getFlightDate().format(formatters).toString().toUpperCase()+"</div>");
               	
               	/// Part - a start from here.........
               	StringBuilder sb = new StringBuilder();
               	sb.append("<html>");
                sb.append("<head>"+"PART A -MANIFEST DISCREPANCIES" );
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
                sb.append("<th> Origin Station </th>");
                sb.append("<th> NO OF PAGES </th>");
                sb.append("<th> NATURE OF DISCREPANCIES </th>");
                sb.append("<th> ACTION TAKEN </th>");
                sb.append("</thead>");
                
               	for(InwardSegmentModel segment : data1.get(0).getSegment()) {
                    sb.append("<tbody>");
                    sb.append("<tr>");
                    sb.append("<td> " + segment.getFlightBoardPoint() + " </td>");
                    sb.append("<td> " +( segment.getManifestedPages()!=null? segment.getManifestedPages(): EMPTY)+ " </td>");
                    sb.append("<td> " + (segment.getNatureOfDiscrepancies() != null ? segment.getNatureOfDiscrepancies() : EMPTY )+" </td>");
                    sb.append("<td> " + (segment.getActionTaken()!=null ? segment.getActionTaken():EMPTY )+ " </td>");
                    sb.append("</tr>");
               	}
               		sb.append("</tbody>");
               		sb.append("</table>");
               		sb.append("</div>");
               		sb.append("</body>");
               		sb.append("</html>");
            		finalsb.append(sb);
               		// concatenate segment data starts from here..........
               		
               		// PART B STARTS FROM HERE.......
               	 StringBuilder sb1 = new StringBuilder();
               	 	sb1.append("<html>");
               	 	sb1.append("<head>"+"PART B -AIRWAYBILL DISCREPANCIES");
               	 	sb1.append("<style>");
               	 	sb1.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                       + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
                       + "  border-bottom: 1px solid #ddd;\r\n" + "}");
               	 	sb1.append("</style>");
               	 	sb1.append("</head>");
               	 	sb1.append("<body>");
               	 	sb1.append("<div>");
               	 	sb1.append("<table>");
               	 	sb1.append("<thead>");
               	 	sb1.append("<tr>");
               	 	sb1.append("<th> AWB NO </th>");
               	 	sb1.append("<th> FROM/TO </th>");
               	 	sb1.append("<th> PCS/WGT </th>");
               	 	sb1.append("<th> CONTENTS </th>");
               	 	sb1.append("</tr>");
               	
               	 	sb1.append("</thead>");
               	 	sb1.append("<tbody>");
               	    sb1.append("<tr>");
               	    if(!CollectionUtils.isEmpty( data1.get(0).getSegmentConcatAwb())) {
               	     for (InwardSegmentConcatenate segmentconcatdata1 : data1.get(0).getSegmentConcatAwb() ) {
       	 		    sb1.append("<td> " +(segmentconcatdata1.getSegmentdesc()!=null ? segmentconcatdata1.getSegmentdesc(): EMPTY)  + " </td>");
       	 		      sb1.append("</tr>");
       	 		  if(!CollectionUtils.isEmpty(segmentconcatdata1.getShipmentDiscrepancy())) {
       	 			  // filter awb data
       	 			List<InwardServiceReportShipmentDiscrepancyModel> shpdata = (List<InwardServiceReportShipmentDiscrepancyModel>) segmentconcatdata1.getShipmentDiscrepancy().stream()
       	 			.filter(obj ->(!("MAIL").equalsIgnoreCase(obj.getShipmentType()))).collect(Collectors.toList());
               	 	 for(InwardServiceReportShipmentDiscrepancyModel shipmentata :shpdata) {
               	 		 
               	 		 sb1.append("<tr>");
               	 		 sb1.append("<td> " + (shipmentata.getShipmentNumber()!= null ? shipmentata.getShipmentNumber():EMPTY)+ " </td>");
               	 		 sb1.append("<td> " + ((shipmentata.getOrigin()!=null ? shipmentata.getOrigin():EMPTY) 
               	 				 + "/" + (shipmentata.getDestination()!= null ? shipmentata.getDestination():EMPTY))+ " </td>");
               	 		 sb1.append(
                           "<td> " + ((shipmentata.getPiece()!=null ? shipmentata.getPiece() : EMPTY) 
                        		   + "/" + (shipmentata.getWeight()!=null ? shipmentata.getWeight():EMPTY )+ "K" )+ " </td>");
               	 		 sb1.append("<td> " + (shipmentata.getNatureOfGoodsDescription()!=null ? shipmentata.getNatureOfGoodsDescription(): EMPTY) + " </td>");
               	 		 sb1.append("</tr>");
               	 		 sb1.append("<tr>");
               	 		 sb1.append("<td style=\"font-weight:bold\">" + "IRREGULARITY Type/Remarks-" + " </td>" );
               	 		
               	 	
               	 		sb1.append("<td> " + ( shipmentata.getIrregularityType()!=null ? shipmentata.getIrregularityType() :EMPTY)+ "/" 
               	 		 + (shipmentata.getIrregularityDescription()!= null ? shipmentata.getIrregularityDescription(): EMPTY)+ " </td>");
               	 		sb1.append("</tr>");
               	 		sb1.append("<tr>");
               	 		sb1.append("<td style=\"font-weight:bold\"> "+"RMK-"+ " </td>" );
               	 		 sb1.append("<td> " +(shipmentata.getRemarks() != null ? shipmentata.getRemarks(): EMPTY)+ " </td>");
               	 		 sb1.append("</tr>");
               	 	 }
       	 		  }
                  }
               	    }
                  
                 sb1.append("</tbody>");
                 sb1.append("</table>");
                 sb1.append("</div>");
                 sb1.append("</body>");
                 sb1.append("</html>");
      		  // SB1 Complete
                 finalsb.append(sb1);
              // PART c STARTS FROM HERE.......
               	 StringBuilder sb2 = new StringBuilder();
               	 	sb2.append("<html>");
               	 	sb2.append("<head>"+"PART C-PHYSICAL CARGO DISCREPANCIES");
               	 	sb2.append("<style>");
               	 	sb2.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                       + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
                       + "  border-bottom: 1px solid #ddd;\r\n" + "}");
               	 	sb2.append("</style>");
               	 	sb2.append("</head>");
               	 	sb2.append("<body>");
               	 	sb2.append("<div>");
               	 	sb2.append("<table>");
               	 	sb2.append("<thead>");
               	 sb2.append("<tr>");
               	 	sb2.append("<th> AWB NO </th>");
               	 	sb2.append("<th> FROM/TO </th>");
               	 	sb2.append("<th> PCS/WGT </th>");
               	 	sb2.append("<th> CONTENTS </th>");
               	 sb2.append("</tr>");
              
              
               	 	sb2.append("</thead>");
               	 	sb2.append("<tbody>");
               	    sb2.append("<tr>");
               	    if(!CollectionUtils.isEmpty( data1.get(0).getSegmentConcatAwb())) {
               	     for (InwardSegmentConcatenate segmentconcatdata1 : data1.get(0).getSegmentConcatAwb() ) {
       	 		    sb2.append("<td> " +(segmentconcatdata1.getSegmentdesc()!=null ? segmentconcatdata1.getSegmentdesc(): EMPTY)  + " </td>");
       	 		      sb2.append("</tr>");
       	 		  if(!CollectionUtils.isEmpty(segmentconcatdata1.getPhysicalDiscrepancy())) {
       	 			List<InwardServiceReportShipmentDiscrepancyModel> pdata = (List<InwardServiceReportShipmentDiscrepancyModel>) segmentconcatdata1.getPhysicalDiscrepancy().stream()
       	       	 			.filter(obj ->(!("MAIL").equalsIgnoreCase(obj.getShipmentType()))).collect(Collectors.toList());
               	 	 for(InwardServiceReportShipmentDiscrepancyModel phydata : pdata ) {
               	 		
               	 		 sb2.append("<tr>");
               	 		 sb2.append("<td> " + (phydata.getShipmentNumber()!= null ? phydata.getShipmentNumber():EMPTY)+ " </td>");
               	 		 sb2.append("<td> " + ((phydata.getOrigin()!=null ? phydata.getOrigin():EMPTY) 
               	 				 + "/" + (phydata.getDestination()!= null ? phydata.getDestination():EMPTY))+ " </td>");
               	 		 sb2.append(
                           "<td> " + ((phydata.getPiece()!=null ? phydata.getPiece() : EMPTY) 
                        		   + "/" + (phydata.getWeight()!=null ? phydata.getWeight():EMPTY )+ "K" )+ " </td>");
               	 		 sb2.append("<td> " + (phydata.getNatureOfGoodsDescription()!=null ? phydata.getNatureOfGoodsDescription(): EMPTY) + " </td>");
               	 		 sb2.append("</tr>");
               	 		 sb2.append("<tr>");
               	 		
               	 		 sb2.append("<td style=\"font-weight:bold\">" + "IRREGULARITY Type/Remarks-" + " </td>" );
               	 		 sb2.append("<td> "+(phydata.getIrregularityType()!=null? phydata.getIrregularityType():EMPTY)+"/"+(phydata.getIrregularityPieces() != null ?phydata.getIrregularityPieces() : EMPTY)+ "/" 
               	 		 + (phydata.getIrregularityDescription()!= null ? phydata.getIrregularityDescription(): EMPTY)+ " </td>");
               	 		 sb2.append("</tr>");
               	 		 sb2.append("<tr>");
               	 		sb2.append("<td style=\"font-weight:bold\"> "+"RMK-"+ " </td>" );

               	 		 sb2.append("<td> " + (phydata.getRemarks() != null ? phydata.getRemarks(): EMPTY)+ " </td>");
               	 		 sb2.append("</tr>");
               	 	 }
       	 		  }
                  }
               	    }
                 sb2.append("</tbody>");
                 sb2.append("</table>");
                 sb2.append("</div>");
                 sb2.append("</body>");
                 sb2.append("</html>");
      		  // SB2 Complete
                 finalsb.append(sb2);
                 
                 // PART c STARTS FROM HERE.......
               	 StringBuilder sb3 = new StringBuilder();
               	 	sb3.append("<html>");
               	 	sb3.append("<head>"+"PART D-OTHER DISCREPANCIES");
               	 	sb3.append("<style>");
               	 	sb3.append("table {\r\n" + "  border-collapse: collapse;\r\n" + "  width: 100%;\r\n" + "}\r\n"
                       + "\r\n" + "th, td {\r\n" + "  padding: 8px;\r\n" + "  text-align: left;\r\n"
                       + "  border-bottom: 1px solid #ddd;\r\n" + "}");
               	 	sb3.append("</style>");
               	 	sb3.append("</head>");
               	 	sb3.append("<body>");
               	 	sb3.append("<div>");
               	 	sb3.append("<table>");
               	
               	 	sb3.append("<thead>");
               	 	
               	 	sb3.append("<th> S.No </th>");
               	 	sb3.append("<th>REMARKS </th>");
               	 	sb3.append("</thead>");
               	 	sb3.append("<tbody>");
               	    sb3.append("<tr>");
               	    if(!CollectionUtils.isEmpty( data1.get(0).getSegmentConcatAwb())) {
               	     for (InwardSegmentConcatenate segmentconcatdata1 : data1.get(0).getSegmentConcatAwb() ) {
       	 		    sb3.append("<td> " +(segmentconcatdata1.getSegmentdesc()!=null ? segmentconcatdata1.getSegmentdesc(): EMPTY)  + " </td>");
       	 		    sb3.append("</tr>");
       	 		  if(!CollectionUtils.isEmpty(segmentconcatdata1.getOtherDiscrepancy())) {
       	 			BigInteger SID = BigInteger.ZERO;
       	 			InwardServiceReportOtherDiscrepancyModel othermodel= new InwardServiceReportOtherDiscrepancyModel();
               	 	 for( InwardServiceReportOtherDiscrepancyModel othdata :segmentconcatdata1.getOtherDiscrepancy() ) {
               	 		othermodel.setRemarks(othdata.getRemarks());
               	 		//othdata.setSid(SID);
               	 		SID = SID.add(BigInteger.ONE);
               	 		 sb3.append("<tr>");
               	 		 sb3.append("<td> " + SID + " </td>");
               	 		 sb3.append("<td> " + ( othermodel.getRemarks()!= null ? othermodel.getRemarks(): EMPTY)+ " </td>");
               	 		 sb3.append("</tr>");
               	 	 }
       	 		  }
                  }
               	    }
                  
                 sb3.append("</tbody>");
                 sb3.append("</table>");
                 sb3.append("</div>");
                 sb3.append("</body>");
                 sb3.append("</html>");
      		  // SB3 Complete
                 finalsb.append(sb3);
                 finalsb.append("<div>"+"Cargo Damage Finalise By :"+(event.getCargoDamageCompletedBy()!=null ? event.getCargoDamageCompletedBy():EMPTY) +" "+ (event.getCargoDamageCompletedAt()!=null ? event.getCargoDamageCompletedAt().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase() : EMPTY)+"</div>");
                    	finalsb.append("<div>"+"Document Complete By :"+data1.get(0).getDocumentCompletedBy()+" "+ data1.get(0).getDocumentCompletedAt().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase()+"</div>");
                		finalsb.append("<div>"+"Breakdown Complete BY :"+data1.get(0).getBreakDownCompletedBy()+" "+ data1.get(0).getBreakDownCompletedAt().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase()+"</div>");
                		finalsb.append("<div>"+ "Ramp Checkin Complete By :"+(data1.get(0).getRampCheckedInBy()!=null? data1.get(0).getRampCheckedInBy():EMPTY)+""+
                		(data1.get(0).getRampCheckedInDate()!=null? data1.get(0).getRampCheckedInDate().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase():EMPTY)+ "</div>");
                		finalsb.append("<div>"+"InwardServiceReport Finalize By :"+event.getInwardfinalizeBy()+" "+ event.getInwardfinalizeAt().format(DateTimeFormatter.ofPattern(TIMEFORMAT)).toUpperCase()+"</div>");
                		finalsb.append("<div>"+ "END OF REPORT....."+"</div>");
               		finalsb.append("</body>");
	              	finalsb.append("</html>"); 
                	
        		   emailEvent.setTemplate(null);

                        emailEvent.setMailBody(finalsb.toString());
//
//                        // Send email
                        boolean mailSent = this.mailProcessor.sendEmail(emailEvent);
			      }
	         }

			 catch (CustomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
	}
}
	            
	           
	        
	   
	
			      



	



