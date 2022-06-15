package com.ngen.cosys.satssg.interfaces.singpost.model;

import java.math.BigInteger;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.validation.groups.FetchULDValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JsonPropertyOrder(value = { "outgoingFlightCarrier", "outgoingFlightNumber", "outgoingFlightDate", "uldIdentification" })
@JacksonXmlRootElement(localName = "ULDDataRequest")
public class FetchULDDataRequestModel {

   @NotBlank(message = "container.id.cant.blank", groups = FetchULDValidationGroup.class)
   @Size.List({ @Size(min = 10, message = "container.id.cant.be.less", groups = FetchULDValidationGroup.class),
         @Size(max = 10, message = "container.id.cant.be.more.than", groups = FetchULDValidationGroup.class) })
   @JacksonXmlProperty(localName = "containerId")
   private String containerId;
   
   private BigInteger autoWeighBupHeaderId;
   
   private String carrierCode;
   
   private String uldKey;
   
   private String uldNumber;

}
