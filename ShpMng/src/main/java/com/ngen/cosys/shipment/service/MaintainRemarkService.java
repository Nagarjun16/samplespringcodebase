package com.ngen.cosys.shipment.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.DeleteRemarkBO;
import com.ngen.cosys.shipment.model.MaintainRemark;
import com.ngen.cosys.shipment.model.RequestSearchRemarksBO;
import com.ngen.cosys.shipment.model.ResponseSearchRemarksBO;

/**
 * This interface takes care of the responsibilities related to the Maintain Remark 
 *service operation that comes from the controller. 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MaintainRemarkService {

   /**
    * get remark
    * @return List of remark inserted
    * @throws CustomException
    */
   ResponseSearchRemarksBO getRemark(RequestSearchRemarksBO searchRemarks) throws  CustomException;
   
	/**
	 * insert remark
	 * @return List of remark inserted
	 * @throws CustomException
	 */
     void insertRemark(List<MaintainRemark> parmRemarkList) throws CustomException;
	
	/**
	 * get remark
	 * @return List of remark inserted
	 * @throws CustomException
	 */
	void deleteRemark(DeleteRemarkBO shipmentNumber) throws  CustomException;
	
}
