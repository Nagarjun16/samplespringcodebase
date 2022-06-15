/**
 * 
 * NeutralAwbService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchNAWBRQ;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.model.SearchStockRQ;
import com.ngen.cosys.shipment.nawb.model.Stock;

/**
 * This interface takes care of the responsibilities of Neutral Awb maintenance
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface NeutralAWBService {

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
    * Search AWB From Stock based on criteria
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
    * Save NAWB Details
    * 
    * @param neutralAWBMaster
    * @return NeutralAWBMaster
    * @throws CustomException
    */
   NeutralAWBMaster saveNAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   /**
    * Issue AWB in Stock
    * 
    * @param stock
    * @return Stock
    * @throws CustomException
    */
   Stock issueAWBInStock(Stock stock) throws CustomException;

   AgentInfo fetchAgentInfo(AgentInfo agent) throws CustomException;

   int updateIssuedNumer(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   NeutralAWBMaster saveneutralAWB(NeutralAWBMaster neutralAWBMaster) throws CustomException;

   String getAirlineName(String prefix) throws CustomException;

   SearchStockRQ updateInProcessForAwbNumber(SearchStockRQ searchStockRQ) throws CustomException;
   
   boolean checkIfImportShipment(SearchNAWBRQ searchSIDRQ) throws CustomException;

   Boolean checkMalOcsForNawb(NeutralAWBMaster nawbShipment) throws CustomException;

}