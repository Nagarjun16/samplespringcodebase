/**
 * 
 * AwbStockService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 January, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.stockmanagement.service;

import java.sql.SQLException;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.model.StockDetail;
import com.ngen.cosys.shipment.stockmanagement.model.StockSummary;

/**
 * This interface take care of AWB Stock Maintenance services.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
public interface AwbStockService {
   
   /**
    * Check StockID Exists for carrier
    * 
    * @param awbNumber
    * @return
    * @throws CustomException
    */
   public boolean checkStockIdExistsForCarrier(String awbNumber) throws CustomException;
   
   /**
    * Check AWB Number Already exists in the Stock ID with Multiple status check
    *  
    * @param awbNumber
    * @return
    * @throws CustomException
    */
   public boolean checkAwbNumberAlreadyExists(String awbNumber) throws CustomException;
   
   /**
    * AWB Number Mod check
    * 
    * @param awbNumber
    * @return
    * @throws CustomException
    */
   public boolean awbNumberModCheck(String awbNumber) throws CustomException;
   
   /**
    * Check AWB Number is Black Listed
    * 
    * @param awbNumber
    * @return
    * @throws CustomException
    */
   public boolean isAwbNumberBlacklisted(String awbNumber) throws CustomException;

   
   
   /**
    * Add new AWB Stock Header details based on Carrier code & StockID
    * 
    * @param stockDetail
    * @throws CustomException
 * @throws SQLException 
    */
   public StockDetail addAWBStockHeader(StockDetail stockDetail) throws CustomException; 
   
   /**
    * Add AWB Stock Summary Detail
    * 
    * @param stockDetail
 * @return 
    * @throws CustomException
    */
   public StockDetail addAWBStockSummaryDetail(StockDetail stockDetail) throws CustomException;
   
   
   /**
    * Get Next AWB Number based on previous AWB Number
    * 
    * @param awbNumber
    * @return
    * @throws CustomException
    */
   public String getNextAWBNumber(String awbNumber) throws CustomException;
   
   /**
    * Get AWB Stock Detail header for update records
    * 
    * @param stockDetail
    * @return
    * @throws CustomException
    */
   public StockDetail getAWBStockDetailHeader(StockDetail stockDetail) throws CustomException;
   
   /**
    * Update low Stock Limit value for modification 
    * 
    * @param stockDetail
    * @throws CustomException
    */
   public void updateAWBLowStockLimit(StockDetail stockDetail) throws CustomException;
   
   
   /**
    * Get AWB Stock Summary details
    *  
    * @param stockDetail
    * @return
    * @throws CustomException
    */
   public List<StockSummary> getAWBStockSummaryDetail(StockDetail stockDetail) throws CustomException;
   
   
   /**
    * Purge AWB Stock detail into Excel 
    * 
    * @param stockDetail
    * @throws CustomException
    */
   public void archiveAWBStockDetail(StockSummary stockSummary) throws CustomException;
   
   public StockDetail fetchLowStockLimit (StockDetail stockDetail) throws CustomException;

   public StockDetail markDelete(StockDetail stockDetail) throws CustomException;

}