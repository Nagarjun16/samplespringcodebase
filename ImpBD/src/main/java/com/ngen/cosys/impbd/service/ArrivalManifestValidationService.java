package com.ngen.cosys.impbd.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;

public interface ArrivalManifestValidationService {
   
   
   
   public List<ArrivalManifestShipmentInfoModel> fetchDuplicateShipments(ArrivalManifestShipmentInfoModel shipmentNumber) throws CustomException;
   
   public List<ArrivalManifestUldModel> fetchUldDetails(ArrivalManifestUldModel uldData) throws CustomException;
   
   public List<ArrivalManifestShipmentInfoModel> fetchShipments(ArrivalManifestShipmentInfoModel shipmentNumber) throws CustomException;
   
   public List<ArrivalManifestShipmentInfoModel> fetchDuplicateSplitShipments(ArrivalManifestShipmentInfoModel shipmentNumber) throws CustomException;
   
   
   

}
