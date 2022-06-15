package com.ngen.cosys.shipment.exportawbdocument.model;

import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportAwbDocumentSearchModel extends BaseBO {
	private static final long serialVersionUID = 1L;
	
	private String shipmentType;
	
	@NotBlank(message = "data.mandatory.required")
	private String shipmentNumber;
	
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate shipmentDate;
	
	private Boolean nonIATA;
	
}
