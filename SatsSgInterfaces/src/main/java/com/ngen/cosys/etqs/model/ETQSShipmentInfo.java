package com.ngen.cosys.etqs.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JacksonXmlRootElement
public class ETQSShipmentInfo {

	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private Long shipmentId;

	private String shipmentNumber;

	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	private LocalDate shipmentDate;

	private String agentCode;

	private String serviceNumber;

	private String serviceStatus;

	private String queeNumber;

	private String queeStatus;

	private String pieces;

	private String weight;

	private String natureOfGoods;

	private List<String> shcs;

	private String terminal;

	private String flightKey;
	
	private String documentId;

}
