package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
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
@NoArgsConstructor
@Validated
public class ULDInFormation extends BaseBO {
   
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   @NotNull(message="export.segment.blank")
   private BigInteger segmentId;
//   @NotNull(message="ULD Number is blank", groups=
//         {AssignAddULDValidatorGroup.class, AssignAddTrolleyValidatorGroup.class}) 
   @NotBlank(message="export.uldnumber.blank")
   @Size(max=11, message="export.uldnumber.length.validation")
   private String uldTrolleyNo; 
   @NotBlank(message="export.content.code.blank")
//   @NotBlank(message="Content Code is blank", groups= 
//{AssignAddULDValidatorGroup.class, AssignAddTrolleyValidatorGroup.class})
   private String contentCode; 
   @NotNull(message="export.height.code.blank")
   private String heightCode; 
//   @NotBlank(message="Handling Area is blank", groups=
//         {AssignAddULDValidatorGroup.class, AssignAddTrolleyValidatorGroup.class})
   private String handlingArea;
   private String offPoint;
   @Size(max=65, message="remarks.maximum.length.is.65")
   private String remarks;
   private String uldType;
   private String uldNumber;
   private String uldCarrier;
   private boolean phcFlag;
   private String eccFlag;
   private boolean  standByFlag;
   private boolean  trolleyFlag;
   private boolean  trolleyInd;
   private boolean  transitFlag;
 
   private BigDecimal tareWeight;
   private BigDecimal seriesTareWeight;
   private BigDecimal loadedWeight;
   private BigDecimal estimatedWeight;
   private BigInteger loadedPieces;
   @Range(min = 1, max = 99,message="export.priority.range.validation")
   private Integer priority;
   private LocalDateTime loadingStartedOn;
   private LocalDateTime loadingCompletedOn;
   private BigInteger handlingServiceGroupId;
   private BigInteger reasonIdForMovement;
   private LocalDateTime uldTagPrintedOn;
   private BigInteger flightId;
   private BigDecimal actualGrossWeight;
   
   private String wareHouseLocation;

}
