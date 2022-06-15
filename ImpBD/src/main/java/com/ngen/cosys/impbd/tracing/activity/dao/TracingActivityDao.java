/**
 * This is a repository interface which has methods for creating a tracing record
 */
package com.ngen.cosys.impbd.tracing.activity.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentInventoryModel;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentModel;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentShcModel;

public interface TracingActivityDao {

	/**
	 * Method to get flight information
	 * 
	 * @param requestModel
	 * @return TracingActivityShipmentModel
	 * @throws CustomException
	 */
	TracingActivityShipmentModel getFlightInfo(TracingActivityShipmentModel requestModel) throws CustomException;

	/**
	 * Method to get SHC of an Shipment
	 * 
	 * @param requestModel
	 * @return TracingActivityShipmentShcModel
	 * @throws CustomException
	 */
	List<TracingActivityShipmentShcModel> getShc(TracingActivityShipmentModel requestModel) throws CustomException;

	/**
	 * Method to max case number
	 * 
	 * @return BigInteger
	 * @throws CustomException
	 */
	BigInteger getMaxCaseNumber() throws CustomException;

	/**
	 * Method to check tracing exists for a shipment
	 * 
	 * @param requestModel
	 * @return Boolean
	 * @throws CustomException
	 */
	Boolean checkTracingActivityExistsForShipment(TracingActivityShipmentModel requestModel) throws CustomException;

	/**
	 * Method to create tracing for a shipment
	 * 
	 * @param requestModel
	 * @throws CustomException
	 */
	void createTracingActivityForShipment(TracingActivityShipmentModel requestModel) throws CustomException;

	/**
	 * Add SHC for a shipment
	 * 
	 * @param requestModel
	 * @throws CustomException
	 */
	void addShcToTracingActivity(List<TracingActivityShipmentShcModel> requestModel) throws CustomException;

	List<TracingActivityShipmentInventoryModel> getInventoryInfoForTracing(TracingActivityShipmentModel t) throws CustomException;

	void addInventoryToTracingActivity(List<TracingActivityShipmentInventoryModel> tracingShipmentInventory) throws CustomException;
	
	/**
	 * Method to delete the tracing if it exists and not closed
	 * 
	 * @param requestModel
	 * @throws CustomException
	 */
	void deleteTracingActivityForShipment(TracingActivityShipmentModel requestModel) throws CustomException;

}