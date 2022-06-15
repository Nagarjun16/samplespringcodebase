package com.ngen.cosys.flight.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.flight.dao.ScheduleFlightDetailsDao;
import com.ngen.cosys.flight.model.BatchJobsLog;
import com.ngen.cosys.flight.model.FlightSchedulePeriod;
import com.ngen.cosys.flight.model.OperativeFlight;
import com.ngen.cosys.flight.model.OperativeFlightFct;
import com.ngen.cosys.flight.model.OperativeFlightJoint;
import com.ngen.cosys.flight.model.OperativeFlightLeg;
import com.ngen.cosys.flight.model.OperativeFlightSegment;
import com.ngen.cosys.flight.model.OprFlightSegTempBO;
import com.ngen.cosys.flight.model.SchFlight;
import com.ngen.cosys.flight.model.SchFlightJoint;
import com.ngen.cosys.flight.model.SchFlightLeg;
import com.ngen.cosys.flight.model.SchFlightSeg;
import com.ngen.cosys.flight.model.SchFlightSegTempBO;
import com.ngen.cosys.flight.model.ScheduleFlightFact;
import com.ngen.cosys.framework.constant.DeleteIndicator;
import com.ngen.cosys.framework.constant.InsertIndicator;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.timezone.util.TenantZoneTime;

@Service("operativeFlightScheduleServiceImpl")
public class OperativeFlightScheduleServiceImpl implements OperativeFlightScheduleService {

   private static final Logger LOGGER = LoggerFactory.getLogger(OperativeFlightScheduleService.class);

   @Autowired
   private ScheduleFlightDetailsDao scheduleFlightDetailsDao;

   public static final String ZERO = "0";
   public static final String ONE = "1";
   public static final String CREATEDBY = "SCHEDULER";

   @Override
   @Transactional
   public List<OperativeFlight> fetchOperativeFlightSchedule() throws CustomException {
      FlightSchedulePeriod scedulePeriod = new FlightSchedulePeriod();
      List<FlightSchedulePeriod> schPeriodList = scheduleFlightDetailsDao.getScheduleList(scedulePeriod);
      List<OperativeFlight> displayFlights = new CopyOnWriteArrayList<>();
      for (FlightSchedulePeriod period : schPeriodList) {
         FlightSchedulePeriod flightSchedulePeriodDetails = scheduleFlightDetailsDao.findSchDetailsForSchPeriod(period);
         if (!CollectionUtils.isEmpty(flightSchedulePeriodDetails.getSchFlightList())) {
            for (SchFlight schDetail : flightSchedulePeriodDetails.getSchFlightList()) {
               displayFlights.addAll(populateOprFlight(schDetail, flightSchedulePeriodDetails, false));
            }
         }
      }
      // checking flight exist or not, if exists remove from list
      for (OperativeFlight oprFlt : displayFlights) {
         if (checkOperativeFlightExists(oprFlt)) {
            displayFlights.remove(oprFlt);
         }
      }
      return displayFlights;
   }

   private boolean checkOperativeFlightExists(OperativeFlight oprFlight) throws CustomException {
      Integer count = scheduleFlightDetailsDao.oprFlightExists(oprFlight);
      if (count == 0)
         return false;
      else
         return true;
   }

   protected boolean schExistsWithFrequency(SchFlight schFlight) throws CustomException {
      Integer count = scheduleFlightDetailsDao.fetchFrequencyWithSchedulePeriodId(schFlight);
      if (count == 0)
         return false;
      else
         return true;
   }

   protected List<OperativeFlight> populateOprFlightUptoConfigDate(SchFlight schDetail,
         FlightSchedulePeriod schedulePeriod, boolean isUpdateOprFlt) throws CustomException {
      List<OperativeFlight> oprativeFlightList = new ArrayList<>();

      LocalDate currentDate = LocalDate.now();
      while (currentDate.isBefore(schedulePeriod.getDateTo()) || currentDate.isEqual(schedulePeriod.getDateTo())) {
         checkAndCreateFlight(currentDate, schDetail, oprativeFlightList, schedulePeriod, isUpdateOprFlt);
         currentDate = currentDate.plusDays(1);
         // index++;
      }
      return oprativeFlightList;
   }

