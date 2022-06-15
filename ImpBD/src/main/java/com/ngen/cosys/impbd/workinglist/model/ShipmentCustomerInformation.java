package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ShipmentCustomerInformation extends BaseBO {

   private static final long serialVersionUID = 1L;

   private List<FlightCompleteShipmentsData> flightCustomerMailDetails;

   private String shipmentNumber;

   private String origin;

   private String destination;

   private BigInteger breakdownPieces;

   private BigDecimal breakdownWeight;

   private BigInteger manifestedPieces;

   private BigDecimal manifestedWeight;

}
