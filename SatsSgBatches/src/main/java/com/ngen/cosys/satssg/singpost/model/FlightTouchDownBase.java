package com.ngen.cosys.satssg.singpost.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@JacksonXmlRootElement(localName = "Request")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser", "mailBag", "userType", "sectorId", "versionDateTime" })
public class FlightTouchDownBase extends BaseBO {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 8780574175278002033L;

   @JacksonXmlProperty(localName = "Bag")
   @JacksonXmlElementWrapper(useWrapping = false)
   private List<FlightTouchDown> mailBag;
}