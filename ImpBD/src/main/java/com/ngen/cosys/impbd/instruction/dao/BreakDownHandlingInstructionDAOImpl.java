package com.ngen.cosys.impbd.instruction.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.instruction.constants.HandlingInformationSqlId;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationByHouse;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInformationQuery;
import com.ngen.cosys.impbd.instruction.model.BreakDownHandlingInstructionShipmentModel;
import com.ngen.cosys.impbd.instruction.model.BreakdownHandlingListResModel;
import com.ngen.cosys.model.SHCModel;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class BreakDownHandlingInstructionDAOImpl extends BaseDAO implements BreakDownHandlingInstructionDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<BreakdownHandlingListResModel> selectBreakDownHandlingInformation(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException {
		breakDownHandlingInformationQuery.setFlightOriginDate(breakDownHandlingInformationQuery.getFlightOriginDate());
		breakDownHandlingInformationQuery.setTenantAirport(MultiTenantUtility.getAirportCodeFromContext());
		return fetchList(HandlingInformationSqlId.SELECT_BREAKDOWNHANDLINGINFORMATION.toString(),
				breakDownHandlingInformationQuery, sqlSessionTemplate);
	}

	public Integer createBreakDownHandlingInformation(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInformationQuery) throws CustomException {
		return insertData(HandlingInformationSqlId.INSERT_BREAKDOWNHANDLINGINFORMATION.toString(),
				breakDownHandlingInformationQuery, sqlSessionTemplate);
	}

	public Integer createBreakDownHandlingInformationSHC(SHCModel shcModel) throws CustomException {
		return insertData("insertSHCInfoBH", shcModel, sqlSessionTemplate);
	}

	public Integer createBreakDownHandlingInformationHouse(
			BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse) throws CustomException {
		return insertData("insertSHCInfoBHouse", breakDownHandlingInformationByHouse, sqlSessionTemplate);
	}

	@Override
	public Integer updateBreakDownHandlingInformation(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInformation) throws CustomException {
		return insertData("updateBreakdownHandling", breakDownHandlingInformation, sqlSessionTemplate);
	}

	@Override
	public Integer updateBreakDownHandlingInformationSHC(SHCModel shcModel) throws CustomException {
		return insertData("updateBreakdownHandlingSHC", shcModel, sqlSessionTemplate);
	}

	@Override
	public Integer updateBreakDownHandlingInformationHouse(
			BreakDownHandlingInformationByHouse breakDownHandlingInformationByHouse) throws CustomException {
		return insertData("updateBreakdownHandlingHouse", breakDownHandlingInformationByHouse, sqlSessionTemplate);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CARGO_BREAKDOWN_HANDLING_INFORMATION)
	@Override
	public Integer deleteBreakDownHandlingInformation(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInstructionShipmentModel)
			throws CustomException {
		return deleteData("deleteBreakdownHandling", breakDownHandlingInstructionShipmentModel, sqlSessionTemplate);
	}

	@Override
	public Integer deleteBreakDownHandlingInformationHouse(
			BreakDownHandlingInstructionShipmentModel breakDownHandlingInformationByHouse) throws CustomException {
		return deleteData("deleteBreakdownHandlingHouse", breakDownHandlingInformationByHouse, sqlSessionTemplate);
	}

	@Override
	public List<BreakdownHandlingListResModel> selectArrivalData(
			BreakDownHandlingInformationQuery breakDownHandlingInformationQuery) throws CustomException {
		return fetchList("selectManifestData", breakDownHandlingInformationQuery, sqlSessionTemplate);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CARGO_BREAKDOWN_HANDLING_INFORMATION)
	@Override
	public int createShipmentRemark(BreakDownHandlingInstructionShipmentModel breakDownHandlingInstructionShipmentModel)
			throws CustomException {
		return insertData("saveToMaintainRemark", breakDownHandlingInstructionShipmentModel, sqlSessionTemplate);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CARGO_BREAKDOWN_HANDLING_INFORMATION)
	@Override
	public int updateShipmentRemark(BreakDownHandlingInstructionShipmentModel breakDownHandlingInstructionShipmentModel)
			throws CustomException {
		return updateData("updateMaintainRemark", breakDownHandlingInstructionShipmentModel, sqlSessionTemplate);
	}

	@Override
	public List<String> getHAWBInfo(String shipmentNumber) throws CustomException {
		return this.fetchList("getHAWBInfo", shipmentNumber, sqlSessionTemplate);
	}

	@Override
	public boolean checkForManifestData(String shipmentNumber) throws CustomException {
		return this.fetchObject("sqlCheckForManifestData", shipmentNumber, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.instruction.dao.BreakDownHandlingInstructionDAO#
	 * isBreakDownInstructionExists(java.math.BigInteger, java.lang.String,
	 * java.time.LocalDate, java.lang.String)
	 */
	@Override
	public boolean isBreakDownInstructionExists(BigInteger flightId, String shipmentNumber, LocalDate shipmentDate,
			String houseNumber) throws CustomException {
		Map<String, Object> queryParameterMap = new HashMap<>();
		queryParameterMap.put("flightId", flightId);
		queryParameterMap.put("shipmentNumber", shipmentNumber);
		queryParameterMap.put("shipmentDate", shipmentDate);
		queryParameterMap.put("houseNumber", houseNumber);

		return this.fetchObject("sqlQueryCheckBreakDownInstructionExistsForShipmentORHouse", queryParameterMap,
				sqlSessionTemplate);
	}

}