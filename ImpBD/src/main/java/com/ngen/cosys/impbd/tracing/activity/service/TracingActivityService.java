/**
 * This is a interface which has business methods to create tracing activity for a irregularity found/part shipments
 */
package com.ngen.cosys.impbd.tracing.activity.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.tracing.activity.model.TracingActivityShipmentModel;

public interface TracingActivityService {

	/**
	 * This is a method which creates a tracing record for a given shipment
	 * 
	 * @param requestModel
	 * @throws CustomException
	 */
	void createTracing(List<TracingActivityShipmentModel> requestModel) throws CustomException;
	
	/**
	 * Method to delete the tracing if it exists and not closed
	 * 
	 * @param requestModel
	 * @throws CustomException
	 */
	void deleteTracingActivityForShipment(List<TracingActivityShipmentModel> requestModel) throws CustomException;

}