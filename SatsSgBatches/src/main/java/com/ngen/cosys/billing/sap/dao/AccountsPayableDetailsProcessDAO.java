package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import com.ngen.cosys.billing.sap.model.AccountsPayableDetails;
import com.ngen.cosys.framework.exception.CustomException;

public interface AccountsPayableDetailsProcessDAO {
   List<AccountsPayableDetails> fetchAccountsPayableDetails() throws CustomException;

   public String getAccountPayableFileSequencesNumber() throws CustomException;
   
   public void updateCustomerPostingStatus(List<Long> billGenIds) throws CustomException;
}
