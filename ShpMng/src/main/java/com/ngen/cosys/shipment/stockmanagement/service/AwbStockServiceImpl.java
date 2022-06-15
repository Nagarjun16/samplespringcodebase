package com.ngen.cosys.shipment.stockmanagement.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.dao.AwbStockDAO;
import com.ngen.cosys.shipment.stockmanagement.model.StockDetail;
import com.ngen.cosys.shipment.stockmanagement.model.StockSummary;

/**
 * This class take care of AWB Stock Maintenance services.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@Service
@Transactional
public class AwbStockServiceImpl implements AwbStockService {

	private static final String DUPLICATE_STOCK_ERROR = "exp.stockMangement.duplicateStock";
	private static final String INVALID_AWB_PREFIX = "exp.stockMangement.invalidAWBPrefix";

	@Autowired
	private AwbStockDAO awbStockDAO;
	private String createdUserCode = "SIN3";

	@Override
	public boolean awbNumberModCheck(String awbNumber) throws CustomException {
		return false;
	}

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
	public StockDetail addAWBStockHeader(StockDetail stockDetail) throws CustomException {
		stockDetail.setCreatedBy(createdUserCode);
		if (!awbStockDAO.checkPrefixValidity(stockDetail)) {
			stockDetail.addError(INVALID_AWB_PREFIX, "firstAwbNumber", ErrorType.ERROR);
		} else if (awbStockDAO.checkDuplicateStock(stockDetail)) {
			stockDetail.addError(DUPLICATE_STOCK_ERROR, "stockId", ErrorType.ERROR);
		} else {
			awbStockDAO.addAWBStockHeader(stockDetail);
		}
		return stockDetail;
	}

	@Override
	public StockDetail addAWBStockSummaryDetail(StockDetail stockDetail) throws CustomException {
		Long stockId = stockDetail.getAwbStockId();
		String createdBy = stockDetail.getCreatedBy();
		LocalDateTime createdOn = stockDetail.getCreatedOn();
		String prefixAwbNumber = stockDetail.getFirstAwbNumber().substring(0, 3);
		String suffixAwbNumber = stockDetail.getFirstAwbNumber().substring(3, stockDetail.getFirstAwbNumber().length());
		StockSummary stockSummary = null;
		int length = stockDetail.getNumberOfAwb().intValue();
		int blackListed = 0;
		int duplicated = 0;
		boolean modCheckApplicable = awbStockDAO.modSevenCheckApplicable(prefixAwbNumber);
		for (int start = 0; start < length; start++) {
			if (start > 0) {
				if (modCheckApplicable) {
					suffixAwbNumber = getNextAWBNumber(suffixAwbNumber);
				} else {

					String awbSuffixNumber = String.valueOf(Integer.parseInt(suffixAwbNumber) + 1);
					if (awbSuffixNumber.length() < suffixAwbNumber.length()) {
						awbSuffixNumber = StringUtils.leftPad(awbSuffixNumber, 8, "0");
					}
					suffixAwbNumber = awbSuffixNumber;
				}
			}
			if (Optional.ofNullable(suffixAwbNumber).isPresent()) {
				stockSummary = new StockSummary();
				stockSummary.setAwbStockId(stockId);
				stockSummary.setAwbPrefix(prefixAwbNumber);
				stockSummary.setAwbSuffix(suffixAwbNumber);
				stockSummary.setAwbNumber(prefixAwbNumber + suffixAwbNumber);
				stockSummary.setCreatedBy(createdBy);
				stockSummary.setCreatedOn(createdOn);
				if (awbStockDAO.checkBlackListAWB(stockSummary)) {
					++blackListed;
				} else if (awbStockDAO.checkDuplicateAWB(stockSummary)) {
					++duplicated;
				} else {
					awbStockDAO.addAWBStockSummaryDetail(stockSummary);
				}
			}
		}
		int noOfSavedRecords = stockDetail.getNumberOfAwb().intValue() - blackListed - duplicated;
		stockDetail.setNoOfAWBCreated(BigInteger.valueOf(noOfSavedRecords));
		return stockDetail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.stockmanagement.service.AwbStockService#
	 * getNextAWBNumber(java.lang.String)
	 */
	@Override
	public String getNextAWBNumber(String awbNumber) throws CustomException {
		if (Optional.ofNullable(awbNumber).isPresent()) {
			int partialAwbNumber = Integer.parseInt(awbNumber.substring(0, awbNumber.length() - 1));
			partialAwbNumber += 1;
			int modValue = partialAwbNumber % 7;
			String newAwbNumber = String.valueOf(partialAwbNumber) + String.valueOf(modValue);
			newAwbNumber = StringUtils.leftPad(newAwbNumber, 8, "0");
			return newAwbNumber;
		}

		return null;
	}

	@Override
	public StockDetail getAWBStockDetailHeader(StockDetail stockDetail) throws CustomException {
		return awbStockDAO.getAWBStockDetailHeader(stockDetail);
	}

	@Override
	public void updateAWBLowStockLimit(StockDetail stockDetail) throws CustomException {
		awbStockDAO.updateAWBLowStockLimit(stockDetail);
	}

	@Override
	public List<StockSummary> getAWBStockSummaryDetail(StockDetail stockDetail) throws CustomException {
		return awbStockDAO.getAWBStockSummaryDetail(stockDetail);
	}

	@Override
	public void archiveAWBStockDetail(StockSummary stockSummary) throws CustomException {
	}

	@Override
	public StockDetail fetchLowStockLimit(StockDetail stockDetail) throws CustomException {
		int lowStockLimit = awbStockDAO.fetchLowStockLimit(stockDetail);
		stockDetail.setLowStockLimit(BigInteger.valueOf(lowStockLimit));
		return stockDetail;
	}

	@Override
	public StockDetail markDelete(StockDetail stockDetail) throws CustomException {
		return awbStockDAO.markDelete(stockDetail);
	}

}