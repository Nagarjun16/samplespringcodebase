package com.ngen.cosys.impbd.workinglist.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

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
public class FlightCompleteShipmentsData extends BaseBO {
	
	private static final long serialVersionUID = 8972621865563427008L;
	
	private BigInteger shipmentId;
	
	private String ShipmentNumber;
	
	private LocalDate shipmentDate;
	
	private String customerCode;
	
	private String customerShortName;
	
	private String notificationTypeEmail;
	
	private BigInteger breakDownPieces;
	
	private BigDecimal breakDownWeight;
	
	private String origin;
	
	private String destination;
	
	private BigInteger manifestPieces;
	
	private BigDecimal manifestWeight;
	
	private String flightDetails;
	
	private BigDecimal charges;
	
	 private Boolean documentPouchReceived = Boolean.FALSE;
	 
	 private Boolean documentOriginalReceived = Boolean.FALSE;
	 
	 private String documentReceived;
	 
	 private BigInteger awbPiece;
		
	private BigDecimal awbWeight;
	
	private BigInteger flightId;
	

}
