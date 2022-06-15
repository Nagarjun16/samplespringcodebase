/**
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 July, 2017 NIIT -
 */
package com.ngen.cosys.flight.dao;

import static com.ngen.cosys.flight.constant.Constants.ONE;
import static com.ngen.cosys.flight.constant.Constants.TRUE;
import static com.ngen.cosys.flight.constant.Constants.ZERO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.flight.model.CustomsFlight;
import com.ngen.cosys.flight.model.ExportFlightevents;
import com.ngen.cosys.flight.model.FlightEnroutement;
import com.ngen.cosys.flight.model.IcsFlightDetails;
import com.ngen.cosys.flight.model.ImportFlightevents;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.OperativeFlightAuto;
import com.ngen.cosys.flight.model.OperativeFlightExp;
import com.ngen.cosys.flight.model.OperativeFlightFct;
import com.ngen.cosys.flight.model.OperativeFlightHandlingArea;
import com.ngen.cosys.flight.model.OperativeFlightJoint;
import com.ngen.cosys.flight.model.OperativeFlightLeg;
import com.ngen.cosys.flight.model.OperativeFlightSegment;
import com.ngen.cosys.framework.constant.DeleteIndicator;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.constant.InsertIndicator;
import com.ngen.cosys.framework.constant.UpdateIndicator;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * This class takes care of the responsibilities related to the Operative Flight
 * Flight DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */

@Repository("flightDAO")

