/**
 * 
 * HandoverInboundUldTrollyService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 23 January, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.HandoverInboundContainerTrolly;
import com.ngen.cosys.impbd.model.HandoverInboundTrolly;
import com.ngen.cosys.model.FlightModel;

/**
 * This interface takes care of the responsibilities related to the Handover
 * Inbound Trolly service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface HandoverInboundUldTrollyService {
	/**
	 * This method responsible for Fetching The Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundUldTrollyService
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> fetchInboundTrolly(FlightModel inboundTrollyList) throws CustomException;

	/**
	 * This method responsible for Update The Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundUldTrollyService
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> addUpdateTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException;

	/**
	 * This method responsible for Add The Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundUldTrollyService
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> insertInboundTrolly(HandoverInboundTrolly inboundTrolly) throws CustomException;

	/**
	 * This method responsible for Delete the Handover Inbound Trolly Number
	 * Details.
	 * 
	 * @param HandoverInboundUldTrollyService
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundContainerTrolly> deleteTrollyNo(HandoverInboundContainerTrolly handoverTrolly)
			throws CustomException;

	/**
	 * This method responsible for edit The Handover Inbound Trolly Details.
	 * 
	 * @param HandoverInboundUldTrollyService
	 * @return
	 * @throws CustomException
	 */
	List<HandoverInboundTrolly> editInboundTrolly(HandoverInboundTrolly editTrollyData) throws CustomException;
}
