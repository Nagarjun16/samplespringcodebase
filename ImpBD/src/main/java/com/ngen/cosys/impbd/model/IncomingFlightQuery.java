package com.ngen.cosys.impbd.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.UserCarrierValidation;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies Ltd
 *
 */
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@UserCarrierValidation(carrierCode="carrierCode", flightKey = "", loggedInUser = "loggedInUser", type = "CARRIER", shipmentNumber = "")
public class IncomingFlightQuery extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private String terminalPoint;

	@NotNull(message = "ERROR_FROM_DATE_CANNOT_BE_BLANK")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime fromDate;

	@NotNull(message = "ERROR_TO_DATE_CANNOT_BE_BLANK")
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime toDate;

	private String carrierCode;

	private List<String> carrierGroup;
	
	private String carrierGp;
	
	private String terminalCode;
	
	private String flightKey;
	
	private Integer domesticFlightFlag;

	//JV01
	private String warehouseLevel;
	
	private String arrDepStatus;
	
	private String buBdOffice;
	
	private String flightType;
	
	private String rho; 
}