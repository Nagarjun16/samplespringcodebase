package com.ngen.cosys.billing.chargerecalculate.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

@Repository("chargerecalculateDAO")
public class ChargeRecalculateDAOImpl extends BaseDAO implements ChargeRecalculateDAO {

   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSessionROI;

   /**
    *
    */
   @Override
   public List<BigInteger> fetchShipmentsForRecalculation() throws CustomException {
      return fetchList("fetchShipmentsForRecalculation", null, sqlSessionROI);
   }
}