package com.ngen.cosys.impbd.displaycpm.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DisplayCpmModel extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * FlightNumber of the flight for tracking Container Pallet Message
    */
   private String flight;
   /**
    * Flight ID
    */
   private BigInteger flightId;

   /**
    * Flight Origin Date for tracking Container Pallet Message
    */

   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;
   /**
    * STA Capture time from Oprative Flight
    */
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime sta;
   /**
    * ETA Capture time from Oprative Flight
    */
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime eta;
   /**
    * ATA Capture time from Oprative Flight
    */
   @JsonSerialize(using = LocalTimeSerializer.class)
   private LocalTime ata;

   /**
    * Aircraft Registration Code
    */
   private String acRegistration;

   /**
    * siRemarks
    */

   private String siRemarks;

   /**
    * list of individual ULDNumber under this particular Flight Number
    */
   private List<DisplayCpmDetailsModel> listDisplayCpmDetails;

}