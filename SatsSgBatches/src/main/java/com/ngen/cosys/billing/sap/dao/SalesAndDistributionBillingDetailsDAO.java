package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import com.ngen.cosys.billing.sap.model.SalesAndDistributionBillingDetails;
import com.ngen.cosys.framework.exception.CustomException;

public interface SalesAndDistributionBillingDetailsDAO {
   public List<SalesAndDistributionBillingDetails> fetchSalesDistributionBillingDetails() throws CustomException;

   public String getSaleAndDistributionFileSequencesNumber() throws CustomException;
   
   public String getSaleAndDistributionCompanyCode() throws CustomException;
   
   public String getSaleAndDistributionPricingTypeCode() throws CustomException;
   
   public String getSaleAndDistributionInternalSaleOrderType() throws CustomException;
   
   public String getSaleAndDistributionExternalSaleOrderType() throws CustomException;
   
   
   

}
