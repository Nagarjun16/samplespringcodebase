package com.ngen.cosys.report.service.poi.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@NgenCosysAppAnnotation
public class SpecialCargoShipment extends BaseBO{
	  /**
	 * 
	 */
	  private static final long serialVersionUID = 4270074060393723577L;
	  private List<SpecialCargoInventory> inventoryList;
	  private List<SpecialCargoInventory> loadedInventoryList;
      private List<SpecialCargoSHC> shipmentMasterSHC;
      private List<SpecialCargoSHC> bookingSHC;
      private List<SpecialCargoSHC> inventorySHC;
      private List<SpecialCargoSHC> partSHC;
      private List<SpecialCargoRequest> requestList;
      private BigInteger bookingId;
      private BigInteger flightBookingId;
      private BigInteger partBookingId;
      private BigInteger flightPartBookingId;
      private BigInteger flightId;
      private String flightKey;
      private LocalDate flightDate;
      private BigInteger segmentId;
      private String flightBoardPoint;
      private String flightOffPoint;
      private BigInteger shipmentId;
      private boolean partShipment;
      private String shipmentType;
      private String shipmentNumber;
      private LocalDate shipmentDate;
      private String origin;
      private String destination;
      private String natureOfGoodsDescription;
      private String partSuffix;
      private Boolean select;
      private String bookingStatusCode;
      private String segment;
      private BigInteger flightBookedPieces;
      private BigDecimal flightBookedWeight;
      private String iventorySHC;
      private String awbSHC;
      private String whLocation;
      private LocalDateTime expDateTime;
      private LocalDateTime handoverExpDateTime;
      private String acceptanceStatus;
      private boolean acceptancePartConfirm;
      private boolean throughTransitFlag;
      @JsonSerialize(using = LocalDateTimeSerializer.class)
      private LocalDateTime documentReceivedOn;
      private BigInteger availablePieces;
      private BigInteger loadedPieces;
      private BigDecimal loadedWeight;
      
      //For Inserting Handover to Login Id and Staff Id
      private String handOverToCCStaffId;
      private String handoverToLoginId;
      private String staffName;
      //Adding attribute to validate HO photo setup
      private String shcGroup;
      //for audit Trail
      private LocalDateTime timeStamp;
     
      //forMonitering screen
      private List<SpecialCargoHandover> specialCargoMoniteringHandoverSection;
      private boolean missmatchSectionGreen = Boolean.FALSE;
      private boolean missmatchSectionWhite = Boolean.FALSE;
      private boolean missmatchSectionRed = Boolean.FALSE;
      private BigInteger flightBookingSHCId;
      //DLS missing uld in handover
      private String missingDLSUldListForDisplay;
      private String missingDLSUldShcListForDisplay;
      //last print uld tag
      private String missingPrintTagUldListForDisplay;
      private String missingPrintTagUldShcListForDisplay;
      //Notoc missing uld in handover
      private String missingNotocUldListForDisplay;
      //Loaded missing uld in handover
      private String missingLoadedUldListForDisplay;
      private String missingLoadedUldShcListForDisplay;
      private int handOverCount;
      private int reqCount;
      private boolean partSuffixToShow;


}
