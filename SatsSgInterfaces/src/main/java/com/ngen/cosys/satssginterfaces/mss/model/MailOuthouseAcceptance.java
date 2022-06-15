package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class MailOuthouseAcceptance extends BaseBO {

   /**
   *
   */
   private static final long serialVersionUID = 1L;

   /**
    * This field contains Mail Bag Number
    */
   private String shipmentType;

   /**
    * This field contains Mail Bag Number
    */
   private String mailBagNumber;
   /**
    * This field contains Dn Number Id
    */
   private int dnId;

   /**
    * This field contains Mail Bag Category
    */
   private String category;

   /**
    * This field contains Mail Bag Subcategory
    */
   private String mailType;

   /**
    * This field contains Year
    */
   private BigInteger year;

   /**
    * This field contains Receptacle Number
    */
   private String receptacleNumber;

   /**
    * This field contains Pieces
    */
   private BigInteger pieces;

   /**
    * This field contains Weight
    */
   private BigDecimal weight;

   /**
    * This field contains Origin Office Exchange
    */
   private String origin;

   /**
    * This field contains Destination Office Exchange
    */
   private String destination;
   /**
    * This field contains Mail Bag Category
    */
   private String mailBagMailCategory;

   /**
    * This field contains Mail Bag Sub Category
    */
   private String mailBagMailSubcategory;

   /**
    * This field contains Booking Id
    */
   private int bookingId;

   /**
    * This field contains PA Flight Id
    */
   private String paFlightId;

   /**
    * This field contains PA Flight Key
    */
   private String paFlightKey;

   /**
    * This field contains PA Flight Date
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate paFlightDate;
   /**
    * This field contains Dispatch Number
    */
   private String dispatchNumber;

   /**
    * This field contains Last Bag Indicator
    */
   private int lastBagIndicator;

   /**
    * This field contains Registered Indicator
    */
   private int registeredIndicator;

   /**
    * This field contains Next Destination
    */
   private String nextDestination;

   /**
    * This field contains Customer Id
    */
   private int customerId;
  
   private String updateDnNumber;
   
   private String storeLocation;
   
   private String warehouseLocation;
   
   private String damaged;
   
   private boolean select;
   
   private String carrierCode;
   
   private boolean carrierDetermined = true;
}
