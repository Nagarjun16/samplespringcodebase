package com.ngen.cosys.application.service;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.application.dao.ManifestReconcillationStatementDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.enums.TextMessageConstants;
import com.ngen.cosys.model.HwbModel;
import com.ngen.cosys.model.ManifesrReconciliationStatementModel;
import com.ngen.cosys.model.MasterAwbDetailModel;

@Service
public class ProduceManifestReconcillationStatementMessageServiceImpl
      implements ProduceManifestReconcillationStatementMessageService {

   @Autowired
   ManifestReconcillationStatementDAO manifestReconcillationStatementDAO;

   CharSequence crlfCharSequence = new String(new char[] { 0x0d, 0x0a });

   private static final byte[] LINEFEED_SITA = { 0x0D, 0x0A };

   @Override
   public String buildManifestReconcillationStatementMesaage(
         ManifesrReconciliationStatementModel manifesrReconciliationStatementModel) throws CustomException, Exception {
      // GET THE VALUES FROM THE DATABASE
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream doutt = new DataOutputStream(bout);
      constructHeader(doutt);
      constructMRSMessage(appendBlankSpaceAndZero(manifesrReconciliationStatementModel), doutt);
      doutt.writeBytes("LAST");

      return new String(bout.toByteArray());
   }

   public void constructMRSMessage(ManifesrReconciliationStatementModel reconciliationStatementModel,
         DataOutputStream dout) throws IOException {
      constructHeader(reconciliationStatementModel, dout);
      constructFlightInfo(reconciliationStatementModel, dout);
      if (!CollectionUtils.isEmpty(reconciliationStatementModel.getMAwbDetails())) {
         for (MasterAwbDetailModel mwb : reconciliationStatementModel.getMAwbDetails()) {
            constructMasterAwbDetail(mwb, dout);
            constructMasterAwbDetailsTwo(mwb, dout);
         }
      }
      if (!CollectionUtils.isEmpty(reconciliationStatementModel.getMAwbDetails())) {
         for (HwbModel hwb : reconciliationStatementModel.getHwbModel()) {
            constructHouseWayBill(hwb, dout);
            constructPermitDetails(hwb, dout);
         }
      }
   }

   private void constructHeader(ManifesrReconciliationStatementModel reconciliationStatementModel,
         DataOutputStream dout) throws IOException {
      dout.writeBytes(("MRS"));
      dout.writeBytes(TextMessageConstants.SLANT.getValue());
      dout.writeBytes(reconciliationStatementModel.getMrsSequenceNo().toString());
      dout.write(LINEFEED_SITA);
   }

   private void constructFlightInfo(ManifesrReconciliationStatementModel reconciliationStatementModel,
         DataOutputStream dout) throws IOException {
      dout.writeBytes("01");
      dout.writeBytes(TextMessageConstants.SLANT.getValue());
      dout.writeBytes("00");
      dout.writeBytes("S");
      if (reconciliationStatementModel.getTypeofShipment().equalsIgnoreCase("I")) {
         dout.writeBytes("I");
      } else {
         dout.writeBytes("O");
      }
      if (reconciliationStatementModel.getFlightCancelFlag().equalsIgnoreCase("Y")) {
         dout.writeBytes("X");
      } else if (reconciliationStatementModel.getMAwbDetails().isEmpty()) {
         dout.writeBytes("N");
      } else {
         dout.writeBytes("C");
      }
      dout.writeBytes(reconciliationStatementModel.getFlightNumber());
      String day = null;
      if (reconciliationStatementModel.getScheduledFlightDate().getDayOfMonth() < 10) {
         day = "0" + reconciliationStatementModel.getScheduledFlightDate().getDayOfMonth();
      } else {
         day = reconciliationStatementModel.getScheduledFlightDate().getDayOfMonth() + "";
      }
      dout.writeBytes(
            day + reconciliationStatementModel.getScheduledFlightDate().getMonth().toString().subSequence(0, 3));
      dout.writeBytes(reconciliationStatementModel.getPointOfLading());
      dout.writeBytes(reconciliationStatementModel.getPointOfUnlading());
      dout.writeBytes(System.lineSeparator());
      if (reconciliationStatementModel.getMAwbDetails().isEmpty()) {
         dout.writeBytes("M");
      } else {
         // Set the default status code
         reconciliationStatementModel.setMatchStatus("M");

         // If one of the shipment status is not equal to NOT OK then set to U
         if (!CollectionUtils.isEmpty(reconciliationStatementModel.getMAwbDetails())) {
            for (MasterAwbDetailModel t : reconciliationStatementModel.getMAwbDetails()) {
               if (!"OK".equalsIgnoreCase(t.getDisparityIndicator())) {
                  reconciliationStatementModel.setMatchStatus("U");
                  break;
               }
            }
         }

         dout.writeBytes(reconciliationStatementModel.getMatchStatus());

      }
      dout.writeBytes("                                                       ");
      dout.write(LINEFEED_SITA);

   }

   private void constructMasterAwbDetail(MasterAwbDetailModel reconciliationStatementModel, DataOutputStream dout)
         throws IOException {
      dout.writeBytes("1A");
      String awbPrefix = reconciliationStatementModel.getMasterAWBNumber().substring(0, 3);
      String awbsuffix = reconciliationStatementModel.getMasterAWBNumber().substring(3);
      reconciliationStatementModel.setMasterAWBNumber(awbPrefix + "-" + awbsuffix);
      dout.writeBytes(appendRightBlankSpacesToString(reconciliationStatementModel.getMasterAWBNumber(), 17));
      if (null != reconciliationStatementModel.getAgentsCROrIANumber()) {
         dout.writeBytes(reconciliationStatementModel.getAgentsCROrIANumber());
      } else {
         dout.writeBytes(appendRightBlankSpacesToString(" ", 20));
      }
      if (null != reconciliationStatementModel.getCargoAgentCode()) {

         dout.writeBytes(reconciliationStatementModel.getCargoAgentCode());
      } else {
         dout.writeBytes(appendRightBlankSpacesToString(" ", 6));
      }
      dout.writeBytes(reconciliationStatementModel.getAirportCityCodeofOrigin());
      dout.writeBytes(reconciliationStatementModel.getAirportCityCodeofDestination());
      dout.writeBytes(reconciliationStatementModel.getDisparityIndicator());
      dout.writeBytes(System.lineSeparator());

      dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getOriginalNumberofPackages().intValue(), 5));
      dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getNumberofPackagesinFlight().intValue(), 5));
      if (null != reconciliationStatementModel.getPreviousFlightNumber()) {
         dout.writeBytes(reconciliationStatementModel.getPreviousFlightNumber().toString());
      } else {
         appendRightBlankSpacesToString(" ", 8);
      }
      if (null != reconciliationStatementModel.getPreviousFlightNumber()) {
         dout.writeBytes(reconciliationStatementModel.getPreviousFlightDate().getDayOfMonth() + ""
               + reconciliationStatementModel.getPreviousFlightDate().getMonth());
      } else {
         appendRightBlankSpacesToString(" ", 5);
      }
      dout.writeBytes("            ");
      dout.write(LINEFEED_SITA);
   }

   private void constructMasterAwbDetailsTwo(MasterAwbDetailModel reconciliationStatementModel, DataOutputStream dout)
         throws IOException {
      StringBuilder builder = new StringBuilder();
      dout.writeBytes("1B");
      dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getWeight().intValue(), 8));
      dout.writeBytes(reconciliationStatementModel.getExemptionIndicator());
      dout.writeBytes(reconciliationStatementModel.getDescriptionofGoods());
      if (null != reconciliationStatementModel.getOriginalNoofHAWB()) {
         dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getOriginalNoofHAWB().intValue(), 5));
      } else {
         dout.writeBytes(lpadZeros2Integer(0, 5));

      }
      if (null != reconciliationStatementModel.getNumberofHAWBsubmitted()) {
         dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getNumberofHAWBsubmitted().intValue(), 5));
      } else {
         dout.writeBytes(lpadZeros2Integer(0, 5));

      }
      builder.append(crlfCharSequence);
      if (null != reconciliationStatementModel.getNoOfPiecesinCMDmessage()) {
         dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getNoOfPiecesinCMDmessage().intValue(), 5));
      } else {
         dout.writeBytes(lpadZeros2Integer(0, 5));

      }
      dout.writeBytes("                                     ");
      dout.write(LINEFEED_SITA);
   }
   private void constructMasterAwbDetailsCne(MasterAwbDetailModel reconciliationStatementModel, DataOutputStream dout)
         throws IOException {
      StringBuilder builder = new StringBuilder();
      dout.writeBytes("1C");
      dout.writeBytes(reconciliationStatementModel.getShipperOrConsigneeNameOne());
      dout.write(LINEFEED_SITA);
      builder.append(crlfCharSequence);
      dout.writeBytes(reconciliationStatementModel.getShipperOrConsigneeAddressOne()); 
      dout.writeBytes("                                     ");
      dout.write(LINEFEED_SITA);
   }
   private void constructMasterAwbDetailsShp(MasterAwbDetailModel reconciliationStatementModel, DataOutputStream dout)
         throws IOException {
      StringBuilder builder = new StringBuilder();
      dout.writeBytes("1D");
      dout.writeBytes(reconciliationStatementModel.getShipperOrConsigneeNameTwo());
      dout.write(LINEFEED_SITA);
      builder.append(crlfCharSequence);
      dout.writeBytes(reconciliationStatementModel.getShipperOrConsigneeAddressTwo()); 
      dout.writeBytes("                                     ");
      dout.write(LINEFEED_SITA);
   }
   private void constructHouseWayBill(HwbModel reconciliationStatementModel, DataOutputStream dout) throws IOException {
      dout.writeBytes("2A");
      dout.writeBytes(reconciliationStatementModel.getHawbNumber());
      dout.writeBytes(lpadZeros2Integer(reconciliationStatementModel.getNumberOfPackages().intValue(), 5));
      dout.writeBytes(reconciliationStatementModel.getWeight().toString());
      dout.writeBytes(reconciliationStatementModel.getMatchStatusOfHouseWayBill());
      dout.writeBytes(reconciliationStatementModel.getExemptionCode());
      dout.write(LINEFEED_SITA);
      dout.writeBytes(reconciliationStatementModel.getDescriptionOfGoods());
      dout.write(LINEFEED_SITA);
      dout.writeBytes("                        ");
      dout.write(LINEFEED_SITA);
   }

   private void constructPermitDetails(HwbModel reconciliationStatementModel, DataOutputStream dout)
         throws IOException {
      dout.writeBytes("3A");
      dout.writeBytes(reconciliationStatementModel.getPermitNumber());
      dout.writeBytes("        ");
      dout.write(LINEFEED_SITA);
   }

   public ManifesrReconciliationStatementModel appendBlankSpaceAndZero(
         ManifesrReconciliationStatementModel manifesrReconciliationStatementModel) {
      manifesrReconciliationStatementModel
            .setFlightNumber(appendRightBlankSpacesToString(manifesrReconciliationStatementModel.getFlightNumber(), 8));
      if (null != manifesrReconciliationStatementModel.getMAwbDetails()) {
         for (MasterAwbDetailModel mwb : manifesrReconciliationStatementModel.getMAwbDetails()) {
            mwb.setAgentsCROrIANumber(appendRightBlankSpacesToString(mwb.getAgentsCROrIANumber(), 20));
            mwb.setCargoAgentCode(appendRightBlankSpacesToString(mwb.getCargoAgentCode(), 6));
            mwb.setExemptionIndicator(appendRightBlankSpacesToString(mwb.getExemptionIndicator(), 2));
            mwb.setDescriptionofGoods(appendRightBlankSpacesToString(mwb.getDescriptionofGoods(), 20));
            mwb.setShipperOrConsigneeNameTwo(appendRightBlankSpacesToString(mwb.getShipperOrConsigneeNameTwo(), 30));
            mwb.setShipperOrConsigneeNameOne(appendRightBlankSpacesToString(mwb.getShipperOrConsigneeNameTwo(), 30));
            mwb.setShipperOrConsigneeAddressOne(
                  appendRightBlankSpacesToString(mwb.getShipperOrConsigneeAddressOne(), 35));
            mwb.setShipperOrConsigneeAddressTwo(
                  appendRightBlankSpacesToString(mwb.getShipperOrConsigneeAddressTwo(), 35));
            mwb.setWeight(new BigDecimal(lpadZeros2Integer(mwb.getWeight().intValue(), 5)));
         }
      }
      if (null != manifesrReconciliationStatementModel.getHwbModel()) {
         for (HwbModel hwb : manifesrReconciliationStatementModel.getHwbModel()) {
            if (!ObjectUtils.isEmpty(hwb)) {
               hwb.setHawbNumber(appendRightBlankSpacesToString(hwb.getHawbNumber(), 17));
               hwb.setMatchStatusOfHouseWayBill(appendRightBlankSpacesToString(hwb.getMatchStatusOfHouseWayBill(), 2));
               hwb.setExemptionCode(appendRightBlankSpacesToString(hwb.getExemptionCode(), 2));
               hwb.setDescriptionOfGoods(appendRightBlankSpacesToString(hwb.getDescriptionOfGoods(), 20));
               hwb.setPermitNumber(appendRightBlankSpacesToString(hwb.getPermitNumber(), 11));
               hwb.setWeight(new BigDecimal(lpadZeros2Integer(hwb.getWeight().intValue(), 5)));
            }
         }
      }
      return manifesrReconciliationStatementModel;
   }

   public String appendRightBlankSpacesToString(String stringToAppendBlankspace, int length) {
      StringBuilder string = new StringBuilder();
      if (stringToAppendBlankspace != null) {
         string = new StringBuilder(stringToAppendBlankspace);
         int spaces = length - string.length();
         for (int i = 0; i < spaces; i++) {
            string = string.append(" ");
         }
      } else {
         for (int i = 0; i < length; i++) {
            string = string.append(" ");
         }
      }
      return string.toString();
   }

   public static BigInteger lpadspace2Integer(int data, int length) {
      StringBuilder sData = new StringBuilder(data);
      for (int lpad = 0; lpad < length; lpad++) {
         if (sData.length() != length) {
            sData = sData.append(" ");
         }
      }
      return new BigInteger(sData.toString());
   }

   public static String lpadZeros2Integer(int data, int length) {
      StringBuilder string = new StringBuilder(data);
      for (int i = 0; i < length; i++) {
         string.append("0");
      }
      DecimalFormat df = new DecimalFormat(string.toString());
      return df.format(data);
   }

   private void constructHeader(DataOutputStream dout) throws CustomException, IOException {
      dout.writeBytes("QD");
      dout.writeBytes(" ");
      dout.writeBytes(manifestReconcillationStatementDAO.getMrsDestinationAddress().toString());
      dout.write(LINEFEED_SITA);
      dout.writeBytes(".");
      dout.writeBytes(manifestReconcillationStatementDAO.getMrsOriginatorAddress().toString());
      dout.writeBytes(" ");
      dout.writeBytes(getStringFromDate(new Date(), "ddHHmm"));
      dout.writeBytes(" ");
      dout.writeBytes(manifestReconcillationStatementDAO.getMrsPimaAddress());
      dout.write(LINEFEED_SITA);
   }

   public static String getStringFromDate(Date date, String dateFormat) {
      String sDate = null;
      SimpleDateFormat sdf = null;
      try {
         sdf = new SimpleDateFormat(dateFormat);
         sDate = sdf.format(date);
      } catch (Exception e) {
         // Do nothing
      }
      return sDate;
   }

   @Override
   public void updateMrsSentDateToCustomsFlight(ManifesrReconciliationStatementModel mrs) throws CustomException {
      manifestReconcillationStatementDAO.updateMrsSentDateToCustomsFlight(mrs);
   }

}