   private List<OperativeFlight> populateOprFlight(SchFlight schDetail, FlightSchedulePeriod schedulePeriod,
         boolean isUpdateOprFlt) throws CustomException {
      List<OperativeFlight> oprativeFlightList = new ArrayList<>();
      updateSchPeriodAsPerConfig(schedulePeriod);
      LocalDate runningDate = schedulePeriod.getDateFrom();
      LocalDate endDateSch = schedulePeriod.getDateTo();

      LocalDate currentDate = LocalDate.now();
      // while (currentDate.isBefore(schedulePeriod.getDateTo()) ||
      // currentDate.isEqual(schedulePeriod.getDateTo())) {
      while (runningDate.compareTo(endDateSch) <= 0) {
         checkAndCreateFlight(runningDate, schDetail, oprativeFlightList, schedulePeriod, isUpdateOprFlt);
         runningDate = runningDate.plusDays(1);
         // index++;
      }
      return oprativeFlightList;
   }

   private void updateSchPeriodAsPerConfig(FlightSchedulePeriod flightSchedulePeriod) throws CustomException {

      // FlightConfigurationModel config = scheduleFlightDAO.fetchBaseConfiguration();

      // ------ apply past days limit on the period's from date
      // e.g if 15 days, that limit is current date - 15 days or the start date of
      // schedule whichever is later
      // long pastDaysLimit = config.getPastDays();

      LocalDate tenantCurrDate = TenantZoneTime.getZoneDateTime(LocalDateTime.now(), flightSchedulePeriod.getTenantId())
            .toLocalDate();
      
      LocalDate effectiveStart = tenantCurrDate;

      if (effectiveStart.isBefore(flightSchedulePeriod.getDateFrom())) {
         effectiveStart = flightSchedulePeriod.getDateFrom();
      }
      // ------ apply future days limit on the period's To date
      // e.g if 10 days, that limit is current date + 10 days or the end date of
      // schedule whichever is earlier

      long futureDaysLimit = scheduleFlightDetailsDao.getFutureDays();
      LocalDate effectiveEnd = tenantCurrDate.plusDays(futureDaysLimit);

      if (effectiveEnd.isAfter(flightSchedulePeriod.getDateTo())) {
         effectiveEnd = flightSchedulePeriod.getDateTo();
      }

      flightSchedulePeriod.setDateFrom(effectiveStart);
      flightSchedulePeriod.setDateTo(effectiveEnd);

   }

   private void checkAndCreateFlight(LocalDate runDate, SchFlight schDeatil, List<OperativeFlight> oprativeFlightList,
         FlightSchedulePeriod flightSchedulePeriod, boolean isUpdateOprFlt) {
      DayOfWeek flightday = runDate.getDayOfWeek();
      if (DayOfWeek.MONDAY == flightday && schDeatil.isMonFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isMonJntFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      } else if (DayOfWeek.TUESDAY == flightday && schDeatil.isTueFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isTueJntFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      } else if (DayOfWeek.WEDNESDAY == flightday && schDeatil.isWedFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isWedJntFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      } else if (DayOfWeek.THURSDAY == flightday && schDeatil.isThurFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isThuJntFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      } else if (DayOfWeek.FRIDAY == flightday && schDeatil.isFriFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isFriJunFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      } else if (DayOfWeek.SATURDAY == flightday && schDeatil.isSatFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isSatJntFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      } else if (DayOfWeek.SUNDAY == flightday && schDeatil.isSunFlg()) {
         oprativeFlightList.add(createOperativeFlight(runDate, schDeatil, schDeatil.isSunJntFlg(), flightSchedulePeriod,
               isUpdateOprFlt));
      }
   }

