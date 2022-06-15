package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShipmentTransferManifestModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private String trmNumber;
   private String transfereeCarrier;
   private String receivingCarrier;
   private Boolean finalized;
   private BigInteger pieces;
   private BigDecimal weight;
   private String finalizedBy;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime finalizedAt;

}
