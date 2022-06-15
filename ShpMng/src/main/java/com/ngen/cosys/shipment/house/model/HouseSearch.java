package com.ngen.cosys.shipment.house.model;

import java.math.BigInteger;
import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.house.validator.MAWBValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
//@CheckShipmentUserAccessConstraint(ShipmentNumber = "awbNumber",skipAccessCheck=true)
public class HouseSearch extends BaseBO {
	/**
	 * serialVersionUID = -7902659569698793218L
	 */
	private static final long serialVersionUID = -7902659569698793218L;
	//@CheckShipmentNumberConstraint(groups = { MAWBValidationGroup.class }, mandatory = MandatoryType.Type.REQUIRED)
	@NotBlank(message = "ShipmentNumber Required", groups = { MAWBValidationGroup.class })
	
	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "awbNumber")
	private String awbNumber;
	private String hawbNumber;
	private BigInteger id;
	@JsonSerialize(using = LocalDateSerializer.class)
	@InjectShipmentDate(shipmentNumberField = "awbNumber")
	private LocalDate awbDate;
}