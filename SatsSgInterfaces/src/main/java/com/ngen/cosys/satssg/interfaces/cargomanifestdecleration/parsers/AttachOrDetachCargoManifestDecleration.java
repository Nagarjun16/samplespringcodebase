package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.parsers;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.dao.CargoManifestDeclerationMessageDAO;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.dao.CargoManifestDeclerationMessageDAOImpl;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.AttachAndDetatchCargoManifestDeclerationModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CmdLocalAuthorityInfoModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CustomCargoManifestDeclarationMessageModel;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingExceptionFactory;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.utils.SecureRandomCharGenerator;

@Component
@Scope(scopeName = "prototype")
public class AttachOrDetachCargoManifestDecleration {

   @Autowired
   BeanFactory beanFactory;

   @Autowired
   CargoManifestDeclerationMessageDAOImpl cargoManifestDeclerationMessageDAOImpl;

   @Autowired
   CargoManifestDeclerationMessageDAO dao;

   // System parameter values
   private BigInteger firstWindowForImport;
   private BigInteger secondWindowForImport;
   private BigInteger firstWindowForExport;
   private BigInteger secondWindowForExport;

   @PostConstruct
   public void getOneTimeParameterValues() throws CustomException {
      this.firstWindowForImport = dao.getMrsFirstWindowForImport();
      this.secondWindowForImport = dao.getMrsSecondWindowForImport();
      this.firstWindowForExport = dao.getMrsFirstWindowForExport();
      this.secondWindowForExport = dao.getMrsSecondWindowForExport();
   }

