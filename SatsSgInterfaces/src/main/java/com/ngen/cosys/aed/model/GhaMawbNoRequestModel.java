package com.ngen.cosys.aed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ApiModel
@JacksonXmlRootElement(localName = "GHAMAWBNO")
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
      "terminal", "userType", "sectorId" ,"versionDateTime"})
@JsonPropertyOrder(value = { "GHAMAWBNO", "HEADER", "DETAIL" })
public class GhaMawbNoRequestModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   @JacksonXmlProperty(localName = "HEADER")
   private Header header;

   @JacksonXmlProperty(localName = "DETAIL")
   private Details details;

}
