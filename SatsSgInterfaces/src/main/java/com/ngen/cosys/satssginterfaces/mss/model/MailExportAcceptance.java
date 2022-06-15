package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
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
public class MailExportAcceptance extends BaseBO {
   /**
    * Default serialVersionUID
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
	 * This is shipment number
	 */
   private String shipmentNumber;
   
   /**
    * This field contains Shipment Id
    */
   private int shipmentId;
   
   /**
    * This field contains Dispatch Number
    */
   private String dispatchNumber;
   
   /**
    * This field contains Dispatch Number
    */
   @InjectShipmentDate(shipmentNumberField="shipmentNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate shipmentDate;

   /**
    * This field contains Shipment Inventory Id
    */
   private int shpInvId;

   /**
    * This field contains Shipment House Id
    */
   private int shipmentHouseId;

   /**
    * This field contains Document Id
    */
   private int docId;

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
    * This field contains PA Flight
    */
   private String paFlightKey;

   /**
    * This field contains Last Bag Indicator
    */
   private int lastBagIndicator;

   /**
    * This field contains Registered Indicator
    */
   private int registeredIndicator;

   /**
    * This field contains Dn Number
    */
   private String updateDnNumber;

   /**
    * This field contains damaged
    */
   private String damaged;

   /**
    * This field contains select flag
    */
   private boolean select;

   /**
    * This field contains carrier code
    */
   private String carrierCode;

   /**
    * This field contains carrier flag
    */
   private boolean carrierDetermined = true;

   /**
    * This field contains warehouseLocation
    */
   private String warehouseLocation;
   /**
    * This field contains shipmentLocation
    */
   private String shipmentLocation;
   /**
    * This field contains PA Flight Date
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate paFlightDate;
   /**
    * This field contains PA Flight Id
    */
   private String paFlightId;
   /**
    * This field contains PA Flight Segment
    */
   private ArrayList<String> paFlightSeg;

   private String entityKey;
   private String entityType;

   private String incomingCarrier;

   private String incomingCountry;

   private String incomingCity;

   private String agentCode;

   private String originOfficeExchange;

   private String originCountry;

   private String originCity;

   private String destinationOfficeExchange;

   private String destinationCountry;

   private String destinationCity;
   private String outgoingCountry;
   private String outgoingCity;


   /**
    * This field contains Next Destination. Retrieved from DB and then injected in
    * Model.
    */
   private String nextDestination;
   /**
    * This field contains Flight Off Point. Retrieved from DB and then injected in
    * Model.
    */
   private String flightOffPoint;
   /**
    * This field contains Outgoing Carrier. Retrieved from DB and then injected in
    * Model.
    */
   private String outgoingCarrier;

   /**
    * This field contains Embargo Flag. Retrieved from DB and then injected in
    * Model.
    */
   private String embargoFlag;
   
   /**
    * This field contains Mail Bag Category
    */
   private String mailBagMailCategory;

   /**
    * This field contains Mail Bag Sub Categoryged
    * 
    */
   private String mailBagMailSubcategory;
   private String uldNumber;
   private String mailBagWeight;
   private String user;
}