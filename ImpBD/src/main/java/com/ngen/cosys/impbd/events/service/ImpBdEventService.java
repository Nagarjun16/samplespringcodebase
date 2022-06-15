/**
 * Service interface for business methods which is used by Imp BD Event processors
 * 
 * 1. Customs Submission
 * 2. EAW Shipment - Send Arrival Notification
 * 3. EAP Shipment - Send Arrival Notification
 * 4. General Shipment - Send Arrival Notification
 */
package com.ngen.cosys.impbd.events.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.GeneralShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.InboundShipmentInfoModel;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;

import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.inward.model.InwardServiceReportModel;

public interface ImpBdEventService {

	/**
	 * Method to fetch the shipment info for customs submission
	 * 
	 * @param payload
	 * @throws CustomException
	 * @return List<InboundShipmentInfoModel>
	 */
	List<InboundShipmentInfoModel> getInboundCustomsShipmentInfo(SingaporeCustomsDataSyncEvent payload)
			throws CustomException;

	/**
	 * Method to fetch all inbound EAW shipments
	 * 
	 * @param payload
	 * @throws CustomException
	 * @return List<InboundShipmentInfoModel> - List of shipments which are
	 *         associated with EAW SHC handling group
	 */
	List<InboundShipmentInfoModel> getEAWShipments(EAWShipmentsEvent payload) throws CustomException;

	/**
	 * Method to fetch all inbound EAP shipments
	 * 
	 * @param payload
	 * @throws CustomException
	 * @return List<InboundShipmentInfoModel> - List of shipments which are
	 *         associated with EAP SHC handling group
	 */
	List<InboundShipmentInfoModel> getEAPShipments(EAPShipmentsEvent payload) throws CustomException;

	/**
	 * Method to fetch all inbound general shipments
	 * 
	 * @param payload
	 * @throws CustomException
	 * @return List<InboundShipmentInfoModel> - List of shipments which general i.e.
	 *         excluding EAW/EAP shipments
	 */
	List<InboundShipmentInfoModel> getGeneralShipments(GeneralShipmentsEvent payload) throws CustomException;

	/**
	 * Method to update NOA status for shipments on which notification email has
	 * been triggered
	 * 
	 * @param payload
	 *            - List<InboundShipmentInfoModel> - shipments list for which email
	 *            needs to be triggered
	 * @throws CustomException
	 */
	void updateNOAForShipment(List<InboundShipmentInfoModel> payload) throws CustomException;

	

	InwardReportEvent getCarrierCode(BigInteger flightId) throws CustomException;

	List<String> fetchEmailAdrressesConfigured(String carrierCode) throws CustomException;

	//List<InwardServiceReportPayloadModel> getPartA(InwardReportEvent payload) throws CustomException;

}