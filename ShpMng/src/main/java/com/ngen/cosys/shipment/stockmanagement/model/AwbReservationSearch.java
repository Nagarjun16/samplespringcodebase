package com.ngen.cosys.shipment.stockmanagement.model;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;

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
public class AwbReservationSearch extends BaseBO {
	/**
	 * model for Awb Reservation search and save
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger terminalId;
	private BigInteger core;
	private String destination;
	private String carrierCode;
	private BigInteger stockId;
	private String svc;
	private String awbPrefix;
	private String nextAWBNumber;
	private BigInteger awbStockDetailsId;
	private List<AwbReservation> bookedList;
	private boolean errorFlag;

}