package com.ngen.cosys.shipment.inactive.model;

import java.time.LocalDateTime;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
public class ShipmentFreightOutSHC extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int shipmentInventorySHCId;
	private Long shipmentInventoryId;
	private String specialHandlingCode;
	private String createdUserCode;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDateTime createdDateTime;
	private String lastUpdatedUserCode;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDateTime lastUpdatedDateTime;
}
