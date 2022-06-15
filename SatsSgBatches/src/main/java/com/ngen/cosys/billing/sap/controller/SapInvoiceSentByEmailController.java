package com.ngen.cosys.billing.sap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.sap.service.SapInvoiceSentByEmailService;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class SapInvoiceSentByEmailController {

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@Autowired
	private SapInvoiceSentByEmailService sapInvoiceSentByEmailService;

	private static Logger logger = LoggerFactory.getLogger(SapInvoiceController.class);

	/**
	 * This method will process information from sap invoice and credit note
	 * document
	 * 
	 * @param
	 * @return
	 * @throws CustomException
	 * @throws @throws
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation("process infomation from sap invoice email document")
	@PostRequest(value = "api/sapinvoiceandcreditnotesetup/getSapInvoiceSentByEmailController", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse getSapInvoiceSentByEmailController() {
		BaseResponse response = utilitiesModelConfiguration.getBaseResponseInstance();
		logger.info("File Looking in Directory {}", ' ');

		try {
			sapInvoiceSentByEmailService.invoiceSentByEmail();
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setData(null);

		return response;
	}

}