   private OperativeFlight createOperativeFlight(LocalDate flightDate, SchFlight schDetail, boolean isJointFlight,
         FlightSchedulePeriod flightSchedulePeriod, boolean isUpdateOprFlt) {

      // Check if a flight exists for the date to create, if yes use that flight
      LocalDateTime flightDate00 = LocalDateTime.of(flightDate, LocalTime.MIDNIGHT);
      // OperativeFlight existingOprFlight =
      // existingOperativeFlightsDateMap.get(flightDate00);
      // if (existingOprFlight != null) {
      // if (!isUpdateOprFlt) {
      // // This is display mode, here Opr flight info takes precedence over Schedule
      // // Info...
      // try {
      // existingOprFlight.setFlightLegs(flightDAO.fetchLegs(existingOprFlight));
      // } catch (CustomException e) {
      // // If no legs / issue in getting legs.. ignore
      // }
      // existingOprFlight.setRouting(this.getRouting(existingOprFlight.getFlightLegs()));
      // return existingOprFlight;
      // }
      // }
      // no flight exists, create a dummy one for the purpose of listing...

      OperativeFlight operativeFlight = populateOperativeFlightForDateAsPerSchdule(flightDate, flightSchedulePeriod,
            schDetail, isJointFlight);

      // }
      return operativeFlight;
   }

   private OperativeFlight populateOperativeFlightForDateAsPerSchdule(LocalDate flightDate,
         FlightSchedulePeriod flightSchedulePeriod, SchFlight schDetail, boolean isJointFlight) {
      OperativeFlight operativeFlight = new OperativeFlight();
      // operativeFlight.setFlightId(operativeIndex);
      operativeFlight.setCarrierCode(flightSchedulePeriod.getFlightCarrierCode());
      operativeFlight.setFlightNo(flightSchedulePeriod.getFlightNumber());
      operativeFlight.setFlightKey(flightSchedulePeriod.getFlight());
      operativeFlight.setServiceType(schDetail.getFlightServiceType());
      operativeFlight.setDescription(schDetail.getDescription());
      operativeFlight.setFlightDate(flightDate.atStartOfDay());
      operativeFlight.setCaoPax(schDetail.getFlightType());
      operativeFlight.setFlgApn(flightSchedulePeriod.isApron() ? "1" : "0");
      operativeFlight.setGroundHandlerCode(flightSchedulePeriod.getGroundHandler());
      operativeFlight.setJointFlight(isJointFlight ? "1" : "0");
      operativeFlight.setFlgChaSch(schDetail.isSsmFlag() ? "1" : "0");
      operativeFlight.setFlgManCtl("N");
      operativeFlight.setFlgKvl("N");
      operativeFlight.setFlgDlsCtl("N");
      operativeFlight.setCancellation("A");
      if (!CollectionUtils.isEmpty(schDetail.getSchFlightLegs())) {
         operativeFlight
               .setFlightLegs(createOperativeFlightLeg(operativeFlight, schDetail.getSchFlightLegs(), schDetail));
      }
      if (!CollectionUtils.isEmpty(schDetail.getSchFlightSegments())) {
         operativeFlight.setFlightSegments(assignSegmentValue(prepareSegmentsList(operativeFlight.getFlightLegs()),
               schDetail.getSchFlightSegments()));
      }
      if (!CollectionUtils.isEmpty(schDetail.getFactList())) {
         operativeFlight.setFlightFcts(createFactList(schDetail));
      }
      if (isJointFlight) {
         operativeFlight.setFlightJoints(createOperativeJointFlight(schDetail));
      }
      operativeFlight.setRouting(this.getRouting(operativeFlight.getFlightLegs()));
      return operativeFlight;

   }

