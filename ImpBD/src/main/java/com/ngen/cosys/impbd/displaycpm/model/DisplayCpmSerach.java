package com.ngen.cosys.impbd.displaycpm.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class DisplayCpmSerach extends BaseBO {

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
   private int flightId;

   /**
    * Flight Origin Date for tracking Container Pallet Message
    */
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightDate;

}
