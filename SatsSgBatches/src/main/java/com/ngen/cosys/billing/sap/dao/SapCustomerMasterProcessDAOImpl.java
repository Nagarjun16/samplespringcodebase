package com.ngen.cosys.billing.sap.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.billing.sap.enums.Indicator;
import com.ngen.cosys.billing.sap.enums.query.SAPInterfaceQuery;
import com.ngen.cosys.billing.sap.model.CustomerInfo;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("sapcustomermasterprocessdao")
public class SapCustomerMasterProcessDAOImpl extends BaseDAO implements SapCustomerMasterProcessDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   private static Logger logger = LoggerFactory.getLogger(SapCustomerMasterProcessDAOImpl.class);

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.billing.sap.service.SapCustomerMasterProcessImpl#searchCode(
    * com.ngen.cosys.billing.sap.model.CustomerInfo)
    */

   @Override
   public List<CustomerInfo> saveSapCustomerMasterInfo(List<CustomerInfo> cutomerDataList) throws CustomException {
      logger.info("File data is saving in database {}", ' ');
      if (!CollectionUtils.isEmpty(cutomerDataList)) {

         List<CustomerInfo> insertCutomerInfo = cutomerDataList.stream().filter(cbni -> Indicator.INSERT.toString().equals(cbni.getIndicator())).collect(Collectors.toList());
         List<CustomerInfo> updateCutomerInfo = cutomerDataList.stream().filter(cbnu -> Indicator.UPDATE.toString().equals(cbnu.getIndicator())).collect(Collectors.toList());
         List<CustomerInfo> deleteCutomerInfo = cutomerDataList.stream().filter(cbnd -> Indicator.DELETE.toString().equals(cbnd.getIndicator())).collect(Collectors.toList());

         if (!CollectionUtils.isEmpty(insertCutomerInfo)) {
            super.insertList(SAPInterfaceQuery.INSERT_CUSTOMER_MASTER.getQueryId(), insertCutomerInfo, sqlSession);
         }
         if (!CollectionUtils.isEmpty(updateCutomerInfo)) {
            super.insertList(SAPInterfaceQuery.UPDATE_CUSTOMER_MASTER.getQueryId(), updateCutomerInfo, sqlSession);
         }
         if (!CollectionUtils.isEmpty(deleteCutomerInfo)) {
            super.insertList(SAPInterfaceQuery.DELETE_CUSTOMER_MASTER.getQueryId(), deleteCutomerInfo, sqlSession);
         }
      }

      return cutomerDataList;
   }

}
