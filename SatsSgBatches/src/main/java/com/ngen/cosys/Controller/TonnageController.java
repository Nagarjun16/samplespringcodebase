package com.ngen.cosys.Controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.TonnageReport.Service.TonnageReportService;
import com.ngen.cosys.TonnageReport.model.InsertRequest;
import com.ngen.cosys.TonnageReport.model.TonnageReportRequest;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.service.util.enums.ReportFormat;
import com.ngen.cosys.service.util.model.ReportRequest;

import io.swagger.annotations.ApiOperation;

@RestController
public class TonnageController {

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;
	
	@Autowired
	private TonnageReportService tonnageService;

	@ApiOperation("fetch data according to carrier code")
	@PostRequest(value = "api/shefali/carriercode", method = RequestMethod.POST)
	public BaseResponse<Object> getdetails() throws CustomException {
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
		return response;
	}
	
 public void reportgeneratefromdate1(Month month_name1, int year) {
		 
	 
	   TonnageReportRequest tonnageReportRequest = new TonnageReportRequest();
	   tonnageReportRequest.setMonth(month_name1);
	   tonnageReportRequest.setYear(year);
	    try {
			List<String> carriervalues =  tonnageService.searchcarrier(tonnageReportRequest);
			List<String> reportNames =  Arrays.asList("PAXReport", "PAXandFRT", "SQBUP", "Container_Billing", "BUPandBUCwithouttrans",
					"BUPandBUC1", "fhl_report","fwb_report","plasticAndSpreader", "Tonnage_import_flight_weight_report","DG_PER_BUP","DG_PER_BUP_Transhipment", "BUPandBUC2_with_trans",	
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
		    		}

				}
		} catch (CustomException e) {
			e.printStackTrace();
		}
	     
	     

			//return reportMailPayload;
    	
	 } 
 
 protected void generatereport(ReportRequest reportMailPayloadcreation) {
     String connectorURL = "http://localhost:9001/reportservice/report";
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
             

             System.out.println(mapdata.get("Month")+" "+mapdata.get("Year")+""+ mapdata.get("Carrier"));
             
         
 
         InsertRequest insertdata = new   InsertRequest();    
         insertdata.setDocument(maps.get("reportData")) ;
         insertdata.setDocumentName(maps.get("reportName"));   
         insertdata.setEntityType((String) mapdata.get("Carrier"));
         insertdata.setEntityDate(mapdata.get("Month")+" "+mapdata.get("Year"));
         
         
         
         
      
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

private HttpEntity getMessagePayload(ReportRequest reportMailPayloadcreation, MediaType mediaType) {
	   final HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(mediaType));
	      headers.setContentType(mediaType);
	      return new HttpEntity<>(reportMailPayloadcreation, headers);
}
 
 
}