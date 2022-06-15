/**
 * This is a class which performs Deletion of House Way Bills. Shipment can
 * be categorized into AWB only for deletion. This class establishes to all relevant
 * entities. It also derives , Inventory and Remarks information.
 * 
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 12/06/2019
 */
package com.ngen.cosys.shipment.deletehousewaybill.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.events.payload.ShipmentStatusUpdateEvent;
import com.ngen.cosys.events.producer.ShipmentStatusUpdateEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.model.TenantConfig;
import com.ngen.cosys.shipment.deletehousewaybill.dao.DeleteHouseWayBillDao;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillResponseModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillSearchModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.UpdateShipmentDetails;
import com.ngen.cosys.transaction.PureTransactional;
import com.ngen.cosys.validator.dao.ShipmentValidationDao;

/*
 * This Service Handles deletion for HOUSE for DAXING
 * 
 */
@Service
public class DeleteHouseWayBillServiceImpl implements DeleteHouseWayBillService {

	@Autowired
	private DeleteHouseWayBillDao deleteHouseWayBillDao;
	
	@Autowired
	private ShipmentValidationDao shipmentValidationDao;
	
	@Autowired
	private ShipmentStatusUpdateEventProducer shipmentStatusUpdateEventProducer;

	@Override
	@PureTransactional
	public DeleteHouseWayBillResponseModel deleteHouseWayBill(
			DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel) throws CustomException {
		DeleteHouseWayBillResponseModel shipmentHseInf = this.deleteHouseWayBillDao
				.getShipmentIdInCaseInvIsNotAvlbl(deleteHouseWayBillSearchModel);
		/*
		 * Used for hawb delete Audit.
		 */
		if (shipmentHseInf == null) {
			throw new CustomException("error.enter.valid.awb", "form", ErrorType.ERROR);
		}
		shipmentHseInf.setAwbNumber(deleteHouseWayBillSearchModel.getShipmentNumber());
		shipmentHseInf.setHawbNumber(deleteHouseWayBillSearchModel.getHawbNumber());
		shipmentHseInf.setRemarks(deleteHouseWayBillSearchModel.getRemarks());
		/**
		 *
		 * bug-173 fix
		 **/
		shipmentHseInf.setLoggedInUser(deleteHouseWayBillSearchModel.getLoggedInUser());
		/**
		 *
		 * bug-173 fix END
		 **/

		shipmentValidationDao.validateShipmentBeforeUpdate(deleteHouseWayBillSearchModel.getShipmentNumber(),
				deleteHouseWayBillSearchModel.getShipmentDate(), deleteHouseWayBillSearchModel.getHawbNumber(), true);

		List<BigInteger> customerAddressInfoId = this.deleteHouseWayBillDao.getCustomerAddressInfoIds(shipmentHseInf);

		if (shipmentHseInf.getShipmentHouseId() != 0) {
			this.deleteHouseWayBillDao.deleteShipmentHouseSHCInfo(shipmentHseInf);
			for (BigInteger x : customerAddressInfoId) {
				BigInteger shipmentHouseCUstmrAdressinfId = this.deleteHouseWayBillDao
						.fetchShipmentHouseCustomerContactInfo(x);
				// This will delete the customer and its contact informations and address
				this.deleteHouseWayBillDao.deleteCustomerContactInfo(shipmentHouseCUstmrAdressinfId);
				this.deleteHouseWayBillDao.deleteShipmentHouseCustomerAddressInfo(x);
			}
			this.deleteHouseWayBillDao.deleteShipmentHouseCustomerInfo(shipmentHseInf);
			// this will delete the dimension and dimension details
			this.deleteHouseWayBillDao.deleteShipmentHouseDimensionDetails(shipmentHseInf);
			// Delete House information from Shipment Inventory
			this.deleteHouseWayBillDao.deleteShipmentInventoryDetails(shipmentHseInf);
			// This will delete Imp_BreakDownStorageinfo details
			this.deleteHouseWayBillDao.deleteShipmentImpBreakDownStorageDetails(shipmentHseInf);
			// This will delete Shipment_freightOut details
			this.deleteHouseWayBillDao.deleteShipmentFreightOutDetails(shipmentHseInf);
			this.deleteHouseWayBillDao.deleteShipmentHouseInfo(shipmentHseInf);
			shipmentHseInf.setShipmentNumber(deleteHouseWayBillSearchModel.getShipmentNumber());
			shipmentHseInf.setHawbNumber(deleteHouseWayBillSearchModel.getHawbNumber());
			shipmentHseInf.setRemarks(deleteHouseWayBillSearchModel.getRemarks());
			shipmentHseInf.setInboundFlightId(null);
			shipmentHseInf.setCreatedBy(deleteHouseWayBillSearchModel.getCreatedBy());
			shipmentHseInf.setCreatedOn(deleteHouseWayBillSearchModel.getCreatedOn());
			shipmentHseInf.setShipmentDate(deleteHouseWayBillSearchModel.getShipmentDate());
			// Inserting Remarks for Deletion Purpose
			this.deleteHouseWayBillDao.insertRemarksForDeletion(shipmentHseInf);
			// return shipmentHseInf;
		} else {
			throw new CustomException("hawb.nohouseinfo", "form", ErrorType.ERROR);
		}

		return shipmentHseInf;
	}

