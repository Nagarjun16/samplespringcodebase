package com.ngen.cosys.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class MailbagModel extends BaseBO {

   private static final long serialVersionUID = 1L;

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
}