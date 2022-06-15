package com.ngen.cosys.ics.model;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validation.groups.FetchULDValidationGroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "fetchICSULDListRequest")
public class FetchUldListModel {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   @NotBlank(message = "terminal.id.cant.be.blank", groups = FetchULDValidationGroup.class)
   @Size(min = 10, max = 10, message = "terminal.id.cant.be.more.than", groups = FetchULDValidationGroup.class)
   @JacksonXmlProperty(localName = "cargoTerminal")
   private List<String> cargoTerminal;
}