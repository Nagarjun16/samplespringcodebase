package com.ngen.cosys.icms.model.BookingInterface;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ShipmentInventoryDetails extends BaseBO {

   private static final long serialVersionUID = 1L;
   private BigInteger shipmentId;
   private BigInteger shipmentInventoryId;
   private String locationCode;
   private BigInteger pieces;
   private BigDecimal weight;
   private BigInteger movPieces;
   private BigDecimal movWeight;
   private BigInteger tagPieces;
   private BigDecimal tagWeight;
   private String shipmentLocation;
   private String warehouseLocation;
   private boolean locked;
   private String lockedBy;
   private String lockedReason;
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime lockedAt;
   private String status;
   private String flightKey;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate;
}