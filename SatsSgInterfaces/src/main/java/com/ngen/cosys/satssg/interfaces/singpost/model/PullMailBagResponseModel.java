/**
 * 
 * PullMailBagResponseModel.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          4 May, 2018 NIIT      -
 */
package com.ngen.cosys.satssg.interfaces.singpost.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class represents Application To SingPost (Message Format)
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@JacksonXmlRootElement(localName = "Request")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", 
      "modifiedBy", "modifiedOn", 
      "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", 
      "localDateFormat", "locale", 
      "messageList", "loggedInUser", 
      "flagCRUD", "tenantId", "terminal", "userType", "sectorId"})
public class PullMailBagResponseModel extends BaseBO {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   @JacksonXmlProperty(localName = "DispatchID", isAttribute = true)
   private String dispatchID;
   @JacksonXmlProperty(localName = "RecpID", isAttribute = true)
   private String recpID;
   @JacksonXmlProperty(localName = "Bag")
   @NgenCosysAppAnnotation
   private List<MailBagResponseModel> mailBag;

   private String eventType;
}