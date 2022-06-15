package com.ngen.cosys.impbd.instruction.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationQuery;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInstructionFlightSegmentModel;
import com.ngen.cosys.impbd.instruction.model.BreakdownHandlingListResModel;

public interface BreakDownHandlingInformationService {

	void createBreakDownHandlingInformation(BreakDownHandlingInstructionFlightSegmentModel breakDownHandlingInformation)
			throws CustomException;

	List<BreakdownHandlingListResModel> selectBreakDownHandlingInformations(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException;

	void deleteBreakDownHandlingInformations(
			BreakDownHandlingInstructionFlightSegmentModel breakDownHandlingInformation) throws CustomException;

	List<BreakdownHandlingListResModel> selectArrivalData(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException;

	List<String> getHawbInfo(String shipmentNumber) throws CustomException;

}