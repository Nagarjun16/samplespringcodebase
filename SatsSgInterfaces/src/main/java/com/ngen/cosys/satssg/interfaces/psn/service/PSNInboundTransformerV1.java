package com.ngen.cosys.satssg.interfaces.psn.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.satssg.interfaces.psn.model.AirwayBillIdentification;
import com.ngen.cosys.satssg.interfaces.psn.model.PsnMessageModel;
import com.ngen.cosys.satssg.interfaces.util.ExtractMessageDataParser;
import com.ngen.cosys.satssg.interfaces.util.MessageParserHelper;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingException;
import com.ngen.cosys.satssginterfaces.message.exception.MessageParsingExceptionFactory;

public class PSNInboundTransformerV1 {

   private List<AirwayBillIdentification> identificationsList;

   public PSNInboundTransformerV1() {
      this.identificationsList = new ArrayList<>();
   }

   public void lookForMsgsToProcess(List<String> finalMsgList, int msgloop, PsnMessageModel psnMessageModel)
         throws MessageParsingException {
      for (msgloop = 2; msgloop < finalMsgList.size(); msgloop++) {
         if (msgloop == finalMsgList.size()-1 && (new String(new char[] { 0x03 })).equals(finalMsgList.get(msgloop))) {
            continue;
         }
         if (finalMsgList.get(msgloop++).indexOf(TextMessageConstants.PSN_IDENTIFIER.getValue()) == -1) {
            throw MessageParsingExceptionFactory.createMessageParsingException("MESSAGE FORMAT IS NOT CORRECT. ");
         } else {
            msgloop = psnNewLineParser(finalMsgList, msgloop, psnMessageModel,
                  TextMessageConstants.PSN_IDENTIFIER.getValue());
         }
      }
   }

