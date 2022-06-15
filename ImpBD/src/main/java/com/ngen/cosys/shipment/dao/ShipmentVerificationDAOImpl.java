/**
 * ShipmentVerificationDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 23 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.model.AWBPrintRequest;
import com.ngen.cosys.impbd.model.AWBPrintResponse;
import com.ngen.cosys.model.ShipmentModel;

/**
 * This class takes care of the responsibilities related to creating the
 * shipment document verification DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("ShipmentVerificationDAO")
public class ShipmentVerificationDAOImpl extends BaseDAO implements ShipmentVerificationDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionImpBd;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.dao.ShipmentVerificationDAO#
    * createVerificationDocument(com.ngen.cosys.shipment.model.ShipmentInfo)
    */
   @Override
   public void createVerificationDocument(List<ShipmentModel> shipmentInfo) throws CustomException {
      super.insertData(SqlIDs.SHP_VERFTN_DOC.toString(), shipmentInfo, sqlSessionImpBd);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.dao.PrintAWBBarCodeDAO#validateAWBNumber(com.ngen.
    * cosys.shipment.model.SearchAWBManifest)
    */
   @Override
   public boolean validateAWBNumber(AWBPrintRequest awbDetails) throws CustomException {
      int count = super.fetchObject(SqlIDs.AWB_EXISTS.toString(), awbDetails.getAwbNumber(), sqlSessionImpBd);
      return count > 0;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.shipment.dao.ShipmentVerificationDAO#getShipmentInformation(
    * com.ngen.cosys.barcodeprint.model.AWBPrintRequest)
    */
   @Override
   public List<AWBPrintResponse> getShipmentInformation(AWBPrintRequest awbDetail) throws CustomException {
      return super.fetchList(SqlIDs.SHIPMENT_INFO.toString(), awbDetail.getAwbNumber(), sqlSessionImpBd);
   }

   @Override
   public AWBPrintResponse getShipmentInformationByFlightId(AWBPrintRequest awbDetail) throws CustomException {
      return super.fetchObject("getAWBBarCodePrintDetailsByFlightId", awbDetail, sqlSessionImpBd);
   }

   @Override
   public AWBPrintResponse checkShipmentVerificationDetails(AWBPrintRequest awbDetail) throws CustomException {
      return super.fetchObject("getCheckShipmentVerificationDetails", awbDetail, sqlSessionImpBd);
   }

   @Override
   public String checkShipmentExistInShipmentMaster(String awbNumber) throws CustomException {
      return super.fetchObject("checkShipmentExistInShipmentMaster", awbNumber, sqlSessionImpBd);
   }

   @Override
   public AWBPrintResponse saveShipmentMasterDetails(AWBPrintResponse response) throws CustomException {
      super.insertData("saveBarCodeShipmentMasterDeatils", response, sqlSessionImpBd);
      return new AWBPrintResponse();
   }

   @Override
   public AWBPrintResponse saveShipmentVerificationDetails(AWBPrintResponse response) throws CustomException {
      super.insertData("saveBarCodeShipmentVerificationDeatils", response, sqlSessionImpBd);
      super.insertData("updateShipementMasterDataForBarcode", response, sqlSessionImpBd);
      return new AWBPrintResponse();
   }

   @Override
   public AWBPrintResponse updateShipmentVerificationDetails(AWBPrintResponse response) throws CustomException {
      super.insertData("updateBarCodeShipmentVerificationDeatils", response, sqlSessionImpBd);
      super.insertData("updateShipementMasterDataForBarcode", response, sqlSessionImpBd);
      return new AWBPrintResponse();
   }

   @Override
   public AWBPrintResponse saveShipmentIrregularityDetails(AWBPrintResponse response) throws CustomException {
      super.insertData("saveShipmentIrregularityDetails", response, sqlSessionImpBd);
      return new AWBPrintResponse();
   }

   @Override
   public boolean checkShipmentIrregularityDetails(AWBPrintResponse response) throws CustomException {
      int count = super.fetchObject("getcheckShipmentIrregularityDetails", response, sqlSessionImpBd);
      return count > 0;
   }

   @Override
   public AWBPrintRequest checkShipmentReceiveOriginallyStatus(String awbNumber) throws CustomException {
      return super.fetchObject("getcheckShipmentReceiveOriginallyStatus", awbNumber, sqlSessionImpBd);
   }

   @Override
   public String getFlightIdByFlightKeyAndDate(AWBPrintRequest awbDetail) throws CustomException {
      return super.fetchObject("getFlightIdByFlightKey", awbDetail, sqlSessionImpBd);
   }

   @Override
   public void captutreAuditTrail(HashMap<String, Object> auditMap) throws CustomException {
      insertData("captureAuditTrailDetails", auditMap, sqlSessionImpBd);
   }

}