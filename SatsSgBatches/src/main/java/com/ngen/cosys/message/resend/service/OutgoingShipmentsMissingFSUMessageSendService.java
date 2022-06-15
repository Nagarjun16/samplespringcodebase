/**
 * Service component which has methods exposed for sending FSU messages that were missed out to send
 */

package com.ngen.cosys.message.resend.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface OutgoingShipmentsMissingFSUMessageSendService {

	/**
	 * Method to re-fire DEP for shipments which did not had DEP triggered
	 * 
	 * @throws CustomException
	 */
	void refireDEPMissingShipments() throws CustomException;

	/**
	 * Method to re-fire MAN for shipments which did not had MAN triggered
	 * 
	 * @throws CustomException
	 */
	void refireMANMissingShipments() throws CustomException;

	/**
	 * Method to re-fire RCS for shipments which did not had RCS triggered
	 * 
	 * @throws CustomException
	 */
	void refireRCSMissingShipments() throws CustomException;

}