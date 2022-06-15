/**
 * AwbStockDAO.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date       Author      Reason
 * 1.0          29 Jan, 2018  NIIT      -
 */
package com.ngen.cosys.shipment.stockmanagement.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.model.StockDetail;
import com.ngen.cosys.shipment.stockmanagement.model.StockSummary;

/**
 * This interface interacts with AWB Stock Check tables.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
public interface AwbStockDAO {
	
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
	 */  
	public void addAWBStockHeader(StockDetail stockDetail) throws CustomException;
	
	/**
	 * Add AWB Stock Summary Detail
	 * 
	 * @param stockDetail
	 * @throws CustomException
	 */
	public void addAWBStockSummaryDetail(StockSummary stockSummary) throws CustomException;
	
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
	
	public boolean checkDuplicateStock (StockDetail stockDetail) throws CustomException;
	
	public boolean checkPrefixValidity (StockDetail stockDetail) throws CustomException;
	
	public boolean checkDuplicateAWB (StockSummary stockSummary) throws CustomException;
	
	public boolean checkBlackListAWB (StockSummary stockSummary) throws CustomException;
	
	public int fetchLowStockLimit (StockDetail stockDetail) throws CustomException;
	
	public boolean modSevenCheckApplicable (String awbPrefix) throws CustomException;

	public StockDetail markDelete(StockDetail stockDetail) throws CustomException;
}