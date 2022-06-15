package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

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
@NgenCosysAppAnnotation
@Validated
public class SHC extends BaseBO{
	private static final long serialVersionUID = 1L;
	private BigInteger snapShotShipmentId; 

	 @CheckSpecialHandlingCodeConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
     private String code;
     private long flightBookingId;
     private long partBookingId;
}
