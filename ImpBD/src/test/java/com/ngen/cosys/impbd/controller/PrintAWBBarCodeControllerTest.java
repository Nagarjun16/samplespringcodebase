/**
 * PrintAWBBarCodeControllerTest.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date			Author      Reason
 * 1.0          25 JAN, 2018	NIIT      -
 *//*
package com.ngen.cosys.impbd.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.service.PrintAWBBarCodeService;
*//**
 * This class is Unit Testing for PrintAWBBarCode Controller.
 * 
 * @author NIIT Technologies Ltd.
 *
 *//*
@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
public class PrintAWBBarCodeControllerTest {
	
	@Mock
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Mock
	private PrintAWBBarCodeService printAWBBarCodeService;

	@InjectMocks
	private BaseResponse<AWBPrintRequest> baseResponse;

	@InjectMocks
	private PrintAWBBarCodeController printAWBBarCodeController;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	*//**
	 * This method tests the validateAWBNumber method of PrintAWBBarCodeController.
	 * 
	 * @throws CustomException
	 *//*
	@Test
	public void validateAWBNumberTest() throws CustomException {
		when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
		AWBPrintRequest awbDetail = new  AWBPrintRequest();
		awbDetail.setAwbNumber("4545564564");
		when(printAWBBarCodeService.validateAWBNumber(awbDetail)).thenReturn(awbDetail);
		BaseResponse<AWBPrintRequest> result = printAWBBarCodeController.validateAWBNumber(awbDetail);
		assertNotNull(result);
		assertEquals("4545564564", result.getData().getAwbNumber());	
	}
	*//**
	 * This method tests the  printBarcode method of PrintAWBBarCodeController.
	 * 
	 * @throws CustomException
	 *//*
	@Test
	public void printBarcodeTest() throws CustomException {
		when(utilitiesModelConfiguration.getBaseResponseInstance()).thenReturn(baseResponse);
		AWBPrintRequest awbDetail = new  AWBPrintRequest();
		awbDetail.setAwbNumber("4545564564");
		awbDetail.setFlight("SQ118");
		when(printAWBBarCodeService.printBarcode(awbDetail)).thenReturn(awbDetail);
		BaseResponse<AWBPrintRequest> result = printAWBBarCodeController.printBarcode(awbDetail);
		assertNotNull(result);
		assertEquals("4545564564", result.getData().getAwbNumber());
		assertEquals("SQ118", result.getData().getFlight());
	}
	
}

*/