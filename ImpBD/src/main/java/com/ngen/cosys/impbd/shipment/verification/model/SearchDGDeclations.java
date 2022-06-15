package com.ngen.cosys.impbd.shipment.verification.model;


import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

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
public class SearchDGDeclations extends BaseBO{

	/**
    * 
    */
   private static final long serialVersionUID = 1L;

   @Length(max = 11, message = "ERROR_ELEVEN_DIGIT_NUMBER_IN_AWB")
	@Pattern(regexp = "^[0-9]{1,11}", message = "ERROR_AWB_NUMBER_CANNOT_HAVE_ALPHABET_SPECIAL_CHR")
	@NotBlank(message = "ERROR_SHIPMENT_NUMBER_CANNOT_BE_EMPTY")
	private String shipmentNumber;
	
	private Short dgdReferenceNo;


}
