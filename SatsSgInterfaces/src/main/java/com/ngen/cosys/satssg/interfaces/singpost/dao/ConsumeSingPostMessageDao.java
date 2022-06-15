/**
 * 
 * ConsumeSingPostMessageDao.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          25 May, 2018 NIIT      -
 */

package com.ngen.cosys.satssg.interfaces.singpost.dao;

import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;

/**
 * This consumer interface takes care of the DB interaction activities for the
 * incoming SINGPOST messages.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ConsumeSingPostMessageDao {

	/**
	 * Reference implementation for consuming incoming messages.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel insertMailBagStatus(MailBagResponseModel bag) throws CustomException;

	/**
	 * Fetches the given Operative Flight Details which is been associated with the
	 * Mailbag.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel fetchFlightDetails(MailBagResponseModel bag) throws CustomException;

	/**
	 * Inserts the bag details information received through SINGPOST interface.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel insertBagDetails(MailBagResponseModel bag) throws CustomException;

	/**
	 * Validates if the specified Mailbag is present into DB.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel validateMailBag(MailBagResponseModel bag) throws CustomException;

	/**
	 * Updates Mailbag Status.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel updateMailBagStatus(MailBagResponseModel bag) throws CustomException;

	/**
	 * Updates Mailbag details including the pierces, weight, Flight.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel updateBagDetails(MailBagResponseModel bag) throws CustomException;

	/**
	 * Insert Mailbag details as IPS-AA Message.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel insertIpsAA(MailBagResponseModel bag) throws CustomException;

	/**
	 * Insert Mailbag details as IPS-AA Message.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */
	MailBagResponseModel dlvMailBags(MailBagResponseModel bag) throws CustomException;

	/**
	 * Inserts the bag details information received through SINGPOST interface.
	 * 
	 * @param bag.
	 * @return instance of MailBagResponseModel.
	 * @throws instance
	 *             of CustomException.
	 */

	MailBagResponseModel insertBagDetailsShc(MailBagResponseModel bag) throws CustomException;

	MailBagResponseModel getBookingIdForPADetail(MailBagResponseModel requestModel) throws CustomException;

	MailBagResponseModel updateMailBagStatusForPADetail(MailBagResponseModel requestModel) throws CustomException;

	MailBagResponseModel insertMailBagStatusForPADetail(MailBagResponseModel requestModel) throws CustomException;

	AirmailStatusEvent getMailBagInfoForSingPostDLV(AirmailStatusEvent event) throws CustomException;

	void saveMailBagsForPostalAuthorities(MailBagResponseModel bag) throws CustomException;
}