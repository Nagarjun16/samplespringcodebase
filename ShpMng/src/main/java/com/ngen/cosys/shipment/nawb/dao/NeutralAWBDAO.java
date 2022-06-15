/**
 * 
 * NeutralAWBDAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.nawb.model.NawbChargeValues;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchNAWBRQ;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.model.SearchStockRQ;
import com.ngen.cosys.shipment.nawb.model.Stock;

/**
 * This interface takes care of CRUD operation for Neutral AWB with database
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface NeutralAWBDAO {

   /**
    * Search SID's based on criteria
    * 
    * @param searchSIDRQ
    * @return List<SIDHeaderDetail>
    * @throws CustomException
    */
   List<SIDHeaderDetail> searchSIDList(SearchSIDRQ searchSIDRQ) throws CustomException;

   /**
    * Search SID Details based on criteria
    * 
    * @param searchSIDRQ
    * @return List<NeutralAWBMaster>
    * @throws CustomException
    */
   NeutralAWBMaster searchSIDDetails(SearchNAWBRQ searchSIDRQ) throws CustomException;

   /**
    * Search AWB Fron Stock based on criteria
    * 
    * @param searchStockRQ
    * @return List<Stock>
    * @throws CustomException
    */
   List<Stock> searchAWBFromStockList(SearchStockRQ searchStockRQ) throws CustomException;

   /**
    * Search NAWB Details From NAWB based on criteria
    * 
    * @param searchNAWBRQ
    * @return List<NeutralAWBMaster>
    * @throws CustomException
    */
   NeutralAWBMaster searchNAWBDetails(SearchNAWBRQ searchNAWBRQ) throws CustomException;

   /**
    * Save NAWB Master Details
    * 
    * @param neutralAWBMaster
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBMasterDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB Shipper Info Details
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBShipperInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB Consignee Info Details
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBConsigneeInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperAddressInfoDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBShipperAddressInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperAddressInfoDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBConsigneeAddressInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperContactInfoDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBShipperContactInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ConsigneeContactInfoDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBConsigneeContactInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperRoutingDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */

   NeutralAWBMaster saveNAWBRoutingDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperAccountingInfoDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */

   NeutralAWBMaster saveNAWBAccountingInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperSpecialHandlingDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */

   NeutralAWBMaster saveNAWBSpecialHandlingDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperCommodityDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */

   NeutralAWBMaster saveNAWBCommodityDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperChargeDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBChargeDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB ShipperCustomsDetails
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWBCustomsDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Save NAWB SHC Details
    * 
    * @param neutralAWBMasterResponse
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveSHCDetails(NeutralAWBMaster neutralAWBMaste) throws CustomException;

   /**
    * Issue AWB in Stock
    * 
    * @param stock
    * @return Stock
    * @throws CustomException
    */
   Stock issueAWBInStock(Stock stock) throws CustomException;

   NeutralAWBMaster saveAgentInfo(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveAwbFlight(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   int checkAwbExist(SearchNAWBRQ s) throws CustomException;

   int updateIssuedNumer(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   AgentInfo fetchAgentInfo(AgentInfo agent) throws CustomException;

   BigInteger customerInfoId(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   BigInteger fetchNawbID(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveneutralAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveNAWBAlsoNotifyInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveNAWBAlsoNotifyAddressInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveNAWBAlsoNotifyContactInfoDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveRateDescription(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveRateDescriptionInfo(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveHandlingInformation(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster localAuthorization(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster localAuthorizationDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveOtherChargesDueAgent(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveOtherChargesDueCarrier(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster savePPdDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveColDetails(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster searchAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   String getAirlineName(String prefix) throws CustomException;

   SearchStockRQ updateInProcessForAwbNumber(SearchStockRQ searchStockRQ) throws CustomException;

   boolean checkIfImportShipment(SearchNAWBRQ searchSIDRQ) throws CustomException;

   Boolean checkMalOcsForNawb(NeutralAWBMaster nawbShipment) throws CustomException;

   NawbChargeValues getNawbChargeDetails(NeutralAWBMaster chargevalues) throws CustomException;

}