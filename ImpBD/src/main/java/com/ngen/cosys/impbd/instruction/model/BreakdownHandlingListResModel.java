package com.ngen.cosys.impbd.instruction.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("deprecation")
@Getter
@Setter
@NoArgsConstructor

@Validated
public class BreakdownHandlingListResModel extends BaseBO{
	
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Flight Key is blank")
	private String flightKey;
	private String flightId;
	private String accountRegistration;
	private String segment;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;
	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime sta;
	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime eta;
	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime ata;
	@Valid
	private List<BreakDownHandlingInformation> breakDownHandlingInformation;

}
