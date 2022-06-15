package com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model;

import java.math.BigInteger;
import java.util.List;

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
public class NonAWBLowStockLimitModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String carriercode;
   private BigInteger awbStockId;
   private String stockCategoryCode;
   private long lowStockLimit;
   private String awbPrefix;
   private String awbSuffix;
   private String nextAwbNumber;
   private String lastAwbNumber;
   private long reserved;
   private long issued;
   private long deleted;
   private long unUsedCount; 
   private long totalCount;
   
}
