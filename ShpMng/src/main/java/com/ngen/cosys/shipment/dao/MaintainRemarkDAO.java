/**
 * CodeShareFlightDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date			Author      Reason
 * 1.0          04 Jan, 2018	NIIT      -
 */
package com.ngen.cosys.shipment.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;

public interface MaintainRemarkDAO {

   /**
    * get code maintain remark dao
    * @return list of successfully inserted remarks
    * @throws CustomException
    */
   ResponseSearchRemarksBO fetch(RequestSearchRemarksBO searchRemarks) throws  CustomException;
   
	/**
	 * Create code mamintain remark dao
	 * @return list of successfully inserted remarks
	 * @throws CustomException
	 */
    void insert(List<MaintainRemark> paramRemarksList) throws  CustomException; 
    void insertHWB(List<MaintainRemark> paramRemarksList) throws  CustomException;
	/**
	 * delete code maintain remark dao
	 * @return list of successfully inserted remarks
//	 * @throws CustomException
	 */
	MaintainRemark getRemarksDetails(int remarkId) throws CustomException;

	void delete(MaintainRemark rmk) throws CustomException;
}
