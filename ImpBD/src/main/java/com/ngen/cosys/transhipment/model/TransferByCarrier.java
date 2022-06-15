package com.ngen.cosys.transhipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class TransferByCarrier extends BaseBO {

	/**
	 * default serial number
	 */
	private static final long serialVersionUID = 1L;
	private String select;
	private String to;
	private String awbNumber;
	private String flight;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate date;
	private String handler;
	private String destination;
	private String awbDestination;
	private int pieces;
	private String weight;
	private String weightCode;
	private String natureOfGoods;
	private String shc;
	private String barecodeInd;
	private boolean readyToTransfer;
	private BigInteger flightId;
	private int piecesInventory;	
	private int irregularityPieces;
	private boolean documentReceivedFlag;
	private boolean photoCopyAwbFlag;
	private int totalPieces;
	private int freightOutPieces;
	private String origin;
	private int manifestPieces;
	private int foundPieces;
	private BigDecimal weightInventory;
}