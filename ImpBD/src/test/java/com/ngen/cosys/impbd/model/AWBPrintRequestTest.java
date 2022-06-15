/**
 * AWBPrintRequestTest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date			Author      Reason
 * 1.0         25 JAN, 2018	NIIT      -
 */
package com.ngen.cosys.impbd.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * This class is Unit Testing for AWBPrintRequest model class.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration

public class AWBPrintRequestTest {
	@Test
	public void testAWBPrintRequest() {
		AWBPrintRequest awbPrintRequest	= new AWBPrintRequest();
		awbPrintRequest.setAwbNumber("6182678385");
		awbPrintRequest.setPhotoCopy(true);
		awbPrintRequest.setDocumentReceivedFlag(true);
		awbPrintRequest.setFlight("SQ123");
		awbPrintRequest.setFlightDate(LocalDate.of(2017, 11, 30));
		awbPrintRequest.setFlightKeyId("SQ123");
		List<Flight> list=new ArrayList<>();
		awbPrintRequest.setFlightDetails(list);
		//assertEquals("AWBPrintRequest(awbNumber=6182678385, photoCopy=true, flight=SQ123, flightDate=2017-11-30,documentReceivedFlag=true,flightDetails=[],flightKeyId=SQ123)",awbPrintRequest.toString());
		assertNotNull(awbPrintRequest.getAwbNumber());
		assertNotNull(awbPrintRequest.isPhotoCopy());
		assertNotNull(awbPrintRequest.getFlightDate());
		assertNotNull(awbPrintRequest.getFlightDate());
		
				
}
	
}
