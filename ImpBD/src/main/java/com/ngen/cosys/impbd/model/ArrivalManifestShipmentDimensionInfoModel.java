package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import com.ngen.cosys.model.DimensionModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ArrivalManifestShipmentDimensionInfoModel extends DimensionModel {

   private static final long serialVersionUID = 1L;

   private BigInteger shipmentId;

   private BigInteger id;



}