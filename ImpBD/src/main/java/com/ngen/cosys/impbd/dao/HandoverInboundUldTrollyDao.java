/**
 * 
 * HandoverInboundUldTrollyDao.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.FlightEventsModel;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.impbd.model.HandoverRampCheckInModel;
import com.ngen.cosys.model.FlightModel;

/**
 * This interface takes care of the responsibilities related to the Handover
 * Inbound Trolly DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
public interface HandoverInboundUldTrollyDao {
	/**
	 * It is responsible to fetching The Handover Inbound Trolly Details.
	 * 
	 * @param ExportPrelodgeShipment
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> fetchInboundTrolly(FlightModel inboundTrollyList) throws CustomException;

	/**
	 * this method used for update the Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> updateInboundTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException;

	/**
	 * this method used for Add the Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> insertInboundTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException;

	/**
	 * this method used for Delete the Handover Inbound Trolly Number Details.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundContainerTrolly> deleteTrollyNo(HandoverInboundContainerTrolly deleteTrollyNo)
			throws CustomException;

	/**
	 * this method used for Add the HandoverInboundContainerTrolly Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundContainerTrolly> searchContainerTrollyId(HandoverInboundContainerTrolly handoverInbound)
			throws CustomException;

	/**
	 * this method used for Add the Handover InboundContainerTrolly Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundContainerTrolly insertContainerTrolly(HandoverInboundContainerTrolly insertTrolly)
			throws CustomException;

	/**
	 * this method used for add the HandoverInboundContainerTrolly Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundContainerTrolly updateContainerTrolly(HandoverInboundContainerTrolly updateTrolly)
			throws CustomException;

	/**
	 * It is responsible to edit The Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> editInboundTrolly(HandoverInboundTrolly editTrollyData) throws CustomException;

	/**
	 * It is responsible to find The flight Id Details.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundTrolly getFlightId(HandoverInboundTrolly flightId) throws CustomException;

	/**
	 * It is responsible to find The trip Details.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundTrolly tripDetails(HandoverInboundTrolly tripDetails) throws CustomException;

	/**
	 * It is responsible to find The update Flight Events.
	 * 
	 * @param FlightEventsModel
	 * @return
	 * @throws CustomException
	 */
	FlightEventsModel updatetractorId(FlightEventsModel update) throws CustomException;

	/**
	 * It is responsible to find The check Flight Events.
	 * 
	 * @param FlightEventsModel
	 * @return
	 * @throws CustomException
	 */
	FlightEventsModel checkFligtId(FlightEventsModel update) throws CustomException;

	/**
	 * It is responsible to find The get date.
	 * 
	 * @param HandoverInboundTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundTrolly getData(HandoverInboundTrolly data) throws CustomException;

	/**
	 * It is responsible to find The update Flight Events.
	 * 
	 * @param FlightEventsModel
	 * @return
	 * @throws CustomException
	 */
	FlightEventsModel updatetractorData(FlightEventsModel updateData) throws CustomException;

	/**
	 * this method used for Add the Handover RamCHeckIn Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverRampCheckInModel insertRampCheckIn(HandoverRampCheckInModel handoverRampCheckIn) throws CustomException;

	/**
	 * this method used for Add the HandoverRamCHeckIn Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	List<HandoverRampCheckInModel> searchRampCheckInId(HandoverRampCheckInModel handoverRamp) throws CustomException;

	/**
	 * this method used for add the HandoverRamCHeckIn Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverRampCheckInModel updateRampCheckIn(HandoverRampCheckInModel updateRampCheckIn) throws CustomException;

	/**
	 * this method used for Add the HandoverInboundContainerTrolly Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundContainerTrolly searchContentCode(HandoverInboundContainerTrolly handoverInbound)
			throws CustomException;

	/**
	 * this method used for Add the HandoverInboundContainerTrolly Details.
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	HandoverInboundContainerTrolly searchContentCodeType(HandoverInboundContainerTrolly handoverInbound)
			throws CustomException;

	/**
	 * this method checks for duplicate trolley number
	 * 
	 * @param HandoverInboundContainerTrolly
	 * @return
	 * @throws CustomException
	 */
	List<String> searchContainerTrollyForDuplicate(HandoverInboundTrolly inboundTrolly) throws CustomException;

	/**
	 * Method check ramp check in is completed or not
	 * 
	 * @param flightId
	 * @return boolean - true if completed otherwise false
	 * @throws CustomException
	 */
	boolean isCheckInCompleted(BigInteger flightId) throws CustomException;
}
