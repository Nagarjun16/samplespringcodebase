package com.ngen.cosys.impbd.vctinformation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationShipmentModel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
public class VCTShipmentInformationModel extends BaseBO {
	/**
	 * default
	 */
	private static final long serialVersionUID = 3860829832470467939L;

	private String awbNumber;

	private String hawbNumber;
	
	private String tspNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime tspDate;
	
	private String totalPieces;
	
	private String totalWeight;

	private String number;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime date;

	private BigDecimal value;

	private String remarks;

}
