package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies Ltd.
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
public class RampCheckInDetails extends BaseBO {

	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger flightId;

	private String flight;

	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;

	private String origin;

	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime sta;

	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime eta;

	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime ata;

	private String uldReceived;

	private String uldManifested;

	private String bulk;

	private String carrierCode;
	
	private String segment;

	private BigInteger checkInStatus;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime checkindateTime;

	private List<RampCheckInUld> ulds;
	
	private List<RampCheckInSHC> perGroupshcs;
	
	private Boolean nilCargo=false;
	
	private String checkInCompletedBy;

}
