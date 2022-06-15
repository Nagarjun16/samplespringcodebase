/**
 * DCSFMErrorMessage.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          22 JAN, 2019   NIIT      -
 */
package com.ngen.cosys.altea.fm.enums;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This Enum used for DCS FM Error Message
 * 
 * @author NIIT Technologies Ltd
 *
 */
public enum DCSFMErrorMessage {

   _20809(DCSFMErrorConstants.CODE_20809, DCSFMErrorConstants.MESSAGE_20809, DCSFMErrorConstants.DESCRIPTION_20809), //
   _20007(DCSFMErrorConstants.CODE_20007, DCSFMErrorConstants.MESSAGE_20007, DCSFMErrorConstants.DESCRIPTION_20007), //
   _20810(DCSFMErrorConstants.CODE_20810, DCSFMErrorConstants.MESSAGE_20810, DCSFMErrorConstants.DESCRIPTION_20810), //
   _26611(DCSFMErrorConstants.CODE_26611, DCSFMErrorConstants.MESSAGE_26611, DCSFMErrorConstants.DESCRIPTION_26611), //
   _26674(DCSFMErrorConstants.CODE_26674, DCSFMErrorConstants.MESSAGE_26674, DCSFMErrorConstants.DESCRIPTION_26674), //
   _15136(DCSFMErrorConstants.CODE_15136, DCSFMErrorConstants.MESSAGE_15136, DCSFMErrorConstants.DESCRIPTION_15136), //
   _26699(DCSFMErrorConstants.CODE_26699, DCSFMErrorConstants.MESSAGE_26699, DCSFMErrorConstants.DESCRIPTION_26699), //
   _26700(DCSFMErrorConstants.CODE_26700, DCSFMErrorConstants.MESSAGE_26700, DCSFMErrorConstants.DESCRIPTION_26700), //
   _20811(DCSFMErrorConstants.CODE_20811, DCSFMErrorConstants.MESSAGE_20811, DCSFMErrorConstants.DESCRIPTION_20811), //
   _26698(DCSFMErrorConstants.CODE_26698, DCSFMErrorConstants.MESSAGE_26698, DCSFMErrorConstants.DESCRIPTION_26698), //
   _20812(DCSFMErrorConstants.CODE_20812, DCSFMErrorConstants.MESSAGE_20812, DCSFMErrorConstants.DESCRIPTION_20812), //
   _26668(DCSFMErrorConstants.CODE_26668, DCSFMErrorConstants.MESSAGE_26668, DCSFMErrorConstants.DESCRIPTION_26668), //
   _26669(DCSFMErrorConstants.CODE_26669, DCSFMErrorConstants.MESSAGE_26669, DCSFMErrorConstants.DESCRIPTION_26669), //
   _26670(DCSFMErrorConstants.CODE_26670, DCSFMErrorConstants.MESSAGE_26670, DCSFMErrorConstants.DESCRIPTION_26670), //
   _26671(DCSFMErrorConstants.CODE_26671, DCSFMErrorConstants.MESSAGE_26671, DCSFMErrorConstants.DESCRIPTION_26671), //
   _26672(DCSFMErrorConstants.CODE_26672, DCSFMErrorConstants.MESSAGE_26672, DCSFMErrorConstants.DESCRIPTION_26672), //
   _26695(DCSFMErrorConstants.CODE_26695, DCSFMErrorConstants.MESSAGE_26695, DCSFMErrorConstants.DESCRIPTION_26695), //
   _15262(DCSFMErrorConstants.CODE_15262, DCSFMErrorConstants.MESSAGE_15262, DCSFMErrorConstants.DESCRIPTION_15262), //
   _26704(DCSFMErrorConstants.CODE_26704, DCSFMErrorConstants.MESSAGE_26704, DCSFMErrorConstants.DESCRIPTION_26704), //
   _26696(DCSFMErrorConstants.CODE_26696, DCSFMErrorConstants.MESSAGE_26696, DCSFMErrorConstants.DESCRIPTION_26696), //
   _26697(DCSFMErrorConstants.CODE_26697, DCSFMErrorConstants.MESSAGE_26697, DCSFMErrorConstants.DESCRIPTION_26697), //
   _15267(DCSFMErrorConstants.CODE_15267, DCSFMErrorConstants.MESSAGE_15267, DCSFMErrorConstants.DESCRIPTION_15267), //
   _15116(DCSFMErrorConstants.CODE_15116, DCSFMErrorConstants.MESSAGE_15116, DCSFMErrorConstants.DESCRIPTION_15116), //
   _26749(DCSFMErrorConstants.CODE_26749, DCSFMErrorConstants.MESSAGE_26749, DCSFMErrorConstants.DESCRIPTION_26749), //
   _20797(DCSFMErrorConstants.CODE_20797, DCSFMErrorConstants.MESSAGE_20797, DCSFMErrorConstants.DESCRIPTION_20797), //
   _20089(DCSFMErrorConstants.CODE_20089, DCSFMErrorConstants.MESSAGE_20089, DCSFMErrorConstants.DESCRIPTION_20089), //
   _26684(DCSFMErrorConstants.CODE_26684, DCSFMErrorConstants.MESSAGE_26684, DCSFMErrorConstants.DESCRIPTION_26684), //
   _26735(DCSFMErrorConstants.CODE_26735, DCSFMErrorConstants.MESSAGE_26735, DCSFMErrorConstants.DESCRIPTION_26735), //
   _26733(DCSFMErrorConstants.CODE_26733, DCSFMErrorConstants.MESSAGE_26733, DCSFMErrorConstants.DESCRIPTION_26733), //
   _26734(DCSFMErrorConstants.CODE_26734, DCSFMErrorConstants.MESSAGE_26734, DCSFMErrorConstants.DESCRIPTION_26734), //
   _26732(DCSFMErrorConstants.CODE_26732, DCSFMErrorConstants.MESSAGE_26732, DCSFMErrorConstants.DESCRIPTION_26732), //
   _26731(DCSFMErrorConstants.CODE_26731, DCSFMErrorConstants.MESSAGE_26731, DCSFMErrorConstants.DESCRIPTION_26731), //
   _20816(DCSFMErrorConstants.CODE_20816, DCSFMErrorConstants.MESSAGE_20816, DCSFMErrorConstants.DESCRIPTION_20816);
   
