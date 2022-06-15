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

/**
 * This is the Mailbag Utility class which takes care of Mailbag related formatting.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public class MailbagUtil  {

   private MailbagUtil() {}

   public static MailbagModel getMailbag(String mailbagNumber) {
      MailbagModel mbm = new MailbagModel();
      mbm.setMailBagNumber(mailbagNumber);
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
      mbm.setLastBagIndicator(mailbagNumber.substring(23, 24));
      mbm.setRegisteredInsuredFlag(mailbagNumber.substring(24, 25));
      mbm.setWeight(mailbagNumber.substring(25, 29));
      return mbm;
   }
/*   
   public static void main(String[] args) {
      String mailbagNumber = "MYKULGUSSFOAAEM72072001100028";
      MailbagModel mbm = getMailgag(mailbagNumber);
      System.out.println(mbm);
   }*/
}