	@Override
	@PureTransactional
	public String checkForShipmentIsImportExport(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)
			throws CustomException {
		return deleteHouseWayBillDao.checkForShipmentIsImportExport(deleteHouseWayBillSearchModel);
	}
	
	public UpdateShipmentDetails getShipmentDetails(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)
			throws CustomException {
		// start - used for shipment update event fire
		Map<String, Object> requestMapForFlight = new HashMap<>();
		requestMapForFlight.put("shipmentId", deleteHouseWayBillSearchModel.getShipmentId());
		requestMapForFlight.put("shipmentNumber", deleteHouseWayBillSearchModel.getShipmentNumber());
		requestMapForFlight.put("shipmentDate", deleteHouseWayBillSearchModel.getShipmentDate());
		BigInteger flightId = deleteHouseWayBillDao.getFlightId(requestMapForFlight);

		Map<String, Object> requestMapForShipment = this.createRequestMap(flightId,
				deleteHouseWayBillSearchModel.getShipmentId(), deleteHouseWayBillSearchModel.getShipmentNumber(),
				deleteHouseWayBillSearchModel.getShipmentDate(), deleteHouseWayBillSearchModel.getHawbNumber());
		UpdateShipmentDetails updateShipmentDetail = deleteHouseWayBillDao.fetchShipmentDetails(requestMapForShipment);

		updateShipmentDetail.setFlightId(flightId);
		// end - used for shipment update event fire

		return updateShipmentDetail;
	}
	
	public void publishShipmentEvent(DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel,
			UpdateShipmentDetails updateShipmentDetail) throws CustomException {
		// Check the flight completed status
		int isFlightCompleted = 0;
		if (Objects.nonNull(updateShipmentDetail.getFlightId())) {
			isFlightCompleted = deleteHouseWayBillDao.isFlightCompleted(updateShipmentDetail.getFlightId());
		}
		if (isFlightCompleted > 0) {
			ShipmentStatusUpdateEvent event = new ShipmentStatusUpdateEvent();
			event.setFlightId(updateShipmentDetail.getFlightId());
			event.setHouseNumber(deleteHouseWayBillSearchModel.getHawbNumber());
			event.setShipmentNumber(deleteHouseWayBillSearchModel.getShipmentNumber());
			event.setCreatedBy(deleteHouseWayBillSearchModel.getCreatedBy());
			event.setCreatedOn(LocalDateTime.now());
			event.setFunction("HAWB List");
			event.setMessageType("D");
			event.setClearingAgentCode(updateShipmentDetail.getClearingAgentCode());
			event.setClearingAgentName(updateShipmentDetail.getClearingAgentName());
			event.setConsolAgentCode(updateShipmentDetail.getConsolAgentCode());
			event.setConsolAgentName(updateShipmentDetail.getConsolAgentName());
			event.setDamageRemarks(updateShipmentDetail.getDamageRemarks());
			event.setDeclaredChWt(updateShipmentDetail.getDeclaredChWt());
			event.setDeclaredGrWt(updateShipmentDetail.getDeclaredGrWt());
			event.setDeclaredPcs(updateShipmentDetail.getDeclaredPcs());
			event.setReceivedPcs(updateShipmentDetail.getReceivedPcs());
			event.setReceivedGrWt(updateShipmentDetail.getReceivedGrWt());
			event.setReceivedChWt(updateShipmentDetail.getReceivedChWt());
			event.setHAWBOrigin(updateShipmentDetail.getHAWBOrigin());
			event.setHAWBDest(updateShipmentDetail.getHAWBDest());
			event.setIsDamage(updateShipmentDetail.getIsDamage());
			event.setConsigneeName(updateShipmentDetail.getConsigneeName());
			event.setConsigneeAddress(updateShipmentDetail.getConsigneeAddress());
			shipmentStatusUpdateEventProducer.publish(event);
		}

	}
	
	public Map<String, Object> createRequestMap(BigInteger flightId, BigInteger shipmentId, String ShipmentNumber, LocalDate ShipmentDate, String houseNumber) {
		if (Optional.ofNullable(TenantContext.get()).isPresent()) {
			TenantConfig tenantConfig = TenantContext.get().getTenantConfig();
			if (Optional.ofNullable(tenantConfig).isPresent()) {
				Map<String, Object> requestMap = new HashMap<>();
				requestMap.put("flightId", flightId);
				requestMap.put("tenantAirport", TenantContext.get().getTenantConfig().getAirportCode());
				requestMap.put("shipmentId", shipmentId);
				requestMap.put("HAWBNo", houseNumber);
				requestMap.put("shipmentNumber", ShipmentNumber);
				requestMap.put("shipmentDate", ShipmentDate);
				return requestMap;
			}
		}
		return null;
	}

}
