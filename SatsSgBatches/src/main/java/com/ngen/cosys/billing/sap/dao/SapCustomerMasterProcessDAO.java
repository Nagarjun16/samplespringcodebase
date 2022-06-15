package com.ngen.cosys.billing.sap.dao;

import java.util.List;

import com.ngen.cosys.billing.sap.model.CustomerInfo;
import com.ngen.cosys.framework.exception.CustomException;

public interface SapCustomerMasterProcessDAO {

   List<CustomerInfo> saveSapCustomerMasterInfo(List<CustomerInfo> cutomerDataList) throws CustomException;

}
