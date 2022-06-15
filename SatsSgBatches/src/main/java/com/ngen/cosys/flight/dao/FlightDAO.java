/**
 * FlightDAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 August, 2017 NIIT -
 */
package com.ngen.cosys.flight.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.flight.model.CustomsFlight;
import com.ngen.cosys.flight.model.ExportFlightevents;
import com.ngen.cosys.flight.model.FlightEnroutement;
import com.ngen.cosys.flight.model.IcsFlightDetails;
import com.ngen.cosys.flight.model.ImportFlightevents;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.OperativeFlightExp;
import com.ngen.cosys.flight.model.OperativeFlightFct;
import com.ngen.cosys.flight.model.OperativeFlightJoint;
import com.ngen.cosys.flight.model.OperativeFlightLeg;
import com.ngen.cosys.flight.model.OperativeFlightSegment;
import com.ngen.cosys.framework.exception.CustomException;

/**
 * This interface takes care of the responsibilities related to the Operative
 * Flight Flight DAO operation that comes from the service.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
public interface FlightDAO {

	/**
	 * It is responsible to find operative flight details based on criteria.
	 * 
	 * @param findOperatingFlightRQ
	 * @return
	 * @throws CustomException
	 */
	OperativeFlight find(final OperativeFlight findOperatingFlightRQ) throws CustomException;

	/**
	 * It is responsible for persist details into DB of operative flight.
	 * 
	 * @param maintainOperatingFlightRQ
	 * @return
	 * @throws CustomException
	 */
	OperativeFlight create(final OperativeFlight maintainOperatingFlightRQ) throws CustomException;

	/**
	 * Fetch flight leg list based on flight ID.
	 * 
	 * @param operatingFlight
	 * @return
	 * @throws CustomException
	 */
	List<OperativeFlightLeg> fetchLegs(OperativeFlight operatingFlight) throws CustomException;

	/**
	 * fetch Segment lists based on flight id and flight leg.
	 * 
	 * @param operatingFlight
	 * @return
	 * @throws CustomException
	 */
	List<OperativeFlightSegment> fetchSegmentLegList(OperativeFlight operatingFlight) throws CustomException;

	/**
	 * It persists flight leg details into DB.
	 * 
	 * @param oprFltLeg
	 * @throws CustomException
	 */
	void saveLegs(OperativeFlight oprFltLeg) throws CustomException;

	/**
	 * It is responsible to save flight segments after generating segments based on
	 * flight legs.
	 * 
	 * @param oprFltSegment
	 * @throws CustomException
	 */
	void saveSegments(OperativeFlight oprFltSegment) throws CustomException;

	/**
	 * Update/modification into DB of update flight legs.
	 * 
	 * @param operativeFlight
	 * @throws CustomException
	 */
	void updateLegs(OperativeFlight operativeFlight) throws CustomException;

	/**
	 * update/modification into DB of update flight segments.
	 * 
	 * @param operativeFlight
	 * @throws CustomException
	 */
	void updateSegments(OperativeFlight operativeFlight) throws CustomException;

	/**
	 * It retrieves flight fact list from DB based on criteria.
	 * 
	 * @param oprFltFct
	 * @return
	 * @throws CustomException
	 */

	List<OperativeFlightFct> fetchFacts(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It persists flight fact list in to DB.
	 * 
	 * @param oprFltFct
	 * @throws CustomException
	 */
	void saveFact(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It update flight fact list in to DB.
	 * 
	 * @param oprFltFct
	 * @throws CustomException
	 */
	void updateFact(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It retrieves Joint Flight list from DB based on flight ID..
	 * 
	 * @param oprFltFct
	 * @return
	 * @throws CustomException
	 */
	List<OperativeFlightJoint> fetchJointFlights(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It persists joint flight details in to DB.
	 * 
	 * @param oprFltFct
	 * @throws CustomException
	 */
	void saveJointFlight(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * Manage update transaction of flight fact.
	 * 
	 * @param oprFltFct
	 * @throws CustomException
	 */
	void updateJointFlight(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It retrieves flight exception type based on flight id and exception type.
	 * 
	 * @param oprFltFct
	 * @return
	 * @throws CustomException
	 */
	List<OperativeFlightExp> fetchExps(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It persists flight exception into DB.
	 * 
	 * @param oprFltFct
	 * @throws CustomException
	 */
	void saveExp(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * It is responsible to udpate flight exp.
	 * 
	 * @param oprFltFct
	 * @throws CustomException
	 */
	void updateExp(OperativeFlight oprFltFct) throws CustomException;

	/**
	 * Removes flight leg from DB.
	 * 
	 * @param flightLeg
	 * @return
	 * @throws CustomException
	 */
	boolean deleteLeg(OperativeFlightLeg flightLeg) throws CustomException;

	/**
	 * Removes flight fact from DB.
	 * 
	 * @param flightFact
	 * @return
	 * @throws CustomException
	 */
	boolean deleteFact(OperativeFlightFct flightFact) throws CustomException;

	/**
	 * Removes flight segment from DB.
	 * 
	 * @param deleteFlightSeg
	 * @return
	 * @throws CustomException
	 */
	boolean deleteSeg(OperativeFlightSegment deleteFlightSeg) throws CustomException;

	/**
	 * It is responsible to validate legs.
	 * 
	 * @param finalDestination
	 * @return boolean Value
	 * @throws CustomException
	 */
	int validateLegs(OperativeFlight flightLeg) throws CustomException;

	/**
	 * It saves generated enroutement into DB.
	 * 
	 * @param FlightEnroutement
	 * @throws CustomException
	 */
	void saveNormalEnroutement(List<FlightEnroutement> flightEnroutementList) throws CustomException;

	/**
	 * It validates Carrier Code.
	 * 
	 * @param carrierCode
	 * @return boolean
	 * @throws CustomException
	 */
	boolean validateCarrier(String carrierCode) throws CustomException;

	/**
	 * It validates Service Type.
	 * 
	 * @param serviceType
	 * @return boolean
	 * @throws CustomException
	 */
	boolean isValidServiceType(String serviceType) throws CustomException;

	/**
	 * It Validate AirCraftModel Code.
	 * 
	 * @param aircraftModel
	 * @return boolean
	 * @throws CustomException
	 */
	boolean isValidAircraftModel(String aircraftModel) throws CustomException;

	/**
	 * Validates Boarding Point Code its existing or not in DB.
	 * 
	 * @param boardPointCode
	 * @return boolean
	 * @throws CustomException
	 */
	boolean isValidBoardingPoint(String boardPointCode) throws CustomException;

	/**
	 * Validates Off Point Code its existing or not in DB.
	 * 
	 * @param offPointCode
	 * @return boolean
	 * @throws CustomException
	 */
	boolean isValidOffPoint(String offPointCode) throws CustomException;

	/**
	 * It retrieves flightID after insertion of operative flight.
	 * 
	 * @param flightInfo
	 * @return int
	 * @throws CustomException
	 */
	int fetchFlightId(OperativeFlight flightInfo) throws CustomException;

	/**
	 * It validates JointFlightKey.
	 * 
	 * @param flightKey
	 * @return boolean
	 * @throws CustomException
	 */
	boolean isValidJointFlightKey(String flightKey) throws CustomException;

	/**
	 * It validates JointFlightKey.
	 * 
	 * @param flightKey
	 * @return boolean
	 * @throws CustomException
	 */
	boolean isValidUldKey(String flightKey) throws CustomException;

	/**
	 * It removes record from DB of ULDTYpe.
	 * 
	 * @param flightExp
	 * @return boolean
	 * @throws CustomException
	 */
	boolean deleteExp(OperativeFlightExp flightExp) throws CustomException;

	/**
	 * It removes record from DB of ULD exception Weight Type.
	 * 
	 * @param FlightEnroutement
	 * @throws CustomException
	 */
	boolean isValidateFromSectorAndToSector(String sector) throws CustomException;

	/**
	 * This method fetches Operative Flight by Flight Key.
	 * 
	 * @param flightKey
	 * @return OperativeFlight
	 * @throws CustomException
	 */
	OperativeFlight fetchFlightByKey(String flightKey) throws CustomException;

	ImportFlightevents updateImportFlight(ImportFlightevents flight) throws CustomException;

	ExportFlightevents updateEmportFlight(ExportFlightevents flight) throws CustomException;

	boolean checkExpFlightEvent(ExportFlightevents exportFlight) throws CustomException;

	boolean checkImpFlightEvent(ImportFlightevents importFlight) throws CustomException;

	/**
	 * check for domestic flight
	 * 
	 * @param leg
	 * @return
	 * @throws CustomException validateJointFlight(operatingFlight)
	 */
	boolean validateDomesticFlight(OperativeFlightLeg leg) throws CustomException;

	public void validateJointFlight(OperativeFlightLeg operativeFlightleg) throws CustomException;

	void validateUldTyp(OperativeFlight operativeFlight) throws CustomException;

	void checkForAutoFlight(OperativeFlight operativeFlight) throws CustomException;

	void updateLegsData(OperativeFlight operatingFlight) throws CustomException;

	void updateSegmentsData(OperativeFlight operativeFlight) throws CustomException;

	OperativeFlight updateDataForOperative(OperativeFlight operatingFlight) throws CustomException;

	void updateFactData(OperativeFlight oprFltFct) throws CustomException;

	void deleteOperativeForData(OperativeFlight data) throws CustomException;

	OperativeFlight updateMaintain(OperativeFlight operatingFlight) throws CustomException;

	// int fetchBookingDetails(OperativeFlight flightInfo) throws CustomException;

	int fetchBookingStatusDetails(OperativeFlight flightInfo) throws CustomException;

	// int fetchImportBookingDetails(OperativeFlight flightInfo) throws
	// CustomException;

	boolean checkImportFlights(OperativeFlight flightInfo) throws CustomException;

	boolean checkExportFlights(OperativeFlight flightInfo) throws CustomException;

	CustomsFlight updateImportCustomsFlight(CustomsFlight customsFlight) throws CustomException;

	CustomsFlight updateExportCustomsFlight(CustomsFlight flight) throws CustomException;

	IcsFlightDetails insertIcsInterfaceForImport(IcsFlightDetails flight) throws CustomException;

	boolean countInterfaceICS(IcsFlightDetails ics) throws CustomException;

	LocalDateTime getDateFromSystem(OperativeFlight operatingFlight) throws CustomException;

	boolean checkExpFlightSTD(OperativeFlightLeg flight) throws CustomException;

	boolean checkImpFlightSTA(OperativeFlightLeg flight) throws CustomException;

	int fetchBookingCount(OperativeFlight flightInfo) throws CustomException;

	int fetchImportManifestCount(OperativeFlight flightInfo) throws CustomException;

	void updateIECustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException;

	void updateImportCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException;

	void updateExportCustomsFlightSchedule(CustomsFlight customsFlight) throws CustomException;

	boolean existCustoms(CustomsFlight customsFlight) throws CustomException;

	boolean existExportCustoms(CustomsFlight customsFlight) throws CustomException;

	int fetchFFMManifestCount(OperativeFlight flightInfo) throws CustomException;

	List<Long> equipmentRequestCount(OperativeFlight existingOprFlightDetail) throws CustomException;

	int fetchInboundContainerCount(OperativeFlight existingOprFlightDetail) throws CustomException;

	int checkflightExistancewithsta(OperativeFlightLeg requestModel) throws CustomException;

	int checkflightExistancewithstd(OperativeFlightLeg requestModel) throws CustomException;

	int checkFlightExistanceWithStdorstawithsin(OperativeFlightLeg request) throws CustomException;

	int checkFlightExistanceWithStawithoutBoardPoint(OperativeFlightLeg requestModel) throws CustomException;

	int checkFlightExistanceWithStawithoutOffPoint(OperativeFlightLeg requestModel) throws CustomException;

	public String fetchStationHandler() throws CustomException;

	void saveFlightAttributes(OperativeFlight oprFltLeg) throws CustomException;

}