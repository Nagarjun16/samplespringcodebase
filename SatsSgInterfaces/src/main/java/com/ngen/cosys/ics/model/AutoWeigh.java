package com.ngen.cosys.ics.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor
@Validated
public class AutoWeigh extends BaseBO{
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private long autoWeighBupHeaderId;
	private String acceptedBy;
	private String uldNumber;
	private String customerCode;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;

	private String carrierCode;
	private String offPoint;

	private long flightId;

	private long flightSegmentId;
	private String flightKey;
	private String flightBoardPoint;
	private String flightOffPoint;
	private String segment;
	private String uldTagPrinted;
	private String pdTrolleyNumber;
	private BigDecimal pdTrolleyWeight;
	private BigDecimal grossWeight;
	private boolean xpsShipment;
	private boolean dgShipment;
	private boolean cargo;
	private boolean whs;
	private boolean ics;
	private boolean rprn;
	private boolean bup;
	private boolean reprint = Boolean.FALSE;
	private boolean mail;
	private boolean courier;
	private String tagRemarks;
	private LocalDate uldTagPrintedOn;
	private BigDecimal dryIceWeight;
	private BigDecimal weightCapturedManually;
	private List<AutoWeighDG> dgDetails;
	private BigDecimal uldAndPdWeight;
	private String flightNumberAndDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateTime;
	private String currentUser;
	private String dateTimeAndUserId;
	private boolean bupIndicator;
	private boolean partConfirmFlag;
	private boolean grossWeightLess = Boolean.FALSE;
	private boolean grossWeightMore = Boolean.FALSE;
	
	private String printerName;
	private BigDecimal uldTareWeight;
	private String weighingScaleName;
	
	private String  oldFlightKey;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate oldFlightDate;
	private long uldICSDataRequestId;
	private String series;
	private String ULDType;
}
