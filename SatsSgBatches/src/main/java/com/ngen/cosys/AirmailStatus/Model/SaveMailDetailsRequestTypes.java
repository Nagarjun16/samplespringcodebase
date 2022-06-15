package com.ngen.cosys.AirmailStatus.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "loggedInUser", "flagCRUD", "tenantId",
      "terminal", "userType", "sectorId" })
public class SaveMailDetailsRequestTypes extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String companyCode;
   private String hhtVersion;
   private String scanningPort;
   private long messagePartId;
   private AirmailStatusChildModel mailDetails;

}
