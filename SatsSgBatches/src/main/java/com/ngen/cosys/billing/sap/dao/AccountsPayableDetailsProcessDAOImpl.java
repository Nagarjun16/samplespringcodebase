package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.billing.sap.enums.query.SAPInterfaceQuery;
import com.ngen.cosys.billing.sap.model.AccountsPayableDetails;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("accountsPayableDetailsProcessDAOImpl")
public class AccountsPayableDetailsProcessDAOImpl extends BaseDAO implements AccountsPayableDetailsProcessDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   public SqlSession sqlSession;

   @Override
   public List<AccountsPayableDetails> fetchAccountsPayableDetails() throws CustomException {
      return fetchList(SAPInterfaceQuery.FETCH_ACCOUNT_PAYABLE_DETAILS.getQueryId(), null, sqlSession);
   }

   @Override
   public String getAccountPayableFileSequencesNumber() throws CustomException {
      return fetchObject(SAPInterfaceQuery.FETCH_ACCOUNT_PAYABLE_FILE_SEQ_NUMBER.getQueryId(), null, sqlSession);
   }

   
   @Override
   public void updateCustomerPostingStatus(List<Long> billGenIds) throws CustomException {
      super.updateData(SAPInterfaceQuery.UPDATE_BILLGEN_POSTING_STATUS.getQueryId(), billGenIds, sqlSession);

      super.updateData(SAPInterfaceQuery.UPDATE_APBILLENTRY_POSTING_STATUS.getQueryId(), billGenIds, sqlSession);
   }
   
}
