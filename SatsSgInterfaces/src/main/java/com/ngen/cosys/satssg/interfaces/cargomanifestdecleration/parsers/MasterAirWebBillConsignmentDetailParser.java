package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.parsers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.cxml.response.ParserError;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.cmd.util.MessageParserHelper;
import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.dao.CargoManifestDeclerationMessageDAO;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CmdLocalAuthorityInfoModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CustomCargoManifestDeclarationMessageModel;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingExceptionFactory;

@Component
@Scope(scopeName = "prototype")
public class MasterAirWebBillConsignmentDetailParser {

   @Autowired
   private CargoManifestDeclerationMessageDAO cargoManifestDeclerationMessageDAO;

   public void parseMasterAWBConsignmentDetail(String[] msgdata, int strno,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {

      String msg = "";

      msg = msgdata[0].substring(4);

      if (strno == 0 && msg.lastIndexOf(TextMessageConstants.SLANTCHAR) == 18) {
         if (MessageParserHelper.isNumber(msg.charAt(0)) && MessageParserHelper.isNumber(msg.charAt(1))
               && MessageParserHelper.isNumber(msg.charAt(2)) && msg.charAt(3) == '-') {

            customCargoManifestDeclarationMessageModel.setAwbPrefix(msg.substring(0, 3));
            customCargoManifestDeclarationMessageModel.setAwbSerialNumber(msg.substring(4, 12));

            customCargoManifestDeclarationMessageModel.setOrigin(msg.substring(12, 15));
            customCargoManifestDeclarationMessageModel.setFinalDestination(msg.substring(15, 18));
            String quantityDetails = msg.substring((msg.lastIndexOf(TextMessageConstants.SLANTCHAR)) + 2);
            if (TextMessageConstants.SHIPMENT_DESCRIPTION_CODE_IDENTIFIER.getValue()
                  .equals(msg.substring(msg.lastIndexOf(TextMessageConstants.SLANTCHAR) + 1,
                        msg.lastIndexOf(TextMessageConstants.SLANTCHAR) + 2))) {
               parseQuantityDetails(quantityDetails, customCargoManifestDeclarationMessageModel);
            } else {
               throw MessageParsingExceptionFactory
                     .createMessageParsingException("SHIPMENT DESCRIPTION CODE IS NOT VALID. ");
            }
         }
      } else {
         parseNatureOfGoodsDetails(msgdata, customCargoManifestDeclarationMessageModel);
      }

   }

   public void parseQuantityDetails(String quantityDetails,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      char[] ch = quantityDetails.toCharArray();
      int num = 0;
      char weightUnitCode = 0;
      String str = "";
      for (int i = 0; i < quantityDetails.length(); i++) {
         if (Character.isLetter(ch[i])) {
            weightUnitCode = ch[i];
            str = quantityDetails.substring(quantityDetails.indexOf(ch[i]));
            break;
         }
         num = ++num;
      }

      if (num <= 4) {

         customCargoManifestDeclarationMessageModel
               .setPieces(new BigInteger(quantityDetails.substring(0, quantityDetails.indexOf(weightUnitCode))));

      } else {
         throw MessageParsingExceptionFactory.createMessageParsingException("PIECES IS NOT VALID");
      }
      if (str.length() > 8) {
         throw MessageParsingExceptionFactory.createMessageParsingException("INVALID WEIGHT CODE");
      } else {
         if (weightUnitCode != 'K') {
            throw MessageParsingExceptionFactory.createMessageParsingException("INVALID WEIGHT CODE");
         }
         customCargoManifestDeclarationMessageModel.setWeightUnitCode(weightUnitCode);
         String weight = str.substring(str.indexOf('K') + 1);
         if (!weight.contains(".") && weight.length() < 6) {
            weight = weight + ".0";
         }
         customCargoManifestDeclarationMessageModel.setWeight(new BigDecimal(weight));
      }
   }

   public void parseNatureOfGoodsDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      String harmonisedCode = null;
      String natureOfGooods = "";
      String msg = msgdata[0].substring(3);
      if (msg.indexOf(TextMessageConstants.SLANTCHAR) == 0) {
         if (msg.lastIndexOf(TextMessageConstants.SLANTCHAR) > 0) {

            natureOfGooods = msg.substring(1, msg.lastIndexOf(TextMessageConstants.SLANTCHAR));
            harmonisedCode = msg.substring(msg.lastIndexOf(TextMessageConstants.SLANTCHAR) + 1);
         } else {
            natureOfGooods = msg.substring(1);
         }
         try {
            if (natureOfGooods.length() <= 20) {
               customCargoManifestDeclarationMessageModel.setNatureOfGoods(natureOfGooods);
               if (null != harmonisedCode) {
                  customCargoManifestDeclarationMessageModel.setHarmonisedGoods(new BigInteger(harmonisedCode));
               }
            } else {
               throw MessageParsingExceptionFactory.createMessageParsingException("NATURE OF GOODS  IS NOT VALID. ");
            }
         } catch (Exception e) {
            throw MessageParsingExceptionFactory
                  .createMessageParsingException("NATURE OF GOODS DETAILS IS NOT VALID. ");
         }
      }
   }

