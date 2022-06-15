package com.ngen.cosys.ics.model;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.validation.groups.FetchICSLocationValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JacksonXmlRootElement(localName = "fetchPCHSLocationRequest")
public class FetchICSLocationRequestModel {

   @NotEmpty(message = "uld.or.bin.cant.be.blank", groups = FetchICSLocationValidationGroup.class)
   /*@Size.List({ @Size(min = 10, message = "ULD or BIN Number can't be Less than {min} Character", groups = FetchICSLocationValidationGroup.class),
         @Size(max = 11, message = "ULD or BIN Number can't be more than {max} Character", groups = FetchICSLocationValidationGroup.class) })*/
   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "containerId")
   private List<String> containerId;

}