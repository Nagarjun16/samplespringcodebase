package com.ngen.cosys.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "deleteFlag","deletedBy","deletedOn","createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser" })
public class ICSAircraftTypesUldModel extends BaseBO{
   /**
    * Default serial version id
    */
   private static final long serialVersionUID = 1L;
   @JsonProperty(value = "AIR_TYP")
   private String aircraftType;

}
