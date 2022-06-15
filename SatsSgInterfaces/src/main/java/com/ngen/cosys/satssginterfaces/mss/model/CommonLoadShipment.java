package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author NIIT
 *
 */
@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class CommonLoadShipment extends BaseBO {

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   private String flightKey; //
   private String shipmentNumber;
   private BigInteger flightId;
   private boolean eccShipmentFlag;
   private boolean mailBagFlag;
   private BigInteger flightSegmentId;
   private BigInteger dLSId;
   private BigInteger flightIDNew;
   private BigInteger newFlightSegmentId;
   private String newflightKey;
   private String newsegment;
   private BigDecimal pieces;
   private BigDecimal weight;
   private String natureOfGoods;
   private BigInteger newDLSId;
   private String segment;
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate flightOriginDate; //
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate newflightOriginDate; //
   @Valid
   List<BuildUpULD> uldList; //
   private boolean isFromAmendtrolley;

}
