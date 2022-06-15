/**
 * 
 * OperativeFlightServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 1 August, 2017 NIIT -
 */
package com.ngen.cosys.flight.service;

import static com.ngen.cosys.flight.constant.Constants.FALSE;
import static com.ngen.cosys.flight.constant.Constants.OFF;
import static com.ngen.cosys.flight.constant.Constants.ON;
import static com.ngen.cosys.flight.constant.Constants.ONE;
import static com.ngen.cosys.flight.constant.Constants.TRUE;
import static com.ngen.cosys.flight.constant.Constants.ZERO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.ngen.cosys.flight.constant.AdHocFlightCode;
import com.ngen.cosys.flight.dao.FlightDAO;
import com.ngen.cosys.flight.model.CustomsFlight;
import com.ngen.cosys.flight.model.ExportFlightevents;
import com.ngen.cosys.flight.model.FlightEnroutement;
import com.ngen.cosys.flight.model.IcsFlightDetails;
import com.ngen.cosys.flight.model.ImportFlightevents;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.OperativeFlightExp;
import com.ngen.cosys.flight.model.OperativeFlightJoint;
import com.ngen.cosys.flight.model.OperativeFlightLeg;
import com.ngen.cosys.flight.model.OperativeFlightSegment;
import com.ngen.cosys.flight.model.OprFlightSegTempBO;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.DeleteIndicator;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.constant.InsertIndicator;
import com.ngen.cosys.framework.constant.SaveIndicator;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.service.FrameworkValidationService;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * This class takes care of the responsibilities related to the OperativeFlight
 * Flight service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class OperativeFlightServiceImpl implements OperativeFlightService {
   private static final String FORM_CTRL_CARRIER_CODE = "carrierCode";
   private static final Logger LOGGER = LoggerFactory.getLogger(OperativeFlightService.class);
   public static final String BY_PASS_ULD_TYPE = "ByPassULDType";
   public static final String BY_PASS_ULD_MAX_CHECK = "ByPassULDMaxCheck";
   public static final String CAR001 = "CAR001";
   public static final String OPRFLT_FLTDT_BEFORE = "OPRFLT_FLTDT_BEFORE";
   public static final String OPRFLT_DELETE_LEG1 = "OPRFLT_DELETE_LEG1";
   public static final int OPRFLT_JOINTFLT_MAX_COUNT = 3;
   public static final int OPRFLT_LEG1_ORDER = 1;
   public static final String OPRFLT_FLTDT_DEPT = "OPRFLT_FLTDT_DEPT";

   protected static final List<String> saveIndSuccess = Arrays
         .asList(new String[] { SaveIndicator.TRUE.toString(), SaveIndicator.YES.toString() });
   protected static final List<String> deleteIndSuccess = Arrays
         .asList(new String[] { DeleteIndicator.TRUE.toString(), DeleteIndicator.YES.toString() });

   @Autowired
   private FlightDAO flightDAO;

   @Autowired
   private FrameworkValidationService frameworkValidationService;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.flight.service.OperativeFlightService#maintain(com.ngen.cosys.
    * flight.model.OperativeFlight)
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
   public OperativeFlight maintain(OperativeFlight operatingFlight) throws CustomException {
	   
      if (null == operatingFlight || isFlightExists(operatingFlight)) {
         // Sets error message for "Flight Number"
    	  operatingFlight.addError("OPRFLT001", "flightNo", ErrorType.ERROR);
         //throw new CustomException("OPRFLT001", "flightNo", ErrorType.ERROR);
      }
      List<OperativeFlightLeg> legList = operatingFlight.getFlightLegs().stream()
            .filter(value -> !value.getFlagCRUD().equalsIgnoreCase(Action.DELETE.toString()))
            .collect(Collectors.toList());

      for (OperativeFlightLeg x : legList) {
         if (x.getBoardPointCode().equalsIgnoreCase(x.getOffPointCode())) {
            x.addError("batches.bpoint.opoint.not.same", "mntform", ErrorType.ERROR);
           // throw new CustomException("Board Point & Off Point Can not be same", "", ErrorType.ERROR);
         }
         x.setFlightKey(operatingFlight.getFlightKey());
         if (MultiTenantUtility.isTenantAirport(x.getOffPointCode())) {
            int count = flightDAO.checkflightExistancewithsta(x);
            int count1 = flightDAO.checkFlightExistanceWithStawithoutBoardPoint(x);
            if (count > 0) {
               x.addError(
                     "flight.duplicate.import.same.brd.point.same.sta",
                     "mntform", ErrorType.ERROR);
               //throw new CustomException(
                     //"Duplicate flight route. Import flight from the same Board Point with the same STA date is already saved.",
                     //"mntform", ErrorType.ERROR);
            }
            if (count1 > 0) {
				x.addError(
						"flight.duplicate.imp.same.off.point.same.sta",
						"mntform", ErrorType.ERROR);
				//throw new CustomException(
						//"Duplicate flight route. Import flight to the same Off Point with the same STA date is already saved.",
						//"mntform", ErrorType.ERROR);
			}
         } else if (MultiTenantUtility.isTenantAirport(x.getBoardPointCode())) {
            int countExport = flightDAO.checkflightExistancewithstd(x);
            int count1 = flightDAO.checkFlightExistanceWithStawithoutOffPoint(x);
            if (countExport > 0) {
               x.addError(
                     "flight.duplicate.exp.same.off.point.same.sta",
                     "mntform", ErrorType.ERROR);
              // throw new CustomException(
                     //"Duplicate flight route. Export flight to the same Off Point with the same STA date is already saved.",
                     //"mntform", ErrorType.ERROR);
            }
            if (count1 > 0) {
				x.addError(
						"flight.duplicate.exp.same.brd.point.same.sta",
						"mntform", ErrorType.ERROR);
				//throw new CustomException(
						//"Duplicate flight route. Export flight to the same Board Point with the same STD date is already saved.",
						//"mntform", ErrorType.ERROR);
			}
         }
         
       //For the fix of issue 13588
			if(!DeleteIndicator.YES.toString().equalsIgnoreCase(x.getFlagDelete()) && flightDAO.checkFlightExistanceWithStdorstawithsin(x)>0) {
			x.addError(
						"flight.same.flight.with.std.or.sta",
						"mntform", ErrorType.ERROR);
				//throw new CustomException("Same flight exists with same STD or STA",
						//"mntform", ErrorType.ERROR);
			}

      }
      List<OperativeFlightLeg> boardPointList = legList.stream().filter(leg -> MultiTenantUtility.isTenantAirport(leg.getBoardPointCode()))
				.collect(Collectors.toList());
		List<OperativeFlightLeg> offPointList = legList.stream().filter(leg -> MultiTenantUtility.isTenantAirport(leg.getOffPointCode()))
				.collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(boardPointList) && !CollectionUtils.isEmpty(offPointList)) {
			operatingFlight.setExportorImportFlight("BOTH");
			operatingFlight.setArrivalDate(offPointList.get(0).getArrivalDate().toLocalDate());
			operatingFlight.setDepartreDate(boardPointList.get(0).getDepartureDate().toLocalDate());
		} else if (!CollectionUtils.isEmpty(boardPointList)) {
			operatingFlight.setExportorImportFlight("EXPORT");
			operatingFlight.setDepartreDate(boardPointList.get(0).getDepartureDate().toLocalDate());
		} else if (!CollectionUtils.isEmpty(offPointList)) {
			operatingFlight.setExportorImportFlight("IMPORT");
			operatingFlight.setArrivalDate(offPointList.get(0).getArrivalDate().toLocalDate());
		}
      validateListValue(operatingFlight);
      validateJointFlight(operatingFlight);
      checkForAutoFlight(operatingFlight);
      validateUldTyp(operatingFlight);
      validateDates(operatingFlight);
      validateAllLovValue(operatingFlight);
      checkForDomesticFlight(operatingFlight);
      // Validate STA and STD
       validateStaAndStd(operatingFlight);

      // validate ATA DATE TIME
      // validateATADate(operatingFlight);
      if (operatingFlight != null) {
         if (!CollectionUtils.isEmpty(operatingFlight.getFlightLegs())) {
            operatingFlight.setFlightSegments(prepareSegments(operatingFlight.getFlightLegs()));
         }
      }
      setFlagAttribute(operatingFlight);
      if(CollectionUtils.isEmpty(operatingFlight.getMessageList())) {
    	  for(OperativeFlightLeg leg:operatingFlight.getFlightLegs()) {
    		  if(CollectionUtils.isEmpty(leg.getMessageList())) {
    			  OperativeFlight createdFlight = flightDAO.create(operatingFlight);
        	      List<FlightEnroutement> flightEnroutementList = prepareNormalEnroutements(operatingFlight);
        	      flightDAO.saveNormalEnroutement(flightEnroutementList);
        	      updateFlightEvent(createdFlight, operatingFlight.getTenantAirport());
        	      getFlagAttribute(createdFlight);
        	      LOGGER.debug(this.getClass().getName(), "maintain", Level.DEBUG, operatingFlight, createdFlight); 
        	      return createdFlight;
    		  }
    	  }
    	  
      }
	return operatingFlight;
   }

   private void validateStaAndStd(OperativeFlight operatingFlight) throws CustomException {
      for (OperativeFlightLeg legs : operatingFlight.getFlightLegs()) {
         if (MultiTenantUtility.isTenantAirport(legs.getBoardPointCode())) {
            legs.setFlightKey(operatingFlight.getFlightKey());
            if (flightDAO.checkExpFlightSTD(legs)) {
            	legs.addError("data.exp.flight.std",
                        "Flight Cannot be created, STD time already exists with another flight key", ErrorType.ERROR);
               //throw new CustomException("data.exp.flight.std",
                     //"Flight Cannot be created, STD time already exists with another flight key", ErrorType.ERROR);
            }
         } else if (MultiTenantUtility.isTenantAirport(legs.getOffPointCode())) {
            legs.setFlightKey(operatingFlight.getFlightKey());
            if (flightDAO.checkImpFlightSTA(legs)) {
            	legs.addError("data.imp.flight.sta",
                        "Flight Cannot be created, STA time already exists with another flight key", ErrorType.ERROR);
              // throw new CustomException("data.imp.flight.sta",
                     //"Flight Cannot be created, STA time already exists with another flight key", ErrorType.ERROR);
            }
         }
      }
   }

   public void updateFlightEvent(OperativeFlight flight, String tenantId) throws CustomException {
	   String handler = flightDAO.fetchStationHandler();
      if (!CollectionUtils.isEmpty(flight.getFlightLegs())) {
         for (OperativeFlightLeg legs : flight.getFlightLegs()) {
            // if (flight.getFlightLegs().stream().anyMatch(e ->
            // e.getBoardPointCode().equals("SIN"))) {
            // CHECKING Export flight
        	 
            if (MultiTenantUtility.isTenantAirport(legs.getBoardPointCode())) {
               ExportFlightevents exportFlight = new ExportFlightevents();
               exportFlight.setFlightId(flight.getFlightId());
               if (!flightDAO.checkExpFlightEvent(exportFlight)) {
                  flightDAO.updateEmportFlight(exportFlight);
                  if (handler.equalsIgnoreCase(flight.getGroundHandlerCode())) {
                  CustomsFlight customsFlight = new CustomsFlight();
                  customsFlight.setFlightBoardPoint(legs.getBoardPointCode());
                  customsFlight.setFlightOffPoint(legs.getOffPointCode());
                  customsFlight.setImportExportIndicator("E");
                  customsFlight.setFlightType(flight.getCaoPax());
                  customsFlight.setFlightKey(flight.getFlightKey());
                  customsFlight.setFlightDate(legs.getDepartureDate());
                  customsFlight.setOprFlightDate(flight.getFlightDate());
                  customsFlight.setCreatedBy(flight.getCreatedUserCode());
                  customsFlight.setDateorigin(flight.getFlightDate());
                  customsFlight.setFlighttime(legs.getDepartureDate());
                  customsFlight.setFlightCancelFlag(false);
                  if (!flightDAO.existCustoms(customsFlight)) {
                     flightDAO.updateExportCustomsFlight(customsFlight);
                  }
                  }
                  // ICS insert operation
                  IcsFlightDetails ics = new IcsFlightDetails();
                  ics.setConsumed(false);
                  ics.setFlightId(exportFlight.getFlightId());
                  ics.setOrigin(legs.getBoardPointCode());
                  ics.setDestination(legs.getOffPointCode());
                  ics.setFlightCarrier(flight.getCarrierCode());
                  ics.setFlightDate(flight.getFlightDate());
                  ics.setFlightNumber(flight.getFlightKey());
                  ics.setFlightType(flight.getCaoPax());
                  ics.setStaStd(flight.getDateSta());
                  // boolean count = flightDAO.countInterfaceICS(ics);
                  // if (count) {
                  // ics.setOperationalDirection("UPDATE");
                  // } else {
                  // ics.setOperationalDirection("INSERT");
                  // }
                  ics.setOperationalDirection("INSERT");
                  flightDAO.insertIcsInterfaceForImport(ics);
               }
            }
            // if (flight.getFlightLegs().stream().anyMatch(e ->
            // e.getOffPointCode().equals("SIN"))) {
            // CHECKING iMPORT FLIGHT
            if (MultiTenantUtility.isTenantAirport(legs.getOffPointCode())) {
               ImportFlightevents importFlight = new ImportFlightevents();
               importFlight.setFlightId(flight.getFlightId());
               if (!flightDAO.checkImpFlightEvent(importFlight)) {
                  flightDAO.updateImportFlight(importFlight);
                  
                  if (handler.equalsIgnoreCase(flight.getGroundHandlerCode())) {
                  CustomsFlight customsFlight = new CustomsFlight();
                  customsFlight.setFlightBoardPoint(legs.getBoardPointCode());
                  customsFlight.setFlightOffPoint(legs.getOffPointCode());
                  customsFlight.setImportExportIndicator("I");
                  customsFlight.setFlightType(flight.getCaoPax());
                  customsFlight.setFlightKey(flight.getFlightKey());
                  customsFlight.setFlightDate(legs.getArrivalDate());
                  customsFlight.setOprFlightDate(flight.getFlightDate());
                  customsFlight.setCreatedBy(flight.getCreatedUserCode());
                  customsFlight.setOprFlightDate(flight.getFlightDate());
                  customsFlight.setCreatedBy(flight.getCreatedUserCode());
                  customsFlight.setDateorigin(flight.getFlightDate());
                  customsFlight.setFlighttime(legs.getArrivalDate());
                  customsFlight.setFlightCancelFlag(false);
                  if (!flightDAO.existExportCustoms(customsFlight)) {
                     flightDAO.updateImportCustomsFlight(customsFlight);
                  }
                  
                  }
                  // ICS insert operation
                  IcsFlightDetails ics = new IcsFlightDetails();
                  ics.setConsumed(false);
                  ics.setFlightId(importFlight.getFlightId());
                  ics.setOrigin(legs.getBoardPointCode());
                  ics.setDestination(legs.getOffPointCode());
                  ics.setFlightCarrier(flight.getCarrierCode());
                  ics.setFlightDate(flight.getFlightDate());
                  ics.setFlightNumber(flight.getFlightKey());
                  ics.setFlightType(flight.getCaoPax());
                  ics.setStaStd(flight.getDateSta());
                  // boolean count = flightDAO.countInterfaceICS(ics);
                  // if (count) {
                  // ics.setOperationalDirection("UPDATE");
                  // } else {
                  // ics.setOperationalDirection("INSERT");
                  // }
                  ics.setOperationalDirection("INSERT");
                  flightDAO.insertIcsInterfaceForImport(ics);

               }
            }
         }
      }
   }

   /**
    * This method verifies if Flight Leg is eligible to get deleted. The First Leg
    * cannot be deleted.
    * 
    * @param operativeFlight
    * @throws CustomException
    */
   public void validateLegs(OperativeFlight operativeFlight) throws CustomException {
      if (CollectionUtils.isEmpty(operativeFlight.getFlightLegs())) {
         // Sets error message for "Delete First Flight Leg"
    	  operativeFlight.addError(OPRFLT_DELETE_LEG1, "flightLegs", ErrorType.ERROR);
         //throw new CustomException(OPRFLT_DELETE_LEG1, "flightLegs", ErrorType.ERROR);
      } else {
         for (OperativeFlightLeg leg : operativeFlight.getFlightLegs()) {
            if (OPRFLT_LEG1_ORDER == leg.getLegOrderCode() && SaveIndicator.YES.toString().equals(leg.getFlagSaved())
                  && DeleteIndicator.YES.toString().equals(leg.getFlagDelete())) {
               // Sets error message for "Delete First Flight Leg"
            	leg.addError(OPRFLT_DELETE_LEG1, "flightLegs", ErrorType.ERROR);
               //throw new CustomException(OPRFLT_DELETE_LEG1, "flightLegs", ErrorType.ERROR);
            }
         }
      }
   }

   /**
    * This method takes care of validating all the dates.
    * 
    * @param operativeFlight
    * @throws CustomException
    */
   @SuppressWarnings("unused")
   private void validateDates(OperativeFlight operativeFlight) throws CustomException {
      if (!CollectionUtils.isEmpty(operativeFlight.getFlightLegs())) {
         for (OperativeFlightLeg leg : operativeFlight.getFlightLegs()) {
            validateStandardDatesForFltDt(operativeFlight.getFlightDate(), leg);
         }
      }
   }

   /**
    * This method performs the Standard Dates validation against the Flight Date.
    * 
    * @param flightDate
    * @param leg
    * @throws CustomException
    */
   private void validateStandardDatesForFltDt(LocalDateTime flightDate, OperativeFlightLeg leg) throws CustomException {
      // 5) The Date is flight origin date means STD of flight first leg.
      if (OPRFLT_LEG1_ORDER == leg.getLegOrderCode()
            && !leg.getDepartureDate().toLocalDate().equals(flightDate.toLocalDate())) {
         // Sets error message for "Flight Origin Date not matching the STD of First Leg"
    	  leg.addError("OPRFLT_FLTDT_NOTEQ_STD_LEG1", "flightDate", ErrorType.ERROR);
         //throw new CustomException("OPRFLT_FLTDT_NOTEQ_STD_LEG1", "flightDate", ErrorType.ERROR);
      }
   }

   /**
    * This method performs the Expected Dates validation.
    * 
    * @param leg
    * @throws CustomException
    */
   public void validateExpectedDates(OperativeFlightLeg leg) throws CustomException {
      if (leg.getDatEta() != null && DeleteIndicator.NO.toString().equalsIgnoreCase(leg.getFlagDelete())) {
         if (leg.getDatEtd() == null) {
            // Sets error message for "ETD Blank"
        	 leg.addError("OPRFLT_ETD_BLANK_ETA_GIVEN", "flightLegs.datEtd", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ETD_BLANK_ETA_GIVEN", "flightLegs.datEtd", ErrorType.ERROR);
         }
         if (leg.getDatEta().isBefore(LocalDateTime.now())) {
            // Sets error message for "ETA Past Date"
        	 leg.addError("OPRFLT_ETA_BEFORE", "flightLegs.datEta", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ETA_BEFORE", "flightLegs.datEta", ErrorType.ERROR);
         }
      }
      if (leg.getDatEtd() != null && DeleteIndicator.NO.toString().equalsIgnoreCase(leg.getFlagDelete())) {
         if (leg.getDatEta() == null) {
            // Sets error message for "ETA Blank"
        	 leg.addError("OPRFLT_ETA_BLANK_ETD_GIVEN", "flightLegs.datEta", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ETA_BLANK_ETD_GIVEN", "flightLegs.datEta", ErrorType.ERROR);
         }
         if (leg.getDatEtd().isBefore(LocalDateTime.now())) {
            // Sets error message for "ETD Past Date"
        	 leg.addError("OPRFLT_ETD_BEFORE", "flightLegs.datEtd", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ETD_BEFORE", "flightLegs.datEtd", ErrorType.ERROR);
         }
      }
      if (leg.getDatEtd() != null && leg.getDatEta() != null && leg.getDatEta().isBefore(leg.getDatEtd())
            && DeleteIndicator.NO.toString().equalsIgnoreCase(leg.getFlagDelete())) {
         // Sets error message for "ETA before ETD"
    	  leg.addError("OPRFLT_ETA_BEFORE_ETD", "flightLegs.datEta", ErrorType.ERROR);
         //throw new CustomException("OPRFLT_ETA_BEFORE_ETD", "flightLegs.datEta", ErrorType.ERROR);
      }
   }

   /**
    * This method performs the Actual Dates validation.
    * 
    * @param leg
    * @throws CustomException
    */
   public void validateActualDates(OperativeFlightLeg leg) throws CustomException {
      if (leg.getDatAta() != null && DeleteIndicator.NO.toString().equalsIgnoreCase(leg.getFlagDelete())) {
         if (leg.getDatAtd() == null) {
            // Sets error message for "ATD Blank"
        	 leg.addError("OPRFLT_ATD_BLANK_ATA_GIVEN", "flightLegs.datAtd", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ATD_BLANK_ATA_GIVEN", "flightLegs.datAtd", ErrorType.ERROR);
         }
         if (leg.getDatAta().isBefore(LocalDateTime.now())) {
            // Sets error message for "ATA Past Date"
        	 leg.addError("OPRFLT_ATA_BEFORE", "flightLegs.datAta", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ATA_BEFORE", "flightLegs.datAta", ErrorType.ERROR);
         }
      }
      if (leg.getDatAtd() != null && DeleteIndicator.NO.toString().equalsIgnoreCase(leg.getFlagDelete())) {
         if (leg.getDatAta() == null) {
            // Sets error message for "ATA Blank"
        	 leg.addError("OPRFLT_ATA_BLANK_ATD_GIVEN", "flightLegs.datAta", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ATA_BLANK_ATD_GIVEN", "flightLegs.datAta", ErrorType.ERROR);
         }
         if (leg.getDatAtd().isBefore(LocalDateTime.now())) {
            // Sets error message for "ATD Past Date"
        	 leg.addError("OPRFLT_ATD_BEFORE", "flightLegs.datAtd", ErrorType.ERROR);
            //throw new CustomException("OPRFLT_ATD_BEFORE", "flightLegs.datAtd", ErrorType.ERROR);
         }
      }
      if (leg.getDatAtd() != null && leg.getDatAta() != null && leg.getDatAta().isBefore(leg.getDatAtd())
            && DeleteIndicator.NO.toString().equalsIgnoreCase(leg.getFlagDelete())) {
         // Sets error message for "ATA before ATD"
    	  leg.addError("OPRFLT_ATA_BEFORE_ATD", "flightLegs.datAta", ErrorType.ERROR);
         //throw new CustomException("OPRFLT_ATA_BEFORE_ATD", "flightLegs.datAta", ErrorType.ERROR);
      }
   }

   /**
    * It is responsible to prepare normal Enroutements based on Flight Segments.
    * 
    * @param operativeFlight
    * @return
    * @throws CustomException
    */
   protected List<FlightEnroutement> prepareNormalEnroutements(OperativeFlight operativeFlight) throws CustomException {
      LOGGER.debug(this.getClass().getName(), "prepareNormalEnroutements", Level.DEBUG, operativeFlight, null);
      List<FlightEnroutement> flightEnroutements = new ArrayList<>();
      if (!CollectionUtils.isEmpty(operativeFlight.getFlightSegments())) {
         String startingpoint = operativeFlight.getTenantAirport();
         if (!StringUtils.isEmpty(startingpoint)) {
            for (OperativeFlightSegment operativeFlightSegment : operativeFlight.getFlightSegments()) {
               if (startingpoint.equals(operativeFlightSegment.getCodAptBrd())) {
                  FlightEnroutement flightEnroutement = new FlightEnroutement();
                  flightEnroutement.setBoardPointCode(operativeFlightSegment.getCodAptBrd());
                  flightEnroutement.setFinalDestination(operativeFlightSegment.getCodAptOff());
                  flightEnroutement.setCarrierCode(operativeFlight.getCarrierCode());
                  flightEnroutement.setServiceType(operativeFlight.getCaoPax());
                  flightEnroutement.setPeriodTo(operativeFlight.getFlightDate().toLocalDate());
                  flightEnroutement.setPeriodFrom(operativeFlightSegment.getDatStd().toLocalDate());
                  flightEnroutements.add(flightEnroutement);
               }
            }
         }
      }
      return flightEnroutements;
   }

   /**
    * Re-generating Segment based on new legs information. Excepting if Delete Flag
    * is coming as Y.
    * 
    * @param flightLegs
    * @return
    */
   private CopyOnWriteArrayList<OperativeFlightSegment> prepareUpdatedSegment(List<OperativeFlightLeg> flightLegs) {
      List<OperativeFlightLeg> updatedLegs = flightLegs.stream()
            .filter(e -> !(DeleteIndicator.YES.toString().equalsIgnoreCase(e.getFlagDelete())))
            .collect(Collectors.toList());
      return createOperatingSegments(updatedLegs);
   }

   /**
    * This method is used for all kind of check to overcome the problem of
    * concurrency.
    * 
    * @param oldList
    * @param newList
    * @return list - List<OperativeFlightSegment>
    * @throws CustomException
    */
   private List<OperativeFlightSegment> checkFlightSegForModification(List<OperativeFlightSegment> oldList,
         List<OperativeFlightSegment> newList) throws CustomException {
      List<OperativeFlightSegment> deleteOldReacord = new ArrayList<>();
      List<OperativeFlightSegment> deleteSegs = oldList.stream()
            .filter(e1 -> !newList.stream().anyMatch(
                  e2 -> e1.getCodAptBrd().equals(e2.getCodAptBrd()) && e1.getCodAptOff().equals(e2.getCodAptOff())))
            .collect(Collectors.toList());
      List<OperativeFlightSegment> sameSegs = oldList.stream()
            .filter(e1 -> newList.stream().anyMatch(
                  e2 -> e1.getCodAptBrd().equals(e2.getCodAptBrd()) && e1.getCodAptOff().equals(e2.getCodAptOff())))
            .collect(Collectors.toList());

      List<OperativeFlightSegment> insertSegment = newList.stream()
            .filter(e1 -> !oldList.stream().anyMatch(
                  e2 -> e1.getCodAptBrd().equals(e2.getCodAptBrd()) && e1.getCodAptOff().equals(e2.getCodAptOff())))
            .collect(Collectors.toList());
      sameSegs.forEach(e -> e.setFlagUpdate("Y"));
      deleteSegs.forEach(e -> e.setFlagDelete("Y"));
      insertSegment.forEach(e -> e.setFlagInsert("Y"));
      deleteOldReacord.addAll(sameSegs);
      deleteOldReacord.addAll(deleteSegs);
      deleteOldReacord.addAll(insertSegment);
      return deleteOldReacord;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.flight.service.OperativeFlightService#isFlightExists(com.ngen.
    * cosys.flight.model.OperativeFlight)
    */
   @Override
   public boolean isFlightExists(OperativeFlight operatingFlight) throws CustomException {
      OperativeFlight resFlight = flightDAO.find(operatingFlight);
      LOGGER.debug(this.getClass().getName(), "isFlightExists", Level.DEBUG, operatingFlight, resFlight);
      return resFlight != null;
   }

   /**
    * Prepares Segments list.
    * 
    * @param flightLegs
    * @return
    */
   @Override()
   public List<OperativeFlightSegment> prepareSegments(List<OperativeFlightLeg> flightLegs) {
      List<OperativeFlightSegment> flightSegments = new ArrayList<OperativeFlightSegment>();
      if (flightLegs != null) {
         flightSegments = createOperatingSegments(flightLegs);
      }
      LOGGER.debug(this.getClass().getName(), "prepareSegments", Level.DEBUG, flightLegs, flightSegments);
      return flightSegments;
   }

   /**
    * This method populates the list of OprFlightSegTempBO for temporary
    * determination logic.
    * 
    * @param flightLegs,
    *           not null
    * @param legsClone,
    *           not null
    */
   private static List<OprFlightSegTempBO> populateSegmentCursor(List<OperativeFlightLeg> flightLegs,
         ArrayList<OperativeFlightLeg> legsClone) {
      List<OprFlightSegTempBO> segmentCursor = new ArrayList<>();
      for (OperativeFlightLeg legOuter : flightLegs) {
         for (OperativeFlightLeg legInner : legsClone) {

            if (legOuter.getLegOrderCode() <= legInner.getLegOrderCode()
                  && !legOuter.getBoardPointCode().equals(legInner.getOffPointCode())) {
               OprFlightSegTempBO flightCursor = new OprFlightSegTempBO();
               flightCursor.setFlightId(legOuter.getFlightId());
               flightCursor.setDatStd(legOuter.getDepartureDate());
               // flightCursor.setDatSta(legOuter.getArrivalDate()); // Ashutosh commented
               flightCursor.setAptBrd(legOuter.getBoardPointCode());
               flightCursor.setBrdLeg(legOuter.getLegOrderCode());

               flightCursor.setAptOff(legInner.getOffPointCode());
               flightCursor.setDatSta(legInner.getArrivalDate()); // Ashutosh
               flightCursor.setServiceType(legInner.getAircraftType());
               flightCursor.setOffLeg(legInner.getLegOrderCode());
               flightCursor.setCreatedDateTime(legInner.getCreatedDateTime());
               flightCursor.setUserCode(legInner.getCreatedUserCode());
               segmentCursor.add(flightCursor);
            }
         }
      }
      return segmentCursor;
   }

   /**
    * This method creates the list of Segments.
    * 
    * @param segments,
    *           not null
    * @param segmentCursor,
    *           not null
    */
   private static CopyOnWriteArrayList<OperativeFlightSegment> performBusinessLogic(
         CopyOnWriteArrayList<OperativeFlightSegment> segments, List<OprFlightSegTempBO> segmentTempBOs) {
      int segmentOrder = 1;
      for (OprFlightSegTempBO seg : segmentTempBOs) {
         OperativeFlightSegment segment = new OperativeFlightSegment();
         segment.setFlightId(seg.getFlightId());
         segment.setCodAptBrd(seg.getAptBrd());
         segment.setCodAptOff(seg.getAptOff());
         segment.setCodAptOff(seg.getAptOff());
         segment.setCodSegOdr(String.valueOf(segmentOrder));
         segment.setDatStd(seg.getDatStd());
         segment.setDatSta(seg.getDatSta());

         segment.setFlgLeg(seg.getBrdLeg().equals(seg.getOffLeg()) ? ONE : ZERO);

         segment.setServiceType(seg.getServiceType());
         segment.setCreatedDateTime(seg.getCreatedDateTime());
         segment.setCreatedUserCode(seg.getUserCode());
         segments.add(segment);
         segmentOrder++;
      }
      return segments;
   }

   /**
    * This method creates the list of Flight Segments for a list of Flight Legs.
    * Segments creation logic created from the Stored Procedure SP_OPR_FLT_SEG_CRE
    * 
    * @param flightLegs,
    *           not null
    * @return list of FlightSegment
    */
   @SuppressWarnings("unchecked")
   private static CopyOnWriteArrayList<OperativeFlightSegment> createOperatingSegments(
         List<OperativeFlightLeg> flightLegs) {
      CopyOnWriteArrayList<OperativeFlightSegment> segments = new CopyOnWriteArrayList<>();
      if (!CollectionUtils.isEmpty(flightLegs)) {
         List<OprFlightSegTempBO> segmentTempBOs;
         ArrayList<OperativeFlightLeg> legsClone = new ArrayList<>(flightLegs);
         legsClone = (ArrayList<OperativeFlightLeg>) legsClone.clone();

         segmentTempBOs = populateSegmentCursor(flightLegs, legsClone);
         segments = performBusinessLogic(segments, segmentTempBOs);
         
         
         // for figure Eight Flight
         List<OperativeFlightSegment> copyOfSegments = segments;
         for (int i = 0; i < copyOfSegments.size(); i++) {
             for (int j = i; j < copyOfSegments.size(); j++) {
                 if (i != j
                         && copyOfSegments.get(i).getCodAptOff()
                                 .equalsIgnoreCase(copyOfSegments.get(j).getCodAptOff())
                         && copyOfSegments.get(i).getCodAptBrd()
                                 .equalsIgnoreCase(copyOfSegments.get(j).getCodAptBrd())) {
                     segments.remove(i);
                 }
             }
         }
         //for  figure nine Flight
         for (int i = 0; i < copyOfSegments.size(); i++) {
             for (int j = i; j < copyOfSegments.size(); j++) {
                 if (i != j
                         && copyOfSegments.get(i).getCodAptOff()
                                 .equalsIgnoreCase(copyOfSegments.get(j).getCodAptOff())
                         && copyOfSegments.get(i).getCodAptBrd()
                                 .equalsIgnoreCase(copyOfSegments.get(j).getCodAptBrd())) {
                     segments.remove(i);
                 }
             }
         }
         
//         for(int i = 0; i < segments.size() ; i++) {
//            for(int j = 1; j < segments.size() ; j++) {
//               if((segments.get(i).getCodAptBrd().equalsIgnoreCase(segments.get(j).getCodAptBrd())) && (segments.get(i).getCodAptOff().equalsIgnoreCase(segments.get(j).getCodAptOff())) && i != j) {
//                  if(Integer.parseInt(segments.get(i).getCodSegOdr())<Integer.parseInt(segments.get(j).getCodSegOdr())){
//                     segments.remove(segments.get(i));
//                     break;
//                  }
//               }
//            }
//         }
//
//         for(int i = 0; i < segments.size() ; i++) {
//            for(int j = 1; j < segments.size() ; j++) {
//               if((segments.get(i).getCodAptBrd().equalsIgnoreCase(segments.get(j).getCodAptBrd())) && (segments.get(i).getCodAptOff().equalsIgnoreCase(segments.get(j).getCodAptOff())) && i != j) {
//                  if(Integer.parseInt(segments.get(i).getCodSegOdr())<Integer.parseInt(segments.get(j).getCodSegOdr())){
//                     segments.remove(segments.get(i));
//                     break;
//                  }
//               }
//            }
//         }
      }
      return segments;
   }

   /**
    * This method handles all business logic enter between db and Ui, its setting
    * either 0 or 1 in case of bit datatype in DB.
    * 
    * @param operatingFlight
    */
   private static void setFlagAttribute(OperativeFlight operatingFlight) {
      if (operatingFlight != null) {
         setFlagAttributeForOFL(operatingFlight);
         setFlagAttributForAutoComplete(operatingFlight);
         setFlagAttributeForSeg(operatingFlight);
         setFlagAttributeForLeg(operatingFlight);
      }
   }

   /**
    * 
    * @param operatingFlight
    */
   private static void setFlagAttributForAutoComplete(OperativeFlight operatingFlight) {
      if (!StringUtils.isEmpty(operatingFlight.getFlightAutoCompleteFlag())) {
         if (OFF.equalsIgnoreCase(operatingFlight.getFlightAutoCompleteFlag())) {
            operatingFlight.setFlightAutoCompleteFlag(ZERO);
         } else if (ON.equalsIgnoreCase(operatingFlight.getFlightAutoCompleteFlag())) {
            operatingFlight.setFlightAutoCompleteFlag(ONE);
         }
      }
   }

   /**
    * This method handles business communication for Flight Legs.
    * 
    * @param operatingFlight
    */
   private static void setFlagAttributeForLeg(OperativeFlight operatingFlight) {
      if (!CollectionUtils.isEmpty(operatingFlight.getFlightLegs())) {
         for (OperativeFlightLeg oprSeg : operatingFlight.getFlightLegs()) {
            if (TRUE.equalsIgnoreCase(oprSeg.getDomesticStatus())) {
               oprSeg.setDomesticStatus(ONE);
            } else if (FALSE.equalsIgnoreCase(oprSeg.getDomesticStatus())) {
               oprSeg.setDomesticStatus(ZERO);
            }
         }
      }
   }

   /**
    * This method handles business communication for Flight Segment.
    * 
    * @param operatingFlight
    */
   private static void setFlagAttributeForSeg(OperativeFlight operatingFlight) {
      if (!CollectionUtils.isEmpty(operatingFlight.getFlightSegments())) {
         for (OperativeFlightSegment oprSeg : operatingFlight.getFlightSegments()) {
            if (TRUE.equalsIgnoreCase(oprSeg.getFlgNfl())) {
               oprSeg.setFlgNfl(ONE);
            }
            // else if (FALSE.equalsIgnoreCase(oprSeg.getFlgNfl()))
            else {
               oprSeg.setFlgNfl(ZERO);
            }
            if (TRUE.equalsIgnoreCase(oprSeg.getFlgTecStp())) {
               oprSeg.setFlgTecStp(ONE);
            }
            // else if (FALSE.equalsIgnoreCase(oprSeg.getFlgTecStp()))
            else {
               oprSeg.setFlgTecStp(ZERO);
            }
            flagAttributeForCargoAndNoMail(oprSeg);
         }
      }
   }

   /**
    * This method handles business communication for Flight Segment(Cargo and
    * noMail).
    * 
    * @param oprSeg
    */
   private static void flagAttributeForCargoAndNoMail(OperativeFlightSegment oprSeg) {
      if (TRUE.equalsIgnoreCase(oprSeg.getFlgCargo())) {
         oprSeg.setFlgCargo(ONE);
      }
      // else if (FALSE.equalsIgnoreCase(oprSeg.getFlgCargo()))
      else {
         oprSeg.setFlgCargo(ZERO);
      }
      if (TRUE.equalsIgnoreCase(oprSeg.getNoMail())) {
         oprSeg.setNoMail(ONE);
      }
      // else if (FALSE.equalsIgnoreCase(oprSeg.getNoMail()))
      else {
         oprSeg.setNoMail(ZERO);
      }
   }

   /**
    * This method handles business communication for Flight.
    * 
    * @param operatingFlight
    */
   private static void setFlagAttributeForOFL(OperativeFlight operatingFlight) {
      setAttributesForException(operatingFlight);
      if (TRUE.equalsIgnoreCase(operatingFlight.getJointFlight())) {
         operatingFlight.setJointFlight(ONE);
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getJointFlight())) {
         operatingFlight.setJointFlight(ZERO);
         operatingFlight.setFlightJoints(null);
      }
      if (TRUE.equalsIgnoreCase(operatingFlight.getFlgApn())) {
         operatingFlight.setFlgApn(ONE);
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getFlgApn())) {
         operatingFlight.setFlgApn(ZERO);
      }
      if (AdHocFlightCode.TRUE.toString().equalsIgnoreCase(operatingFlight.getAdHocFlightCode())) {
         operatingFlight.setAdHocFlightCode(AdHocFlightCode.ADHOC.toString());
      } else if (AdHocFlightCode.FALSE.toString().equalsIgnoreCase(operatingFlight.getAdHocFlightCode())) {
         operatingFlight.setAdHocFlightCode(AdHocFlightCode.MANUAL.toString());
      }
      setAttributeForDelay(operatingFlight);
      if (TRUE.equalsIgnoreCase(operatingFlight.getFlgSsmAsm())) {
         operatingFlight.setFlgSsmAsm(ONE);
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getFlgSsmAsm())) {
         operatingFlight.setFlgSsmAsm(ZERO);
      }
   }

   /**
    * This method handles business communication for Exception.
    * 
    * @param operatingFlight
    */
   private static void setAttributesForException(OperativeFlight operatingFlight) {
      if (TRUE.equalsIgnoreCase(operatingFlight.getFlgExpUld())) {
         if (!CollectionUtils.isEmpty(operatingFlight.getFlightExpULDTyps())) {
            operatingFlight.getFlightExpULDTyps().forEach(e -> e.setExceptionType(BY_PASS_ULD_TYPE));
         }
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getFlgExpUld())) {
         operatingFlight.setFlightExpULDTyps(null);
      }
      if (TRUE.equalsIgnoreCase(operatingFlight.getFlgExpWt())) {
         if (!CollectionUtils.isEmpty(operatingFlight.getFlightExps())) {
            operatingFlight.getFlightExps().forEach(e -> e.setExceptionType(BY_PASS_ULD_MAX_CHECK));
         }
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getFlgExpWt())) {
         operatingFlight.setFlightExps(null);
      }
   }

   /**
    * This method handles business communication for Delay
    * 
    * @param operatingFlight
    */
   private static void setAttributeForDelay(OperativeFlight operatingFlight) {
      if (TRUE.equalsIgnoreCase(operatingFlight.getFlgDlyIn())) {
         operatingFlight.setFlgDlyIn(ONE);
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getFlgDlyIn())) {
         operatingFlight.setFlgDlyIn(ZERO);
      }
      if (TRUE.equalsIgnoreCase(operatingFlight.getFlgDlyOut())) {
         operatingFlight.setFlgDlyOut(ONE);
      } else if (FALSE.equalsIgnoreCase(operatingFlight.getFlgDlyOut())) {
         operatingFlight.setFlgDlyOut(ZERO);
      }
   }

   /**
    * This method reverse of above method it checks whether 0 or 1 coming from DB
    * and handles NPE also.
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttribute(OperativeFlight resOperatingFlight) {
      if (resOperatingFlight != null) {
         getFlagAttributeForException(resOperatingFlight);
         getFlagAttributeForOFL(resOperatingFlight);
         getFlagAttributForAutoComplete(resOperatingFlight);
         getFlagAttributeForSegments(resOperatingFlight);
         getFlagAttributeForLegs(resOperatingFlight);
      }
   }

   /**
    * This method handles business communication for OFL .
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttributeForOFL(OperativeFlight resOperatingFlight) {
      if (ONE.equalsIgnoreCase(resOperatingFlight.getJointFlight())) {
         resOperatingFlight.setJointFlight(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getJointFlight())) {
         resOperatingFlight.setJointFlight(FALSE);
      } else if (StringUtils.isEmpty(resOperatingFlight.getJointFlight())) {
         resOperatingFlight.setJointFlight(FALSE);
      }
      if (ONE.equalsIgnoreCase(resOperatingFlight.getFlgApn())) {
         resOperatingFlight.setFlgApn(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getFlgApn())) {
         resOperatingFlight.setFlgApn(FALSE);
      } else if (StringUtils.isEmpty(resOperatingFlight.getFlgApn())) {
         resOperatingFlight.setJointFlight(FALSE);
      }
      getAttibutesForAdhoc(resOperatingFlight);
      getFlagAttributeForDelay(resOperatingFlight);
      getFlagAttributesForExp(resOperatingFlight);
      if (ONE.equalsIgnoreCase(resOperatingFlight.getFlgSsmAsm())) {
         resOperatingFlight.setFlgSsmAsm(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getFlgSsmAsm())) {
         resOperatingFlight.setFlgSsmAsm(FALSE);
      } else if (StringUtils.isEmpty(resOperatingFlight.getFlgSsmAsm())) {
         resOperatingFlight.setFlgSsmAsm(FALSE);
      }
      resOperatingFlight.setFlightKey(resOperatingFlight.getCarrierCode() + resOperatingFlight.getFlightNo());
   }

   /**
    * 
    * @param operatingFlight
    */
   private static void getFlagAttributForAutoComplete(OperativeFlight operatingFlight) {
      if (!StringUtils.isEmpty(operatingFlight.getFlightAutoCompleteFlag())) {
         if (ZERO.equalsIgnoreCase(operatingFlight.getFlightAutoCompleteFlag()))
            operatingFlight.setFlightAutoCompleteFlag(OFF);
         else if (ONE.equalsIgnoreCase(operatingFlight.getFlightAutoCompleteFlag()))
            operatingFlight.setFlightAutoCompleteFlag(ON);
         else if (StringUtils.isEmpty(operatingFlight.getFlightAutoCompleteFlag())) {
            operatingFlight.setFlightAutoCompleteFlag(OFF);
         }
      }
   }

   /**
    * Get Attributes for Adhoc.
    * 
    * @param resOperatingFlight
    */
   private static void getAttibutesForAdhoc(OperativeFlight resOperatingFlight) {
      if (AdHocFlightCode.ADHOC.toString().equalsIgnoreCase(resOperatingFlight.getAdHocFlightCode())) {
         resOperatingFlight.setAdHocFlightCode(AdHocFlightCode.TRUE.toString());
      } else if (AdHocFlightCode.MANUAL.toString().equalsIgnoreCase(resOperatingFlight.getAdHocFlightCode())) {
         resOperatingFlight.setAdHocFlightCode(AdHocFlightCode.FALSE.toString());
      }
   }

   /**
    * This method handles business communication for Exception .
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttributesForExp(OperativeFlight resOperatingFlight) {
      if (ONE.equalsIgnoreCase(resOperatingFlight.getFlgExpUld())) {
         resOperatingFlight.setFlgExpUld(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getFlgExpUld())) {
         resOperatingFlight.setFlgExpUld(FALSE);
      }
      if (ONE.equalsIgnoreCase(resOperatingFlight.getFlgExpWt())) {
         resOperatingFlight.setFlgExpWt(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getFlgExpWt())) {
         resOperatingFlight.setFlgExpWt(FALSE);
      }
   }

   /**
    * This method handles business communication for Delay .
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttributeForDelay(OperativeFlight resOperatingFlight) {
      if (ONE.equalsIgnoreCase(resOperatingFlight.getFlgDlyIn())) {
         resOperatingFlight.setFlgDlyIn(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getFlgDlyIn())) {
         resOperatingFlight.setFlgDlyIn(FALSE);
      } else if (StringUtils.isEmpty(resOperatingFlight.getFlgDlyIn())) {
         resOperatingFlight.setFlgDlyIn(FALSE);
      }
      if (ONE.equalsIgnoreCase(resOperatingFlight.getFlgDlyOut())) {
         resOperatingFlight.setFlgDlyOut(TRUE);
      } else if (ZERO.equalsIgnoreCase(resOperatingFlight.getFlgDlyOut())) {
         resOperatingFlight.setFlgDlyOut(FALSE);
      } else if (StringUtils.isEmpty(resOperatingFlight.getFlgDlyOut())) {
         resOperatingFlight.setFlgDlyOut(FALSE);
      }
   }

   /**
    * This method handles business communication for Flight Legs .
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttributeForLegs(OperativeFlight resOperatingFlight) {
      if (!CollectionUtils.isEmpty(resOperatingFlight.getFlightLegs())) {
         for (OperativeFlightLeg oprLeg : resOperatingFlight.getFlightLegs()) {
            if (ONE.equalsIgnoreCase(oprLeg.getDomesticStatus())) {
               oprLeg.setDomesticStatus(TRUE);
            } else if (ZERO.equalsIgnoreCase(oprLeg.getDomesticStatus())) {
               oprLeg.setDomesticStatus(FALSE);
            }
         }
      }
   }

   /**
    * This method handles business communication for Flight Segments.
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttributeForSegments(OperativeFlight resOperatingFlight) {
      if (!CollectionUtils.isEmpty(resOperatingFlight.getFlightSegments())) {
         for (OperativeFlightSegment oprSeg : resOperatingFlight.getFlightSegments()) {
            if (ONE.equalsIgnoreCase(oprSeg.getFlgNfl())) {
               oprSeg.setFlgNfl(TRUE);
            } else if (ZERO.equalsIgnoreCase(oprSeg.getFlgNfl())) {
               oprSeg.setFlgNfl(FALSE);
            } else if (StringUtils.isEmpty(oprSeg.getFlgNfl())) {
               oprSeg.setFlgNfl(FALSE);
            }
            if (ONE.equalsIgnoreCase(oprSeg.getFlgTecStp())) {
               oprSeg.setFlgTecStp(TRUE);
            } else if (ZERO.equalsIgnoreCase(oprSeg.getFlgTecStp())) {
               oprSeg.setFlgTecStp(FALSE);
            } else if (StringUtils.isEmpty(oprSeg.getFlgTecStp())) {
               oprSeg.setFlgTecStp(FALSE);
            }
            getFlagAttributeForSegFlag(oprSeg);
         }
      }
   }

   /**
    * This method handles business communication for Flight Segments flag value.
    * 
    * @param oprSeg
    */
   private static void getFlagAttributeForSegFlag(OperativeFlightSegment oprSeg) {
      if (ONE.equalsIgnoreCase(oprSeg.getFlgCargo())) {
         oprSeg.setFlgCargo(TRUE);
      } else if (ZERO.equalsIgnoreCase(oprSeg.getFlgCargo())) {
         oprSeg.setFlgCargo(FALSE);
      } else if (StringUtils.isEmpty(oprSeg.getFlgCargo())) {
         oprSeg.setFlgCargo(FALSE);
      }
      if (ONE.equalsIgnoreCase(oprSeg.getNoMail())) {
         oprSeg.setNoMail(TRUE);
      } else if (ZERO.equalsIgnoreCase(oprSeg.getNoMail())) {
         oprSeg.setNoMail(FALSE);
      } else if (StringUtils.isEmpty(oprSeg.getNoMail())) {
         oprSeg.setNoMail(FALSE);
      }
   }

   /**
    * This method handles business communication for Flight Exception.
    * 
    * @param resOperatingFlight
    */
   private static void getFlagAttributeForException(OperativeFlight resOperatingFlight) {
      if (!CollectionUtils.isEmpty(resOperatingFlight.getFlightExpULDTyps())) {
         resOperatingFlight.getFlightExpULDTyps().forEach(e -> {
            if (BY_PASS_ULD_TYPE.equalsIgnoreCase(e.getExceptionType())) {
               resOperatingFlight.setFlgExpUld(TRUE);
            } else {
               resOperatingFlight.setFlgExpUld(FALSE);
            }
         });
      }
      if (!CollectionUtils.isEmpty(resOperatingFlight.getFlightExps())) {
         resOperatingFlight.getFlightExps().forEach(e -> {
            if (BY_PASS_ULD_MAX_CHECK.equalsIgnoreCase(e.getExceptionType())) {
               resOperatingFlight.setFlgExpWt(TRUE);
            } else {
               resOperatingFlight.setFlgExpWt(FALSE);
            }
         });
      }
   }

   /**
    * To Validate all UI LOV Value.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private OperativeFlight validateAllLovValue(OperativeFlight oprFlight) throws CustomException {
      if (oprFlight != null) {
         if (!StringUtils.isEmpty(oprFlight.getCarrierCode())
               && !frameworkValidationService.isValidCarrier(oprFlight.getCarrierCode())) {
            // Sets error message for "Carrier"
        	 oprFlight.addError(CAR001, FORM_CTRL_CARRIER_CODE, ErrorType.ERROR);
           // throw new CustomException(CAR001, FORM_CTRL_CARRIER_CODE, ErrorType.ERROR);
         }
         if (StringUtils.isEmpty(oprFlight.getServiceType())) {
            // Sets empty error message for "Service Type"
            throw new CustomException("OPRFLTSERVICETYPEEMPTY", "serviceType", ErrorType.ERROR);
         } else if (!StringUtils.isEmpty(oprFlight.getServiceType())
               && !flightDAO.isValidServiceType(oprFlight.getServiceType())) {
            // Sets error message for "Service Type"
        	 oprFlight.addError("OPRFLTSERVICETYPE", "serviceType", ErrorType.ERROR);
            //throw new CustomException("OPRFLTSERVICETYPE", "serviceType", ErrorType.ERROR);
         }
         validateGroundhandlerCode(oprFlight);
         validateBayAndRegistration(oprFlight);
         validateBValForJointFlight(oprFlight);
         validateLovForFlightLegs(oprFlight);
         validateLovForULDType(oprFlight);
         validateUldNumber(oprFlight);
      }
      return oprFlight;
   }

   /*
    * Validate Ground Handler Code.
    * 
    * @param oprFlight
    * 
    * @throws CustomException
    */
   private void validateGroundhandlerCode(OperativeFlight oprFlight) throws CustomException {
      if (oprFlight != null && StringUtils.isEmpty(oprFlight.getGroundHandlerCode())) {
         // Sets error message for "Handler"
         // "Ground Handler Code cannot be blank"
    	  oprFlight.addError("OPRFLTGRHND", "groundHandlerCode", ErrorType.ERROR);
         //throw new CustomException("OPRFLTGRHND", "groundHandlerCode", ErrorType.ERROR);
      }
   }

   /**
    * Validate Inbound and Outbound Bay and Registration.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateBayAndRegistration(OperativeFlight oprFlight) throws CustomException {
      if (oprFlight != null) {
         validateParkingBay(oprFlight);
         validateAircraftRegNo(oprFlight);
      }
   }

   /**
    * Validate Aircraft Registration No.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateAircraftRegNo(OperativeFlight oprFlight) throws CustomException {
      if (!StringUtils.isEmpty(oprFlight.getRegisArrival()) && !validateRegsNumber(oprFlight.getRegisArrival())) {
         // Sets error message for "regisArrival"
         // "Invalid Inbound Aircraft Registration Number"
    	  oprFlight.addError("OPRFLTINBREG01", "regisArrival", ErrorType.ERROR);
         //throw new CustomException("OPRFLTINBREG01", "regisArrival", ErrorType.ERROR);
      }
      if (!StringUtils.isEmpty(oprFlight.getRegisDeparture()) && !validateRegsNumber(oprFlight.getRegisDeparture())) {
         // Sets error message for "regisDeparture"
         // "Invalid Outbound Aircraft Registration Number"
    	  oprFlight.addError("OPRFLTINBREG02", "regisDeparture", ErrorType.ERROR);
         //throw new CustomException("OPRFLTINBREG02", "regisDeparture", ErrorType.ERROR);
      }
   }

   /**
    * Validate Parking Bay.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateParkingBay(OperativeFlight oprFlight) throws CustomException {
      if (!StringUtils.isEmpty(oprFlight.getCodPrkBayArr()) && !validateParkingBay(oprFlight.getCodPrkBayArr())) {
         // Sets error message for "codPrkBayArr"
         // "Invalid Inbound Parking Bay"
    	  oprFlight.addError("OPRFLTINBPRK01", "codPrkBayArr", ErrorType.ERROR);
      }
      if (!StringUtils.isEmpty(oprFlight.getCodPrkBayDep()) && !validateParkingBay(oprFlight.getCodPrkBayDep())) {
         // Sets error message for "codPrkBayDep"
         // "Invalid Outbound Parking Bay"
    	  oprFlight.addError("OPRFLTINBPRK02", "codPrkBayDep", ErrorType.ERROR);
      }
   }

   /**
    * It performs Business Validation for Joint Flights.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateBValForJointFlight(OperativeFlight oprFlight) throws CustomException {
      if (!CollectionUtils.isEmpty(oprFlight.getFlightJoints())) {
         int jointCount = 0;
         for (OperativeFlightJoint joint : oprFlight.getFlightJoints()) {
            if (!deleteIndSuccess.contains(joint.getFlagDelete())) {
               jointCount++;
            }
         }
         if (jointCount > OPRFLT_JOINTFLT_MAX_COUNT) {
            // Sets validation message for "Joint Flight count cannot be more than 3"
            // A maximum of 3 slave flights can be assigned to a Master Flight.
        	 oprFlight.addError("OPRFLT_JOINTFLT_MAX_COUNT_MSSG", "jointFlight", ErrorType.ERROR);
         }
         for (OperativeFlightJoint joint : oprFlight.getFlightJoints()) {
            if (!StringUtils.isEmpty(joint.getJointFlightCarCode())
                  && (joint.getJointFlightCarCode().equals(oprFlight.getFlightKey())
                        || !flightDAO.isValidJointFlightKey(joint.getJointFlightCarCode()))) {
               // Sets validation message for "Joint Flight"
            	oprFlight.addError("JNTFLT001", "jointFlight", ErrorType.ERROR);
            }
         }
      }
   }

   /**
    * This method extracts the Boarding Point from the First Leg
    * 
    * @param flightLegs
    * @throws CustomException
    */
   private String getFlightBoardingPoint(List<OperativeFlightLeg> flightLegs) {
      String boardingPoint = "INVALID"; // Just a placeholder.
      for (OperativeFlightLeg leg : flightLegs) {
         if (OPRFLT_LEG1_ORDER == leg.getLegOrderCode()) {
            boardingPoint = leg.getBoardPointCode();
         }
      }
      return boardingPoint;
   }

   /**
    * It validates Flight Joint Flight Boarding Point against the Master Flight
    * Boarding Point.
    * 
    * @param boardingPoint
    * @param joint
    * @throws CustomException
    */
   public void validateJointFlightBoardingPoint(String boardingPoint, OperativeFlightJoint joint)
         throws CustomException {
      OperativeFlight jointOF = flightDAO.fetchFlightByKey(joint.getJointFlightCarCode());
      if (!getFlightBoardingPoint(jointOF.getFlightLegs()).equals(boardingPoint)) {
         // Sets validation message for "Joint Flight"
         // Master and Slave flight(s) always have a common boarding point.
         throw new CustomException("OPRFLT_JOINTFLT_BOARDINGPOINT_NOTMATCH", "jointFlight", ErrorType.ERROR);
      }
   }

   /**
    * It Validates business validation for Flight Legs.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateLovForFlightLegs(OperativeFlight oprFlight) throws CustomException {
      if (!CollectionUtils.isEmpty(oprFlight.getFlightLegs())) {
         for (OperativeFlightLeg leg : oprFlight.getFlightLegs()) {
            validateFlightLegsInfo(leg);
         }
      }
   }

   /**
    * It Validates business validation for Leg Info.
    * 
    * @param leg
    * @throws CustomException
    */
   private void validateFlightLegsInfo(OperativeFlightLeg leg) throws CustomException {
      if (!InsertIndicator.YES.toString().equalsIgnoreCase(leg.getFlagDelete())
            && StringUtils.isEmpty(leg.getAircraftModel())) {
         // Sets validation message for "Aircraft Model Blank"
    	  leg.addError("OPRFLT_AIRCRAFTMODEL_BLANK", "flightLegs.aircraftModel", ErrorType.ERROR);
      } else if (!InsertIndicator.YES.toString().equalsIgnoreCase(leg.getFlagDelete())
            && !StringUtils.isEmpty(leg.getAircraftModel())
            && !flightDAO.isValidAircraftModel(leg.getAircraftModel())) {
         // Sets validation message for "Aircraft Model"
    	  leg.addError("OPRFLTAIRCRACFTMODEL", "flightLegs.aircraftModel", ErrorType.ERROR);
      }
      if (!InsertIndicator.YES.toString().equalsIgnoreCase(leg.getFlagDelete())
            && !StringUtils.isEmpty(leg.getBoardPointCode())
            && !flightDAO.isValidBoardingPoint(leg.getBoardPointCode())) {
         // Sets validation message for "Boarding Point"
    	  leg.addError("OPRFLTBOARDINGPOINT", "flightLegs.boardPointCode", ErrorType.ERROR);
      }
      if (!InsertIndicator.YES.toString().equalsIgnoreCase(leg.getFlagDelete())
            && !StringUtils.isEmpty(leg.getOffPointCode()) && !flightDAO.isValidOffPoint(leg.getOffPointCode())) {
         // Sets validation message for "Off Point"
    	  leg.addError("OPRFLTOFFPOINT", "flightLegs.offPointCode", ErrorType.ERROR);
      }

   }

   /**
    * This method performs business validation for ULD Type.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateLovForULDType(OperativeFlight oprFlight) throws CustomException {
      if (!CollectionUtils.isEmpty(oprFlight.getFlightExpULDTyps())) {
         for (OperativeFlightExp uldType : oprFlight.getFlightExpULDTyps()) {
            if (!DeleteIndicator.YES.toString().equalsIgnoreCase(uldType.getFlagDelete())
                  && !StringUtils.isEmpty(uldType.getUldExpType())
                  && !frameworkValidationService.isValidUldType(uldType.getUldExpType())) {
               // Sets validation message for "UlD Type"

            	oprFlight.addError("ULDINV0001", "deletedUldTypeList", ErrorType.ERROR);
            }
         }
      }
   }

   /**
    * This method validates the UlD Number.
    * 
    * @param oprFlight
    * @throws CustomException
    */
   private void validateUldNumber(OperativeFlight oprFlight) throws CustomException {
      if (!CollectionUtils.isEmpty(oprFlight.getFlightExps())) {
         for (int i = 0; i <= oprFlight.getFlightExps().size() - 1; i++) {
            OperativeFlightExp uldType = oprFlight.getFlightExps().get(i);
            if (!DeleteIndicator.YES.toString().equalsIgnoreCase(uldType.getFlagDelete())
                  && !StringUtils.isEmpty(uldType.getUldNo()) && !flightDAO.isValidUldKey(uldType.getUldNo())) {
            	oprFlight.addError("ULDMODEL02", "flightExps[" + i + "].uldNo", ErrorType.ERROR);
            }
         }
      }
   }

   /**
    * To Validate Parking bay based on Regex.
    * 
    * @param pBay
    * @return
    */
   private boolean validateParkingBay(String pBay) {
      return Pattern.matches("^([a-zA-Z0-9]{1,4})$", pBay);
   }

   /**
    * To Validate Registration Number.
    * 
    * @param regNo
    * @return
    */
   private boolean validateRegsNumber(String regNo) {
      return Pattern.matches("^(.{1,10})$", regNo);
   }

   /**
    * To validate List Value.
    * 
    * @param oprFlt
    * @throws CustomException
    */
   private void validateListValue(OperativeFlight oprFlt) throws CustomException {
      if (!CollectionUtils.isEmpty(oprFlt.getFlightFcts())) {
         oprFlt.getFlightFcts().forEach(e -> {
            if (StringUtils.isEmpty(e.getRemarks())) {
               if (saveIndSuccess.contains(e.getFlagSaved())) {
                  e.setFlagDelete(DeleteIndicator.YES.toString());
               } else {
                  oprFlt.setFlightFcts(null);
               }
            }
         });
      }
      validateExceptions(oprFlt);
      if (FALSE.equalsIgnoreCase(oprFlt.getJointFlight())) {
         oprFlt.getFlightJoints().forEach(e -> {
            if (StringUtils.isEmpty(e.getJointFlightCarCode())) {
               if (saveIndSuccess.contains(e.getFlagSaved())) {
                  e.setFlagDelete(DeleteIndicator.YES.toString());
               } else {
                  oprFlt.setFlightJoints(null);
               }
            }
         });
      }
      validateDelayReason(oprFlt);
   }

   /**
    * It checks Delay Value whether its selected or not.
    * 
    * @param oprFlt
    * @throws CustomException
    */
   private void validateDelayReason(OperativeFlight oprFlt) throws CustomException {
      if (TRUE.equalsIgnoreCase(oprFlt.getFlgDlyIn()) && StringUtils.isEmpty(oprFlt.getDlyInReason())) {
         throw new CustomException("OPRFLTDEL01", "dlyInReason", ErrorType.ERROR);
      } else if (FALSE.equalsIgnoreCase(oprFlt.getFlgDlyIn())) {
         oprFlt.setDlyInReason(null);
      }
      if (TRUE.equalsIgnoreCase(oprFlt.getFlgDlyOut()) && StringUtils.isEmpty(oprFlt.getDlyOutReason())) {
         throw new CustomException("OPRFLTDEL02", "dlyOutReason", ErrorType.ERROR);
      } else if (FALSE.equalsIgnoreCase(oprFlt.getFlgDlyOut())) {
         oprFlt.setDlyOutReason(null);
      }
   }

   /**
    * To validate List value for Exception.
    * 
    * @param oprFlt
    */

   private void checkForAutoFlight(OperativeFlight operativeFlight) throws CustomException {
      flightDAO.checkForAutoFlight(operativeFlight);
   }

   private void validateJointFlight(OperativeFlight operativeFlight) throws CustomException {
      int flag = 0;
      if (!CollectionUtils.isEmpty(operativeFlight.getFlightJoints())) {
         for (OperativeFlightJoint fltjoint : operativeFlight.getFlightJoints()) {
            if (Objects.nonNull(operativeFlight)) {
               String jointFlightCarCode = null;
               if (fltjoint.getJointFlightCarCode() != null) {
                  List<OperativeFlightJoint> flightJoints = operativeFlight.getFlightJoints();
                  for (OperativeFlightJoint flightJoint : flightJoints) {
                     jointFlightCarCode = flightJoint.getJointFlightCarCode();
                  }
               }
               for (OperativeFlightLeg legs : operativeFlight.getFlightLegs()) {
                  legs.setJointFlightCarCode(jointFlightCarCode);
                  if (flag == 0) {

                     flightDAO.validateJointFlight(legs);
                  }
                  flag++;
               }
            }
         }
      }

   }

   private void validateUldTyp(OperativeFlight operativeFlight) throws CustomException {
      if (!CollectionUtils.isEmpty(operativeFlight.getFlightExpULDTyps())) {
         // for (OperativeFlightExp uldTyp : operativeFlight.getFlightExpULDTyps()) {
         // flightDAO.validateUldTyp(uldTyp);
         // }
         flightDAO.validateUldTyp(operativeFlight);
      }
   }

   private void validateExceptions(OperativeFlight oprFlt) {
      if (!CollectionUtils.isEmpty(oprFlt.getFlightExps())) {
         oprFlt.getFlightExps().forEach(e -> {
            if (StringUtils.isEmpty(e.getUldNo()) && StringUtils.isEmpty(e.getUldWtReason())) {
               oprFlt.setFlightExps(null);
            }
         });
      }
      if (!CollectionUtils.isEmpty(oprFlt.getFlightExpULDTyps())) {
         oprFlt.getFlightExpULDTyps().forEach(e -> {
            if (StringUtils.isEmpty(e.getUldExpType()) && StringUtils.isEmpty(e.getUldWtReason())) {
               oprFlt.setFlightExpULDTyps(null);
            }
         });
      }
   }

   private OperativeFlight checkForDomesticFlight(OperativeFlight operativeFlight) throws CustomException {
      for (OperativeFlightLeg fltLeg : operativeFlight.getFlightLegs()) {
         if (flightDAO.validateDomesticFlight(fltLeg)) {
            fltLeg.setDomesticStatus("true");
         } else {
            fltLeg.setDomesticStatus("false");
         }
      }
      return operativeFlight;
   }

}