   private List<OperativeFlightLeg> createOperativeFlightLeg(OperativeFlight flight, List<SchFlightLeg> flightLegs,
         SchFlight scheduleFlight) {
      List<OperativeFlightLeg> operativeFlightLegList = new ArrayList<>();
      for (SchFlightLeg schFlightLeg : flightLegs) {
         OperativeFlightLeg opeFlightLeg = new OperativeFlightLeg();
         opeFlightLeg.setFlightId(flight.getFlightId());
         opeFlightLeg.setBoardPointCode(schFlightLeg.getBrdPt());
         opeFlightLeg.setOffPointCode(schFlightLeg.getOffPt());
         opeFlightLeg.setLegOrderCode(schFlightLeg.getCodLegOrder());
         opeFlightLeg.setAircraftModel(schFlightLeg.getAircraftType());
         opeFlightLeg.setCodTypFltSvc(scheduleFlight.getFlightServiceType());
         opeFlightLeg.setAircraftType(flight.getCaoPax());
         opeFlightLeg.setDepartureDate(flight.getFlightDate().plusDays(schFlightLeg.getDayChangeDep())
               .withHour(schFlightLeg.getDepTime().getHour()).withMinute(schFlightLeg.getDepTime().getMinute())
               .withSecond(schFlightLeg.getDepTime().getSecond()));
         opeFlightLeg.setArrivalDate(flight.getFlightDate().plusDays(schFlightLeg.getDayChangeArr())
               .withHour(schFlightLeg.getArrTime().getHour()).withMinute(schFlightLeg.getArrTime().getMinute())
               .withSecond(schFlightLeg.getArrTime().getSecond()));
         opeFlightLeg.setFlgDly("N");
         opeFlightLeg.setDomesticStatus(schFlightLeg.isDomesticLeg() ? "1" : "0");
         opeFlightLeg.setHandledInSystem(schFlightLeg.isHandledInSystem());
         operativeFlightLegList.add(opeFlightLeg);
      }
      flight.setFlightLegs(operativeFlightLegList);
      return operativeFlightLegList;
   }

   private List<SchFlightSeg> prepareSegments(List<SchFlightLeg> flightLegs) {
      List<SchFlightLeg> updatedLegs = flightLegs.stream()
            .filter(e -> !(DeleteIndicator.YES.toString().equalsIgnoreCase(e.getFlagDelete())))
            .collect(Collectors.toList());
      return createScheduleFlightSegments(updatedLegs);
   }

   private List<OperativeFlightFct> createFactList(SchFlight flight) {
      List<OperativeFlightFct> factList = new ArrayList<>();
      if (!CollectionUtils.isEmpty(flight.getFactList())) {
         for (ScheduleFlightFact fact : flight.getFactList()) {
            OperativeFlightFct newFct = new OperativeFlightFct();
            // TODO Remove the hardcoded SYSADMIN
            newFct.setCreatedUserCode("SYSADMIN");
            newFct.setFlagInsert(InsertIndicator.YES.toString());
            newFct.setCreatedDateTime(LocalDateTime.now());
            newFct.setSeqNo(new BigDecimal(fact.getSequnceNumber()));
            newFct.setRemarks(fact.getFact());
            factList.add(newFct);
         }
      }
      return factList;
   }

   private List<OperativeFlightJoint> createOperativeJointFlight(SchFlight flight) {
      List<OperativeFlightJoint> jointList = new ArrayList<>();
      for (SchFlightJoint schjoint : flight.getSchFlightJointList()) {
         OperativeFlightJoint newJoint = new OperativeFlightJoint();
         newJoint.setDepartureDateTime(LocalDateTime.now());
         newJoint.setJointFlightCarCode(schjoint.getJointFlightKey().substring(0, 1));
         newJoint.setJointFlightCarNo(schjoint.getJointFlightKey().substring(2));
         // TODO Remove the hardcoded SYSADMIN
         newJoint.setCreatedUserCode("SYSADMIN");
         jointList.add(newJoint);
      }
      return jointList;
   }

