package com.ngen.cosys.application.job;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.TonnageReport.Service.TonnageReportService;
import com.ngen.cosys.TonnageReport.model.InsertRequest;
import com.ngen.cosys.TonnageReport.model.TonnageReportRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.scheduler.job.AbstractCronJob;
import com.ngen.cosys.service.util.constants.ServiceURLConstants;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportRequest;

public class TonnageReportJob  extends AbstractCronJob{
	
	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;
	
	@Autowired
	private TonnageReportService tonnageService;
    
	@Autowired
	private SqlSession sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(TonnageReportJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		// method will take care of sending mail with report
		super.executeInternal(jobExecutionContext);
		
		
		@SuppressWarnings("unchecked")
		BaseResponse<Object> response = utilitiesModelConfiguration.getBaseResponseInstance();
		LocalDate datetime = LocalDate.now();
		Month currentMonth = datetime.getMonth();
		int year = datetime.getYear();
		
		for (int i = 0; i <= 11; i++) {
			Month previousMonth = currentMonth.minus(i);
			
			if(i==11) {
				year= year-1;
			}
			this.reportgeneratefromdate1(previousMonth,year);  
			
		    
		}
		
	}

	 public void reportgeneratefromdate1(Month month_name1, int year) {
		 
		 
		   TonnageReportRequest tonnageReportRequest = new TonnageReportRequest();
		   tonnageReportRequest.setMonth(month_name1);
		   tonnageReportRequest.setYear(year);
		   logger.warn("Getting Month and year for tonnage report ---->"+ tonnageReportRequest);
		    try {
				List<String> carriervalues =  tonnageService.searchcarrier(tonnageReportRequest);
				List<String> reportNames =  Arrays.asList("PAXReport", "PAXandFRT", "SQBUP", "Container_Billing", "BUPandBUCwithouttrans",
						"BUPandBUC1", "fhl_report","fwb_report","plasticAndSpreader", "Tonnage_import_flight_weight_report","DG_PER_BUP","DG_PER_BUP_Transhipment","BUPandBUC2_with_trans",
						"Tonnage_import_flight_weight_report_2");
				System.err.println(carriervalues);
				 Map<String, Object> reportParams = new HashMap<>();
					reportParams.put("Month", month_name1);
					reportParams.put("Year", year);
					reportParams.put("tenantID", tonnageReportRequest.getTenantAirport());   //check for tenantId
					
					for ( String carrier : carriervalues )
					{
				 		reportParams.put("Carrier", carrier);
			 
			    		ReportRequest request = new ReportRequest();
			    		for(String reportnameValue: reportNames) {
				    		request.setReportName(reportnameValue);
				    		request.setFormat(ReportFormat.CSV);
				    		request.setParameters(reportParams);
				    		request.setTerminal("T6");
				    		generatereport(request);
				    		logger.warn("Request for generating report ---->"+ request);
			    		}

					}
			} catch (CustomException e) {
				e.printStackTrace();
			}
		     
		     

				//return reportMailPayload;
	    	
		 } 
	 
	 protected void generatereport(ReportRequest reportMailPayloadcreation) {
		 logger.warn("Request received for generating report ---->"+ reportMailPayloadcreation);
	     String  connectorURL =   sqlSession.selectOne(ServiceURLConstants.SQL_SERVICE_API_URL, ServiceURLConstants.REPORT_SERVICE);
	     logger.warn("URL for report service ---->"+ connectorURL);
	     RestTemplate restTemplate = CosysApplicationContext.getRestTemplate();
	      if (!StringUtils.isEmpty(connectorURL)) {
	          Map<String, Object> mapdata= reportMailPayloadcreation.getParameters();
	          List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
	        //Add the Jackson Message converter
	        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

	        // Note: here we are making this converter to process any kind of response, 
	        // not only application/*json, which is the default behaviour
	        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));        
	        messageConverters.add(converter);  
	        restTemplate.setMessageConverters(messageConverters); 
	          
	           ResponseEntity<Object> result = restTemplate.exchange(connectorURL, HttpMethod.POST,
	                 getMessagePayload(reportMailPayloadcreation, MediaType.APPLICATION_JSON), Object.class);
	             System.err.println(result.getBody());
	        //     String reportName = result.getBody().get("reportName");
	             Object data = result.getBody();
	             LinkedHashMap<String, LinkedHashMap<String, String>> map= (LinkedHashMap<String, LinkedHashMap<String, String>>) result.getBody();
	             System.out.println(map.get("data"));
	             
	             LinkedHashMap<String, String> maps=  map.get("data");
	             System.out.println(maps.get("reportName")+" "+maps.get("reportData"));
	             

	             System.out.println(mapdata.get("Month")+" "+mapdata.get("Year"));
	             
	             
	         InsertRequest insertdata = new   InsertRequest();    
	         insertdata.setDocument(maps.get("reportData")) ;
	         insertdata.setDocumentName(maps.get("reportName"));   
	         insertdata.setEntityKey((String) mapdata.get("Carrier"));
	         insertdata.setEntityType((String) mapdata.get("Carrier"));
	         insertdata.setEntityDate(mapdata.get("Month")+" "+mapdata.get("Year"));
	         logger.warn("Data to be inserted ---->"+ insertdata);
	         
	      
			 try {
					/* List<String> autopublish = tonnageService.searchautopublish(insertdata);
					 System.out.println(autopublish);*/
					 
					 int count=1;
					 String  entityValue =  tonnageService.searchentitycombination(insertdata);
					 if(entityValue !=null) {
						 count =Integer.parseInt(entityValue)+1;
					 }
					 insertdata.setEntityKey(String.valueOf(count));
					 Integer  datacount =  tonnageService.searchdatacount(insertdata); 
					 if(datacount != 0 )
					 {
			          // update
						 tonnageService.update(insertdata);
					 }
					 else
					 {
					tonnageService.save(insertdata);
					 }
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	         
	        
	     }
	  }

	private HttpEntity<Object> getMessagePayload(ReportRequest reportMailPayloadcreation, MediaType mediaType) {
		   final HttpHeaders headers = new HttpHeaders();
		      headers.setAccept(Arrays.asList(mediaType));
		      headers.setContentType(mediaType);
		      return new HttpEntity<>(reportMailPayloadcreation, headers);
	}
}
