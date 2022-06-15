/**
 * This is a repository component for handling break down tonnage information
 */
package com.ngen.cosys.impbd.summary.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.summary.model.BreakDownSummary;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryTonnageHandledModel;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryUldModel;
import com.ngen.cosys.impbd.summary.model.Email;

public interface BreakDownSummaryDAO {

	/**
	 * Method to get tonnage summary information based on inbound flight
	 * 
	 * @param breakDownSummary
	 * @return BreakDownSummaryModel - Summary information
	 * @throws CustomException
	 */
	BreakDownSummaryModel get(BreakDownSummaryModel requestModel) throws CustomException;

	/**
	 * Method to get break down tonnage information on flight complete
	 * 
	 * @param requestModel
	 * @return List<BreakDownSummaryUldModel> - List of break down ULD/Trolley and
	 *         their tonnage info
	 * @throws CustomException
	 */
	List<BreakDownSummaryUldModel> getInboundBreakDownTonnageInfo(BreakDownSummary requestModel) throws CustomException;

	/**
	 * Method to get other break down tonnage info which includes VAL, RAC SHC
	 * handling groups
	 * 
	 * @param requestModel
	 * @return List<BreakDownSummaryTonnageHandledModel> - List of tonnage
	 *         information
	 * @throws CustomException
	 */
	List<BreakDownSummaryTonnageHandledModel> getInboundBreakDownOtherTonnageInfo(BreakDownSummary requestModel)
			throws CustomException;

	/**
	 * Method to create break down summary
	 * 
	 * @param breakDownSummary
	 * @throws CustomException
	 */
	void createBreakDownTonnageSummary(BreakDownSummary requestModel) throws CustomException;

	/**
	 * Method to create break down summary ULD/Trolley info
	 * 
	 * @param breakDownTonnageSummaryInfo
	 * @throws CustomException
	 */
	void createBreakDownTonnageULDTrolleySummaryInfo(List<BreakDownSummaryUldModel> requestModel)
			throws CustomException;

	/**
	 * Method to create other tonnage summary info
	 * 
	 * @param breakDownTonnageSummaryInfo
	 * @throws CustomException
	 */
	void createBreakDownOtherTonnageSummaryInfo(List<BreakDownSummaryTonnageHandledModel> requestModel)
			throws CustomException;

	/**
	 * Update feedback for an service contractor
	 * 
	 * @param summaryData
	 * @throws CustomException
	 */
	void updateFeedBack(BreakDownSummary summaryData) throws CustomException;

	/**
	 * Method to clear existing break down summary info when staff re-opens and
	 * again completes the break down
	 * 
	 * @param requestModel
	 * @throws CustomException
	 */
	void clearExistingSummaryInfo(BreakDownSummary requestModel) throws CustomException;
	
	boolean checkForCargoTypeExistsOrNot(BreakDownSummaryTonnageHandledModel requestModel) throws CustomException;
	
    List<Email> fetchEmails(String s)  throws CustomException;
    
    String getServiceContractorName(String s) throws CustomException;


}