   public void validateCmdWithActionCode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException, CustomException, ParseException {

      createFlight(customCargoManifestDeclarationMessageModel);
      validateLateIndicator(customCargoManifestDeclarationMessageModel);

      BigInteger awbCount = cargoManifestDeclerationMessageDAOImpl
            .getAwbNumberCount(customCargoManifestDeclarationMessageModel);
      BigInteger hwbCount = cargoManifestDeclerationMessageDAOImpl
            .getHwbNumberCount(customCargoManifestDeclarationMessageModel);
      BigInteger awbExist = cargoManifestDeclerationMessageDAOImpl
            .getCmdAWBExist(customCargoManifestDeclarationMessageModel);
      BigInteger hwbExist = cargoManifestDeclerationMessageDAOImpl
            .getCmdHAWBExist(customCargoManifestDeclarationMessageModel);
      if (hwbExist.intValue() > 0
            && (StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getHouseWayBillNumber()))) {
         throw MessageParsingExceptionFactory
               .createMessageParsingException("CMD WITH THIS MAWB AND WITHOUT HWB DOES NOT EXIST");
      }
      if (awbExist.intValue() > 0
            && (!StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getHouseWayBillNumber()))) {
         throw MessageParsingExceptionFactory
               .createMessageParsingException("CMD WITH THIS MAWB AND WITHOUT HWB ALREADY EXIST");
      }
      if (customCargoManifestDeclarationMessageModel.getActionCode().equalsIgnoreCase("A")) {
         validateCmdWithAddActionCode(customCargoManifestDeclarationMessageModel, awbCount, hwbCount);
      } else if (customCargoManifestDeclarationMessageModel.getActionCode().equalsIgnoreCase("M")) {
         validateCmdWithModifyActionCode(customCargoManifestDeclarationMessageModel, awbCount);
      } else if (customCargoManifestDeclarationMessageModel.getActionCode().equalsIgnoreCase("D")) {
         validateCmdWithDeleteActionCode(customCargoManifestDeclarationMessageModel, awbCount);
      } else {
         throw MessageParsingExceptionFactory.createMessageParsingException("INVALID ACTION CODE");
      }
   }

   public void validateCmdWithAddActionCode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel, BigInteger awbCount,
         BigInteger hwbCount) throws MessageParsingException, CustomException {
      if (null == customCargoManifestDeclarationMessageModel.getHouseWayBillNumber() && awbCount.intValue() > 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException("CMD ALREADY EXISTS");
      }
      if (null != customCargoManifestDeclarationMessageModel.getHouseWayBillNumber() && hwbCount.intValue() > 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException("CMD ALREADY EXISTS");
      }
   }

   public void validateCmdWithModifyActionCode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel, BigInteger count)
         throws MessageParsingException, CustomException {
      if (count.intValue() == 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException("PREVIOUS CMD NOT FOUND MODIFY UNABLE");
      } else {
         cargoManifestDeclerationMessageDAOImpl.deleteCmdInfo(customCargoManifestDeclarationMessageModel);
      }
   }

   public void validateCmdWithDeleteActionCode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel, BigInteger count)
         throws MessageParsingException, CustomException {
      if (count.intValue() == 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException("PREVIOUS CMD NOT FOUND DELETE UNABLE");
      } else {
         cargoManifestDeclerationMessageDAOImpl.deleteCmdInfo(customCargoManifestDeclarationMessageModel);
         caluclateStatusCodeWithCmdAndDeleteActionCode(customCargoManifestDeclarationMessageModel);
         return;
      }
   }

   public void validateLateIndicator(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException, MessageParsingException {

      CustomCargoManifestDeclarationMessageModel flightdata = cargoManifestDeclerationMessageDAOImpl
            .getLatestFlightToValidateLateIndicator(customCargoManifestDeclarationMessageModel);
      if (null != flightdata) {
         LocalDate date = flightdata.getFlightDate();
         String impOrExpIndicator = flightdata.getImportExportIndicator();
         if (impOrExpIndicator.equalsIgnoreCase("I")) {
            LocalDate cmdLastDate = date.plusDays(firstWindowForImport.intValue());
            LocalDate cmdsecondWindowLastDate = date.plusDays(secondWindowForImport.intValue());
            compareCurrentDateWithCmdWindowDate(cmdLastDate, cmdsecondWindowLastDate,
                  customCargoManifestDeclarationMessageModel);
         } else if (impOrExpIndicator.equalsIgnoreCase("E")) {
            LocalDate cmdLastDate = date.plusDays(firstWindowForExport.intValue());
            LocalDate cmdsecondWindowLastDate = date.plusDays(secondWindowForExport.intValue());
            compareCurrentDateWithCmdWindowDate(cmdLastDate, cmdsecondWindowLastDate,
                  customCargoManifestDeclarationMessageModel);

         }
      } else {
         throw MessageParsingExceptionFactory.createMessageParsingException("AWB RECORD NOT FOUND");
      }
   }

   public void compareCurrentDateWithCmdWindowDate(LocalDate cmdLastDate, LocalDate cmdsecondWindowLastDate,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException, CustomException {
      LocalDate currentDate = java.time.LocalDate.now();
      
      if (cmdLastDate.compareTo(currentDate) < 0 && cmdsecondWindowLastDate.compareTo(currentDate) > 1
            && customCargoManifestDeclarationMessageModel.getLateIndicator().equalsIgnoreCase("N")) {
         throw MessageParsingExceptionFactory
               .createMessageParsingException("LATE CMD SUBMISSION BUT NOT FLAGGED AS LATE");
      } else if (cmdLastDate.compareTo(currentDate) > 1
            && customCargoManifestDeclarationMessageModel.getLateIndicator().equalsIgnoreCase("Y")) {
         throw MessageParsingExceptionFactory
               .createMessageParsingException(TextMessageConstants.INVALID_LATE_INDICATOR.getValue());
      } else if (cmdsecondWindowLastDate.compareTo(currentDate) < 1
            && customCargoManifestDeclarationMessageModel.getLateIndicator().equalsIgnoreCase("N")) {
         throw MessageParsingExceptionFactory
               .createMessageParsingException(TextMessageConstants.INVALID_LATE_INDICATOR.getValue());
      } else if (cmdsecondWindowLastDate.isBefore(currentDate)
            && customCargoManifestDeclarationMessageModel.getLateIndicator().equalsIgnoreCase("Y")
            && cargoManifestDeclerationMessageDAOImpl
                  .getMrsSentDetail(customCargoManifestDeclarationMessageModel) != null) {
         throw MessageParsingExceptionFactory
               .createMessageParsingException("FLIGHT CLOSE MRS ALREADY SENT FOR THIS FLIGHT");
      }
      else if (cmdsecondWindowLastDate.compareTo(currentDate) >= 0
    		  &&cmdLastDate.compareTo(currentDate) < 0 
              && customCargoManifestDeclarationMessageModel.getLateIndicator().equalsIgnoreCase("Y")
              && cargoManifestDeclerationMessageDAOImpl
              .getMrsSentDetail(customCargoManifestDeclarationMessageModel) != null) {
           throw MessageParsingExceptionFactory
                 .createMessageParsingException(TextMessageConstants.FLIGHT_CLOSED_CMD_ROUTED_TO_TDB.getValue());
        } 
        
   }

   public void attachCmdToEarliestFlight(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException, MessageParsingException {

      // get earliest flight to Attach CMD
      BigInteger earliestFlight = cargoManifestDeclerationMessageDAOImpl
            .getEarliestFlightToAttach(customCargoManifestDeclarationMessageModel);
      // get Lowest priority status shipment
      if (!ObjectUtils.isEmpty(earliestFlight)) {
         customCargoManifestDeclarationMessageModel.setEarliestFlight(earliestFlight);
      } else {
         throw MessageParsingExceptionFactory
               .createMessageParsingException("FLIGHT CLOSE MRS ALREADY SENT FOR THIS FLIGHT");
      }
      CustomCargoManifestDeclarationMessageModel shipmentToAttach = cargoManifestDeclerationMessageDAOImpl
            .getShipmentFromCustShipInfo(customCargoManifestDeclarationMessageModel);
      customCargoManifestDeclarationMessageModel.setCustomShipmentInfoId(shipmentToAttach.getCustomShipmentInfoId());
      // insert into cmd table
      // Generate random number for CMDProcessingId sequence
      String cmdProcessingId = SecureRandomCharGenerator.get8DRandomNumber();
      customCargoManifestDeclarationMessageModel.setCmdProcessingId(cmdProcessingId);
      // insert into CMD LocalAuthority info table
      cargoManifestDeclerationMessageDAOImpl.insertCmdInfo(customCargoManifestDeclarationMessageModel);

      for (CmdLocalAuthorityInfoModel larInfo : customCargoManifestDeclarationMessageModel.getLarInfo()) {
         larInfo.setCmdProcessingId(customCargoManifestDeclarationMessageModel.getCmdProcessingId());
         larInfo.setAwbPrefix(customCargoManifestDeclarationMessageModel.getAwbPrefix());
         larInfo.setAwbSerialNumber(customCargoManifestDeclarationMessageModel.getAwbSerialNumber());
         larInfo.setHouseWayBillNumber(customCargoManifestDeclarationMessageModel.getHouseWayBillNumber());
         cargoManifestDeclerationMessageDAOImpl.insertCmdlarInfo(larInfo);
      }

      List<AttachAndDetatchCargoManifestDeclerationModel> cmd = cargoManifestDeclerationMessageDAOImpl
            .getCmdToAttachToEarliestFlight(customCargoManifestDeclarationMessageModel);

      if (!ObjectUtils.isEmpty(earliestFlight)) {

         customCargoManifestDeclarationMessageModel.setCustomFlightId(earliestFlight);

         for (AttachAndDetatchCargoManifestDeclerationModel attach : cmd) {
            attach.setEarliestFlight(earliestFlight);
            cargoManifestDeclerationMessageDAOImpl.deleteFromOtherFlights(attach);
            attach.setShipmentNumber(customCargoManifestDeclarationMessageModel.getAwbPrefix()
                  + customCargoManifestDeclarationMessageModel.getAwbSerialNumber());
            attach.setHwbNumber(customCargoManifestDeclarationMessageModel.getHouseWayBillNumber());
            attach.setCmdProcessingId(cmdProcessingId);
            cargoManifestDeclerationMessageDAOImpl.linkCmdToMrs(attach);
         }
         caluclateStatusCodeWithCmd(customCargoManifestDeclarationMessageModel, shipmentToAttach);
         
         LocalDateTime cmddatetime= TenantZoneTime.getZoneDateTime(LocalDateTime.now(),TenantContext.get().getTenantId());
         LocalDate cmddate = cmddatetime.toLocalDate();
         customCargoManifestDeclarationMessageModel.setCmdRecieved(cmddate);
         cargoManifestDeclerationMessageDAOImpl
               .insertCmdInfoIntoCustShipInfo(customCargoManifestDeclarationMessageModel);
      } else {
         cargoManifestDeclerationMessageDAOImpl.addToHoldShipmentsInfo(customCargoManifestDeclarationMessageModel);
      }
      this.dao.validateCmdtStatus(customCargoManifestDeclarationMessageModel);
      this.dao.detachCMDfromRemainingParts(customCargoManifestDeclarationMessageModel);
      this.dao.attachCmdToFirstPart(customCargoManifestDeclarationMessageModel);

   }

   public void caluclateStatusCodeWithCmd(CustomCargoManifestDeclarationMessageModel shipment,
         CustomCargoManifestDeclarationMessageModel firstShipment) throws CustomException, MessageParsingException {
      List<CustomCargoManifestDeclarationMessageModel> shipmentData = null;
      List<CustomCargoManifestDeclarationMessageModel> cmdinfo = null;
      shipment.setShipmentNumber(shipment.getAwbPrefix() + shipment.getAwbSerialNumber());
      shipmentData = cargoManifestDeclerationMessageDAOImpl.getShipmentsToAttachCmds(shipment);
      shipmentData.get(0).setAwbPrefix(shipment.getAwbPrefix());
      shipmentData.get(0).setAwbSerialNumber(shipment.getAwbSerialNumber());

      if (shipmentData.size() > 1) {
         caluclateStatusCodeWithCmdforPartShipment(shipmentData, shipment, firstShipment);

      } else {
         cmdinfo = cargoManifestDeclerationMessageDAOImpl.getCustomFlightIdAndPiecesFromLinkMrs(shipmentData.get(0));
         BigInteger cmdPieces = new BigInteger("0");
         for (CustomCargoManifestDeclarationMessageModel cmd : cmdinfo) {
            if (!ObjectUtils.isEmpty(cmd.getHwbPieces())) {
               cmdPieces = cmdPieces.add(cmd.getHwbPieces());
            } else {
               cmdPieces = cmd.getPieces();
            }

         }
         for (CustomCargoManifestDeclarationMessageModel cmd : cmdinfo) {

        		if (!shipment.getTenantAirport().equalsIgnoreCase(cmd.getOrigin())
						&& !shipment.getTenantAirport().equalsIgnoreCase(cmd.getFinalDestination())) {
					shipment.setMrsStatusCode("MA");
				} else {
            if (ObjectUtils.isEmpty(cmd.getLocalAuthorityType())) {
               if (cmdPieces.compareTo(shipmentData.get(0).getPieces()) == 0) {
                  shipment.setMrsStatusCode("MA");

               } else {
                  shipment.setMrsStatusCode("PC");
               }
            }

            else if (cmd.getLocalAuthorityType().equalsIgnoreCase("EC")) {
               if (cmdPieces.compareTo(shipmentData.get(0).getPieces()) == 0) {
                  shipment.setMrsStatusCode("MA");

               } else {
                  shipment.setMrsStatusCode("PC");
               }
            } else if (cmd.getLocalAuthorityType().equalsIgnoreCase("PN")
                  || cmd.getLocalAuthorityType().equalsIgnoreCase("ZZZ")) {
               if (cmdPieces.compareTo(shipmentData.get(0).getPieces()) == 0) {
                  shipment.setMrsStatusCode("OK");
               } else {
                  shipment.setMrsStatusCode("PC");
               }

            } else if (cmd.getLocalAuthorityType().equalsIgnoreCase("PTF")) {
               if (cmdPieces.compareTo(shipmentData.get(0).getPieces()) == 0) {
                  shipment.setMrsStatusCode("OK");
               } else {
                  shipment.setMrsStatusCode("PC");
               }
            } else {
               if (cmdPieces.compareTo(shipmentData.get(0).getPieces()) == 0
                     && shipmentData.get(0).getMrsStatusCode().equalsIgnoreCase("UN")) {
                  shipment.setMrsStatusCode("OK");
               } else {
                  shipment.setMrsStatusCode("PC");
               }
            }
				}
            cargoManifestDeclerationMessageDAOImpl.updateMrsStatusCode(shipment);
         }
      }
   }

   public void caluclateStatusCodeWithCmdforPartShipment(List<CustomCargoManifestDeclarationMessageModel> shipmentData,
         CustomCargoManifestDeclarationMessageModel shipment, CustomCargoManifestDeclarationMessageModel firstShipment)
         throws CustomException {
      List<CustomCargoManifestDeclarationMessageModel> shipmentToAttach = shipmentData.stream().filter(
            shp -> (shp.getCustomShipmentInfoId().intValue() == firstShipment.getCustomShipmentInfoId().intValue()))
            .collect(Collectors.toList());

      shipmentData = shipmentData.stream()
            .filter(shp -> (shp.getCustomFlightId().intValue() != firstShipment.getCustomFlightId().intValue()))
            .collect(Collectors.toList());
      for (CustomCargoManifestDeclarationMessageModel cmdrec : shipmentToAttach) {
         cmdrec.setAwbPrefix(cmdrec.getShipmentNumber().substring(0, 3));
         cmdrec.setAwbSerialNumber(cmdrec.getShipmentNumber().substring(3));
         List<CustomCargoManifestDeclarationMessageModel> cmdinfo = cargoManifestDeclerationMessageDAOImpl
               .getCustomFlightIdAndPiecesFromLinkMrs(cmdrec);
         if (!CollectionUtils.isEmpty(cmdinfo)) {
            BigInteger cmdPieces = new BigInteger("0");
            for (CustomCargoManifestDeclarationMessageModel cmd : cmdinfo) {

               if (!ObjectUtils.isEmpty(cmd.getHwbPieces())) {
                  cmdPieces = cmdPieces.add(cmd.getHwbPieces());
               } else {
                  cmdPieces = cmd.getPieces();
               }

            }
            for (CustomCargoManifestDeclarationMessageModel cmd : cmdinfo) {
               if (cmd.getLocalAuthorityType().equalsIgnoreCase("EC") && cmdPieces == cmdrec.getPieces()) {

                  cmdrec.setMrsStatusCode("MA");
                  break;
               } else if (cmd.getLocalAuthorityType().equalsIgnoreCase("PN") && cmdPieces == cmdrec.getPieces()) {
                  cmdrec.setMrsStatusCode("OK");

               } else if (cmdPieces == cmdrec.getPieces() && cmdrec.getMrsStatusCode().equalsIgnoreCase("UN")) {
                  cmdrec.setMrsStatusCode("OK");
               } else {
                  cmdrec.setMrsStatusCode("PC");
               }
            }
         } else {
            cmdrec.setMrsStatusCode("PA");
         }
         cargoManifestDeclerationMessageDAOImpl.updateMrsStatusCode(cmdrec);
         cargoManifestDeclerationMessageDAOImpl.updatePartshipmentInfoInCustoms(cmdrec);
      }
      for (CustomCargoManifestDeclarationMessageModel cmd : shipmentData) {
         cmd.setMrsStatusCode("PA");
         cargoManifestDeclerationMessageDAOImpl.updateMrsStatusCode(cmd);
         cargoManifestDeclerationMessageDAOImpl.updatePartshipmentInfoInCustoms(cmd);
      }
   }

   public void caluclateStatusCodeWithCmdAndDeleteActionCode(CustomCargoManifestDeclarationMessageModel shipment)
         throws CustomException, MessageParsingException {
      List<CustomCargoManifestDeclarationMessageModel> cmd = cargoManifestDeclerationMessageDAOImpl
            .getCustomFlightIdAndPiecesFromLinkMrs(shipment);
      // not required
      CustomCargoManifestDeclarationMessageModel firstShipment = null;
      if (!CollectionUtils.isEmpty(cmd)) {
         caluclateStatusCodeWithCmd(shipment, firstShipment);
      } else {
         cargoManifestDeclerationMessageDAOImpl.deleteAttachedCmd(shipment);
         BigInteger earliestFlight = cargoManifestDeclerationMessageDAOImpl.getEarliestFlightToAttach(shipment);
         // get Lowest priority status shipment
         if (earliestFlight != null) {
            shipment.setEarliestFlight(earliestFlight);
         }
         
         CustomCargoManifestDeclarationMessageModel shipmentToAttach = cargoManifestDeclerationMessageDAOImpl
               .getShipmentFromCustShipInfo(shipment);
         shipment.setCustomShipmentInfoId(shipmentToAttach.getCustomShipmentInfoId());
         shipment.setShipmentNumber(shipment.getAwbPrefix() + shipment.getAwbSerialNumber());
         List<CustomCargoManifestDeclarationMessageModel> shipmentData = cargoManifestDeclerationMessageDAOImpl
               .getCustomFlightIdAndPiecesFromCustShipInfo(shipment);
         shipmentData.get(0).setAwbPrefix(shipment.getAwbPrefix());
         shipmentData.get(0).setAwbSerialNumber(shipment.getAwbSerialNumber());
         for (CustomCargoManifestDeclarationMessageModel data : shipmentData) {
            // Derive the status code for shipments which have delivery
            if (!StringUtils.isEmpty(data.getDoNumber()) && (!"NO".equalsIgnoreCase(data.getDoNumber()))
                  && StringUtils.isEmpty(data.getCancellationReasonCode())) {
               switch (data.getLocalAuthorityType()) {
               case "IA":
                  shipment.setMrsStatusCode("NO");
                  break;
               case "EC":
                  shipment.setMrsStatusCode("MA");
                  break;
               case "PN":
                  shipment.setMrsStatusCode("OK");
                  break;
               default:
                  shipment.setMrsStatusCode("NO");
                  break;
               }
            } else {
               // Import MRS code status
               String typeOfShipment = !StringUtils.isEmpty(data.getImportExportIndicator())
                     ? data.getImportExportIndicator()
                     : "X";
               if ("I".equalsIgnoreCase(typeOfShipment) && (!StringUtils.isEmpty(data.getLocalAuthorityType()))) {
                  switch (data.getLocalAuthorityType()) {
                  case "PN":
                     shipment.setMrsStatusCode("OK");
                     break;
                  case "EC":
                     shipment.setMrsStatusCode("MA");
                     break;
                  default:
                     shipment.setMrsStatusCode("NO");
                     break;
                  }
               } else if ("I".equalsIgnoreCase(typeOfShipment) && (StringUtils.isEmpty(data.getLocalAuthorityType()))) {
                  shipment.setMrsStatusCode("NO");
               } else if ((!"I".equalsIgnoreCase(typeOfShipment))
                     && (!StringUtils.isEmpty(data.getLocalAuthorityType()))) {
                  switch (data.getLocalAuthorityType()) {

                  case "PTF":
                     shipment.setMrsStatusCode("NO");
                     break;
                  case "EC":
                     shipment.setMrsStatusCode("MA");
                     break;
                  case "PN":
                     shipment.setMrsStatusCode("OK");
                     break;
                  default:
                     shipment.setMrsStatusCode("NO");
                     break;
                  }
               } else {
                  shipment.setMrsStatusCode("NO");
               }

            }

         }
         shipment.setCustomFlightId(shipmentData.get(0).getCustomFlightId());
         cargoManifestDeclerationMessageDAOImpl.updateMrsStatusCode(shipment);
         BigInteger hwbExist = cargoManifestDeclerationMessageDAOImpl
                 .getCmdHAWBExist(shipment);
         if(hwbExist.intValue() == 0) {
        	 cargoManifestDeclerationMessageDAOImpl.updateCmdDeleteInfoIntoCustomsShipmentInfo(shipment); 
         }
        
      }
   }

   public void createFlight(CustomCargoManifestDeclarationMessageModel messageData)
         throws ParseException, MessageParsingException, CustomException {
      int fltMonth = getMonth(messageData.getFlightMonth());
      int flightDay = messageData.getFlightDay().intValue();
      int monthCompare = LocalDate.now(ZoneId.systemDefault()).getMonthValue();
      
      LocalDate flightDateTime = null;
      //LocalDate cmddate1= java.time.LocalDate.now();
      LocalDateTime cmddatetime= java.time.LocalDateTime.now().plusHours(8);
       LocalDate cmddate = cmddatetime.toLocalDate();
      // For year change
      if (fltMonth > monthCompare && monthCompare == 1) {
         flightDateTime = LocalDate.of(LocalDate.now(ZoneId.systemDefault()).getYear() - 1, fltMonth, flightDay);
      } else {
         flightDateTime = LocalDate.of(LocalDate.now(ZoneId.systemDefault()).getYear(), fltMonth, flightDay);
      }
      //
      messageData.setFlightDate(flightDateTime);
      messageData.setFlightkey((messageData.getCarrierCode() + messageData.getFlightNumber()).trim());

      CustomCargoManifestDeclarationMessageModel flightDetails = cargoManifestDeclerationMessageDAOImpl
            .getFlightDetails(messageData);
      if (!ObjectUtils.isEmpty(flightDetails)) {
         messageData.setScheduleArrivalDate(flightDetails.getScheduleArrivalDate());
         messageData.setScheduleDepartureDate(flightDetails.getScheduleDepartureDate());
      } else {
         messageData.setScheduleArrivalDate(java.time.LocalDateTime.now());
         messageData.setScheduleDepartureDate(java.time.LocalDateTime.now());
      }
      if (messageData.getFlightDate().compareTo(cmddate) > 0
            || messageData.getFlightDate().compareTo(cmddate.minusDays(90)) < 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException("CMD DATE OUT OF RANGE");
      }

   }

   public int getMonth(String month) {
      try {
         Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(month);
         Calendar cal = Calendar.getInstance();
         cal.setTime(date);
         int value = cal.get(Calendar.MONTH);
         return (value + 1);
      } catch (ParseException parseException) {
         return 13;
      }
   }

}