   public void parseFlightDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException, CustomException {
      int characters = 0;
      String msg = msgdata[0];
      String flightDate = msg.substring(msg.lastIndexOf(TextMessageConstants.SLANTCHAR) + 1);
      String flightNumberAndcarrierCode = msg.substring(msg.indexOf(TextMessageConstants.SLANTCHAR) + 1,
            msg.lastIndexOf(TextMessageConstants.SLANTCHAR));
      for (int i = 0; i < flightNumberAndcarrierCode.length(); i++) {
         if (Character.isLetter(flightNumberAndcarrierCode.toCharArray()[i])) {
            ++characters;
         }
      }
      if (characters > 3 || flightDate.length() != 5) {
         throw MessageParsingExceptionFactory.createMessageParsingException("Flight DATE DETAILS IS NOT VALID. ");
      }
      if (flightDate.length() == 5) {
         customCargoManifestDeclarationMessageModel.setFlightDay(new BigInteger(flightDate.substring(0, 2)));
         customCargoManifestDeclarationMessageModel.setFlightMonth(flightDate.substring(2));
      }
      if (characters <= 3) {

         StringBuilder flightNumber = new StringBuilder(flightNumberAndcarrierCode);
         StringBuilder flightNum = new StringBuilder();
         StringBuilder carrier = new StringBuilder();
         if (flightNumber.length() < 6 && characters == 2) {
            flightNum = new StringBuilder(flightNumber.substring(2)).reverse();
            carrier = (new StringBuilder(flightNumber.substring(0, 2)));
         } else if (flightNumber.length() >= 6 && (characters == 2 || characters == 3)) {
            flightNum = new StringBuilder(flightNumber.reverse().substring(0, 4));
            carrier = (new StringBuilder(flightNumber.substring(4))).reverse();
         }

         customCargoManifestDeclarationMessageModel.setCarrierCode(carrier.toString());
         customCargoManifestDeclarationMessageModel.setFlightNumber(flightNum.reverse().toString());
         if (!StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getFlightNumber())) {
            String flight = "";
            int totalLen = 4;
            int flightlen = customCargoManifestDeclarationMessageModel.getFlightNumber().length();
            int diffLen = totalLen - flightlen;
            if (diffLen > 0) {
               for (int i = 0; i < diffLen; i++) {
                  flight = flight + 0;
               }
               if (flight.length() > 0 && customCargoManifestDeclarationMessageModel.getFlightNumber().length() < 4) {
                  customCargoManifestDeclarationMessageModel
                        .setFlightNumber(flight + customCargoManifestDeclarationMessageModel.getFlightNumber());
               }
            }
         }
      }

