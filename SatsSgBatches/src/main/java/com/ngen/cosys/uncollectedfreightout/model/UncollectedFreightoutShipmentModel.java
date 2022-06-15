package com.ngen.cosys.uncollectedfreightout.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class UncollectedFreightoutShipmentModel extends BaseBO {
	  /**
	 * The default serialVersionUID.
	    */
	   private static final long serialVersionUID = 1L;
	private BigInteger shipmentId;
	   private String shipmentNumber;
	   private String awbNumber;

	   private String natureOfGoods;

	   private BigInteger pieces;

	   private BigDecimal weight;

	   private String origin;

	   private String chargeCode;

	   private String flight;

	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime flightDate;
	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime shipmentDate;

	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime date;
	   private List<String> shipmentList;
	   private String customerCode;
	   private List<String> emailList;
	   private String commaSepratedShiomentsList;
	   private String reminder1;
	   private String reminder2;
	   private String reminder3;
	   private String reminder4;
	   private String currentReminder;
	   private String abandon;
	   private String freightDate;
       private String irpRefNo;
       private String agentName;
       private String collectionTerminal;
       private String shipmentType;
       private String destination;
       private String currentDate;
       private String mail;
       private BigInteger flightId;
       private BigInteger irrPieces;
       private BigDecimal irrWeight;
       private String shcHandlingGroupCode;
       private String boardpoint;
       private String flightKey;
       private String lastCycle;
       private String houseNumber;
	   private String carrierCode;
}



