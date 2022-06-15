package com.ngen.cosys.shipment.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.FWB;
import com.ngen.cosys.shipment.model.FWBDetails;
import com.ngen.cosys.shipment.model.FetchFWBRequest;
import com.ngen.cosys.shipment.model.FetchRouting;
import com.ngen.cosys.shipment.model.Routing;

public interface MaintainFWBService {

   FWB get(FetchFWBRequest requestModel) throws CustomException;

   FWB save(FWB requestModel) throws CustomException;

   void delete(FWB requestModel) throws CustomException;

   FWB flagReadStatus(FWB fwb) throws CustomException;

   Routing fetchRoutingDetails(FetchRouting requestModel) throws CustomException;

  /* void checkDuplicate(AccountingInfo reqParam) throws CustomException;

   void checkDuplicateRateDesc(RateDescription reqParam) throws CustomException;

   void checkDuplicateRateDescOtherInfo(RateDescOtherInfo reqParam) throws CustomException;

   void checkDuplicateOtherCharges(OtherCharges reqParam) throws CustomException;

   void checkDuplicateOtherParticipantInfo(OtherParticipantInfo reqParam) throws CustomException;

   void checkDuplicateOtherCustomsInfo(OtherCustomsInfo reqParam) throws CustomException;*/
   
   FWBDetails fetchFWBDetailsForMobile(FWBDetails requestModel) throws CustomException;
   
   FWBDetails saveFWBDetailsForMobile(FWBDetails requestModel) throws CustomException;
   
   Boolean checkisShipmentDelivered(FWB requestModel) throws CustomException;
}