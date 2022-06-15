/**
 * 
 * SearchSIDRQ.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          29 January, 2018 NIIT      -
 */
package com.ngen.cosys.shipment.nawb.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This Model Class takes care of the Search SID requests.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@XmlRootElement
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class SearchSIDRQ extends BaseBO {

	private static final long serialVersionUID = 1L;
	private BigInteger sidHeaderId;
	private String sidNumber;
	private String shipmentNumber;
	private String status;
	private String handlingTerminal;
	private String stockId;
	private String carrierCode;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime fromDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime toDate;

}