   private String code;
   private String message;
   private String description;
   
   /**
    * Initialize
    * 
    * @param code
    *           Error Code
    * @param message
    *           Error Message
    * @param description
    *           Error Description
    */
   private DCSFMErrorMessage(String code, String message, String description) {
      this.code = code;
      this.message = message;
      this.description = description;
   }
   
   /**
    * Initialize
    * 
    * @param code
    *           Error Code
    * @param message
    *           Error Message
    */
   private DCSFMErrorMessage(String code, String message) {
      this.code = code;
      this.message = message;
   }
   
   /**
    * Error code
    * 
    * @return code
    */
   @JsonValue
   public String code() {
      return this.code;
   }
   
   /**
    * Error Message
    * 
    * @return message
    */
   public String message() {
      return this.message;
   }
   
   /**
    * Error Description
    * 
    * @return description
    */
   public String description() {
      return this.description;
   }
   
   /**
    * Gets Enum of value
    * 
    * @param code
    *           code
    * @return DCSFMErrorMessage
    */
   public static DCSFMErrorMessage enumOf(String code) {
      DCSFMErrorMessage[] errorMessages = values();
      //
      for (DCSFMErrorMessage errorMessage : errorMessages) {
         if (Objects.equals(errorMessage.code, code)) {
            return errorMessage;
         }
      }
      //
      return null;
   }
   
   public class DCSFMErrorConstants {

      private DCSFMErrorConstants() {}
      
      // Error Code
      public static final String CODE_20809 = "20809";
      public static final String CODE_20007 = "20007";
      public static final String CODE_20810 = "20810";
      public static final String CODE_26611 = "26611";
      public static final String CODE_26674 = "26674";
      public static final String CODE_15136 = "15136";
      public static final String CODE_26699 = "26699";
      public static final String CODE_26700 = "26700";
      public static final String CODE_20811 = "20811";
      public static final String CODE_26698 = "26698";
      public static final String CODE_20812 = "20812";
      public static final String CODE_26668 = "26668";
      public static final String CODE_26669 = "26669";
      public static final String CODE_26670 = "26670";
      public static final String CODE_26671 = "26671";
      public static final String CODE_26672 = "26672";
      public static final String CODE_26695 = "26695";
      public static final String CODE_15262 = "15262";
      public static final String CODE_26704 = "26704";
      public static final String CODE_26696 = "26696";
      public static final String CODE_26697 = "26697";
      public static final String CODE_15267 = "15267";
      public static final String CODE_15116 = "15116";
      public static final String CODE_26749 = "26749";
      public static final String CODE_20797 = "20797";
      public static final String CODE_20089 = "20089";
      public static final String CODE_26684 = "26684";
      public static final String CODE_26735 = "26735";
      public static final String CODE_26733 = "26733";
      public static final String CODE_26734 = "26734";
      public static final String CODE_26732 = "26732";
      public static final String CODE_26731 = "26731";
      public static final String CODE_20816 = "20816";
      
