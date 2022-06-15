/**
 * Repository interface which has methods to get missing FSU messages for import shipments
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDeliveredStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDocumentReleaseStoreEvent;
import com.ngen.cosys.framework.exception.CustomException;

public interface IncomingShipmentsMissingFSUMessageSendDAO {

	/**
	 * Method to get list of shipments for which RCF/NFD is missing
	 * 
	 * @return List<InboundShipmentBreakDownCompleteStoreEvent> - List of shipments
	 * @throws CustomException
	 */
	List<InboundShipmentBreakDownCompleteStoreEvent> getBreakDownCompleteShipments() throws CustomException;

	/**
	 * Method to get list of shipments for which AWD is missing
	 * 
	 * @return List<InboundShipmentDocumentReleaseStoreEvent> - List of shipments
	 * @throws CustomException
	 */
	List<InboundShipmentDocumentReleaseStoreEvent> getDocumentReleasedShipments() throws CustomException;

	/**
	 * Method to get list of shipments for which DLV is missing
	 * 
	 * @return List<InboundShipmentDeliveredStoreEvent> - List of shipments
	 * @throws CustomException
	 */
	List<InboundShipmentDeliveredStoreEvent> getDeliveredShipments() throws CustomException;

}