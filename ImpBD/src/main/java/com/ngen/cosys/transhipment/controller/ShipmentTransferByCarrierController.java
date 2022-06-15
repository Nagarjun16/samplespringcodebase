package com.ngen.cosys.transhipment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.transhipment.model.TransferByCarrierSearch;
import com.ngen.cosys.transhipment.service.ShipmentTransferByCarrierService;

import io.swagger.annotations.ApiParam;

@NgenCosysAppInfraAnnotation
public class ShipmentTransferByCarrierController {

	@Autowired
	private ShipmentTransferByCarrierService service;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	@SuppressWarnings("unchecked")
	@ApiParam("Search Transhipment By Carrier.")
	@PostRequest(value = "api/transfer/shipment-transfer-by-carrier", method = RequestMethod.POST)
	public BaseResponse<TransferByCarrierSearch> search(@Valid @RequestBody TransferByCarrierSearch search)
			throws CustomException {
		BaseResponse<TransferByCarrierSearch> returnOnj = utilitiesModelConfiguration.getBaseResponseInstance();
		returnOnj.setData(service.search(search));
		return returnOnj;
	}
}