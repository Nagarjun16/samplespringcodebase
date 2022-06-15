package com.ngen.cosys.impbd.shipment.breakdown.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Validated
public class InboundUldFlightModel extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flightDate;
	private String flightNumber;
	private String uldNumber;
	private List<InboundUldFlightModel>flightList;

}
