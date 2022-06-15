package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.billing.sap.enums.query.SAPInterfaceQuery;
import com.ngen.cosys.billing.sap.model.SalesAndDistributionBillingDetails;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("salesAndDistributionBillingDetailsDAOImpl")
public class SalesAndDistributionBillingDetailsDAOImpl extends BaseDAO implements SalesAndDistributionBillingDetailsDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   public SqlSession sqlSession;

   @Override
   public List<SalesAndDistributionBillingDetails> fetchSalesDistributionBillingDetails() throws CustomException {
      return fetchList(SAPInterfaceQuery.FETCH_SALE_AND_DISTRIBUTION_BILLING_DETAILS.getQueryId(), null, sqlSession);
   }

   @Override
   public String getSaleAndDistributionFileSequencesNumber() throws CustomException {
      return fetchObject(SAPInterfaceQuery.FETCH_SALE_AND_DISTRIBUTION_FILE_SEQ_NUMBER.getQueryId(), null, sqlSession);
   }

@Override
public String getSaleAndDistributionCompanyCode() throws CustomException {
	 return fetchObject(SAPInterfaceQuery.GET_SALE_AND_DISTRIBUTION_COMPANY_CODE.getQueryId(), null, sqlSession);
}

@Override
public String getSaleAndDistributionPricingTypeCode() throws CustomException {
	return fetchObject(SAPInterfaceQuery.GET_SALE_AND_DISTRIBUTION_PRICING_TYPE_CODE.getQueryId(), null, sqlSession);
}

@Override
public String getSaleAndDistributionInternalSaleOrderType() throws CustomException {
	return fetchObject(SAPInterfaceQuery.GET_SALE_AND_DISTRIBUTION_INTERNAL_SALE_ORDER_TYPE.getQueryId(), null, sqlSession);
}

@Override
public String getSaleAndDistributionExternalSaleOrderType() throws CustomException {
	return fetchObject(SAPInterfaceQuery.GET_SALE_AND_DISTRIBUTION_EXTERNAL_SALE_ORDER_TYPE.getQueryId(), null, sqlSession);
}

}
