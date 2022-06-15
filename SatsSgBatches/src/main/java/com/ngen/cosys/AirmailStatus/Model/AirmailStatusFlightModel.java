package com.ngen.cosys.AirmailStatus.Model;

import java.math.BigInteger;
import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AirmailStatusFlightModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String carrierCode;
   private String flightNumber;
   private String flightKey;
   private BigInteger flightId;
   private LocalDate flightOriginDate;
   private LocalDate dateSTD;
   private LocalDate dateSTA;
   private LocalDate dateETD;
   private LocalDate dateETA;
   private LocalDate dateATD;
   private LocalDate dateATA;
   private String flightOffPoint;
   private String flightBoardPoint;
   private BigInteger segmentId;
   private Boolean activeFlag;
}
