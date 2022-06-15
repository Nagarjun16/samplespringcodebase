/**
 * PrintAWBBarCodeDAOImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date			Author      Reason
 * 1.0         23 January, 2018	NIIT      -
 */
package com.ngen.cosys.impbd.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AWBPrintRequest;


/**
 * This class takes care of the responsibilities related to the PrintAWBBarCode DAO
 * operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("printAWBBarCodeDAO")
public class PrintAWBBarCodeDAOImpl extends BaseDAO implements PrintAWBBarCodeDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionShipment;
	/* (non-Javadoc)
	 * @see com.ngen.cosys.shipment.dao.PrintAWBBarCodeDAO#validateAWBNumber(com.ngen.cosys.shipment.model.SearchAWBManifest)
	 */
	@Override
	public boolean validateAWBNumber(AWBPrintRequest awbDetails) throws CustomException {
		int count = super.fetchObject("isAWBExists",awbDetails.getAwbNumber(),sqlSessionShipment) ;
	return count > 0;
	}
	
	

}
