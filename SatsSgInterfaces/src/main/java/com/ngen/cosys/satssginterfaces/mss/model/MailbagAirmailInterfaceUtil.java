/**
 * 
 * MailbagUtil.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          13 April, 2018   NIIT      -
 */
package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This is the Mailbag Utility class which takes care of Mailbag related formatting.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class MailbagAirmailInterfaceUtil  {

   private MailbagAirmailInterfaceUtil() {}

   public static MailbagAirmailInterfaceModel getMailDetailsSplit(String mailbagNumber) {
      MailbagAirmailInterfaceModel mbm = new MailbagAirmailInterfaceModel();
      mbm.setDispatchSeries(mailbagNumber.substring(0, 20));
      // Set Origin Office of Exchange information
      mbm.setOriginCountry(mailbagNumber.substring(0, 2));
      mbm.setOriginAirport(mailbagNumber.substring(2, 5));
      mbm.setOriginQualifier(mailbagNumber.substring(5, 6));
      // Set Destination Office of Exchange information
      mbm.setDestinationCountry(mailbagNumber.substring(6, 8));
      mbm.setDestinationAirport(mailbagNumber.substring(8, 11));
      mbm.setDestinationQualifier(mailbagNumber.substring(11, 12));
      // Set rest of the fields
      mbm.setCategory(mailbagNumber.substring(12, 13));
      mbm.setSubCategory(mailbagNumber.substring(13, 15));
      mbm.setYear(mailbagNumber.substring(15, 16));
      mbm.setDispatchNumber(mailbagNumber.substring(16, 20));
      mbm.setReceptableNumber(mailbagNumber.substring(20, 23));
      if(mailbagNumber.substring(23, 24).equals("1")) {
         mbm.setLastBagIndicator(true);
      } else {
         mbm.setLastBagIndicator(false);
      }
      mbm.setRegisteredInsuredFlag(mailbagNumber.substring(24, 25));
     // mbm.setWeight(BigDecimal.valueOf(Long.parseLong(mailbagNumber.substring(25, 29))/10));
      mbm.setWeight(new BigDecimal(mailbagNumber.substring(25, 29)).divide(BigDecimal.TEN,1,RoundingMode.CEILING)); 
      
      return mbm;
   }
/*   
   public static void main(String[] args) {
      String mailbagNumber = "MYKULGUSSFOAAEM72072001100028";
      MailbagModel mbm = getMailgag(mailbagNumber);
      System.out.println(mbm);
   }*/
}