/**
 * Service component which has methods exposed for sending FSU messages that were missed out to send
 */
package com.ngen.cosys.message.resend.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface IncomingShipmentsMissingFSUMessageSendService {

	/**
	 * Method to re-fire RCF for shipments which did not had RCF/NFD triggered
	 * 
	 * @throws CustomException
	 */
	void refireRCFNFDMissingShipments() throws CustomException;

	/**
	 * Method to re-fire AWD for shipments which did not had AWD triggered
	 * 
	 * @throws CustomException
	 */
	void refireAWDMissingShipments() throws CustomException;

	/**
	 * Method to re-fire DLV for shipments which did not had DLV triggered
	 * 
	 * @throws CustomException
	 */
	void refireDLVMissingShipments() throws CustomException;

}