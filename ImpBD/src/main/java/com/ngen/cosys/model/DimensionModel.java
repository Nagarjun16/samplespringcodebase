package com.ngen.cosys.model;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@Validated
public class DimensionModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String weightUnitCode;
   
   @NotNull(message="impbd.weight.field.mandatory.in.dimensions",groups = ArrivalManifestValidationGroup.class)
   @CheckWeightConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = { ArrivalManifestValidationGroup.class })
   private BigDecimal weight;
   
   @NotNull(message="impbd.measurementunitcode.field.mandatory.in.dimension",groups = ArrivalManifestValidationGroup.class)
   private String measurementUnitCode;
   
   
   @NotNull(message="Impbd.width.field.is.mandatory.in.dimension",groups = ArrivalManifestValidationGroup.class)
   private BigDecimal width;
   
   @NotNull(message="impbd.height.field.is.mandatory.in.dimension",groups = ArrivalManifestValidationGroup.class)
   private BigDecimal height;
   
   @NotNull(message="impbd.length.field.is.mandatory.in.dimension",groups = ArrivalManifestValidationGroup.class)
   private BigDecimal length;
   
   @NotNull(message="impbd.number.of.pieces.field.is.mandatory.in.dimension",groups = ArrivalManifestValidationGroup.class)
   private BigInteger noOfPieces;

}