      // Error Message
      public static final String MESSAGE_20809 = "Rejected due to flight not found";
      public static final String MESSAGE_20007 = "Not Authorised: <Permission Name>";
      public static final String MESSAGE_20810 = "Rejected due to invalid flight status <<flight status>>";
      public static final String MESSAGE_26611 = "Rejected due to invalid ramp status <<Ramp Status>>";
      public static final String MESSAGE_26674 = "Rejected because origin port for deadload or load item does not match with the station of loading";
      public static final String MESSAGE_15136 = "Rejected because deadload items exist with a destination not in the routing: <List of Invalid Destinations>";
      public static final String MESSAGE_26699 = "Rejected because one deadload destination is beyond its ULD destination";
      public static final String MESSAGE_26700 = "Rejected because one deadload destination does not match with its bulk destination";
      public static final String MESSAGE_20811 = "Rejected because ULD Type <List ULD Types> does not fit on the aircraft";
      public static final String MESSAGE_26698 = "Rejected because baggage commodity cannot be created by an external cargo system";
      public static final String MESSAGE_20812 = "Rejected because maximum weight exceeded for <ULD serial number>/ULD ID <Unique ID>";
      public static final String MESSAGE_26668 = "Rejected because ULD <<ULD serial number>> has a physical link with a ULD that does not exist";
      public static final String MESSAGE_26669 = "Rejected because ULD <ULD serial number> has a physical link with a ULD that has a link to the same side with a different ULD";
      public static final String MESSAGE_26670 = "Rejected because ULD <ULD serial number> has a physical link with itself";
      public static final String MESSAGE_26671 = "Rejected because ULD <ULD serial number> has more than 2 physical links";
      public static final String MESSAGE_26672 = "Rejected because ULD <ULD serial number> has a physical link on the Short Side and on the Long Side";
      public static final String MESSAGE_26695 = "Rejected because <ULD serial number or Type> ID <Unique ID> cannot have a commodity of type ‘Empty’ with weight greater than zero";
      public static final String MESSAGE_15262 = "Rejected as invalid Deadload weight for <ULD serial number or Type or Barrow> ID <Unique ID>";
      public static final String MESSAGE_26704 = "Rejected because <BLK or Barrow> <Unique ID> cannot have a commodity of type ‘Empty’";
      public static final String MESSAGE_26696 = "Rejected because <Barrow> cannot have a tare weight";
      public static final String MESSAGE_26697 = "Rejected because BLK ID <Unique ID> cannot have a tare weight";
      public static final String MESSAGE_15267 = "Rejected due to invalid DG/SL <List of invalid DG/SL codes>";
      public static final String MESSAGE_15116 = "Rejected because net weight does not cover DG/SL weight for <List of: ULD serial number(or ULD type),Barrow ID and/or Bulk ID>";
      public static final String MESSAGE_26749 = "Rejected because Dry Operating Weight indicator is received for a commodity item that is not equipment or crew baggage";
      public static final String MESSAGE_20797 = "Description is missing for an equipment commodity";
      public static final String MESSAGE_20089 = "Rejected because Barrow ID is invalid. Barrow id should be less than or equal to 7 characters";
      public static final String MESSAGE_26684 = "Rejected because Deadload number <Deadload number> does not match with the load ID <Unique reference identifier>";
      public static final String MESSAGE_26735 = "Rejected because partial DG data received";
      public static final String MESSAGE_26733 = "Rejected because no DG data received";
      public static final String MESSAGE_26734 = "Rejected because no match found for DG data received";
      public static final String MESSAGE_26732 = "Rejected due to no UN/ID Number and/or no IMP Code received";
      public static final String MESSAGE_26731 = "Rejected because  UN/ID is incorrect";
      public static final String MESSAGE_20816 = "Pending action from FM due to <internal or external> cargo status <cargo status>";
      
      // Description
      public static final String DESCRIPTION_20809 = "";
      public static final String DESCRIPTION_20007 = "";
      public static final String DESCRIPTION_20810 = "";
      public static final String DESCRIPTION_26611 = "";
      public static final String DESCRIPTION_26674 = "";
      public static final String DESCRIPTION_15136 = "";
      public static final String DESCRIPTION_26699 = "";
      public static final String DESCRIPTION_26700 = "";
      public static final String DESCRIPTION_20811 = "";
      public static final String DESCRIPTION_26698 = "";
      public static final String DESCRIPTION_20812 = "";
      public static final String DESCRIPTION_26668 = "";
      public static final String DESCRIPTION_26669 = "";
      public static final String DESCRIPTION_26670 = "";
      public static final String DESCRIPTION_26671 = "";
      public static final String DESCRIPTION_26672 = "";
      public static final String DESCRIPTION_26695 = "";
      public static final String DESCRIPTION_15262 = "";
      public static final String DESCRIPTION_26704 = "";
      public static final String DESCRIPTION_26696 = "";
      public static final String DESCRIPTION_26697 = "";
      public static final String DESCRIPTION_15267 = "";
      public static final String DESCRIPTION_15116 = "";
      public static final String DESCRIPTION_26749 = "";
      public static final String DESCRIPTION_20797 = "";
      public static final String DESCRIPTION_20089 = "";
      public static final String DESCRIPTION_26684 = "";
      public static final String DESCRIPTION_26735 = "Only if the operating carrier has FM_DGSL_UNID customisation enabled";
      public static final String DESCRIPTION_26733 = "Only if the operating carrier has FM_DGSL_UNID customisation enabled";
      public static final String DESCRIPTION_26734 = "Only if the operating carrier has FM_DGSL_UNID customisation enabled";
      public static final String DESCRIPTION_26732 = "Only if the operating carrier has FM_DGSL_UNID customisation enabled";
      public static final String DESCRIPTION_26731 = "Only if the operating carrier has FM_DGSL_UNID customisation enabled";
      public static final String DESCRIPTION_20816 = "Pending action from FM due to flight status LL";
      
   }
   
}
