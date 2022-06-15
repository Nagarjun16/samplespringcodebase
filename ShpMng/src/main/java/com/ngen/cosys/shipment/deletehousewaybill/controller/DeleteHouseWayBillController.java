
package com.ngen.cosys.shipment.deletehousewaybill.controller;

import java.math.BigInteger;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillResponseModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.DeleteHouseWayBillSearchModel;
import com.ngen.cosys.shipment.deletehousewaybill.model.UpdateShipmentDetails;
import com.ngen.cosys.shipment.deletehousewaybill.service.DeleteHouseWayBillService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * DeleteHouseWayBillController.java , this controller is responsible for Deletion 
 * Of house Way Bills for DAXING
 *
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation
public class DeleteHouseWayBillController {
	
	@Autowired
	private DeleteHouseWayBillService deleteHouseWayBillService;
	
	@Autowired
	private UtilitiesModelConfiguration utility;
	
		
	/**
	    * this controller method gives the HAWB visibility whether there is any house 
	    * exists for AWB or not
	    * @param deleteHouseWayBillSearchModel
	    * @return
	    * @throws CustomException
	    */
	@ApiOperation("Check whether shipment has house or not")
	   @PostRequest(value = "/api/shipment/deleteHouse/deleteHouseWayBill", method = RequestMethod.POST)
	   public BaseResponse<DeleteHouseWayBillResponseModel> deleteHouseWayBill(
	         @ApiParam("DeleteHouseWayBillSearchModel") @Validated @RequestBody  DeleteHouseWayBillSearchModel deleteHouseWayBillSearchModel)
	         throws CustomException {
	      @SuppressWarnings("unchecked")
	      BaseResponse<DeleteHouseWayBillResponseModel> baseResponse = utility.getBaseResponseInstance();
	      //String expImpChck=deleteHouseWayBillService.checkForShipmentIsImportExport(deleteHouseWayBillSearchModel);
	     /* if(expImpChck.equalsIgnoreCase("E")) {
	    	  DeleteHouseWayBillResponseModel response = deleteHouseWayBillService.deleteHouseWayBillExport(deleteHouseWayBillSearchModel);
	  		if(response == null) {
				throw new CustomException("hawb.house.already.deleted", "form", ErrorType.ERROR);
			}
	    	  baseResponse.setData(response);*/
	      //}else {
	      
	         UpdateShipmentDetails updateShipmentDetail = null;
	         if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
	        	 updateShipmentDetail = deleteHouseWayBillService.getShipmentDetails(deleteHouseWayBillSearchModel);
	         }
	    	  DeleteHouseWayBillResponseModel response = deleteHouseWayBillService.deleteHouseWayBill(deleteHouseWayBillSearchModel);
	    	  baseResponse.setData(response);
	    	  if (FeatureUtility.isFeatureEnabled(ApplicationFeatures.Imp.Bd.TriggerStatusUpdateMsg.class)) {
	    		  
	    		  deleteHouseWayBillService.publishShipmentEvent(deleteHouseWayBillSearchModel, updateShipmentDetail);
	    	  }
	  		if (!ObjectUtils.isEmpty(response)) {
	  			BillingShipment billingShipment = new BillingShipment();
	  			billingShipment.setShipmentId(BigInteger.valueOf(response.getShipmentId()));
	  			billingShipment.setShipmentNumber(deleteHouseWayBillSearchModel.getShipmentNumber());
	  			billingShipment.setShipmentDate(deleteHouseWayBillSearchModel.getShipmentDate());
	  			billingShipment.setProcessType(ChargeEvents.PROCESS_IMP.getEnum());
	  			billingShipment.setEventType(ChargeEvents.IMP_SHIPMENT_UPDATE);
	  			billingShipment.setHandlingTerminal(deleteHouseWayBillSearchModel.getTerminal());
	  			billingShipment.setUserCode(deleteHouseWayBillSearchModel.getLoggedInUser());
	  			// calculate charge
	  			try {
	  				Charge.calculateCharge(billingShipment);
	  			} catch (Exception e) {
	  				e.printStackTrace();
	  			}
	  		}
	      	
	     
	    
	      return baseResponse;
	   }
	

}
