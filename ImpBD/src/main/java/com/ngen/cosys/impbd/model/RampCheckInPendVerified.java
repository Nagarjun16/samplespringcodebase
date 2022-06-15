package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies
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
@Validated
public class RampCheckInPendVerified extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger impRampCheckInId;

	private String id;

	private BigInteger flightId;

	private String uldNumber;

	private List<RampCheckInSHC> shcs;

	private String transferType;

	private String contentCode;

	private Boolean usedAsTrolley = false;

	private Boolean damaged = false;

	private Boolean empty = false;

	private Boolean piggyback = false;

	private Boolean phc = false;

	private Boolean val = false;

	private Boolean manual = false;

	private String driverId;

	private String checkedinAt;

	private String checkedinBy;

	private String checkedinArea;

	private String offloadReason;

	private String remarks;

	private Integer statueCode;

	private Boolean offloadedFlag;

	private String tractorNumber;

	private BigInteger handOverId;

	private String handedOverArea;

	private String countPenVerified;

	private String flight;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime handoverDateTime;

	private String impHandOverContainerTrolleyInformationId;

	private BigInteger ouboundFlightId;

	private String ouboundFlightKey;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime ouboundFlightDate;

	private String handlingInstructions;

	private String handlingMode;

	private String phcFlag;
	
	private Integer ucmFinalized;
	
	private String origin;
	
	   private BigInteger uldTemperatureLogId;
	   
	   private String temperatureType;
	   
	   private BigDecimal temperatureValue;
	   
	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime temperatureCaptureDt;

	   private String uldEvent;
	   
	   private String tempRemarks;
	   
	   private BigDecimal temperatureTypeValue;
}
