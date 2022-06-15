/**
 * This is a interface which has business methods for capturing information in
 * InwardServiceReport
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 04/04/2018
 */
package com.ngen.cosys.inward.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.inward.model.DamageModel;
import com.ngen.cosys.inward.model.InwardSegmentConcatenate;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportOtherDiscrepancyModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;

public interface InwardServiceReportDao {

   /**
    * Method to get segment list
    * 
    * @param requestModel
    * @return List<InwardServiceReportModel>
    * @throws CustomException
    */
   List<InwardServiceReportModel> getSegments(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to get inward service report details by flight for a AWB
    * 
    * @param requestModel
    * @return InwardServiceReportModel - This object holds segments and its
    *         discrepancy details
    * @throws CustomException
    */
   InwardServiceReportModel getInwardServiceReportByAwb(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to get inward service report details by flight for a Mail
    * 
    * @param requestModel
    * @return InwardServiceReportModel - This object holds segments and its
    *         discrepancy details
    * @throws CustomException
    */
   InwardServiceReportModel getInwardServiceReportByMail(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to get other discrepancy if captured for ULD's which are damaged in
    * Ramp Check In
    * 
    * @param requestModel
    * @return List<InwardServiceReportOtherDiscrepancyModel> - List of remarks for
    *         ULD
    * @throws CustomException
    */
   List<InwardServiceReportOtherDiscrepancyModel> getInwardServiceReportByULDForOtherDiscrepancy(
         InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to check service report finalized or not for Cargo
    * 
    * @param requestModel
    * @return Boolean - true if finalized other wise false
    * @throws CustomException
    */
   Boolean checkServiceReportFinalizedForCargo(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to check service report finalized or not for Mail
    * 
    * @param requestModel
    * @return Boolean - true if finalized other wise false
    * @throws CustomException
    */
   Boolean checkServiceReportFinalizedForMail(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to create inward service report by flight
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createFlight(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to create shipment document discrepancy
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createShipmentDiscrepancy(List<InwardServiceReportShipmentDiscrepancyModel> requestModel)
         throws CustomException;

   /**
    * Method to create other discrepancy
    * 
    * @param requestModel
    * @throws CustomException
    */
   void creatOtherDiscrepancy(List<InwardServiceReportOtherDiscrepancyModel> requestModel) throws CustomException;

   /**
    * Method to finalize the service report for a flight by Cargo
    * 
    * @param requestModel
    * @throws CustomException
    */
   void finalizeServiceReportForCargo(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to finalize the service report for a flight by Mail
    * 
    * @param requestModel
    * @throws CustomException
    */
   void finalizeServiceReportForMail(InwardServiceReportModel requestModel) throws CustomException;

   List<InwardServiceReportModel> fetchDiscrepancy(InwardServiceReportModel fetchinwardService) throws CustomException;

   List<InwardServiceReportModel> fetchboardpoints(InwardServiceReportModel fetchboardpoint) throws CustomException;

   InwardServiceReportModel fetchId(InwardServiceReportModel fetchinwardService) throws CustomException;

   Integer checkDocumentCompletedOrNot(InwardServiceReportModel requestModel) throws CustomException;

   Integer checkBreakdownCompletedOrNot(InwardServiceReportModel requestModel) throws CustomException;

   Integer checkDuplicateForDoc(InwardServiceReportShipmentDiscrepancyModel requestModel) throws CustomException;

   Integer checkshipirrduplicate(InwardServiceReportShipmentDiscrepancyModel ship) throws CustomException;

   String fetchFlightInfo(InwardServiceReportModel requestModel) throws CustomException;

   Integer checkDocumentCompletedOrNotForMail(InwardServiceReportModel requestModel) throws CustomException;

   Integer checkBreakdownCompletedOrNotForMail(InwardServiceReportModel requestModel) throws CustomException;

   List<String> fetchEmailAdrressesConfigured(String string) throws CustomException;

   String getCarrierCode(BigInteger bigInteger) throws CustomException;

   Integer checkDamageStatus(InwardServiceReportModel requestModel) throws CustomException;

   List<String> fetchEmailAdrressesDamageConfigured(String carrierCode) throws CustomException;

   void unFinalizeServiceReport(InwardServiceReportModel requestModel) throws CustomException;

   BigInteger getInwardIdforAwb(InwardSegmentConcatenate segAwb) throws CustomException;

   InwardServiceReportModel getRegistrationNumber(InwardServiceReportModel requestModel) throws CustomException;

   InwardServiceReportModel getDamageInfo(InwardServiceReportModel requestModel) throws CustomException;

   List<DamageModel> getDamageReportData(InwardServiceReportModel data)  throws CustomException;

    InwardServiceReportShipmentDiscrepancyModel getAwbDetails(InwardServiceReportShipmentDiscrepancyModel requestModel) throws CustomException;
   
 






}