   private String getRouting(List<OperativeFlightLeg> flightLegList) {
      StringBuilder sb = new StringBuilder();
      if (!CollectionUtils.isEmpty(flightLegList)) {
         List<OperativeFlightLeg> sortedFlightLegList = flightLegList.stream()
               .sorted(Comparator.comparing(OperativeFlightLeg::getLegOrderCode)).collect(Collectors.toList());
         for (int i = 0; i < sortedFlightLegList.size(); i++) {
            OperativeFlightLeg o = sortedFlightLegList.get(i);
            if (i == 0) {
               sb.append(o.getBoardPointCode());
               sb.append(" - ");
               sb.append(o.getOffPointCode());
            } else {
               sb.append(" - " + o.getOffPointCode());
            }
         }
      }
      return sb.toString();
   }

   protected FlightSchedulePeriod prepareSegments(FlightSchedulePeriod flightSchedulePeriod) {
      for (SchFlight schFlight : flightSchedulePeriod.getSchFlightList()) {
         schFlight.setSchFlightSegments(createScheduleFlightSegments(schFlight.getSchFlightLegs()));
      }
      return flightSchedulePeriod;
   }

   protected List<SchFlightSeg> createScheduleFlightSegments(List<SchFlightLeg> flightLegs) {
      List<SchFlightSeg> segments = new ArrayList<>();
      List<SchFlightSegTempBO> segmentTempBOs;
      ArrayList<SchFlightLeg> legsClone = new ArrayList<>(flightLegs);
      legsClone = (ArrayList<SchFlightLeg>) legsClone.clone();
      segmentTempBOs = populateSegmentCursor(flightLegs, legsClone);
      performBusinessLogic(segments, segmentTempBOs, flightLegs);
      return segments;
   }

   private List<SchFlightSegTempBO> populateSegmentCursor(List<SchFlightLeg> flightLegs,
         ArrayList<SchFlightLeg> legsClone) {
      List<SchFlightSegTempBO> segmentCursor = new ArrayList<>();
      for (SchFlightLeg legOuter : flightLegs) {
         for (SchFlightLeg legInner : legsClone) {
            if ((legOuter.getCodLegOrder() <= legInner.getCodLegOrder())
                  && !legOuter.getBrdPt().equals(legInner.getOffPt())) {
               SchFlightSegTempBO flightCursor = new SchFlightSegTempBO();
               flightCursor.setFlightScheduleID(legOuter.getFlightScheduleID());
               flightCursor.setAptBrd(legOuter.getBrdPt());
               flightCursor.setBrdLeg(legOuter.getCodLegOrder());
               flightCursor.setAptOff(legInner.getOffPt());
               flightCursor.setOffLeg(legInner.getCodLegOrder());
               segmentCursor.add(flightCursor);
            }
         }
      }
      return segmentCursor;
   }

   protected List<SchFlightSeg> performBusinessLogic(final List<SchFlightSeg> segments,
         List<SchFlightSegTempBO> segmentTempBOs, List<SchFlightLeg> schFlightLegs) {
      short segmentOrder = 1;
      for (SchFlightSegTempBO segTempBo : segmentTempBOs) {
         SchFlightSeg segment = new SchFlightSeg();
         segment.setFlightScheduleID(segTempBo.getFlightScheduleID());
         segment.setBrdPt(segTempBo.getAptBrd());
         segment.setOffPt(segTempBo.getAptOff());
         segment.setServiceType(segTempBo.getServiceType());
         segment.setCodSegOrder(segmentOrder);
         if (schFlightLegs.stream().anyMatch(
               e -> e.getBrdPt().equals(segTempBo.getAptBrd()) && e.getOffPt().equals(segTempBo.getAptOff()))) {
            segment.setFlgLeg(true);

         } else {
            segment.setFlgLeg(false);
         }
         // TODO Remove hardcoded Audit Param
         segment.setCreatedBy("COSYS");
         segment.setNoMail(false);
         segments.add(segment);
         segmentOrder++;
      }
      return segments;
   }

