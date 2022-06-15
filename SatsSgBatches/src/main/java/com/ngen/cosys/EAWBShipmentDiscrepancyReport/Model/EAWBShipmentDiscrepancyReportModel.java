package com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model;

import java.math.BigInteger;

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
public class EAWBShipmentDiscrepancyReportModel extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private BigInteger pieceDescrepancy;
   private BigInteger weightDescrepancy;
   private BigInteger shipmentid;
   private String shipmentnumber;

}
