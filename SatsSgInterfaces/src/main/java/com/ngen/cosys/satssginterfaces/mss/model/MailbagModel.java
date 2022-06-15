package com.ngen.cosys.satssginterfaces.mss.model;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
@Validated
public class MailbagModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String mailBagNumber;

   private String dispatchSeries;

   private String originCountry;

   private String originAirport;

   private String originQualifier;

   private String destinationCountry;

   private String destinationAirport;

   private String destinationQualifier;

   private String category;

   private String subCategory;

   private String year;

   private String dispatchNumber;

   private String receptableNumber;

   private String lastBagIndicator;

   private String registeredInsuredFlag;

   private String weight;
   
	private String truckDocColor;
	
	private String infeedColor;
	
	private String manifestColor;
	
   private String rampColor;
	
   private String offloadColor;

   /**
    * Retrieved from DB and then injected in Model.
    */
   private String flightOffPoint;

   /**
    * Retrieved from DB and then injected in Model.
    */
   private String outgoingCarrier;

   // TODO - Need to decide on actual value propagation.
   private String incomingCarrier = null;
   // TODO - Need to decide on actual value propagation.
   private String incomingCountry = null;
   // TODO - Need to decide on actual value propagation.
   private String incomingCity = null;
}