package com.ngen.cosys.impbd.instruction.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationByHouse;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationQuery;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInstructionShipmentModel;
import com.ngen.cosys.impbd.instruction.model.BreakdownHandlingListResModel;
import com.ngen.cosys.model.SHCModel;

public interface BreakDownHandlingInstructionDAO {
	/**
	 * @param breakDownHandlingInformation
	 * @return
	 * @throws CustomException
	 */
	List<BreakdownHandlingListResModel> selectBreakDownHandlingInformation(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException;

	Integer createBreakDownHandlingInformation(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInformationQuery) throws CustomException;

	Integer createBreakDownHandlingInformationHouse(
			BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse) throws CustomException;

	Integer updateBreakDownHandlingInformation(BreakDownHandlingInstructionShipmentModel breakDownHandlingInformation)
			throws CustomException;

	Integer updateBreakDownHandlingInformationSHC(SHCModel shcModel) throws CustomException;

	Integer updateBreakDownHandlingInformationHouse(
			BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse) throws CustomException;

	Integer deleteBreakDownHandlingInformation(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInstructionShipmentModel) throws CustomException;

	Integer deleteBreakDownHandlingInformationHouse(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInformationByHouse) throws CustomException;

	List<BreakdownHandlingListResModel> selectArrivalData(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException;

	int createShipmentRemark(BreakDownHandlingInstructionShipmentModel model) throws CustomException;

	int updateShipmentRemark(BreakDownHandlingInstructionShipmentModel model) throws CustomException;

	List<String> getHAWBInfo(String shipmentNumber) throws CustomException;

	boolean checkForManifestData(String shipmentNumber) throws CustomException;

	/**
	 * Method to check break handling instruction info exists OR not
	 * 
	 * @param flightId
	 * @param shipmentNumber
	 * @param shipmentDate
	 * @param houseNumber
	 * @return boolean - if exists otherwise false
	 * @throws CustomException
	 */
	boolean isBreakDownInstructionExists(BigInteger flightId, String shipmentNumber, LocalDate shipmentDate,
			String houseNumber) throws CustomException;

}