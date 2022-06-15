package com.ngen.cosys.impbd.shipment.verification.service;

import java.util.List;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.shipment.verification.model.DgRegulations;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModel;
import com.ngen.cosys.impbd.shipment.verification.model.EliElmDGDModelList;
import com.ngen.cosys.impbd.shipment.verification.model.SearchDGDeclations;
import com.ngen.cosys.impbd.shipment.verification.model.SearchRegulationDetails;
import com.ngen.cosys.impbd.shipment.verification.model.ShipperDeclaration;

public interface DocumentVerificationService {
   
     DocumentVerificationFlightModel fetch(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;

     DocumentVerificationFlightModel documentsOffload(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;
     
     DocumentVerificationFlightModel updateShipmentRemarks(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;
     
     DocumentVerificationFlightModel documentsOnHold(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;

     DocumentVerificationFlightModel documentComplete(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;

     DocumentVerificationFlightModel updateFlightDelay(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;
         
     void reOpenDocumentComplete(DocumentVerificationFlightModel documentVerificationFlightModel)throws CustomException;

     DocumentVerificationFlightModel saveDocumentVerification(DocumentVerificationFlightModel documentVerificationFlightModel) throws CustomException;

     ShipperDeclaration create(ShipperDeclaration shippersDeclarationDetails)  throws CustomException;

     List<ShipperDeclaration> find(SearchDGDeclations search)throws CustomException;

     ShipperDeclaration deleteDgdDetails(ShipperDeclaration shipperDeclaration)throws CustomException;

     List<DgRegulations> getDgdRegulationDetails(SearchRegulationDetails dgd) throws CustomException;

     Integer getOverPackSeqNo(SearchDGDeclations search)throws CustomException;
     
     EliElmDGDModel createOrSaveEliElmData(EliElmDGDModel elmDGDModel) throws CustomException;

     EliElmDGDModel deleteEliElmData(EliElmDGDModel elmDGDModel) throws CustomException;

     EliElmDGDModel getEliElmData(EliElmDGDModel elmDGDModel)  throws CustomException;

     EliElmDGDModelList getEliElmRemark(EliElmDGDModelList elmDGDModel)  throws CustomException;
   
     public void printAwbBarcode(AWBPrintRequest request);
}
