package com.ngen.cosys.house.information.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.house.information.service.HouseInformationService;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.ShipmentMaster;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class HouseInformationController {

	@Autowired
	private HouseInformationService houseInformationService;

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;

	/**
	 * Searching for HAWB Information.
	 * 
	 * @param search
	 * @return ShipmentInfoModel
	 * @throws CustomException
	 */
	@ApiOperation("Searching for HAWB information")
	@RequestMapping(value = "/api/shipmentinfo/getHouseInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public BaseResponse<ShipmentInfoModel> getShipmentInformation(@Validated @RequestBody ShipmentInfoSearchReq search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentInfoModel> shipmentInfo = utilitiesModelConfiguration.getBaseResponseInstance();
		ShipmentInfoModel fetchLShipmenInfo = houseInformationService.getHouseInfo(search);
		shipmentInfo.setData(fetchLShipmenInfo);
		return shipmentInfo;
	}

	/**
	 * 
	 * Changing HandledBy
	 * @param search
	 * @return ShipmentMaster
	 * @throws CustomException
	 */
	@ApiOperation("API operation for Changing the Handling to House or Master")
	@PostRequest(value = "/api/shipment/awb/changeHandling", method = RequestMethod.POST)
	public BaseResponse<ShipmentMaster> changeHandling(@Valid @RequestBody ShipmentMaster search)
			throws CustomException {
		@SuppressWarnings("unchecked")
		BaseResponse<ShipmentMaster> shipmentInfo = utilitiesModelConfiguration.getBaseResponseInstance();
		ShipmentMaster updateShipmentInfo = houseInformationService.changeHandling(search);
		shipmentInfo.setData(updateShipmentInfo);
		if (CollectionUtils.isEmpty(search.getMessageList())){
			if(FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class) && Objects.nonNull(search.getShipmentUpdateEventFireFlag()) && search.getShipmentUpdateEventFireFlag()) {
				houseInformationService.publishShipmentEvent(search);
			}
		}
		return shipmentInfo;
	}
}
