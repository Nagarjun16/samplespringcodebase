package com.ngen.cosys.satssginterfaces.mss.model;

import java.time.LocalDate;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ConnectingFlight extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long manifestConnectingFlightId;
	
	private long manifestId;
	
	@NotBlank(message="export.connetcting.flight.blank")
	@Length(min=5, max=8, message="export.connecting.flight.number.length.validation")
	@Pattern(regexp="^[A-Z0-9]{2,3}[0-9]{2,4}[A-Z0-9!@#$%^&*)(+=._-]$", message="invalid.conn.flight.number")
	private String flightKey;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightDate;
	
	@CheckAirportCodeConstraint()
	private String destination;

}
