package com.ngen.cosys.ForeignUld.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal",
      "loggedInUser", "userType", "sectorId" })

public class ForeignUldArrivedOnIncomingFlightModel extends BaseBO  {
	
	private static final long serialVersionUID = 1L;
	   private String carrierCode;
	   private String fromDate;
	   private String toDate;
	   

}
