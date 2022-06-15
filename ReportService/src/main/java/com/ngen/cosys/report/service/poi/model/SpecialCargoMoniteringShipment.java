package com.ngen.cosys.report.service.poi.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * List of shipments and detail for SpecialCargoMonitering Function
 *
 */
@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoMoniteringShipment extends BaseBO {


	private static final long serialVersionUID = 1L;
	private String shipmentNumber;
    private LocalDate shipmentDate;
    //
    private String origin;
    private String destination;
    private String orgDst;
    //
    private BigInteger flightBookedPieces;
    private BigDecimal flightBookedWeight;
    private BigInteger bkgPieces;
    private BigDecimal bkgWeight;
    
    private String awbSHC;
    
    private List<SpecialCargoMissingUldShc> missingDLSUldList;
    private List<SpecialCargoMissingUldShc> missingDLSUldSHCList;
    
    private List<SpecialCargoMissingUldShc> missingUldTagList;
    private List<SpecialCargoMissingUldShc> missingUldTagSHCList;
    
    private List<SpecialCargoMissingUldShc> missingUldLoadList;
    private List<SpecialCargoMissingUldShc> missingUldLoadSHCList;
    
    private List<SpecialCargoMissingUldShc> missingUldNotocList;
    private List<SpecialCargoMissingUldShc> missingUldNotocSHCList;
    
    private List<SpecialCargoRequest> specialCargoMoniteringRequestSection;
//						HO Location - not in request
//						Date / Time - not in request
//					
    private List<SpecialCargoHandover> specialCargoMoniteringHandoverSection;
//					SpecialCargoHandoverAudit
//					Handover and confirm section--(List)
//						From Loc
//						Flight 
//						Req Pcs 
//						Req By 
//						Req Time
//						HO Location 
//						HO Pcs
//						HO SHC
//						Handover By 
//						Handover TO 
//						Handover TO 
//						Date Time 
//						
//						
//					SpecialCargoHandoverImage
//

    
    //
    
    private BigInteger bookingId;
    private BigInteger flightBookingId;
    private BigInteger partBookingId;
    private BigInteger flightPartBookingId;
    private BigInteger flightId;
    private BigInteger segmentId;
    private String flightBoardPoint;
    private String flightOffPoint;
    private BigInteger shipmentId;
    private String partShipment;
    private String shipmentType;
    private String natureOfGoodsDescription;
    private String partSuffix;
    private Boolean select;
    private String bookingStatusCode;
    private String segment;
    private String iventorySHC;
    private String whLocation;
    private String expDateTime;
    private String acceptanceStatus;
    private boolean acceptancePartConfirm;
    private boolean throughTransitFlag;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime documentReceivedOn;
    private BigInteger availablePieces;
    private List<SpecialCargoInventory> inventoryList;
    private List<SpecialCargoSHC> shipmentMasterSHC;
    private List<SpecialCargoSHC> bookingSHC;
    private List<SpecialCargoSHC> inventorySHC;
    private List<SpecialCargoSHC> partSHC;


}
