package com.ngen.cosys.impbd.workinglist.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListModel;
import com.ngen.cosys.impbd.workinglist.model.BreakDownWorkingListShipmentResult;
import com.ngen.cosys.impbd.workinglist.model.SendTSMMessage;

public interface BreakDownWorkingListService {

	public BreakDownWorkingListModel getBreakDownWorkingList(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException;

	public List<Integer> updateFlightDelayForShipments(
			List<BreakDownWorkingListShipmentResult> breakDownWorkingListShipmentResult) throws CustomException;

	public BreakDownWorkingListModel breakDownComplete(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException;

	public BreakDownWorkingListModel reOpenBreakDownComplete(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException;
	
	/**
	 * 
	 * @param breakDownWorkingListModel
	 * @throws CustomException
	 * send report in ExcelFormat On BreakDownComplete
	 */
	public void sendLhRcfNfdReport(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException;

	public void validateDataAndTriggerEvents(SendTSMMessage requestModel) throws CustomException;
	
	public void reSendSegregationReport(BreakDownWorkingListModel requestModel) throws CustomException;

	void breakdownWorkingListFlightCompleteEventInitialize(BreakDownWorkingListModel breakDownWorkingListModel)
			throws CustomException;

}
