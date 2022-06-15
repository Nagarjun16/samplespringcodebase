package com.ngen.cosys.impbd.tracing.activity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.ngen.cosys.model.ShipmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TracingActivityShipmentModel extends ShipmentModel {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;
   private BigInteger flightId;

   private String caseNumber;
   private String caseStatus;
   private String carrier;
   private String flightKey;
   private LocalDate flightDate;
   private String boardingPoint;
   private String offPoint;
   private String tracingCreatedfor;
   private String irregularityTypeCode;
   private BigInteger irregularitypieces;
   private BigDecimal irregularityWeight;

   private String importUserCode;
   private String importStaffName;
   private String importStaffNumber;
   private String houseNumber;
   private String shipmentType;

   private List<TracingActivityShipmentShcModel> shcs;
   private List<TracingActivityShipmentInventoryModel> inventories;
}