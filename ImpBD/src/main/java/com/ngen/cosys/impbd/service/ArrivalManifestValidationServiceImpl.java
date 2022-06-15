/**
 * 
 */
package com.ngen.cosys.impbd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.dao.ArrivalManifestValidationDao;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;

@Service
public class ArrivalManifestValidationServiceImpl implements ArrivalManifestValidationService {
   
   @Autowired
   private ArrivalManifestValidationDao validationDao;

   

   @Override
   public List<ArrivalManifestShipmentInfoModel> fetchDuplicateShipments(
         ArrivalManifestShipmentInfoModel shipmentNumber) throws CustomException {
      
      return validationDao.checkShipmentExists(shipmentNumber);
   }

   @Override
   public List<ArrivalManifestUldModel> fetchUldDetails(ArrivalManifestUldModel uldData) throws CustomException {
      // TODO Auto-generated method stub
      return validationDao.fetchUldInfo(uldData);
   }

   @Override
   public List<ArrivalManifestShipmentInfoModel> fetchShipments(ArrivalManifestShipmentInfoModel shipmentNumber)
         throws CustomException {
      // TODO Auto-generated method stub
      return validationDao.fetchShipmentExists(shipmentNumber);
   }

@Override
public List<ArrivalManifestShipmentInfoModel> fetchDuplicateSplitShipments(
		ArrivalManifestShipmentInfoModel shipmentNumber) throws CustomException {
	return validationDao.fetchDuplicateSplitShipments(shipmentNumber);
}
   
   

}