   public List<OperativeFlightSegment> prepareSegmentsList(List<OperativeFlightLeg> flightLegs) {
      List<OperativeFlightSegment> flightSegments = new ArrayList<OperativeFlightSegment>();
      if (flightLegs != null) {
         flightSegments = createOperatingSegments(flightLegs);
      }
      return flightSegments;
   }

   private static CopyOnWriteArrayList<OperativeFlightSegment> createOperatingSegments(
         List<OperativeFlightLeg> flightLegs) {
      CopyOnWriteArrayList<OperativeFlightSegment> segments = new CopyOnWriteArrayList<>();
      if (!CollectionUtils.isEmpty(flightLegs)) {
         List<OprFlightSegTempBO> segmentTempBOs;
         ArrayList<OperativeFlightLeg> legsClone = new ArrayList<>(flightLegs);
         legsClone = (ArrayList<OperativeFlightLeg>) legsClone.clone();
         segmentTempBOs = populateSegmentCursorBO(flightLegs, legsClone);
         segments = performBusinessLogic(segments, segmentTempBOs);
      }
      return segments;
   }

   private static List<OprFlightSegTempBO> populateSegmentCursorBO(List<OperativeFlightLeg> flightLegs,
         ArrayList<OperativeFlightLeg> legsClone) {
      List<OprFlightSegTempBO> segmentCursor = new ArrayList<>();
      for (OperativeFlightLeg legOuter : flightLegs) {
         for (OperativeFlightLeg legInner : legsClone) {
            if (legOuter.getLegOrderCode() <= legInner.getLegOrderCode()
                  && !legOuter.getBoardPointCode().equals(legInner.getOffPointCode())) {
               OprFlightSegTempBO flightCursor = new OprFlightSegTempBO();
               flightCursor.setFlightId(legOuter.getFlightId());
               flightCursor.setDatStd(legOuter.getDepartureDate());
               // Commented by Ashutosh
               // flightCursor.setDatSta(legOuter.getArrivalDate());
               flightCursor.setAptBrd(legOuter.getBoardPointCode());
               flightCursor.setBrdLeg(legOuter.getLegOrderCode());

               flightCursor.setAptOff(legInner.getOffPointCode());
               // Added by Ashutosh
               flightCursor.setDatSta(legInner.getArrivalDate());
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

   private List<OperativeFlightSegment> assignSegmentValue(List<OperativeFlightSegment> list,
         List<SchFlightSeg> schFlightSeg) {
      for (OperativeFlightSegment oprSeg : list) {
         Optional<SchFlightSeg> segOptional = schFlightSeg.stream()
               .filter(seg -> seg.getBrdPt().equalsIgnoreCase(oprSeg.getCodAptBrd())
                     && seg.getOffPt().equalsIgnoreCase(oprSeg.getCodAptOff()))
               .findFirst();
         if (segOptional.isPresent()) {
            SchFlightSeg seg = segOptional.get();
            oprSeg.setFlgCargo(seg.isNoCargo() ? "1" : "0");
            oprSeg.setNoMail(seg.isNoMail() ? "1" : "0");
            oprSeg.setFlgNfl(seg.isFreightNA() ? "1" : "0");
            oprSeg.setFlgTecStp(seg.isTechStop() ? "1" : "0");
         }
      }
      return list;
   }

   @Override
   public String getServiceFlightURL(String endpointURLAppYML) throws CustomException {
      return scheduleFlightDetailsDao.getServiceFlightURL(endpointURLAppYML);
   }

   @Override
   public void performJobLog(BatchJobsLog logDetails) throws CustomException {
      logDetails.setJobId(scheduleFlightDetailsDao.getJobIdFromBatch());
      LOGGER.warn("ScheduleFlightCreationJob Job Log Param ::: ", logDetails.toString());
      scheduleFlightDetailsDao.saveBatchLogs(logDetails);
   }
}