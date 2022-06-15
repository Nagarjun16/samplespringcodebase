/**
 *   ChangeOfAwbHawbServiceImpl.java
 *   
 *   Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 May, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.changeofawb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.shipment.changeofawb.dao.ChangeOfAwbHawbDAO;
import com.ngen.cosys.shipment.changeofawb.model.ChangeOfAwbHawb;

/**
 * 
 * Service Implementation of change of AWB methods
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@Service
@Transactional
public class ChangeAwbHawbServiceImpl implements ChangeAwbHawbService {

   @Autowired
   private ChangeOfAwbHawbDAO changeAwbHawbDao;
   
   @Autowired
   HAWBHandlingHelper  hawbHandlingHelper;

   @Override
   public ChangeOfAwbHawb updateAwbNumber(ChangeOfAwbHawb request) throws CustomException {
	   if(changeAwbHawbDao.CheckAnyPaidChargeForAWB(request)) {
		   request.addError("awb.paid.charges.exist.awb", null, ErrorType.ERROR);
	   } else {
		   request = changeAwbHawbDao.updateAwbNumber(request);
	   }
	   return request;
   }

   @Override
   public ChangeOfAwbHawb updateHawbNumber(ChangeOfAwbHawb request) throws CustomException {
	   
	   if(changeAwbHawbDao.CheckAnyPaidChargeForAWB(request)) {
		   request.addError("awb.paid.charges.exist.hawb", null, ErrorType.ERROR);
		   return request;
	   } else {
		   //checking handledByHouse 
		   HAWBHandlingHelperRequest helperRequest=new HAWBHandlingHelperRequest();
		   helperRequest.setShipmentNumber(request.getShipmentNumber());
		   helperRequest.setShipmentDate(request.getShipmentDate());
		   boolean handledByHouse=hawbHandlingHelper.isHandledByHouse(helperRequest);
		   if(handledByHouse) {
			   //if handledByHouse then update in shipmentHouseInformation
			  request = changeAwbHawbDao.updateHawbNumberHandledByHouse(request);
			  return request;
		  }
		  else {
			  //if not handledByHouse then update in  Shipment_FreightHouseListByHAWB
	          request = changeAwbHawbDao.updateHawbNumber(request);
	          return request;
		  }
	   }

   }
   

}