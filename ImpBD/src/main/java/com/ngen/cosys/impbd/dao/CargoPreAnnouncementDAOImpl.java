package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.SqlIDs;
import com.ngen.cosys.impbd.model.CargPreAnnouncementShcModel;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBO;
import com.ngen.cosys.impbd.model.FlightDetails;

/**
 * @author Vamsikrishna.Tai
 *
 */
@Repository("CargoPreAnnouncementDAO")
public class CargoPreAnnouncementDAOImpl extends BaseDAO implements CargoPreAnnouncementDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionImpBd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#cargoPreAnnouncement(com.
	 * ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@Override
	public List<CargoPreAnnouncement> cargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO)
			throws CustomException {
		return fetchList(SqlIDs.GET_ANNOUNCEMENT_TABLE.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#flightDetails(com.ngen.cosys
	 * .impbd.model.CargoPreAnnouncementBO)
	 */
	@Override
	public CargoPreAnnouncementBO flightDetails(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		return fetchObject(SqlIDs.GET_ANNOUNCEMENT_FLIGHT.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#updateCargoPreAnnouncement(
	 * com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void updateCargoPreAnnouncement(List<CargoPreAnnouncement> cargoPreAnnouncementList) throws CustomException {
		for (CargoPreAnnouncement cargoPreAnnouncement : cargoPreAnnouncementList) {
			//for updating uldnumber
			CargoPreAnnouncement existingUld=fetchObject("getUldNumberforUpdate", cargoPreAnnouncement, sqlSessionImpBd);
			if(!existingUld.getUldNumber().equalsIgnoreCase(cargoPreAnnouncement.getUldNumber())) {
				//set existing uldnumber to update rampuld
				cargoPreAnnouncement.setRampUldNumber(existingUld.getUldNumber());
			}

			FlightDetails flightDetails =new FlightDetails();
			flightDetails.setFlightKey(cargoPreAnnouncement.getFlight());
			flightDetails.setFlightOriginDate(cargoPreAnnouncement.getDate());
			BigInteger outBoundFlightId=isFlightExist(flightDetails);
			cargoPreAnnouncement.setBookingFlightId(outBoundFlightId);
			cargoPreAnnouncement.setManualFlag(true);
			cargoPreAnnouncement.setManualUpdateFlag(true);
		}
		updateData(SqlIDs.UPDATE_CARGO_PREANNOUNCEMENT.toString(), cargoPreAnnouncementList, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#deleteCargoPreAnnouncement(
	 * com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void deleteCargoPreAnnouncement(List<CargoPreAnnouncement> cargoPreAnnouncementList) throws CustomException {
		deleteData(SqlIDs.DELETE_CARGO_PREANNOUNCEMENT_SHC.toString(),cargoPreAnnouncementList, sqlSessionImpBd);
		deleteData(SqlIDs.DELETE_CARGO_PREANNOUNCEMENT.toString(), cargoPreAnnouncementList, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#insertCargoPreAnnouncement(
	 * com.ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void insertCargoPreAnnouncement(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
		insertData(SqlIDs.INSERT_CARGO_PREANNOUNCEMENT.toString(), cargoPreAnnouncement, sqlSessionImpBd);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#
	 * isCargoPreAnnouncementRecordExist(com.ngen.cosys.impbd.model.
	 * CargoPreAnnouncement)
	 */
	@Override
	public CargoPreAnnouncement isCargoPreAnnouncementRecordExist(CargoPreAnnouncement cargoPreAnnouncement)
			throws CustomException {
		return fetchObject(SqlIDs.ISCARGOPREANNOUNCEMENTRECORDEXIST.toString(), cargoPreAnnouncement, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#cargoPrefinalize(com.ngen.
	 * cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void cargoPrefinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		cargoPreAnnouncementBO.setFinalizedBy(cargoPreAnnouncementBO.getModifiedBy());
		cargoPreAnnouncementBO.setFinalizedAt(cargoPreAnnouncementBO.getModifiedOn());
		cargoPreAnnouncementBO.setPreannoucemntFinalized(Boolean.TRUE);
		updateData(SqlIDs.UPDATE_CARGO_PREANNOUNCEMENT_FINALIZE.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);
		updateData("updateManualFlagOnFinalise", cargoPreAnnouncementBO, sqlSessionImpBd);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#cargoPreunfinalize(com.ngen.
	 * cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void cargoPreunfinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		cargoPreAnnouncementBO.setFinalizedBy(cargoPreAnnouncementBO.getModifiedBy());
		cargoPreAnnouncementBO.setFinalizedAt(cargoPreAnnouncementBO.getModifiedOn());
		cargoPreAnnouncementBO.setPreannoucemntFinalized(Boolean.FALSE);
		updateData(SqlIDs.UPDATE_CARGO_PREANNOUNCEMENT_UNFINALIZE.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#isFinalizeORunFinalize(com.
	 * ngen.cosys.impbd.model.CargoPreAnnouncementBO)
	 */
	@Override
	public Boolean isFinalizeORunFinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		Boolean finalize = fetchObject(SqlIDs.ISFINALIZE_OR_UNFINALIZE.toString(), cargoPreAnnouncementBO,
				sqlSessionImpBd);

		return finalize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#
	 * insertCargoPreannouncementRamCheckIn(com.ngen.cosys.impbd.model.
	 * CargoPreAnnouncement)
	 */
	//@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void insertCargoPreannouncementRamCheckIn(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
		insertData(SqlIDs.INSERT_CARGOPREANNOUNCEMENT_RAMCHECKIN.toString(), cargoPreAnnouncement, sqlSessionImpBd);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#
	 * updateCargoPreannouncementRamCheckIn(com.ngen.cosys.impbd.model.
	 * CargoPreAnnouncement)
	 */

	@Override
	public void updateCargoPreannouncementRamCheckIn(List<CargoPreAnnouncement> cargoPreAnnouncementList)
			throws CustomException {
		
		for (CargoPreAnnouncement cargoPreAnnouncement : cargoPreAnnouncementList) {
			CargoPreAnnouncement getId=new CargoPreAnnouncement();
			if(!StringUtils.isEmpty(cargoPreAnnouncement.getRampUldNumber())) {
				getId.setUldNumber(cargoPreAnnouncement.getRampUldNumber());
			}else {
				getId.setUldNumber(cargoPreAnnouncement.getUldNumber());
			}
			getId.setFlightId(cargoPreAnnouncement.getFlightId());
			BigInteger rampCheckinId=isRamcheckinRecordExist(getId);
			
			cargoPreAnnouncement.setRampCheckinId(rampCheckinId);
		}
		updateData(SqlIDs.UPDATE_CARGOPREANNOUNCEMENT_RAMCHECKIN.toString(), cargoPreAnnouncementList, sqlSessionImpBd);
	}

	@Override
	public BigInteger isRamcheckinRecordExist(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {

		return fetchObject(SqlIDs.IS_EXIST_RAMPCHECKIN.toString(), cargoPreAnnouncement, sqlSessionImpBd);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void insertCargoPreAnnouncementSHC(CargPreAnnouncementShcModel shcModel) throws CustomException {
		insertData(SqlIDs.INSERT_CARGOPREANNOUNCEMENT_SHC.toString(), shcModel, sqlSessionImpBd);
	}

	@Override
	public void insertCargoPreAnnouncementRamCheckinSHC(CargPreAnnouncementShcModel shcModel) throws CustomException {
		insertData(SqlIDs.INSERT_CARGOPREANNOUNCEMENT_RAMPCHECKIN_SHC.toString(), shcModel, sqlSessionImpBd);
	}

	@Override
	public void deleteCargoPreAnnouncementShc(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
		deleteData(SqlIDs.DELETE_CARGO_PREANNOUNCEMENT_SHC.toString(), cargoPreAnnouncement, sqlSessionImpBd);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void mailPrefinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		updateData(SqlIDs.UPDATE_MAIL_PREANNOUNCEMENT_FINALIZE.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT)
	@Override
	public void mailPreunfinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException {
		updateData(SqlIDs.UPDATE_MAIL_PREANNOUNCEMENT_UNFINALIZE.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);
	}

	@Override
	public void deleteCargoPreAnnouncementRampcheckinShc(CargoPreAnnouncement cargoPreAnnouncement)
			throws CustomException {
		deleteData(SqlIDs.DELETE_CARGO_PREANNOUNCEMENT_RAMP_SHC.toString(), cargoPreAnnouncement, sqlSessionImpBd);
	}

	@Override
	public BigInteger isFlightExist(FlightDetails flightDetails) throws CustomException {
		Integer i =  fetchObject(SqlIDs.IS_FLIGHT_EXIST.toString(), flightDetails, sqlSessionImpBd);
		if(ObjectUtils.isEmpty(i)) {
		   return null;
		}else {
			
		   return BigInteger.valueOf(i);
		}
	}

	@Override
	public CargoPreAnnouncementBO preAnnoncementfinalizeInfo(CargoPreAnnouncementBO cargoPreAnnouncementBO)
			throws CustomException {
		
		return fetchObject(SqlIDs.PREANNOUNCEMENT_FINALIZE_INFO.toString(), cargoPreAnnouncementBO, sqlSessionImpBd);
	}

	@Override
	public void updateBrakBulkStatus(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException {
		
		updateData(SqlIDs.UPDATE_BREAK_BULK.toString(), cargoPreAnnouncement, sqlSessionImpBd);
	}
	
	@Override
	public List<String> getPRNShcList(CargoPreAnnouncementBO cargoPreAnnouncement) throws CustomException{
		
		return fetchList("getPRNSHCList", cargoPreAnnouncement, sqlSessionImpBd);
	}

	@Override
	public List<String> getPrnGroupshcList(CargoPreAnnouncementBO cargoPreAnnouncement) throws CustomException{
		
		return fetchList("getPRNGroupSHCList", cargoPreAnnouncement, sqlSessionImpBd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.dao.CargoPreAnnouncementDAO#isULDCheckedIn(com.ngen.
	 * cosys.impbd.model.CargoPreAnnouncement)
	 */
	@Override
	public boolean isULDCheckedIn(CargoPreAnnouncement request) throws CustomException {
		return this.fetchObject("sqlCheckULDCheckedInForCargoPreannounce", request, sqlSessionImpBd);
	}
}