      BigInteger cosysCount = cargoManifestDeclerationMessageDAO
            .getShipmentCountFromCosys(customCargoManifestDeclarationMessageModel);
      if (ObjectUtils.isEmpty(cosysCount) || cosysCount.intValue() == 0) {
         throw MessageParsingExceptionFactory.createMessageParsingException("INVALID AWB NUMBER");
      }
   }

   public void parseHouseWayBillDetails(String[] msgdata, int strNo,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      String msg = "";
      if (strNo == 0) {
         msg = msgdata[0].substring(4);
         String hwbNumber = msg.substring(0, (msg.indexOf(TextMessageConstants.SLANTCHAR)));

         if ((!StringUtils.isEmpty(hwbNumber)) && hwbNumber.length() <= 17 && hwbNumber.length() > 0) {
            customCargoManifestDeclarationMessageModel.setHouseWayBillNumber(hwbNumber);
         } else {
            throw MessageParsingExceptionFactory
                  .createMessageParsingException("CMD WITH THIS MAWB AND WITHOUT HWB DOES NOT EXIST");
         }
         String withoutHwbNumber = msg.substring(msg.indexOf(TextMessageConstants.SLANTCHAR));
         parseHwbQuantityDetails(withoutHwbNumber, customCargoManifestDeclarationMessageModel);
      } else {
         parseHwbNatureOfGoodsDetails(msgdata, customCargoManifestDeclarationMessageModel);
      }
   }

   public void parseHwbQuantityDetails(String hwbQuantityDetails,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      String hwbPieces = hwbQuantityDetails.substring(hwbQuantityDetails.indexOf(TextMessageConstants.SLANTCHAR) + 1,
            hwbQuantityDetails.lastIndexOf(TextMessageConstants.SLANTCHAR));
      BigInteger pieces = new BigInteger(hwbPieces);
      if (!StringUtils.isEmpty(hwbPieces) && hwbPieces.length() <= 4 && pieces.intValue() > 0) {
         customCargoManifestDeclarationMessageModel.setHwbPieces(new BigInteger(hwbPieces));
      } else {
         throw MessageParsingExceptionFactory.createMessageParsingException("INVALID PIECES");
      }

      String withoutPieces = hwbQuantityDetails
            .substring(hwbQuantityDetails.lastIndexOf(TextMessageConstants.SLANTCHAR));
      if (withoutPieces.length() <= 9) {
         String weightCode = withoutPieces.substring(1, 2);
         if ("K".equalsIgnoreCase(weightCode)) {
            customCargoManifestDeclarationMessageModel.setHwbWeightUnitCode(weightCode);
         } else {
            throw MessageParsingExceptionFactory.createMessageParsingException("INVALID WEIGHT CODE");
         }
         String weight = withoutPieces.substring(2);
         if (StringUtils.isEmpty(weight) || (new BigDecimal(weight).compareTo(BigDecimal.ZERO) == 0)) {
            throw MessageParsingExceptionFactory.createMessageParsingException("INVALID WEIGHT");
         } else {
            customCargoManifestDeclarationMessageModel.setHwbWeight(new BigDecimal(withoutPieces.substring(2)));
         }
      } else {
         throw MessageParsingExceptionFactory.createMessageParsingException("INVALID WEIGHT");
      }
   }

   public void parseHwbNatureOfGoodsDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      try {
         String hwbNatureOfGooods = null;
         String hwbHarmonisedCode = null;
         String msg = msgdata[0].substring(3);
         if (msg.indexOf(TextMessageConstants.SLANTCHAR) == 0) {
            if (msg.indexOf(TextMessageConstants.SLANTCHAR) == 0) {
               if (msg.lastIndexOf(TextMessageConstants.SLANTCHAR) > 0) {
                  hwbNatureOfGooods = msg.substring(1, msg.lastIndexOf(TextMessageConstants.SLANTCHAR));
                  hwbHarmonisedCode = msg.substring(msg.lastIndexOf(TextMessageConstants.SLANTCHAR) + 1);
               } else {
                  hwbNatureOfGooods = msg.substring(1);
               }

               if (hwbNatureOfGooods.length() <= 20) {
            	  if(StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getHwbNatureOfGoods())) {
            		   customCargoManifestDeclarationMessageModel.setHwbNatureOfGoods(hwbNatureOfGooods);
            	  }
               
                  if (null != hwbHarmonisedCode) {
                     customCargoManifestDeclarationMessageModel
                           .setHwbHarmonisedGoods(new BigInteger(hwbHarmonisedCode));
                  }
               } else {
                  throw MessageParsingExceptionFactory
                        .createMessageParsingException("NATURE OF GOODS DETAILS IS NOT VALID. ");
               }
            }
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("NATURE OF GOODS DETAILS IS NOT VALID. ");
      }
   }

   public void parseMasterAwbShipperOrConsigneeDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      try {
         String shipperOrConsigneeNameOrAddress = msgdata[0].trim().substring(4);
         if (msgdata[1].indexOf(TextMessageConstants.SLANTCHAR) == 0 && !StringUtils.isEmpty(msgdata[1].substring(1))) {
            if (msgdata[0].substring(0, 3).equalsIgnoreCase("SHP")) {
               customCargoManifestDeclarationMessageModel.setShipperAddress(shipperOrConsigneeNameOrAddress);
            } else {
               customCargoManifestDeclarationMessageModel.setConsigneeAddress(shipperOrConsigneeNameOrAddress);
            }
         } else {
            throw MessageParsingExceptionFactory
                  .createMessageParsingException("MASTER AWB SHIPPER ADDRESS DETAILS IS NOT VALID. ");
         }

         if (!StringUtils.isEmpty(shipperOrConsigneeNameOrAddress)) {
            if (msgdata[0].substring(0, 3).equalsIgnoreCase("SHP")) {
               if (StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getShipperName())) {
                  customCargoManifestDeclarationMessageModel.setShipperName(shipperOrConsigneeNameOrAddress);
               }
            } else {
               if (StringUtils.isEmpty(customCargoManifestDeclarationMessageModel.getConsigneeName())) {
                  customCargoManifestDeclarationMessageModel.setConsigneeName(shipperOrConsigneeNameOrAddress);
               }
            }
         } else {
            throw MessageParsingExceptionFactory
                  .createMessageParsingException("MASTER AWB SHIPPER NAME IS NOT VALID. ");
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("INVALID SHIPPER DETAILS ", e.getMessage());
      }
   }

   public void parsePermitDetails(String[] msgdata, int strNo,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel,
         List<CmdLocalAuthorityInfoModel> cmdLarinfo) throws MessageParsingException {
      try {
         CmdLocalAuthorityInfoModel cmdlar = new CmdLocalAuthorityInfoModel();
         customCargoManifestDeclarationMessageModel.setLocalAuthorityType("PN");
         if (strNo == 0) {
            msgdata[0] = msgdata[0].substring(4);
            if (msgdata[0].indexOf((TextMessageConstants.TBD_PERMIT_IDENTIFIER.getValue())) == 0) {
               customCargoManifestDeclarationMessageModel.setPermitNumber(msgdata[0].substring(4));
               cmdlar.setLocalAuthorityType("PN");
               cmdlar.setPermitNumber(msgdata[0].substring(4));
               cmdlar.setAwbPrefix(customCargoManifestDeclarationMessageModel.getAwbPrefix());
               cmdlar.setAwbSerialNumber(customCargoManifestDeclarationMessageModel.getAwbSerialNumber());
               cmdLarinfo.add(cmdlar);
            }
            if (msgdata[0].indexOf(TextMessageConstants.CUSTOMS_PERMIT_IDENTIFIER.getValue()) == 0) {
               String msg = msgdata[0];
               String mixIndicator = msg.substring(0, msg.indexOf(TextMessageConstants.SLANTCHAR));
               customCargoManifestDeclarationMessageModel.setMixPack(mixIndicator);
               customCargoManifestDeclarationMessageModel.setPermitNumber(msgdata[0].substring(6));
               cmdlar.setLocalAuthorityType("PN");
               cmdlar.setPermitNumber(msgdata[0].substring(4));
               cmdlar.setAwbPrefix(customCargoManifestDeclarationMessageModel.getAwbPrefix());
               cmdlar.setAwbSerialNumber(customCargoManifestDeclarationMessageModel.getAwbSerialNumber());
               cmdLarinfo.add(cmdlar);
            }
         } else {
            cmdlar.setLocalAuthorityType("PN");
            cmdlar.setPermitNumber(msgdata[0].substring(4));
            cmdlar.setAwbPrefix(customCargoManifestDeclarationMessageModel.getAwbPrefix());
            cmdlar.setAwbSerialNumber(customCargoManifestDeclarationMessageModel.getAwbSerialNumber());
            cmdLarinfo.add(cmdlar);
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("MISSING PERMIT NUMBER OR EXEMPTION CODE",
               e.getMessage());
      }
   }

   public void parseImportMasterAwbDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      try {
         String awbNumber = msgdata[0].substring(4);
         if (awbNumber.length() == 12) {
            customCargoManifestDeclarationMessageModel.setImportAwbPrefix(awbNumber.substring(0, 3));
            customCargoManifestDeclarationMessageModel.setImportAwbPrefix(awbNumber.substring(5));
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("PERMIT DETAILS IS NOT VALID. ",
               e.getMessage());
      }
   }

   public void parseExemptionDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel,
         List<CmdLocalAuthorityInfoModel> cmdLarinfo) throws MessageParsingException, CustomException {
      customCargoManifestDeclarationMessageModel.setLocalAuthorityType("EC");
      String exemptionDetails = msgdata[0].substring(3);
      String exemptionCode = "";
      if (exemptionDetails.lastIndexOf(TextMessageConstants.SLANTCHAR) > 0) {
         exemptionCode = exemptionDetails.substring(exemptionDetails.indexOf(TextMessageConstants.SLANTCHAR) + 1,
               exemptionDetails.lastIndexOf(TextMessageConstants.SLANTCHAR));
         String exemptionRemarks = exemptionDetails
               .substring(exemptionDetails.lastIndexOf(TextMessageConstants.SLANTCHAR) + 1);
         if (null != exemptionRemarks && exemptionRemarks.length() <= 25) {
            customCargoManifestDeclarationMessageModel.setExemptionRemarks(exemptionRemarks);
         } else {
            throw MessageParsingExceptionFactory.createMessageParsingException("EXEMPTION REMARKS IS NOT VALID. ");
         }
      } else {
         exemptionCode = exemptionDetails.substring(exemptionDetails.indexOf(TextMessageConstants.SLANTCHAR) + 1);
         if (exemptionCode.equalsIgnoreCase("ZZ")) {
            throw MessageParsingExceptionFactory
                  .createMessageParsingException("REMARKS REQUIRED FOR ZZ EXEMPTION CODE");
         }
      }
      if (exemptionCode.length() == 2) {
         customCargoManifestDeclarationMessageModel.setExemptionCode(exemptionCode);
         BigInteger isValidexemptionCode = cargoManifestDeclerationMessageDAO
               .isValidexemptioncode(customCargoManifestDeclarationMessageModel);
         if (!(isValidexemptionCode.intValue() > 0)) {
            throw MessageParsingExceptionFactory.createMessageParsingException("INVALID PERMIT NUMBER");
         }
         CmdLocalAuthorityInfoModel cmdlar = new CmdLocalAuthorityInfoModel();
         customCargoManifestDeclarationMessageModel.setLocalAuthorityType("EC");
         cmdlar.setLocalAuthorityType("EC");
         cmdlar.setPermitNumber(exemptionCode);
         cmdlar.setAwbPrefix(customCargoManifestDeclarationMessageModel.getAwbPrefix());
         cmdlar.setAwbSerialNumber(customCargoManifestDeclarationMessageModel.getAwbSerialNumber());
         cmdLarinfo.add(cmdlar);
      } else {
         throw MessageParsingExceptionFactory.createMessageParsingException("EXEMPTION CODE IS NOT VALID. ");
      }
   }

   public void parseDeliverUndeliverInfoDetails(String[] msgdata,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      String deliveryIndicator = msgdata[0].substring(4);
      if (!StringUtils.isEmpty(deliveryIndicator)) {
         customCargoManifestDeclarationMessageModel.setDeliveredOrUndeliveredIndicator(deliveryIndicator);
      } else {
         throw MessageParsingExceptionFactory
               .createMessageParsingException("DELIVERED OR UNDELIVERED INDICATOR  IS NOT VALID. ");
      }
   }

   public void parseSenderInfo(String[] msgdata, int strNo,
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws MessageParsingException {
      try {
         if (strNo == 0) {
            String senderInfo = msgdata[0].substring(4);
            String senderName = senderInfo.substring(0, senderInfo.indexOf(TextMessageConstants.SLANTCHAR));
            String senderInfoWithoutSenderName = senderInfo.replace(senderName + '/', "");
            String senderCompany = senderInfoWithoutSenderName.substring(0);
            if (senderName.length() <= 30) {
               customCargoManifestDeclarationMessageModel.setSenderName(senderName);
            }
            if (senderCompany.length() <= 30) {
               customCargoManifestDeclarationMessageModel.setSendrCompany(senderCompany);
            }
         } else {
            String senderInfoWithoutSenderNameAndCompany = msgdata[0].substring(4);
            String agentCrOrIaNumber = senderInfoWithoutSenderNameAndCompany.substring(0,
                  senderInfoWithoutSenderNameAndCompany.indexOf((TextMessageConstants.SLANTCHAR)));
            String senderInfoWithoutSenderCrOrIaInfo = senderInfoWithoutSenderNameAndCompany
                  .replace(agentCrOrIaNumber + '/', "");
            String cargoAgentCode = senderInfoWithoutSenderCrOrIaInfo.substring(0,
                  senderInfoWithoutSenderCrOrIaInfo.indexOf((TextMessageConstants.SLANTCHAR)));
            String cargoAgentReference = senderInfoWithoutSenderNameAndCompany
                  .substring(senderInfoWithoutSenderNameAndCompany.lastIndexOf((TextMessageConstants.SLANTCHAR) + 1));
            if (agentCrOrIaNumber.length() <= 20) {
               customCargoManifestDeclarationMessageModel.setAgentCrOrIaNumber(agentCrOrIaNumber);
            }
            if (cargoAgentCode.length() <= 6) {
               customCargoManifestDeclarationMessageModel.setCargoAgentCode(cargoAgentCode);
            }
            if (cargoAgentReference.length() <= 7) {
               customCargoManifestDeclarationMessageModel.setCargoAgentReference(cargoAgentReference);
            }
         }
      } catch (Exception e) {
         throw MessageParsingExceptionFactory.createMessageParsingException("SENDER INFORMATION IS NOT VALID. ",
               e.getMessage());
      }
   }

   public void propogateException(MessageParsingException e) throws MessageParsingException {
      StringBuilder str = new StringBuilder();
      for (ParserError t : e.getParserError()) {
         str.append(t.getDescription());
      }
      throw MessageParsingExceptionFactory.createMessageParsingException(str.toString());
   }

}