package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import com.ngen.cosys.model.FlightModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SearchArrivalManifestModel extends FlightModel {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = -8327429227721528511L;
   
   private BigInteger segmentId;
   
   private String shipmentType;

}