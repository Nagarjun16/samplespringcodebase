/**
 * AwbStockDAOImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date       Author      Reason
 * 1.0          29 Jan, 2018  NIIT      -
 */
package com.ngen.cosys.shipment.stockmanagement.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.util.LoggerUtil;
import com.ngen.cosys.shipment.stockmanagement.model.StockDetail;
import com.ngen.cosys.shipment.stockmanagement.model.StockSummary;

/**
 * This class interacts with AWB Stock Check tables.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@Repository("awbStockDAO")
public class AwbStockDAOImpl extends BaseDAO implements AwbStockDAO {

   private static final Logger LOGGER = LoggerFactory.getLogger(AwbStockDAOImpl.class);
   
   private static final String SQL_INSERT_SM_STOCK_DETAIL   = "insertStockDetail";
   private static final String SQL_SELECT_SM_STOCK_DETAIL   = "selectStockDetail";
   private static final String SQL_UPDATE_SM_STOCK_DETAIL   = "updateStockDetail";
   
   private static final String SQL_INSERT_SM_STOCK_SUMMARY  = "insertStockSummary";
   private static final String SQL_SELECT_SM_STOCK_SUMMARY  = "selectStockSummary";
   private static final String SQL_ARCHIVE_SM_STOCK_SUMMARY = "archiveStockSummary";
   private static final String SQL_CHECK_DUPLICATE_STOCK = "checkDuplicateStock";
   private static final String SQL_CHECK_DUPLICATE_AWB = "checkDuplicateAWB";
   private static final String SQL_FETCH_LOWSTOCK_LIMIT = "fetchLowStockLimit";
   private static final String SQL_CHECK_BLACKLIST_AWB = "checkBlackListedAWB";
   private static final String SQL_CHECK_MODCHECK_CARRIER = "stockManagmentAWBModCheck";
   private static final String SQL_CHECK_PREFIX_VALIDITY = "checkPrefixValidity";
   private static final String SQL_UPDATE_LEFT_STOCKS_DELETED = "sqlupdateLeftStocksDeleted";
   
   
   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionShipment;

   @Override
   public boolean checkStockIdExistsForCarrier(String awbNumber) throws CustomException {
      return false;
   }
   
   @Override
   public boolean checkAwbNumberAlreadyExists(String awbNumber) throws CustomException {
      return false;
   }

   @Override
   public boolean isAwbNumberBlacklisted(String awbNumber) throws CustomException {
      return false;
   }

   @Override
   public void addAWBStockHeader(StockDetail stockDetail) throws CustomException {
      LOGGER.debug(LoggerUtil.getLoggerMessage(
            this.getClass().getName(), "addAWBStockHeader", Level.DEBUG, stockDetail, null));
      int rowCount = insertData(SQL_INSERT_SM_STOCK_DETAIL, stockDetail, sqlSessionShipment);
      
   }

   @Override
   public void addAWBStockSummaryDetail(StockSummary stockSummary) throws CustomException {
      LOGGER.debug(LoggerUtil.getLoggerMessage(
            this.getClass().getName(), "addAWBStockSummaryDetail", Level.DEBUG, stockSummary, null));   
      int rowCount = insertData(SQL_INSERT_SM_STOCK_SUMMARY, stockSummary, sqlSessionShipment);
   }

   @Override
   public StockDetail getAWBStockDetailHeader(StockDetail stockDetail) throws CustomException {
      LOGGER.debug(LoggerUtil.getLoggerMessage(
            this.getClass().getName(), "getAWBStockDetailHeader", Level.DEBUG, stockDetail, null));
      return fetchObject(SQL_SELECT_SM_STOCK_DETAIL, stockDetail, sqlSessionShipment);
   }

   @Override
   public void updateAWBLowStockLimit(StockDetail stockDetail) throws CustomException {
      LOGGER.debug(LoggerUtil.getLoggerMessage(
            this.getClass().getName(), "updateAWBLowStockLimit", Level.DEBUG, stockDetail, null));
      updateData(SQL_UPDATE_SM_STOCK_DETAIL, stockDetail, sqlSessionShipment);
   }

   @Override
   public List<StockSummary> getAWBStockSummaryDetail(StockDetail stockDetail) throws CustomException {
      LOGGER.debug(LoggerUtil.getLoggerMessage(
            this.getClass().getName(), "getAWBStockSummaryDetail", Level.DEBUG, stockDetail, null));
      return fetchList(SQL_SELECT_SM_STOCK_SUMMARY, stockDetail, sqlSessionShipment);
   }

   @Override
   public void archiveAWBStockDetail(StockSummary stockSummary) throws CustomException {
      LOGGER.debug(LoggerUtil.getLoggerMessage(
            this.getClass().getName(), "archiveAWBStockDetail", Level.DEBUG, stockSummary, null));
      fetchList(SQL_ARCHIVE_SM_STOCK_SUMMARY, stockSummary, sqlSessionShipment);
   }
   
   @Override
   public boolean checkDuplicateStock (StockDetail stockDetail) throws CustomException {
	   int noOfRecords = fetchObject(SQL_CHECK_DUPLICATE_STOCK, stockDetail, sqlSessionShipment);
	   if (noOfRecords > 0) {
		   return true;
	   }
	   return false;
   }
   
   @Override
   public boolean checkPrefixValidity (StockDetail stockDetail) throws CustomException {
	   int noOfRecords = fetchObject(SQL_CHECK_PREFIX_VALIDITY, stockDetail, sqlSessionShipment);
	   if (noOfRecords > 0) {
		   return true;
	   }
	   return false;
   }
   
   @Override
   public boolean checkDuplicateAWB (StockSummary stockSummary) throws CustomException {
	   int noOfRecords = fetchObject(SQL_CHECK_DUPLICATE_AWB, stockSummary, sqlSessionShipment); 
	   if (noOfRecords > 0) {
		   return true;
	   }
	   return false;
   }
   
   @Override
   public boolean checkBlackListAWB (StockSummary stockSummary) throws CustomException {
	   int noOfRecords = fetchObject(SQL_CHECK_BLACKLIST_AWB, stockSummary, sqlSessionShipment); 
	   if (noOfRecords > 0) {
		   return true;
	   }
	   return false;
   }
   
   
   @Override
   public int fetchLowStockLimit(StockDetail stockDetail) throws CustomException {
	   Integer lowStockLimit = fetchObject(SQL_FETCH_LOWSTOCK_LIMIT, stockDetail, sqlSessionShipment);
	   if (lowStockLimit == null) {
		   return 0;
	   }
	   return lowStockLimit.intValue();
   }
   
   @Override
   public boolean modSevenCheckApplicable (String awbPrefix) throws CustomException {
	   int flag = fetchObject(SQL_CHECK_MODCHECK_CARRIER, awbPrefix, sqlSessionShipment);
	   return flag == 1;
   }
	/**
	 * Checks if is awb number black listed.
	 *
	 * @param stockDetail
	 *            the stock detail
	 * @return the int
	 * @throws CustomException
	 *             the custom exception
	 *//*
	@Override
	public int isAwbNumberBlackListed(StockDetail stockDetail) throws CustomException {
		return super.fetchObject("isAwbNumberBlackListed", stockDetail, sqlSessionShipment);
	}

	*//**
	 * Check awb number already exists.
	 *
	 * @param stockDetail
	 *            the stock detail
	 * @return the int
	 * @throws CustomException
	 *             the custom exception
	 *//*
	@Override
	public int checkAwbNumberAlreadyExists(StockDetail stockDetail) throws CustomException {

		return super.fetchObject("checkAwbNumberAlreadyExists", stockDetail, sqlSessionShipment);

	}
	
	*//**
	 * Check low stock limit exists.
	 *
	 * @param stockSummary the stock summary
	 * @return the int
	 * @throws CustomException the custom exception
	 *//*
	@Override
	public int checkLowStockLimitExists(StockSummary stockSummary) throws CustomException {
		int lowStockLimit = 0;
		try{
			lowStockLimit = super.fetchObject("checkLowStockLimitExists", stockSummary, sqlSessionShipment);
		}catch (Exception e) {
			return 0;
		}
		return lowStockLimit;
	}
	
	*//**
	 * Check stock id exists for carrier.
	 *
	 * @param stockSummary the stock summary
	 * @return the int
	 * @throws CustomException the custom exception
	 *//*
	@Override
	public int checkStockIdExistsForCarrier(StockSummary stockSummary) throws CustomException {
		return super.fetchObject("checkStockIdExistsForCarrier", stockSummary, sqlSessionShipment);
	}
	
	*//**
	 * Checks if is carrier code valid.
	 *
	 * @param stockSummary the stock summary
	 * @return the int
	 * @throws CustomException the custom exception
	 *//*
	@Override
	public int isCarrierCodeValid(StockSummary stockSummary) throws CustomException {
		return super.fetchObject("isCarrierCodeValid", stockSummary, sqlSessionShipment);
	}
	
	*//**
	 * Gets the awb prefix by carrier code.
	 *
	 * @param stockSummary the stock summary
	 * @return the awb prefix by carrier code
	 * @throws CustomException the custom exception
	 *//*
	@Override
	public String getAwbPrefixByCarrierCode(StockSummary stockSummary) throws CustomException {
		return super.fetchObject("getAwbPrefixByCarrierCode", stockSummary, sqlSessionShipment);
	}*/

	@Override
	public StockDetail markDelete(StockDetail stockDetail) throws CustomException {
		for (StockSummary stock : stockDetail.getStockStatusList()) {
			updateData(SQL_UPDATE_LEFT_STOCKS_DELETED , stock, sqlSessionShipment);
		}
		return stockDetail;
	}



	/**
	 * Checks if is awb number black listed.
	 *
	 * @param stockDetail
	 *            the stock detail
	 * @return the int
	 * @throws CustomException
	 *             the custom exception
	 */
	/*
	 * @Override public int isAwbNumberBlackListed(StockDetail stockDetail) throws
	 * CustomException { return super.fetchObject("isAwbNumberBlackListed",
	 * stockDetail, sqlSessionShipment); }
	 * 
	 *//**
		 * Check awb number already exists.
		 *
		 * @param stockDetail
		 *            the stock detail
		 * @return the int
		 * @throws CustomException
		 *             the custom exception
		 */
	/*
	 * @Override public int checkAwbNumberAlreadyExists(StockDetail stockDetail)
	 * throws CustomException {
	 * 
	 * return super.fetchObject("checkAwbNumberAlreadyExists", stockDetail,
	 * sqlSessionShipment);
	 * 
	 * }
	 * 
	 *//**
		 * Check low stock limit exists.
		 *
		 * @param stockSummary
		 *            the stock summary
		 * @return the int
		 * @throws CustomException
		 *             the custom exception
		 */
	/*
	 * @Override public int checkLowStockLimitExists(StockSummary stockSummary)
	 * throws CustomException { int lowStockLimit = 0; try{ lowStockLimit =
	 * super.fetchObject("checkLowStockLimitExists", stockSummary,
	 * sqlSessionShipment); }catch (Exception e) { return 0; } return lowStockLimit;
	 * }
	 * 
	 *//**
		 * Check stock id exists for carrier.
		 *
		 * @param stockSummary
		 *            the stock summary
		 * @return the int
		 * @throws CustomException
		 *             the custom exception
		 */
	/*
	 * @Override public int checkStockIdExistsForCarrier(StockSummary stockSummary)
	 * throws CustomException { return
	 * super.fetchObject("checkStockIdExistsForCarrier", stockSummary,
	 * sqlSessionShipment); }
	 * 
	 *//**
		 * Checks if is carrier code valid.
		 *
		 * @param stockSummary
		 *            the stock summary
		 * @return the int
		 * @throws CustomException
		 *             the custom exception
		 */
	/*
	 * @Override public int isCarrierCodeValid(StockSummary stockSummary) throws
	 * CustomException { return super.fetchObject("isCarrierCodeValid",
	 * stockSummary, sqlSessionShipment); }
	 * 
	 *//**
		 * Gets the awb prefix by carrier code.
		 *
		 * @param stockSummary
		 *            the stock summary
		 * @return the awb prefix by carrier code
		 * @throws CustomException
		 *             the custom exception
		 *//*
			 * @Override public String getAwbPrefixByCarrierCode(StockSummary stockSummary)
			 * throws CustomException { return
			 * super.fetchObject("getAwbPrefixByCarrierCode", stockSummary,
			 * sqlSessionShipment); }
			 */
}