   private int psnNewLineParser(List<String> finalMsgList, int msgloop, PsnMessageModel psnMessageModel,
         String psnIdentifier) throws MessageParsingException {

      AirwayBillIdentification airwayidentification = null;

      int pos = 0;
      String[] msgData = new String[30];
      int psn = msgloop;
      for (; psn < finalMsgList.size(); psn++) {

         if (finalMsgList.get(psn).indexOf("CSN") == 0 || finalMsgList.get(psn).indexOf("ASN") == 0) {
            break;
         }
         airwayidentification = new AirwayBillIdentification();
         msgData[0] = finalMsgList.get(msgloop);
         char[] msgChars = new char[msgData[0].length()];
         msgData[0].getChars(0, msgChars.length, msgChars, 0);

         String awbPrefix = ExtractMessageDataParser.getFixLenDigit(msgChars, 3, pos);
         if (awbPrefix == null) {
            throw MessageParsingExceptionFactory.createMessageParsingException("day should be in 'M3' format",
                  String.valueOf(msgChars));
         } else {
            pos += awbPrefix.length();
            airwayidentification.setAwbPrefix(awbPrefix);
         }
         if (msgChars[pos] != TextMessageConstants.HIPHENCHAR) {
            throw MessageParsingExceptionFactory.createMessageParsingException("Seperator HIPHEN is required");

         }
         pos++;
         String awbNo = ExtractMessageDataParser.getFixLenDigit(msgChars, 8, pos);
         if (awbNo == null) {
            throw MessageParsingExceptionFactory.createMessageParsingException("AWB suffix should be in 'n[8]' format",
                  String.valueOf(msgChars));
         } else {
            airwayidentification.setAwbNo(awbNo);
            pos += awbNo.length();
         }
         airwayidentification.setShipmentNumber(awbPrefix.concat(awbNo));
         if (pos < msgChars.length - 1) {
            if (msgChars[pos++] == TextMessageConstants.HIPHENCHAR) {

               String hwbNo = ExtractMessageDataParser.getVarLenDigit(msgChars, 12, pos);
               if (hwbNo != null) {
                  airwayidentification.setHwbNo(hwbNo);
                  pos += awbNo.length();
               }
            }
         }

         identificationsList.add(airwayidentification);
      }
      psnMessageModel.setAirwayBillIdentifications(identificationsList);
      msgloop++;
      int pos1 = 0;
      String[] msgData1 = new String[30];
      msgData1[0] = finalMsgList.get(msgloop);
      char[] msgChars1 = new char[msgData1[0].length()];
      msgData1[0].getChars(0, msgChars1.length, msgChars1, 0);

      if (finalMsgList.get(msgloop).indexOf("CSN") == -1 && finalMsgList.get(msgloop).indexOf("ASN") == -1) {
         throw MessageParsingExceptionFactory.createMessageParsingException("MESSAGE FORMAT IS NOT CORRECT. ",
               "CNE or ASN is required");
      }
      String identifier = ExtractMessageDataParser.getFixLenAlphabet(msgChars1, 3, pos1);
      psnMessageModel.setIdentifiers(identifier);
      pos1 = 3;
      if (msgChars1[pos1] != TextMessageConstants.SLANTCHAR) {
         throw MessageParsingExceptionFactory.createMessageParsingException("Seperator SLANT is required");
      }
      pos1++;

      String ack = ExtractMessageDataParser.getVarLenAlphaNumeric(msgChars1, 2, pos1, TextMessageConstants.SLANTCHAR);

      if (ack == null) {
         throw MessageParsingExceptionFactory.createMessageParsingException("ack Code should be in 'mm/a' format",
               String.valueOf(msgChars1));
      }

      if (ack.length() == 1) {
         psnMessageModel.setHldAckCode(ack);
         pos1 += ack.length();

         if (msgChars1[pos1] != TextMessageConstants.SLANTCHAR) {
            throw MessageParsingExceptionFactory.createMessageParsingException("Seperator SLANT is required");
         }
         pos1++;
         String ackDes = ExtractMessageDataParser.getVarLenAlphabet(msgChars1, 20, pos1,
               TextMessageConstants.SLANTCHAR);
         if (ackDes == null) {
            throw MessageParsingExceptionFactory.createMessageParsingException("Ack des  should be in 't[20]' format",
                  String.valueOf(msgChars1));
         } else {
            pos1 += ackDes.length();
            psnMessageModel.setAckDes(ackDes);
         }

      } else if (ack.length() == 2) {

         psnMessageModel.setAckCode(ack);
         pos1 += ack.length();

         if (msgChars1[pos1] != TextMessageConstants.HIPHENCHAR) {
            throw MessageParsingExceptionFactory.createMessageParsingException("Seperator HIPHEN is required");

         }
         pos1++;
         String no = ExtractMessageDataParser.getVarLenDigit(msgChars1, 5, pos1);
         if (no == null) {
            throw MessageParsingExceptionFactory
                  .createMessageParsingException("cust std no  should be in 'm[5]' format", String.valueOf(msgChars1));
         } else {
            pos1 += no.length();
            psnMessageModel.setNoOfPieces(no);
         }

         if (pos1 < msgChars1.length - 1) {
            if (msgChars1[pos1] != TextMessageConstants.SLANTCHAR) {
               throw MessageParsingExceptionFactory.createMessageParsingException("Seperator SLANT is required");
            }
            pos1++;
            String ipsnDat = ExtractMessageDataParser.getFixLenAlphaNumeric(msgChars1, 9, pos1);
            if (ipsnDat == null) {
               throw MessageParsingExceptionFactory.createMessageParsingException("ipsn  should be in 'm[9]' format",
                     String.valueOf(msgChars1));
            } else {
               for (int i = 0; i < ipsnDat.substring(0, 2).toCharArray().length; i++) {
                  if (!MessageParserHelper.isNumber(ipsnDat.toCharArray()[i])) {
                     throw MessageParsingExceptionFactory
                           .createMessageParsingException("MESSAGE FORMAT IS NOT CORRECT");
                  }
               }
               for (int i1 = 0; i1 < ipsnDat.substring(2, 5).toCharArray().length; i1++) {
                  if (!MessageParserHelper.isAlphabet(ipsnDat.substring(2, 5).toCharArray()[i1])) {
                     throw MessageParsingExceptionFactory
                           .createMessageParsingException("MESSAGE FORMAT IS NOT CORRECT");
                  }
               }
               for (int i = 0; i < ipsnDat.substring(5, 9).toCharArray().length; i++) {
                  if (!MessageParserHelper.isNumber(ipsnDat.substring(5, 9).toCharArray()[i])) {
                     throw MessageParsingExceptionFactory
                           .createMessageParsingException("MESSAGE FORMAT IS NOT CORRECT");
                  }
               }
               psnMessageModel.setTransDateTime(ipsnDat);
               pos1 += ipsnDat.length();
               if (!psnMessageModel.getAckCode().matches("[0-9][hH]")) {
                  psnMessageModel.setHldAckCode(null);
                  psnMessageModel.setAckDes(null);
               }
            }

         }
      }
      if (pos1 < msgChars1.length - 1) {

         if (psnMessageModel.getAckCode() != null) {
            pos1++;
            String entryType = ExtractMessageDataParser.getFixLenAlphaNumeric(msgChars1, 9, pos1);
            if (entryType != null) {
               psnMessageModel.setEntryType(entryType);
            }
            String entryNo = ExtractMessageDataParser.getVarLenAlphaNumeric(msgChars1, 15, pos1,
                  TextMessageConstants.SLANTCHAR);
            if (entryNo != null) {
               psnMessageModel.setEntryNo(entryNo);
            }

            if (pos1 < msgChars1.length - 1 && psnMessageModel.getAckCode().matches("[0-9][hH]")) {
               String remark = ExtractMessageDataParser.getVarLenAlphaNumeric(msgChars1, 15, pos1,
                     TextMessageConstants.SLANTCHAR);
               if (remark != null) {
                  psnMessageModel.setRemark(remark);
               }
            }
         }
      }
      return msgloop;
   }

}