/**
 * 
 * LoadShipmentService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 6 March, 2018 NIIT -
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.BuildupLoadShipment;
import com.ngen.cosys.satssginterfaces.mss.model.CommonLoadShipment;

/**
 * Service to serve Load Shipment related request
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface LoadShipmentService {

   public BuildupLoadShipment insertLoadShipment(BuildupLoadShipment loadModel) throws CustomException;
	/**
	 * @param comman
	 * @throws CustomException
	 */
	void insertLoadingData(CommonLoadShipment comman) throws CustomException;

}