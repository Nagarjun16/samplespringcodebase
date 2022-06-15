/**
 * PrintAWBBarCodeDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date			Author      Reason
 * 1.0         23 January, 2018	NIIT      -
 */
package com.ngen.cosys.impbd.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AWBPrintRequest;

/**
 * This interface takes care of the responsibilities related to the
 * PrintAWBBarCode DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface PrintAWBBarCodeDAO {
	/**
	 * Validates AWB number against Arival manifest shipment info
	 * 
	 * @param awbDetail
	 * @return int
	 * @throws CustomException
	 */
boolean validateAWBNumber(AWBPrintRequest awbDetail) throws CustomException;

}
