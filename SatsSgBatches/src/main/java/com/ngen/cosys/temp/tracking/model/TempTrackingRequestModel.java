package com.ngen.cosys.temp.tracking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deletedOn", "deletedBy", "deleteFlag", "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId",
      "terminal", "loggedInUser" })
@JacksonXmlRootElement(localName = "TempRequest")
public class TempTrackingRequestModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   @JacksonXmlProperty(localName = "AWBNumber")
   private String awbNumber;

   @JacksonXmlProperty(localName = "ULDNumber")
   private String uldNumber;

   @JacksonXmlProperty(localName = "Location")
   private String location;

   @JacksonXmlProperty(localName = "From")
   private String fromDate;

   @JacksonXmlProperty(localName = "To")
   private String toDate;

   @JacksonXmlProperty(localName = "FrequencyMin")
   private String frequencyMin;

}
