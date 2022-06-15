package com.ngen.cosys.impbd.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.ArrivalManifestByFlightModel;
import com.ngen.cosys.impbd.model.ArrivalManifestShipmentInfoModel;
import com.ngen.cosys.impbd.model.ArrivalManifestUldModel;
import com.ngen.cosys.impbd.model.SearchArrivalManifestModel;

public interface ArrivalManifestService {

   ArrivalManifestByFlightModel fetchShipmentDetails(SearchArrivalManifestModel searchArrivalManifestModel)
         throws CustomException;

   void createULDShipment(ArrivalManifestByFlightModel arrivalFlightDetails) throws CustomException;

   void deleteULDShipment(ArrivalManifestUldModel arrivalFlightDetails) throws CustomException;

   void createAdditionalInfo(ArrivalManifestShipmentInfoModel arrivalFlightDetails) throws CustomException;

   void createPreannounceMentData(ArrivalManifestByFlightModel arrivalFlightDetails) throws CustomException;

   boolean checkValidFlight(ArrivalManifestByFlightModel flightData) throws CustomException;

   ArrivalManifestShipmentInfoModel fetchRoutingDetail(ArrivalManifestByFlightModel routingData) throws CustomException;

   void postProcessManifestInformation(ArrivalManifestByFlightModel airlineFlightManifest) throws CustomException;

   void createThroughTransitAdvice(ArrivalManifestByFlightModel airlineFlightManifest) throws CustomException;
   
   boolean isImportTransShipment(ArrivalManifestShipmentInfoModel reuseShipmentValidatorModel) throws CustomException;

}