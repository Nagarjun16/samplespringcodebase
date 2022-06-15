package com.ngen.cosys.impbd.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;

public interface ArrivalManifestValidationDao {
   
   public List<ArrivalManifestShipmentInfoModel> checkShipmentExists(ArrivalManifestShipmentInfoModel shipmentModel)throws CustomException;
   
   public List<ArrivalManifestUldModel> fetchUldInfo(ArrivalManifestUldModel shipmentModel)throws CustomException;
   
   public List<ArrivalManifestShipmentInfoModel> fetchShipmentExists(ArrivalManifestShipmentInfoModel shipmentModel)throws CustomException;
   
   public List<ArrivalManifestShipmentInfoModel> fetchDuplicateSplitShipments(ArrivalManifestShipmentInfoModel shipmentModel)throws CustomException;

	Boolean checkSegmentLevelDuplicateShipments(ArrivalManifestShipmentInfoModel shipmentModel) throws CustomException;

	/**
	 * Method to check shipment exists for an ULD OR under Loose
	 * 
	 * @param shipmentModel
	 * @return Boolean - true if exists
	 * @throws CustomException
	 */
	Boolean isShipmentExistsInArrivalManifest(ArrivalManifestShipmentInfoModel shipmentModel) throws CustomException;
	
}
