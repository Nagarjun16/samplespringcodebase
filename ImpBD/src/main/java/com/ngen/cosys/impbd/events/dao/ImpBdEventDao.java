/**
 * Repository interface for business methods which is used by Imp BD Event processors
 * 
 * 1. Customs Submission
 * 2. EAW Shipment - Send Arrival Notification
 * 3. EAP Shipment - Send Arrival Notification
 * 4. General Shipment - Send Arrival Notification
 */
package com.ngen.cosys.impbd.events.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.awb.notification.model.AwbNotificationShipment;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.GeneralShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.InboundShipmentInfoModel;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;


public interface ImpBdEventDao {

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
	
	/**
     * Method to fetch all inbound EAW shipments for IVRS
     * 
     * @param notificationDetail
     * @throws CustomException
     * @return List<AwbNotificationShipment> - List of shipments which are
     *         associated with EAW SHC handling group
     */
    List<AwbNotificationShipment> getIVRSEAWShipments(ShipmentNotificationDetail notificationDetail)
          throws CustomException;

    /**
     * Method to fetch all inbound EAP shipments for IVRS
     * 
     * @param notificationDetail
     * @throws CustomException
     * @return List<AwbNotificationShipment> - List of shipments which are
     *         associated with EAP SHC handling group
     */
    List<AwbNotificationShipment> getIVRSEAPShipments(ShipmentNotificationDetail notificationDetail)
          throws CustomException;

    /**
     * Method to fetch all inbound general shipments for IVRS
     * 
     * @param notificationDetail
     * @throws CustomException
     * @return List<AwbNotificationShipment> - List of shipments which general i.e.
     *         excluding EAW/EAP shipments
     */
    List<AwbNotificationShipment> getIVRSGeneralShipments(ShipmentNotificationDetail notificationDetail) 
          throws CustomException;

	//List<InwardServiceReportPayloadModel> getPartA(InwardReportEvent payload)  throws CustomException;

	

	List<String> getEmailAddress(String carrierCode) throws CustomException;

	InwardReportEvent getCarrier(BigInteger flightId)  throws CustomException;



}
