package com.ngen.cosys.impbd.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validators.PrintAwbValidaton;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class AWBPrintResponse extends BaseBO {
   /**
    * The default serialVersionUID.
    */
  private static final long serialVersionUID = 1L;
  /**
   * AWB number
   */
  private String awbNumber;
  /**
   * Tells printed copy or a original document
   */
  private boolean isPrintCopy;
  /**
   * Flight Number
   */
  @NotNull(message = "g.required",groups = {PrintAwbValidaton.class})
  @NotEmpty(message = "g.required",groups = {PrintAwbValidaton.class})
  private String flight;
  /**
   * Flight Departure time
   */
  @NotNull(message = "g.required",groups = {PrintAwbValidaton.class})
  private  LocalDate flightDate;
  /**
   * Flight Segment
   */
/*  @NotNull(message = "Required",groups = {PrintAwbValidaton.class})
  @NotEmpty(message = "Required",groups = {PrintAwbValidaton.class})
  private String segment;*/
  
  private String carrierCode;
  
  private String flightKey;
  
  private String origin;
  
  private String desination;
  
  private String natureOfGoodsDescription;
  
  private BigDecimal pieces;
  
  private BigDecimal weight;
  
  private String segmentId;
  
  private boolean documentReceivedFlag;
  
  private boolean photoCopyAwbFlag;
  
  private boolean documentPouchReceivedFlag;
  
  private boolean barcodePrintedFlag;
  
  private boolean svc;
  
  private boolean manuallyCreated;
  
  private LocalDateTime shipmentDate;
  
  private String weightUnitCode;
  
  private String totalPieces;
  
  private String shipmentDescriptionCode;
  
  private String customsOriginCode;
  
  private String customsReference;
  
 private String flightSegmentId;
 
 private String shipmentType;
 
 private String shipmentId;
 
 private BigInteger transactionSequenceNo;
 
 private String cargoIrregularityCode;
 
 private String irregularityRemarks;
  

}
