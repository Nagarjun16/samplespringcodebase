package com.ngen.cosys.shipment.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class ShipmentCancelModel extends BaseBO {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger shipmentId;
	private String shipmentNumber;
	

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;
	
	
	
	

}
