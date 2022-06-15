package com.ngen.cosys.shipment.coolportmonitoring.model;

import java.time.LocalDateTime;

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
 * @author Nikhil.5.Gupta
 *
 */
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class CoolportMonitoringDetail extends BaseBO {

	private static final long serialVersionUID = 1L;

	private String uldNumber;
	private String awbNumber;
	private String ata;
	private String eta;
	private String sta;
	private String flightDelay;
	private String origin;
	private String destination;
	private String ffmOrBookingIndicator;
	private int pieces;
	private int weight;
	private String natureOfGoods;
	private String shc;
	private String bookingDetail;
	private String transferType;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime shipmentDate;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateSTA;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateSTD;
	private String remark;
	private String wareHouseLocation;
	private String flightkey;
	private String currentTemparature;
	private String temparature;
	private Boolean select;

}