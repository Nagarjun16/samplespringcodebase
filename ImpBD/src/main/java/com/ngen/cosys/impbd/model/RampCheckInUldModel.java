package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckDriverIDConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies
 *
 */
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RampCheckInUldModel extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger impRampCheckInId;
   
   private String id;
   
   private BigInteger flightId;
      
   
   private List<RampCheckInSHC> shcs;
   
   private String transferType;
   
   private String contentCode;
   
   private Boolean usedAsTrolley;
   
   private Boolean damaged;
   
   private Boolean empty;
   
   private Boolean piggyback;
   
   private Boolean phc;
   
   private Boolean val;

   private Boolean manual;
   
   //@CheckDriverIDConstraint(mandatory = MandatoryType.Type.REQUIRED, groups = { ArrivalManifestValidationGroup.class })
   private String driverId;
   
   private String checkedinAt;
   
   private String checkedinBy;
   
   private String checkedinArea;

   private String offloadReason;

   private String remarks;

   private Integer statueCode;
   
   private Boolean offloadedFlag;
   
   private String tractorNumber;
   
   private BigInteger handOverId;
   
   private String handedOverArea;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime handoverDateTime;

   private String impHandOverContainerTrolleyInformationId;
   
   private List<RampCheckInUldNumber> uldNumber;
}