public class FlightDAOImpl extends BaseDAO implements FlightDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightDAO.class);
	public static final String ERR_MSSG = "Exception happenned while inserting ...";

	public static final String BY_PASS_ULD_TYPE = "ByPassULDType";
	public static final String BY_PASS_ULD_MAX_CHECK = "ByPassULDMaxCheck";
	public static final String UPDATE = "updateOperativeFlight";
	public static final String UPDATE_LEG = "updateFlightLeg";
	public static final String SAVE = "saveFlight";
	public static final String CANCEL = "cancelFlight";
	public static final String CLOSE_FOR_BOOKING = "closeForBooking";
	public static final String OPEN_FOR_BOOKING = "openForBooking";
	public static final String RESTORE = "restoreFlight";
	public static final String FETCH_LEGS = "fetchFlightLegList";
	public static final String SAVE_LEGS = "saveFlightLegs";
	public static final String DELETE_LEG = "deleteOperativeFlightLeg";
	public static final String FETCH_SEGS_LEGS = "fetchFlightSegmentLegList";
	public static final String SAVE_SEGMENTS = "saveFlightSegments";
	public static final String DELETE_SEGMENTS = "deleteOperativeFlightSeg";
	public static final String UPDATE_SEGMENT = "updateFlightSegment";
	public static final String FETCH_FACTS = "fetchFlightFctList";
	public static final String SAVE_FACTS = "saveFlightFcts";
	public static final String DELETE_FACTS = "deleteOperativeFlightFacts";
	public static final String UPDATE_FACT = "updateFlightFct";
	public static final String FETCH_JOINTS = "fetchJointFlightList";
	public static final String SAVE_JOINT = "saveJointFlight";
	public static final String UPDATE_JOINT = "updateJointFlight";
	public static final String FETCH_EXPSS = "fetchFlightExpList";
	public static final String SAVE_EXPS = "saveFlightExps";
	public static final String UPDATE_EXPS = "updateFlightExp";
	public static final String DELETE_EXPS = "deleteOperativeFlightExp";
	public static final String DELETE_JOINTS = "deleteOperativeFlightJoint";
	public static final String SEARCH = "searchOperativeFlightList";
	public static final String VALIDATE_SECTOR = "validateSector";
	public static final String AUTO_FLIGHT_OFF = "Off";
	public static final String AUTO_COMPLETE_OFF = "0";
	public static final String AUTO_FLIGHT_ON = "On";
	public static final String AUTO_COMPLETE_ON = "1";
	public static final String SAVE_FLIGHT_ATTRIBUTES = "saveFlightAttributes";

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionFlight;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#find(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	@Override
	public OperativeFlight find(OperativeFlight operatingFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl find ", operatingFlight);
		OperativeFlight resFlight = super.fetchObject("fetchFlightDetail", operatingFlight, sqlSessionFlight);
		if (Optional.ofNullable(resFlight).isPresent()) {
			if (resFlight.getServiceType() != null) {
				resFlight.setDescription(
						super.fetchObject("fetchServiceDescription", resFlight.getServiceType(), sqlSessionFlight));
			}
			List<OperativeFlightLeg> flightLegs = fetchLegs(resFlight);
			if (!CollectionUtils.isEmpty(flightLegs)) {
				resFlight.setFlightLegs(flightLegs);
				List<OperativeFlightSegment> flightSegments = fetchSegmentLegList(resFlight);
				if (!CollectionUtils.isEmpty(flightSegments)) {
					resFlight.setFlightSegments(flightSegments);
				}
			}
			findChildren(resFlight);
		}
		LOGGER.debug(this.getClass().getName(), "find", Level.DEBUG, operatingFlight, resFlight);
		return resFlight;
	}

	/**
	 * This method responsible to fetch Flight Facts, Flight Joints, Flight
	 * Exception.
	 * 
	 * @param resFlight
	 * @throws CustomException
	 */
	private void findChildren(OperativeFlight resFlight) throws CustomException {
		resFlight.setFlightFcts(this.fetchFacts(resFlight));
		resFlight.setFlightExpULDTyps(this.fetchFlightExpULDList(resFlight));
		resFlight.setFlightExps(this.fetchExps(resFlight));
		resFlight.setFlightJoints(this.fetchJointFlights(resFlight));
		resFlight.setHandlingArea(this.fetchList("sqlGetFlightHandlingArea", resFlight, sqlSessionFlight));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#create(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_CREATION)
	@Override
	public OperativeFlight create(OperativeFlight operatingFlight) throws CustomException {
		if (Optional.ofNullable(operatingFlight).isPresent()) {
			super.insertData(SAVE, operatingFlight, sqlSessionFlight);
			getFlightId(operatingFlight);
			deriveHandlingArea(operatingFlight);
			saveLegs(operatingFlight);
			saveSegments(operatingFlight);
			saveExp(operatingFlight);
			saveJointFlight(operatingFlight);
			saveFact(operatingFlight);
			saveFlightAttributes(operatingFlight);

		}
		LOGGER.debug(this.getClass().getName(), "create", Level.DEBUG, operatingFlight, operatingFlight);
		return operatingFlight;
	}

	/**
	 * Method which derives handling area based on warehouse configuration
	 * 
	 * @param operatingFlight
	 * @throws CustomException
	 */
	private void deriveHandlingArea(final OperativeFlight operatingFlight) throws CustomException {
		// Derive the flight type
		int importFlightCount = 0;
		int exportFlightCount = 0;
		String aircraftType = "**";
		for (OperativeFlightLeg t : operatingFlight.getFlightLegs()) {
			if (MultiTenantUtility.isTenantAirport(t.getBoardPointCode())) {
				aircraftType = t.getAircraftModel();
				exportFlightCount++;
			}

			if (MultiTenantUtility.isTenantAirport(t.getOffPointCode())) {
				aircraftType = t.getAircraftModel();
				importFlightCount++;
			}
		}

		// Set the aircraft type
		operatingFlight.setAircraftType(aircraftType);

		if (importFlightCount > 0 && exportFlightCount > 0) {
			operatingFlight.setFlightType("BOTH");
		} else if (importFlightCount > 0) {
			operatingFlight.setFlightType("IMPORT");
		} else if (exportFlightCount > 0) {
			operatingFlight.setFlightType("EXPORT");
		}

		// Get the Aircraft Body Type
		String aircraftBodyType = this.fetchObject("sqlGetAircraftBodyType", operatingFlight, sqlSessionFlight);
		if (!StringUtils.isEmpty(aircraftBodyType)) {
			operatingFlight.setAircraftBodyType(aircraftBodyType);
		} else {
			operatingFlight.setAircraftBodyType("**");
		}

		// Derive the handling terminals for the flight configuration
		List<OperativeFlightHandlingArea> handlingArea = super.fetchList("sqlDeriveHandlingAreaForFlight",
				operatingFlight, sqlSessionFlight);
		List<String> haString = new ArrayList<>(); //
		int temp = 0;
		if (!CollectionUtils.isEmpty(handlingArea)) {
			for (OperativeFlightHandlingArea b : handlingArea) {
				haString.add(b.getTerminalCode());
				temp++;
			}
			operatingFlight.setHandlingArea(haString);
		}

		// if (temp == 0) {
		if (operatingFlight.getFlightType() == "BOTH") {
			handlingArea = super.fetchList("sqlDeriveAllTerminalForFlight", operatingFlight, sqlSessionFlight);
			if (!CollectionUtils.isEmpty(handlingArea)) {
				for (OperativeFlightHandlingArea b : handlingArea) {
					haString.add(b.getTerminalCode());
				}
				operatingFlight.setHandlingArea(haString);
			}
		}
		// Insert the handling terminals
		if (!CollectionUtils.isEmpty(handlingArea)) {
			handlingArea.forEach(t -> {
				t.setFlightId(operatingFlight.getFlightId());
				if (operatingFlight.getFlightKey() != null) {
					t.setFlightKey(operatingFlight.getFlightKey());
					t.setCreatedBy(operatingFlight.getCreatedBy());
					t.setModifiedBy(operatingFlight.getCreatedBy());
				}
			});
		}

		if (!CollectionUtils.isEmpty(handlingArea)) {
			super.insertData("insertFlightHandlingArea", handlingArea, sqlSessionFlight);
		}
	}

	/**
	 * Method to update/save handling area after the first save
	 * 
	 * @param operatingFlight
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_UPDATE)
	private void saveHandlingArea(final OperativeFlight operatingFlight) throws CustomException {
		// Delete existing handling areas for the given flight
		this.deleteData("sqlDeleteHandlingAreaByFlight", operatingFlight, sqlSessionFlight);
		// this.deriveHandlingArea(operatingFlight);

		if (!CollectionUtils.isEmpty(operatingFlight.getHandlingArea())) {
			for (String t : operatingFlight.getHandlingArea()) {
				OperativeFlightHandlingArea param = new OperativeFlightHandlingArea();
				param.setFlightId(operatingFlight.getFlightId());
				param.setTerminalCode(t);
				super.insertData("insertFlightHandlingArea", param, sqlSessionFlight);
			}
		}
	}

	/**
	 * This method catering flight Id from DB after inserting row in to Maintain
	 * Operative Flight.
	 * 
	 * @param operatingFlight
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	private void getFlightId(OperativeFlight operatingFlight) throws CustomException {
		long flightId;
		if (operatingFlight.getFlightId() != 0) {
			flightId = operatingFlight.getFlightId();
		} else {
			flightId = fetchFlightId(operatingFlight);
			operatingFlight.setFlightId(flightId);
		}
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl getFlightId ", flightId);
		List<OperativeFlightLeg> oprFltLegs = operatingFlight.getFlightLegs();
		if (!CollectionUtils.isEmpty(oprFltLegs)) {
			oprFltLegs.forEach(e -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(e.getFlagInsert())) {
					e.setFlightId(flightId);
					e.setCreatedBy(operatingFlight.getCreatedBy());
					e.setCreatedOn(operatingFlight.getCreatedOn());
				}
			});
		}
		List<OperativeFlightSegment> oprFltSegms = operatingFlight.getFlightSegments();
		if (!CollectionUtils.isEmpty(oprFltSegms)) {
			oprFltSegms.forEach(e -> {
				e.setFlightId(flightId);
				e.setCreatedBy(operatingFlight.getCreatedBy());
				e.setCreatedOn(operatingFlight.getCreatedOn());
			});
		}
		setFlightIdIntoExp(operatingFlight, flightId);
		setFlightIdIntoJointFlight(operatingFlight, flightId);
		List<OperativeFlightFct> oprFltFcts = operatingFlight.getFlightFcts();
		if (!CollectionUtils.isEmpty(oprFltFcts)) {
			oprFltFcts.forEach(e -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(e.getFlagInsert())) {
					e.setFlightId(flightId);
					e.setCreatedBy(operatingFlight.getCreatedBy());
					e.setCreatedOn(operatingFlight.getCreatedOn());
				}
			});
		}
	}

	/**
	 * This method gets flight id and set into joint flight.
	 * 
	 * @param operatingFlight
	 * @param flightId
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	private void setFlightIdIntoExp(OperativeFlight operatingFlight, long flightId) {
		List<OperativeFlightExp> flightExpTyps = operatingFlight.getFlightExpULDTyps();
		if (!CollectionUtils.isEmpty(flightExpTyps)) {
			flightExpTyps.forEach(e -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(e.getFlagInsert())) {
					e.setFlightId(flightId);
					e.setCreatedBy(operatingFlight.getCreatedBy());
					e.setCreatedOn(operatingFlight.getCreatedOn());
				}
			});
		}
		List<OperativeFlightExp> oprFltExps = operatingFlight.getFlightExps();
		if (!CollectionUtils.isEmpty(oprFltExps)) {
			oprFltExps.forEach(e -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(e.getFlagInsert())) {
					e.setFlightId(flightId);
					e.setCreatedBy(operatingFlight.getCreatedBy());
					e.setCreatedOn(operatingFlight.getCreatedOn());
				}
			});
		}
	}

	/**
	 * This method gets flight id from OFL and set into Exception.
	 * 
	 * @param operatingFlight
	 * @param flightId
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	private void setFlightIdIntoJointFlight(OperativeFlight operatingFlight, long flightId) {
		List<OperativeFlightJoint> oprFltJoint = operatingFlight.getFlightJoints();
		if (!CollectionUtils.isEmpty(oprFltJoint)) {
			oprFltJoint.forEach(e -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(e.getFlagInsert())) {
					e.setFlightId(flightId);
					e.setDepartureDateTime(operatingFlight.getFlightDate());
					e.setCreatedBy(operatingFlight.getCreatedBy());
					e.setCreatedOn(operatingFlight.getCreatedOn());
				}
				if (!CollectionUtils.isEmpty(operatingFlight.getFlightLegs())) {
					operatingFlight.getFlightLegs().forEach(e1 -> {
						if (1 == e1.getLegOrderCode()) {
							e.setBoardingPoint(e1.getBoardPointCode());
						}
					});
				}
			});
		}
	}

	/**
	 * It Saves Normal Enroutement after creating of maintain Operative flight.
	 * 
	 * @param flightEnroutementList
	 * @throws CustomException
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void saveNormalEnroutement(List<FlightEnroutement> flightEnroutementList) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveNormalEnroutement ", flightEnroutementList);
		List<FlightEnroutement> flightEnroutements = validateNormalEnroutement(flightEnroutementList);
		if (!CollectionUtils.isEmpty(flightEnroutements)) {
			for (FlightEnroutement flightEnroutement : flightEnroutements) {
				super.insertData("saveNormalEnroutement", flightEnroutement, sqlSessionFlight);
			}
		}
	}

	/**
	 * Validates Normal Enroutement.
	 * 
	 * @param flightEnroutementList
	 * @return List<FlightEnroutement>
	 * @throws CustomException
	 */
	public List<FlightEnroutement> validateNormalEnroutement(List<FlightEnroutement> flightEnroutementList)
			throws CustomException {
		List<FlightEnroutement> flightEnrontlist = new ArrayList<>();
		if (!CollectionUtils.isEmpty(flightEnroutementList)) {
			for (FlightEnroutement flightEnroutement : flightEnroutementList) {
				FlightEnroutement flightEnroutementQuery = super.fetchObject("maintainfetchNormalEnroutement",
						flightEnroutement, sqlSessionFlight);// Enroutement is exist or not
				if (flightEnroutementQuery != null && flightEnroutementQuery.getEnroutementId() != null) {
					// if start date select query and inside update statement

					// if end date select query and inside update statement
					if (flightEnroutement.getPeriodFrom().compareTo(flightEnroutementQuery.getPeriodFrom()) < 0) {
						super.updateData("updateNormalEnroutementFrom", flightEnroutement, sqlSessionFlight);
					} else if (flightEnroutement.getPeriodTo().compareTo(flightEnroutementQuery.getPeriodTo()) > 0) {
						super.updateData("updateNormalEnroutement", flightEnroutement, sqlSessionFlight);
					}

				} else {
					flightEnrontlist.add(flightEnroutement);
					// super.insertData("saveNormalEnroutement", flightEnroutement,
					// sqlSessionFlight);
				}
			}
		}

		LOGGER.debug(this.getClass().getName(), "validateNormalEnroutement", Level.DEBUG, flightEnroutementList,
				flightEnrontlist);
		return flightEnrontlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#fetchLegs(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	public List<OperativeFlightLeg> fetchLegs(OperativeFlight operativeFlight) throws CustomException {
		List<OperativeFlightLeg> operativeFlightLeg = super.fetchList(FETCH_LEGS, operativeFlight, sqlSessionFlight);
		return super.fetchList(FETCH_LEGS, operativeFlight, sqlSessionFlight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#saveLegs(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void saveLegs(OperativeFlight operatingFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveFlightLegs ", operatingFlight);
		List<OperativeFlightLeg> flightLegs = operatingFlight.getFlightLegs();
		if (!CollectionUtils.isEmpty(flightLegs)) {
			for (OperativeFlightLeg flightLeg : flightLegs) {
				flightLeg.setFlightId(operatingFlight.getFlightId());
				if (!StringUtils.isEmpty(operatingFlight.getRegisArrival())) {
					flightLeg.setRegistration(operatingFlight.getRegisArrival());
				} else if (!StringUtils.isEmpty(operatingFlight.getRegisDeparture())) {
					flightLeg.setRegistration(operatingFlight.getRegisDeparture());
				}
				super.insertData(SAVE_LEGS, flightLeg, sqlSessionFlight);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateLegs(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void updateLegs(OperativeFlight operatingFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightLegs ", operatingFlight);
		List<OperativeFlightLeg> flightLegs = operatingFlight.getFlightLegs();
		if (!CollectionUtils.isEmpty(flightLegs)) {
			for (OperativeFlightLeg leg : flightLegs) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(leg.getFlagDelete())) {
					super.deleteData(DELETE_LEG, leg, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(leg.getFlagUpdate())) {
					if (!StringUtils.isEmpty(operatingFlight.getRegisArrival())) {
						leg.setRegistration(operatingFlight.getRegisArrival());
					} else if (!StringUtils.isEmpty(operatingFlight.getRegisDeparture())) {
						leg.setRegistration(operatingFlight.getRegisDeparture());
					}

					super.updateData(UPDATE_LEG, leg, sqlSessionFlight);
				}
			}
			flightLegs.forEach(leg -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(leg.getFlagInsert())) {
					try {
						if (!StringUtils.isEmpty(operatingFlight.getRegisArrival())) {
							leg.setRegistration(operatingFlight.getRegisArrival());
						} else if (!StringUtils.isEmpty(operatingFlight.getRegisDeparture())) {
							leg.setRegistration(operatingFlight.getRegisDeparture());
						}
						int count = super.updateData(UPDATE_LEG, leg, sqlSessionFlight);
						if (count == 0 && !DeleteIndicator.YES.toString().equalsIgnoreCase(leg.getFlagDelete()))
							super.insertData(SAVE_LEGS, leg, sqlSessionFlight);
					} catch (CustomException e) {
						LOGGER.debug(ERR_MSSG, e);
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#fetchSegmentLegList(com.ngen.cosys.flight
	 * .model.OperativeFlight)
	 */
	@Override
	public List<OperativeFlightSegment> fetchSegmentLegList(OperativeFlight operatingFlight) throws CustomException {
		List<OperativeFlightSegment> flightSegments = super.fetchList(FETCH_SEGS_LEGS, operatingFlight,
				sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "fetchSegmentLegList", Level.DEBUG, operatingFlight, flightSegments);
		return flightSegments;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#saveSegments(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void saveSegments(OperativeFlight oprFltSegment) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveFlightSegments ", oprFltSegment);
		List<OperativeFlightSegment> flightSegments = oprFltSegment.getFlightSegments();
		if (!CollectionUtils.isEmpty(flightSegments)) {
			for (OperativeFlightSegment flightSegment : flightSegments) {
				if (Optional.ofNullable(flightSegment).isPresent()) {
					if (flightSegment.getFlgNfl() == null) {
						flightSegment.setFlgNfl("0");
					}
					if ("1".equals(flightSegment.getFlgNfl())) {
						flightSegment.setFlgNfl("true");
					} else {
						flightSegment.setFlgNfl("false");
					}

					if (flightSegment.getFlgTecStp() == null) {
						flightSegment.setFlgTecStp("0");
					}
					if ("1".equals(flightSegment.getFlgTecStp())) {
						flightSegment.setFlgTecStp("true");
					} else {
						flightSegment.setFlgTecStp("false");
					}

					if (flightSegment.getFlgCargo() == null) {
						flightSegment.setFlgCargo("0");
					}
					if ("1".equals(flightSegment.getFlgCargo())) {
						flightSegment.setFlgCargo("true");
					} else {
						flightSegment.setFlgCargo("false");
					}

					if (flightSegment.getNoMail() == null) {
						flightSegment.setNoMail("0");
					}
					if ("1".equals(flightSegment.getNoMail())) {
						flightSegment.setNoMail("true");
					} else {
						flightSegment.setNoMail("false");
					}

					if (TRUE.equalsIgnoreCase(flightSegment.getFlgNfl())) {
						flightSegment.setFlgNfl(ONE);
					}
					// else if (FALSE.equalsIgnoreCase(flightSegment.getFlgNfl()))
					else {
						flightSegment.setFlgNfl(ZERO);
					}
					if (TRUE.equalsIgnoreCase(flightSegment.getFlgTecStp())) {
						flightSegment.setFlgTecStp(ONE);
					}
					// else if (FALSE.equalsIgnoreCase(flightSegment.getFlgTecStp()))
					else {
						flightSegment.setFlgTecStp(ZERO);
					}
					if (TRUE.equalsIgnoreCase(flightSegment.getFlgCargo())) {
						flightSegment.setFlgCargo(ONE);
					}
					// else if (FALSE.equalsIgnoreCase(oprSeg.getFlgCargo()))
					else {
						flightSegment.setFlgCargo(ZERO);
					}
					if (TRUE.equalsIgnoreCase(flightSegment.getNoMail())) {
						flightSegment.setNoMail(ONE);
					}
					// else if (FALSE.equalsIgnoreCase(oprSeg.getNoMail()))
					else {
						flightSegment.setNoMail(ZERO);
					}
					super.insertData(SAVE_SEGMENTS, flightSegment, sqlSessionFlight);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateSegments(com.ngen.cosys.flight.
	 * model.OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void updateSegments(OperativeFlight operativeFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightSegments ", operativeFlight);
		List<OperativeFlightSegment> flightSegments = operativeFlight.getFlightSegments();
		if (!CollectionUtils.isEmpty(flightSegments)) {
			for (OperativeFlightSegment segment : flightSegments) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(segment.getFlagDelete())) {
					super.deleteData(DELETE_SEGMENTS, segment, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(segment.getFlagUpdate())) {
					super.updateData(UPDATE_SEGMENT, segment, sqlSessionFlight);
				}
			}
			flightSegments.forEach(segment -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(segment.getFlagInsert())) {
					try {
						int count = super.updateData(UPDATE_SEGMENT, segment, sqlSessionFlight);
						if (count == 0)
							super.insertData(SAVE_SEGMENTS, segment, sqlSessionFlight);
					} catch (CustomException e) {
						LOGGER.error(ERR_MSSG, e);
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateSegments(com.ngen.cosys.flight.
	 * model.OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void updateSegmentsData(OperativeFlight operativeFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightSegments ", operativeFlight);
		List<OperativeFlightSegment> flightSegments = operativeFlight.getFlightSegments();
		if (!CollectionUtils.isEmpty(flightSegments)) {
			/*
			 * for (OperativeFlightSegment segment : flightSegments) { if
			 * (DeleteIndicator.YES.toString().equalsIgnoreCase("y")) {
			 * super.deleteData("deleteOperativeFlightSegData", segment, sqlSessionFlight);
			 * } }
			 */
			flightSegments.forEach(segment -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase("Y")) {
					try {
						// segment.setFlightId(operativeFlight.getFlightId() - 1);
						/*
						 * segment.setFlgNfl(null); segment.setFlgTecStp(null);
						 * segment.setFlgCargo(null); segment.setNoMail(null);
						 */
						super.insertData("saveFlightSegmentsData", segment, sqlSessionFlight);
					} catch (CustomException e) {
						LOGGER.error(ERR_MSSG, e);
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#fetchFacts(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	public List<OperativeFlightFct> fetchFacts(OperativeFlight oprFltFct) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl fetchFlightSegmentLegList() ", oprFltFct);
		List<OperativeFlightFct> flightFcts = super.fetchList(FETCH_FACTS, oprFltFct, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "fetchFacts", Level.DEBUG, oprFltFct, flightFcts);
		return flightFcts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#saveFact(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void saveFact(OperativeFlight oprFltFct) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveFlightFct ", oprFltFct);
		List<OperativeFlightFct> flightFcts = oprFltFct.getFlightFcts();
		if (!CollectionUtils.isEmpty(flightFcts)) {
			for (OperativeFlightFct flightFct : flightFcts) {
				if (flightFct.getRemarks() != null
						&& InsertIndicator.YES.toString().equalsIgnoreCase(flightFct.getFlagInsert())) {
					super.insertData(SAVE_FACTS, flightFct, sqlSessionFlight);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#saveFlightAttributes(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void saveFlightAttributes(OperativeFlight operatingFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveFlightAttributes ", operatingFlight);
		List<OperativeFlightLeg> flightLegs = operatingFlight.getFlightLegs();
		if (!CollectionUtils.isEmpty(flightLegs)) {
			for (OperativeFlightLeg flightLeg : flightLegs) {
				flightLeg.setFlightId(operatingFlight.getFlightId());
				if (Optional.ofNullable(flightLeg.getWarehouseLevel()).isPresent()
						|| Optional.ofNullable(flightLeg.getBubdOffice()).isPresent()
						|| Optional.ofNullable(flightLeg.getRho()).isPresent()) {
					
					if (MultiTenantUtility.isTenantAirport(flightLeg.getBoardPointCode())
							|| MultiTenantUtility.isTenantAirport(flightLeg.getOffPointCode())) {
						super.insertData(SAVE_FLIGHT_ATTRIBUTES, flightLeg, sqlSessionFlight);
					}
					
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateFact(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void updateFact(OperativeFlight oprFltFct) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightFct ", oprFltFct);
		List<OperativeFlightFct> flightFcts = oprFltFct.getFlightFcts();
		if (!CollectionUtils.isEmpty(flightFcts)) {
			for (OperativeFlightFct fact : flightFcts) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(fact.getFlagDelete())) {
					super.deleteData(DELETE_FACTS, fact, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(fact.getFlagUpdate())) {
					super.updateData(UPDATE_FACT, fact, sqlSessionFlight);
				} else if (InsertIndicator.YES.toString().equalsIgnoreCase(fact.getFlagInsert())) {
					super.insertData(SAVE_FACTS, fact, sqlSessionFlight);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#fetchJointFlights(com.ngen.cosys.flight.
	 * model.OperativeFlight)
	 */
	@Override
	public List<OperativeFlightJoint> fetchJointFlights(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl fetchJointFlightList", oprFlt);
		List<OperativeFlightJoint> flightJoints = super.fetchList(FETCH_JOINTS, oprFlt, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "fetchFacts", Level.DEBUG, oprFlt, flightJoints);
		return flightJoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#saveJointFlight(com.ngen.cosys.flight.
	 * model.OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void saveJointFlight(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveJointFlight ", oprFlt);
		List<OperativeFlightJoint> flightJoints = oprFlt.getFlightJoints();

		if (!CollectionUtils.isEmpty(flightJoints)) {

			for (OperativeFlightJoint flightJoint : flightJoints) {

				super.insertData(SAVE_JOINT, flightJoint, sqlSessionFlight);

			}
		}

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void validateJointFlight(OperativeFlightLeg operatingFlightleg) throws CustomException {
		if (operatingFlightleg.getJointFlightCarCode() != null && operatingFlightleg.getJointFlightCarCode() != "") {
			int count = (Integer) super.fetchObject("validateExitingJointFlight", operatingFlightleg, sqlSessionFlight);
			if (operatingFlightleg.getJointFlightCarCode().length() != 0) {
				if (count == 0) {
					operatingFlightleg.addError("invalid.joint.flight.carrier", "flightExp", ErrorType.ERROR);

				}

			}
		}

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void validateUldTyp(OperativeFlight operativeFlight) throws CustomException {
		for (int i = 0; i <= operativeFlight.getFlightExpULDTyps().size() - 1; i++) {
			if (!ObjectUtils.isEmpty(operativeFlight.getFlightExpULDTyps().get(i))) {
				OperativeFlightExp operatingFlightUld = operativeFlight.getFlightExpULDTyps().get(i);
				if (operatingFlightUld.getUldExpType() != null && (operatingFlightUld.getUldExpType().length() != 0)) {
					int count = (Integer) super.fetchObject("validateExitingUldType", operatingFlightUld,
							sqlSessionFlight);
					if (count == 0) {
						operativeFlight.addError("data.Invalid.Uld", "FlightExpULDTyps[" + i + "].flightExp",
								ErrorType.ERROR);
					}
				}
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateJointFlight(com.ngen.cosys.flight.
	 * model.OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void updateJointFlight(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateJointFlight ", oprFlt);
		List<OperativeFlightJoint> flightJoints = oprFlt.getFlightJoints();
		if (!CollectionUtils.isEmpty(flightJoints)) {
			for (OperativeFlightJoint flightJoint : flightJoints) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(flightJoint.getFlagDelete())) {
					super.deleteData(DELETE_JOINTS, flightJoint, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(flightJoint.getFlagUpdate())) {
					super.updateData(UPDATE_JOINT, flightJoint, sqlSessionFlight);
				}
			}
			flightJoints.forEach(jointFlight -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(jointFlight.getFlagInsert())) {
					try {
						if (jointFlight.getJointFlightCarCode() != null && jointFlight.getJointFlightCarCode() != "") {
							super.insertData(SAVE_JOINT, jointFlight, sqlSessionFlight);
						}
					} catch (CustomException e) {
						LOGGER.debug(ERR_MSSG, e);
					}
				}
			});

			/*
			 * for (OperativeFlightJoint flightJoint : flightJoints) {
			 * super.deleteData(DELETE_JOINTS, flightJoint, sqlSessionFlight); } for
			 * (OperativeFlightJoint flightJoint : flightJoints) { if(flightJoints.get) {
			 * 
			 * super.insertData(SAVE_JOINT, flightJoint, sqlSessionFlight); } }
			 */

			for (OperativeFlightJoint flightJoint : flightJoints) {
				super.deleteData(DELETE_JOINTS, flightJoint, sqlSessionFlight);
			}
			flightJoints.forEach(jointFlight -> {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(jointFlight.getFlagInsert())) {
					try {
						if (jointFlight.getJointFlightCarCode() != null && jointFlight.getJointFlightCarCode() != "") {
							super.insertData(SAVE_JOINT, jointFlight, sqlSessionFlight);
						}
					} catch (CustomException e) {
						LOGGER.debug(ERR_MSSG, e);
					}
				}
			});

		}
	}

	/**
	 * This method is used for fetching ByPassULDType.
	 * 
	 * @param oprFlt
	 * @return List<OperativeFlightExp>
	 * @throws CustomException
	 */
	private List<OperativeFlightExp> fetchFlightExpULDList(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl fetchFlightExpList ", oprFlt);
		OperativeFlightExp operativeFlightExp = new OperativeFlightExp();
		operativeFlightExp.setFlightId(oprFlt.getFlightId());
		operativeFlightExp.setExceptionType(BY_PASS_ULD_TYPE);
		return super.fetchList(FETCH_EXPSS, operativeFlightExp, sqlSessionFlight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#fetchExps(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	public List<OperativeFlightExp> fetchExps(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl fetchFlightExpList ", oprFlt);
		OperativeFlightExp operativeFlightExp = new OperativeFlightExp();
		operativeFlightExp.setFlightId(oprFlt.getFlightId());
		operativeFlightExp.setExceptionType(BY_PASS_ULD_MAX_CHECK);
		return super.fetchList(FETCH_EXPSS, operativeFlightExp, sqlSessionFlight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#saveExp(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_EXCEPTION)
	public void saveExp(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl saveFlightExp ", oprFlt);
		List<OperativeFlightExp> flightExps = oprFlt.getFlightExps();
		List<OperativeFlightExp> flightExpULDTyps = oprFlt.getFlightExpULDTyps();
		if (!CollectionUtils.isEmpty(flightExps)) {
			for (OperativeFlightExp flightExp : flightExps) {
				if (!StringUtils.isEmpty(flightExp.getUldWtReason())) {
					flightExp.setFlightId(oprFlt.getFlightId());
					super.insertData(SAVE_EXPS, flightExp, sqlSessionFlight);
				} else {
					oprFlt.addError("data.UldWtReason", "flightExp", ErrorType.ERROR);
				}
			}
		}
		if (!CollectionUtils.isEmpty(flightExpULDTyps)) {
			for (OperativeFlightExp flightExpUld : flightExpULDTyps) {
				if (flightExpUld.getUldWtReason() != null) {
					flightExpUld.setFlightId(oprFlt.getFlightId());
					super.insertData(SAVE_EXPS, flightExpUld, sqlSessionFlight);
				} else {
					oprFlt.addError("data.UldWtReason", "flightExpUld", ErrorType.ERROR);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateExp(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_EXCEPTION)
	public void updateExp(OperativeFlight oprFlt) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightExp ", oprFlt);
		List<OperativeFlightExp> flightExps = oprFlt.getFlightExps();
		if (!CollectionUtils.isEmpty(flightExps)) {
			for (OperativeFlightExp expWt : flightExps) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(expWt.getFlagDelete())) {
					super.deleteData(DELETE_EXPS, expWt, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(expWt.getFlagUpdate())) {
					if (!StringUtils.isEmpty(expWt.getUldWtReason())) {
						super.updateData(UPDATE_EXPS, expWt, sqlSessionFlight);
					} else {
						oprFlt.addError("data.UldWtReason", "expWt", ErrorType.ERROR);
					}
				} else if (InsertIndicator.YES.toString().equalsIgnoreCase(expWt.getFlagInsert())) {
					if (!StringUtils.isEmpty(expWt.getUldWtReason())) {
						super.insertData(SAVE_EXPS, expWt, sqlSessionFlight);
					} else {
						oprFlt.addError("data.UldWtReason", "expWt", ErrorType.ERROR);
					}
				}
			}
		}
		upateFlightExpULDTyps(oprFlt);
	}

	/**
	 * It handles ExpULD Types Transaction.
	 * 
	 * @param oprFlt
	 * @throws CustomException
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_EXCEPTION)
	private void upateFlightExpULDTyps(OperativeFlight oprFlt) throws CustomException {
		List<OperativeFlightExp> flightExpULDTyps = oprFlt.getFlightExpULDTyps();
		if (!CollectionUtils.isEmpty(flightExpULDTyps)) {
			for (OperativeFlightExp expULDTyps : flightExpULDTyps) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(expULDTyps.getFlagDelete())) {
					super.deleteData(DELETE_EXPS, expULDTyps, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(expULDTyps.getFlagUpdate())) {
					if (!StringUtils.isEmpty(expULDTyps.getUldWtReason())) {
						super.updateData(UPDATE_EXPS, expULDTyps, sqlSessionFlight);
					} else {
						oprFlt.addError("data.UldTypeReason", "expULDTyps", ErrorType.ERROR);
					}
				}
			}
			for (OperativeFlightExp expULDTyps : flightExpULDTyps) {
				if (InsertIndicator.YES.toString().equalsIgnoreCase(expULDTyps.getFlagInsert())) {

					if (!StringUtils.isEmpty(expULDTyps.getUldWtReason())) {
						super.insertData(SAVE_EXPS, expULDTyps, sqlSessionFlight);
					} else {
						oprFlt.addError("data.UldTypeReason", "expULDTyps", ErrorType.ERROR);
					}

				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#deleteLeg(com.ngen.cosys.flight.model.
	 * OperativeFlightLeg)
	 */
	@Override
	public boolean deleteLeg(OperativeFlightLeg flightLeg) throws CustomException {
		int count = super.deleteData(DELETE_LEG, flightLeg, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "deleteLeg", Level.DEBUG, flightLeg, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#deleteFact(com.ngen.cosys.flight.model.
	 * OperativeFlightFct)
	 */
	@Override
	public boolean deleteFact(OperativeFlightFct flightFact) throws CustomException {
		int count = super.deleteData(DELETE_FACTS, flightFact, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "deleteFact", Level.DEBUG, flightFact, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#deleteSeg(com.ngen.cosys.flight.model.
	 * OperativeFlightSegment)
	 */
	@Override
	public boolean deleteSeg(OperativeFlightSegment deleteFlightSeg) throws CustomException {
		int count = super.deleteData(DELETE_SEGMENTS, deleteFlightSeg, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "deleteSeg", Level.DEBUG, deleteFlightSeg, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#validateLegs(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	public int validateLegs(OperativeFlight flightLeg) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl validateLegs ", flightLeg);
		return super.fetchObject("validateFlightLegs", flightLeg, sqlSessionFlight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#validateCarrier(java.lang.String)
	 */
	@Override
	public boolean validateCarrier(String carrierCode) throws CustomException {
		int count = (Integer) super.fetchObject("validateFlightCarrier", carrierCode.toUpperCase(), sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "validateCarrier", Level.DEBUG, carrierCode, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#isValidServiceType(java.lang.String)
	 */
	@Override
	public boolean isValidServiceType(String serviceType) throws CustomException {
		int count = (Integer) super.fetchObject("validateSvType", serviceType.toUpperCase(), sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidServiceType", Level.DEBUG, serviceType, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#isValidAircraftModel(java.lang.String)
	 */
	@Override
	public boolean isValidAircraftModel(String aircraftModel) throws CustomException {
		int count = (Integer) super.fetchObject("validateAircraftModel", aircraftModel.toUpperCase(), sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidAircraftModel", Level.DEBUG, aircraftModel, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#isValidBoardingPoint(java.lang.String)
	 */
	@Override
	public boolean isValidBoardingPoint(String boardPointCode) throws CustomException {
		int count = (Integer) super.fetchObject("validateBoardingPoint", boardPointCode.toUpperCase(),
				sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidBoardingPoint", Level.DEBUG, boardPointCode, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#isValidOffPoint(java.lang.String)
	 */
	@Override
	public boolean isValidOffPoint(String offPointCode) throws CustomException {
		int count = (Integer) super.fetchObject("validateOffPoint", offPointCode.toUpperCase(), sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidOffPoint", Level.DEBUG, offPointCode, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#fetchFlightId(com.ngen.cosys.flight.model
	 * .OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_EXCEPTION)
	public int fetchFlightId(OperativeFlight flightInfo) throws CustomException {
		return (Integer) super.fetchObject("fetchFlightId", flightInfo, sqlSessionFlight);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#isValidJointFlightKey(string)
	 */
	@Override
	public boolean isValidJointFlightKey(String flightKey) throws CustomException {
		int count = (Integer) super.fetchObject("validateFlightKeyExistance", flightKey, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidJointFlightKey", Level.DEBUG, flightKey, count);
		return count != 0;
	}

	@Override
	public void checkForAutoFlight(OperativeFlight operativeFlight) throws CustomException {
		LOGGER.warn("checkForAutoFlight 1");
		int checkIs = 0;
		if (!StringUtils.isEmpty(operativeFlight.getAutoFlightFlag())) {
			LOGGER.warn("Inside if checkForAutoFlight");
			if (AUTO_FLIGHT_OFF.equals(operativeFlight.getAutoFlightFlag())) {
				LOGGER.warn("Inside if 1 checkForAutoFlight");
				operativeFlight.setAutoFlightFlag(AUTO_FLIGHT_OFF);
				operativeFlight.setFlightAutoCompleteFlag(AUTO_COMPLETE_OFF);
				checkIs++;
			} else {
				LOGGER.warn("Inside else checkForAutoFlight");
				checkIs = 0;
			}
		}
		if (checkIs == 0) {
			LOGGER.warn("Else 1 checkForAutoFlight 2");
			int checkImport = 0;
			int i = 0;
			LOGGER.warn("Before Flight Leg List");
			for (OperativeFlightLeg leg : operativeFlight.getFlightLegs()) {
				LOGGER.warn("Inside Flight Leg List board point Import = " + leg.getBoardPointCode());
				if (!MultiTenantUtility.isTenantAirport(leg.getBoardPointCode())) {
					LOGGER.warn("Inside Import Case");
					checkImport++;
					// Import Case
					if (StringUtils.isEmpty(operativeFlight.getAutoFlightFlag())
							|| AUTO_FLIGHT_OFF.equals(operativeFlight.getAutoFlightFlag())) {
						operativeFlight.setAutoFlightFlag(AUTO_FLIGHT_OFF);
						operativeFlight.setFlightAutoCompleteFlag(AUTO_COMPLETE_OFF);
					} else {
						if (++i == operativeFlight.getFlightLegs().size()) {
							operativeFlight.addError("data.auto.Flight.status", "Carrier is not exists in AutoFlight",
									ErrorType.ERROR);
						}
					}
				} else {
					LOGGER.warn("Inside Export Case");

					LOGGER.warn("Inside Flight Leg List board point Export 2 = " + leg.getBoardPointCode());
					if (!StringUtils.isEmpty(operativeFlight.getAutoFlightFlag())
							&& (AUTO_FLIGHT_OFF.equals(operativeFlight.getAutoFlightFlag()) && checkImport != 0)) {
						operativeFlight.setAutoFlightFlag(null);
					}
					// Export Case
					int temp = 0;
					int multipleDest = 0;
					List<OperativeFlightAuto> autoflightlistCarrier = Collections.emptyList();
					List<OperativeFlightAuto> autoflightlistCarrierWithflt = Collections.emptyList();
					List<OperativeFlightAuto> autoflightlistCarrierWithDest = Collections.emptyList();
					List<OperativeFlightAuto> autoflightlistCarrierFltWithDest = Collections.emptyList();
					for (OperativeFlightLeg leg1 : operativeFlight.getFlightLegs()) {
						LOGGER.warn("Inside Export Case Flight Legs");
						leg1.setFlightKey(operativeFlight.getFlightKey());
						leg1.setCarrierCode(operativeFlight.getCarrierCode());
						if (MultiTenantUtility.isTenantAirport(leg1.getBoardPointCode())) {
							autoflightlistCarrier = super.fetchList("checkForAutoCarrier", leg1, sqlSessionFlight);
							autoflightlistCarrierWithflt = super.fetchList("checkForAutoFlightWithCarrier", leg1,
									sqlSessionFlight);
							autoflightlistCarrierWithDest = super.fetchList("checkForAutoFlightWithDest", leg1,
									sqlSessionFlight);
							autoflightlistCarrierFltWithDest = super.fetchList("checkForAutoCarrierWithDest", leg1,
									sqlSessionFlight);
							LOGGER.warn("Inside Export Case Flight Legs 2");
							if (!CollectionUtils.isEmpty(autoflightlistCarrier)) {
								LOGGER.warn("autoflightlistCarrier = " + autoflightlistCarrier.toString());
							} else {
								LOGGER.warn("autoflightlistCarrier = NULL");
							}
							if (!CollectionUtils.isEmpty(autoflightlistCarrierWithflt)) {
								LOGGER.warn(
										"autoflightlistCarrierWithflt = " + autoflightlistCarrierWithflt.toString());
							} else {
								LOGGER.warn("autoflightlistCarrierWithflt = NULL");
							}
							if (!CollectionUtils.isEmpty(autoflightlistCarrierWithDest)) {
								LOGGER.warn(
										"autoflightlistCarrierWithDest = " + autoflightlistCarrierWithDest.toString());
								temp++;
							} else {
								LOGGER.warn("autoflightlistCarrierWithDest = NULL");
							}
							if (!CollectionUtils.isEmpty(autoflightlistCarrierFltWithDest)) {
								LOGGER.warn("autoflightlistCarrierFltWithDest = "
										+ autoflightlistCarrierFltWithDest.toString());
								multipleDest++;
							} else {
								LOGGER.warn("autoflightlistCarrierFltWithDest = NULL");
							}
						}

					}
					if (StringUtils.isEmpty(operativeFlight.getAutoFlightFlag())) {
						if (!CollectionUtils.isEmpty(autoflightlistCarrier)
								|| !CollectionUtils.isEmpty(autoflightlistCarrierWithflt) || (temp != 0)
								|| (multipleDest != 0)) {
							operativeFlight.setAutoFlightFlag(AUTO_FLIGHT_ON);
							operativeFlight.setFlightAutoCompleteFlag(AUTO_COMPLETE_ON);
						} else {
							operativeFlight.setAutoFlightFlag(AUTO_FLIGHT_OFF);
							operativeFlight.setFlightAutoCompleteFlag(AUTO_COMPLETE_OFF);
						}
					} else if (AUTO_FLIGHT_OFF.equals(operativeFlight.getAutoFlightFlag())) {
						operativeFlight.setAutoFlightFlag(AUTO_FLIGHT_OFF);
						operativeFlight.setFlightAutoCompleteFlag(AUTO_COMPLETE_OFF);
					} else {
						if (!CollectionUtils.isEmpty(autoflightlistCarrier)
								|| !CollectionUtils.isEmpty(autoflightlistCarrierWithflt) || (temp != 0)
								|| (multipleDest != 0)) {
							operativeFlight.setAutoFlightFlag(AUTO_FLIGHT_ON);
							operativeFlight.setFlightAutoCompleteFlag(AUTO_COMPLETE_ON);
						} else {
							operativeFlight.addError("data.auto.Flight.status", "Carrier is not exists in AutoFlight",
									ErrorType.ERROR);
						}
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#deleteExp(com.ngen.cosys.flight.model.
	 * OperativeFlightExp)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_EXCEPTION)
	public boolean deleteExp(OperativeFlightExp flightExp) throws CustomException {
		int count = super.deleteData(DELETE_EXPS, flightExp, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "deleteExp", Level.DEBUG, flightExp, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#isValidateFromSectorAndToSector(string)
	 */
	@Override
	public boolean isValidateFromSectorAndToSector(String finalDestination) throws CustomException {
		int count = (Integer) super.fetchObject(VALIDATE_SECTOR, finalDestination, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidateFromSectorAndToSector", Level.DEBUG, finalDestination,
				count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#isValidateFromSectorAndToSector(string)
	 */
	@Override
	public boolean isValidUldKey(String uldKey) throws CustomException {
		int count = (Integer) super.fetchObject("validateUldKeyExistance", uldKey, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "isValidUldKey", Level.DEBUG, uldKey, count);
		return count != 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#fetchFlightByKey(java.lang.String)
	 */
	@Override

	public OperativeFlight fetchFlightByKey(String flightKey) throws CustomException {
		OperativeFlight flightRS = super.fetchObject("fetchFlightByKey", flightKey, sqlSessionFlight);
		if (flightRS != null) {
			flightRS.setFlightLegs(fetchLegs(flightRS));
			flightRS.setFlightSegments(fetchSegmentLegList(flightRS));
		}
		LOGGER.debug(this.getClass().getName(), "fetchFlightByKey", Level.DEBUG, flightKey, flightRS);
		return flightRS;
	}

	@Override
	public ImportFlightevents updateImportFlight(ImportFlightevents flight) throws CustomException {
		this.insertData("insertImportFlight", flight, sqlSessionFlight);
		return flight;
	}

	// Customs flight for Import
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	public CustomsFlight updateImportCustomsFlight(CustomsFlight flight) throws CustomException {
		this.insertData("insertImportCustomsFlight", flight, sqlSessionFlight);
		return flight;
	}

	// nsert ics interface details flight for Import
	@Override
	public IcsFlightDetails insertIcsInterfaceForImport(IcsFlightDetails flight) throws CustomException {
		this.insertData("insertIcsInterfaceImport", flight, sqlSessionFlight);
		return flight;

	}

	// get count from interface ics countInterICS
	@Override
	public boolean countInterfaceICS(IcsFlightDetails ics) throws CustomException {
		int count = (Integer) super.fetchObject("countInterICS", ics, sqlSessionFlight);
		LOGGER.debug(this.getClass().getName(), "countInterfaceICS", Level.DEBUG, ics, count);
		return count != 0;
	}

	// Customs flight for Export
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	public CustomsFlight updateExportCustomsFlight(CustomsFlight flight) throws CustomException {
		this.insertData("insertExportCustomsFlight", flight, sqlSessionFlight);
		return flight;

	}

	@Override
	public ExportFlightevents updateEmportFlight(ExportFlightevents flight) throws CustomException {
		this.insertData("insertExportFlight", flight, sqlSessionFlight);
		return flight;
	}

	@Override
	public boolean checkExpFlightEvent(ExportFlightevents flightId) throws CustomException {
		int count = fetchObject("checkExportFlightEvent", flightId, sqlSessionFlight);
		return count > 0;
	}

	@Override
	public boolean checkExpFlightSTD(OperativeFlightLeg flight) throws CustomException {
		int count = fetchObject("checkExportFlightSTD", flight, sqlSessionFlight);
		return count > 0;
	}

	@Override
	public boolean checkImpFlightSTA(OperativeFlightLeg flight) throws CustomException {
		int count = fetchObject("checkImportFlightSTA", flight, sqlSessionFlight);
		return count > 0;
	}

	@Override
	public boolean checkImpFlightEvent(ImportFlightevents flightId) throws CustomException {
		int count = fetchObject("checkImportFlightEvent", flightId, sqlSessionFlight);
		return count > 0;
	}

	@Override
	public boolean validateDomesticFlight(OperativeFlightLeg leg) throws CustomException {
		int count = super.fetchObject("validateDomesticOptFlightLeg", leg, sqlSessionFlight);
		return count == 1;
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void updateLegsData(OperativeFlight operatingFlight) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightLegs ", operatingFlight);
		List<OperativeFlightLeg> flightLegs = operatingFlight.getFlightLegs();
		LocalDateTime createdOn = operatingFlight.getCreatedOn();
		if (!CollectionUtils.isEmpty(flightLegs)) {
			/*
			 * for (OperativeFlightLeg leg : flightLegs) {
			 * leg.setFlightId(operatingFlight.getFlightId()); if (leg.getFlightId() > 1000)
			 * super.deleteData("deleteOperativeFlightLegData", leg, sqlSessionFlight);
			 * 
			 * }
			 */
			for (OperativeFlightLeg leg : flightLegs) {
				leg.setFlightId(operatingFlight.getFlightId());
				// if (leg.getFlightId() > 1000)
				super.insertData("saveFlightLegs", leg, sqlSessionFlight);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.flight.dao.FlightDAO#update(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_CREATION)
	public OperativeFlight updateDataForOperative(OperativeFlight operatingFlight) throws CustomException {
		if (Optional.ofNullable(operatingFlight).isPresent()) {
			super.insertData(SAVE, operatingFlight, sqlSessionFlight);
			getFlightId(operatingFlight);
			deriveHandlingArea(operatingFlight);
			saveLegs(operatingFlight);
			saveSegments(operatingFlight);
			saveExp(operatingFlight);
			saveJointFlight(operatingFlight);
			saveFact(operatingFlight);
			updateFlightEvent(operatingFlight, operatingFlight.getTenantAirport());
			/*
			 * int count = fetchBookingDetails(operatingFlight); if (count == 0) {
			 * super.insertData(SAVE, operatingFlight, sqlSessionFlight);
			 * getFlightId(operatingFlight); deriveHandlingArea(operatingFlight);
			 * saveLegs(operatingFlight); saveSegments(operatingFlight);
			 * saveExp(operatingFlight); saveJointFlight(operatingFlight);
			 * saveFact(operatingFlight); }
			 */
		}
		LOGGER.debug(this.getClass().getName(), "update", Level.DEBUG, operatingFlight, operatingFlight);
		return operatingFlight;

	}

	public void updateFlightEvent(OperativeFlight flight, String tenantId) throws CustomException {
		if (!CollectionUtils.isEmpty(flight.getFlightLegs())) {
			for (OperativeFlightLeg legs : flight.getFlightLegs()) {
				if (MultiTenantUtility.isTenantAirport(legs.getBoardPointCode())) {
					ExportFlightevents exportFlight = new ExportFlightevents();

					exportFlight.setFlightId(flight.getFlightId());
					if (!checkExpFlightEvent(exportFlight)) {
						// updateEmportFlight(exportFlight);
						CustomsFlight customsFlight = new CustomsFlight();

						customsFlight.setFlightBoardPoint(legs.getBoardPointCode());
						customsFlight.setFlightOffPoint(legs.getOffPointCode());
						customsFlight.setImportExportIndicator("E");
						customsFlight.setFlightType(flight.getCaoPax());
						customsFlight.setFlightKey(flight.getFlightKey());
						customsFlight.setFlightDate(legs.getDepartureDate());
						customsFlight.setOprFlightDate(flight.getFlightDate());
						customsFlight.setCreatedBy(flight.getCreatedUserCode());
						customsFlight.setFlightCancelFlag(false);

						// updateExportCustomsFlightSchedule(customsFlight);
					}
				}

				// CHECKING iMPORT FLIGHT
				if (legs.getOffPointCode().equals(flight.getTenantId())) {
					ImportFlightevents importFlight = new ImportFlightevents();
					importFlight.setFlightId(flight.getFlightId());
					if (!checkImpFlightEvent(importFlight)) {
						// updateImportFlight(importFlight);
						CustomsFlight customsFlight = new CustomsFlight();
						customsFlight.setFlightBoardPoint(legs.getBoardPointCode());
						customsFlight.setFlightOffPoint(legs.getOffPointCode());
						customsFlight.setImportExportIndicator("I");
						customsFlight.setFlightType(flight.getCaoPax());
						customsFlight.setFlightKey(flight.getFlightKey());
						customsFlight.setFlightDate(legs.getArrivalDate());
						customsFlight.setOprFlightDate(flight.getFlightDate());
						customsFlight.setCreatedBy(flight.getCreatedUserCode());
						customsFlight.setFlightCancelFlag(false);
						// updateImportCustomsFlightSchedule(customsFlight);

					}
				}
			}
		}

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	public void updateImportCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException {
		super.updateData("updateImportCustomsSchedule", customsFlight, sqlSessionFlight);

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	private void updateImportCancelCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException {
		super.updateData("updateImportCancelCustomsSchedule", customsFlight, sqlSessionFlight);

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	private void updateExportCancelCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException {
		super.updateData("updateExportCancelCustomsSchedule", customsFlight, sqlSessionFlight);

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	public void updateExportCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException {
		super.updateData("updateExportCustomsSchedule", customsFlight, sqlSessionFlight);

	}

	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	private void updateImportExportCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException {
		super.updateData("updateImportExportCustomsSchedule", customsFlight, sqlSessionFlight);

	}

	// for update
	// for update
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public OperativeFlight updateMaintain(OperativeFlight operatingFlight) throws CustomException {
		super.insertData(SAVE, operatingFlight, sqlSessionFlight);
		getFlightId(operatingFlight);
		deriveHandlingArea(operatingFlight);
		saveLegs(operatingFlight);
		saveSegments(operatingFlight);
		saveExp(operatingFlight);
		saveJointFlight(operatingFlight);
		saveFact(operatingFlight);
		return operatingFlight;
	}

	/**
	 * Method to update/save handling area after the first save
	 * 
	 * @param operatingFlight
	 * @throws CustomException
	 */
	protected void saveHandlingAreaData(final OperativeFlight operatingFlight) throws CustomException {
		// Delete existing handling areas for the given flight
		this.deleteData("sqlDeleteHandlingAreaByFlight", operatingFlight, sqlSessionFlight);

		if (!CollectionUtils.isEmpty(operatingFlight.getHandlingArea())) {
			for (String t : operatingFlight.getHandlingArea()) {
				OperativeFlightHandlingArea param = new OperativeFlightHandlingArea();
				param.setFlightId(operatingFlight.getFlightId());
				param.setTerminalCode(t);
				super.insertData("insertFlightHandlingArea", param, sqlSessionFlight);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.flight.dao.FlightDAO#updateFact(com.ngen.cosys.flight.model.
	 * OperativeFlight)
	 */
	@Override
	public void updateFactData(OperativeFlight oprFltFct) throws CustomException {
		LOGGER.debug("Log message at DEBUG level from FlightDAOImpl updateFlightFct ", oprFltFct);
		List<OperativeFlightFct> flightFcts = oprFltFct.getFlightFcts();
		if (!CollectionUtils.isEmpty(flightFcts)) {
			for (OperativeFlightFct fact : flightFcts) {
				if (DeleteIndicator.YES.toString().equalsIgnoreCase(fact.getFlagDelete())) {
					super.deleteData(DELETE_FACTS, fact, sqlSessionFlight);
				} else if (UpdateIndicator.YES.toString().equalsIgnoreCase(fact.getFlagUpdate())) {
					super.updateData(UPDATE_FACT, fact, sqlSessionFlight);
				} else if (InsertIndicator.YES.toString().equalsIgnoreCase(fact.getFlagInsert())
						&& Optional.ofNullable(fact.getRemarks()).isPresent() && fact.getFlightId() > 1000) {
					super.insertData(SAVE_FACTS, fact, sqlSessionFlight);
				}
			}
		}
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT)
	public void deleteOperativeForData(OperativeFlight operatingFlight) throws CustomException {
		if (Optional.ofNullable(operatingFlight).isPresent()) {
			if (Optional.ofNullable(operatingFlight.getFlightFcts()).isPresent()) {
				for (OperativeFlightFct facts : operatingFlight.getFlightFcts()) {
					super.deleteData("deleteOperativeFlightFactsData", facts, sqlSessionFlight);
				}
			}
			if (Optional.ofNullable(operatingFlight.getFlightSegments()).isPresent()) {
				for (OperativeFlightSegment seg : operatingFlight.getFlightSegments()) {
					super.deleteData("deleteOperativeFlightSegmentsData", seg, sqlSessionFlight);
				}
			}
			if (Optional.ofNullable(operatingFlight.getHandlingArea()).isPresent()) {
				for (String handler : operatingFlight.getHandlingArea()) {
					super.deleteData("sqlDeleteFlightHandlingArea", operatingFlight.getFlightId(), sqlSessionFlight);
				}
			}
			if (Optional.ofNullable(operatingFlight.getFlightExps()).isPresent()) {
				for (OperativeFlightExp exp : operatingFlight.getFlightExps()) {
					super.deleteData("deleteExportFlightEventFromFlight", exp, sqlSessionFlight);
				}
			}

			if (Optional.ofNullable(operatingFlight.getFlightJoints()).isPresent()) {
				for (OperativeFlightJoint jointFlight : operatingFlight.getFlightJoints()) {
					super.deleteData("deleteOperativeFlightJoint", jointFlight, sqlSessionFlight);
				}
			}
			if (Optional.ofNullable(operatingFlight.getFlightLegs()).isPresent()) {
				for (OperativeFlightLeg leg : operatingFlight.getFlightLegs()) {
					super.deleteData("deleteOperativeFlightLegData", leg, sqlSessionFlight);
				}
			}
			super.deleteData("deleteExportFlightEvent", operatingFlight.getFlightId(), sqlSessionFlight);
			super.deleteData("deleteImportFlightEvent", operatingFlight.getFlightId(), sqlSessionFlight);
			super.deleteData("deleteIcsInterface", operatingFlight.getFlightId(), sqlSessionFlight);
			// allow to delete Equipment Request
			List<Long> equipmentRequestIdList = equipmentRequestCount(operatingFlight);
			if (!CollectionUtils.isEmpty(equipmentRequestIdList)) {
				for (Long equipmentRequestId : equipmentRequestIdList) {
					super.deleteData("deleteEquipment_Request_Required_Containers_Info", equipmentRequestId,
							sqlSessionFlight);
					super.deleteData("deleteEquipment_Request_Shipments", equipmentRequestId, sqlSessionFlight);
					super.deleteData("deleteEquipment_Request_Release_Details", equipmentRequestId, sqlSessionFlight);
				}
				super.deleteData("deleteEquipment_Request", operatingFlight, sqlSessionFlight);
			}
			super.deleteData("deleteOperativeFlightData", operatingFlight, sqlSessionFlight);
		}
	}

	@Override
	public int fetchBookingCount(OperativeFlight flightInfo) throws CustomException {
		return super.fetchObject("checkBookedFlightStatus", flightInfo, sqlSessionFlight);
	}

	@Override
	public int fetchImportManifestCount(OperativeFlight flightInfo) throws CustomException {
		return super.fetchObject("checkBookedImportFlightStatus", flightInfo, sqlSessionFlight);
	}

	@Override
	public List<Long> equipmentRequestCount(OperativeFlight flightInfo) throws CustomException {
		return super.fetchList("equipmentRequestId", flightInfo, sqlSessionFlight);
	}

	@Override
	public int fetchFFMManifestCount(OperativeFlight flightInfo) throws CustomException {
		return super.fetchObject("checkBookedFFMFlightStatus", flightInfo, sqlSessionFlight);
	}

	@Override
	public int fetchInboundContainerCount(OperativeFlight flightInfo) throws CustomException {
		return super.fetchObject("fetchInboundContainerCount", flightInfo, sqlSessionFlight);
	}

	@Override
	public boolean checkImportFlights(OperativeFlight flightInfo) throws CustomException {
		int exits = super.fetchObject("importFlightExits", flightInfo, sqlSessionFlight);
		return !(exits == 0);
	}

	@Override
	public boolean checkExportFlights(OperativeFlight flightInfo) throws CustomException {
		int exits = super.fetchObject("exportFlightExits", flightInfo, sqlSessionFlight);
		return !(exits == 0);
	}

	@Override
	public int fetchBookingStatusDetails(OperativeFlight flightInfo) throws CustomException {
		return super.fetchObject("bookingStatus", flightInfo, sqlSessionFlight);
	}

	@Override
	public LocalDateTime getDateFromSystem(OperativeFlight operatingFlight) throws CustomException {
		return super.fetchObject("getSystemDate", operatingFlight, sqlSessionFlight);
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.ACES_CUSTOMS)
	public void updateIECustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException {
		super.updateData("updateImportExportCustomsSchedule", customsFlight, sqlSessionFlight);
	}

	@Override
	public boolean existCustoms(CustomsFlight customsFlight) throws CustomException {
		int exits = super.fetchObject("customsImportExists", customsFlight, sqlSessionFlight);
		return !(exits == 0);
	}

	@Override
	public boolean existExportCustoms(CustomsFlight customsFlight) throws CustomException {
		int exits = super.fetchObject("customsImportExists", customsFlight, sqlSessionFlight);
		return !(exits == 0);
	}

	@Override
	public int checkflightExistancewithsta(OperativeFlightLeg requestModel) throws CustomException {
		int count = fetchObject("checkFlightExistanceWithSta", requestModel, sqlSessionFlight);
		return count;
	}

	@Override
	public int checkflightExistancewithstd(OperativeFlightLeg requestModel) throws CustomException {
		int countExport = fetchObject("checkFlightExistanceWithStd", requestModel, sqlSessionFlight);
		return countExport;
	}

	@Override
	public String fetchStationHandler() throws CustomException {
		return fetchObject("fetchStationHandler", null, sqlSessionFlight);
	}

	// For the fix of 13588
	@Override
	public int checkFlightExistanceWithStdorstawithsin(OperativeFlightLeg request) throws CustomException {
		return fetchObject("checkFlightExistanceWithStdorstawithsin", request, sqlSessionFlight);

	}

	@Override
	public int checkFlightExistanceWithStawithoutBoardPoint(OperativeFlightLeg requestModel) throws CustomException {
		int countImport = fetchObject("checkFlightExistanceWithStawithoutBoardPoint", requestModel, sqlSessionFlight);
		return countImport;
	}

	@Override
	public int checkFlightExistanceWithStawithoutOffPoint(OperativeFlightLeg requestModel) throws CustomException {
		int countExport = fetchObject("checkFlightExistanceWithStawithoutOffPoint", requestModel, sqlSessionFlight);
		return countExport;
	}

}