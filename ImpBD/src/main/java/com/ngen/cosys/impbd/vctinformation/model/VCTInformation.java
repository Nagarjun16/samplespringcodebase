package com.ngen.cosys.impbd.vctinformation.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
public class VCTInformation extends BaseBO {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	private BigInteger impCargoPickupScheduleId;

	private String requestfor;
	
	private String vctNumber;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime vctDate;
	

	private String vehicleRegistrationNumber;

	private String driverName;

	private String driverMobileNumber;

	private String driverLicenseNumber;

	private String driverAadharCard;

	private Integer vtNumberOfPieces;

	private BigDecimal vtGrossWeight;

	private String agentCode;

	private String shc;
	
	private String type;
	


	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime vehicleEntryTime;

	private  String vctInDoorNumber;

	private String vctInRemarks;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime vehicleExitTime;

	private String vctOutDoorNumber;

	private String vctOutRemarks;
	
	private Integer CargoPickupScheduleId;
	
	private List<VCTShipmentInformationModel> vctShipmentInformationlist;
	
	private Boolean vctIn;
	private Boolean vctOut;

}
