/**
 * This is a interface which has business methods for capturing information in
 * InwardServiceReport
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 04/04/2018
 */
package com.ngen.cosys.inward.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;

public interface InwardServiceReportService {

   /**
    * Method to create service report data on break down/document complete
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createServiceReportOnFlightComplete(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to create service report data on ramp check in complete for ULD's
    * which have been marked as damaged and added with other discrepancy
    * 
    * @param requestModel
    * @throws CustomException
    */
   void createServiceReportOnRampCheckInComplete(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method to create service report data
    * 
    * @param requestModel
    * @throws CustomException
    */
   InwardServiceReportModel createServiceReport(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method which finalizes service report for a flight
    * 
    * @param requestModel
    * @throws CustomException
    */
   void finalizeServiceReportForCargo(InwardServiceReportModel requestModel) throws CustomException;

   /**
    * Method which fetch service report data
    * 
    * @param requestModel
    * @throws CustomException
    */

   List<InwardServiceReportModel> fetch(InwardServiceReportModel fetchinwardService) throws CustomException;

   /**
    * Method which fetchboardpoints
    * 
    * @param requestModel
    * @throws CustomException
    */

   List<InwardServiceReportModel> fetchBoardPoint(InwardServiceReportModel fetchboardpoint) throws CustomException;

   void finalizeServiceReportForMail(InwardServiceReportModel requestModel) throws CustomException;

   void sendemailReport(InwardServiceReportModel requestModel) throws CustomException;

    void createServiceReportForSave(InwardServiceReportModel requestModel) throws CustomException;

    InwardServiceReportShipmentDiscrepancyModel getAwbDetails(InwardServiceReportShipmentDiscrepancyModel requestModel) throws CustomException;

}