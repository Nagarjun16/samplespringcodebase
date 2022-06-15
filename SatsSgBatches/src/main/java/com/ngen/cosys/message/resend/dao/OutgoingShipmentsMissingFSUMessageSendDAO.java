/**
 * Repository interface which has methods to get missing FSU messages for export shipments
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import com.ngen.cosys.events.payload.OutboundShipmentFlightCompletedStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentManifestedStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.OutgoingShipmentsMissingFSURCSMessageSendModel;

public interface OutgoingShipmentsMissingFSUMessageSendDAO {

	/**
	 * Method to get list of shipments for which MAN is missing
	 * 
	 * @return List<OutboundShipmentManifestedStoreEvent> - List of shipments
	 * @throws CustomException
	 */
	List<OutboundShipmentManifestedStoreEvent> getManifestCompleteShipments() throws CustomException;

	/**
	 * Method to get list of shipments for which DEP is missing
	 * 
	 * @return List<OutboundShipmentFlightCompletedStoreEvent> - List of shipments
	 * @throws CustomException
	 */
	List<OutboundShipmentFlightCompletedStoreEvent> getFlightCompletedShipments() throws CustomException;

	/**
	 * Method to get list of shipments for which RCS is missing
	 * 
	 * @return List<OutgoingShipmentsMissingFSURCSMessageSendModel> - List
	 *         of shipments
	 * @throws CustomException
	 */
	List<OutgoingShipmentsMissingFSURCSMessageSendModel> getAcceptanceFinalizeShipmens() throws CustomException;

}