package com.ngen.cosys.flight.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope("prototype")
public class IcsFlightDetails extends BaseBO {
	private static final long serialVersionUID = 1L;
	private long icsFlightDetailsId;
	private Long flightId;
	private String flightType;
	private String flightCarrier;
	private String flightNumber;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime staStd;
	private String origin;
	private String destination;
	private String operationalDirection;
	private boolean consumed;

}
