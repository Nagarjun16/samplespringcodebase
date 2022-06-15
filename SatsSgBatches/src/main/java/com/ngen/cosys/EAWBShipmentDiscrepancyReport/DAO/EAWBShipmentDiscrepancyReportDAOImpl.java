package com.ngen.cosys.EAWBShipmentDiscrepancyReport.DAO;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitParentModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class EAWBShipmentDiscrepancyReportDAOImpl extends BaseDAO implements EAWBShipmentDiscrepancyReportDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public List<String> getShipemntInformationForACarrier() throws CustomException {

      return this.fetchList("getShipemntByCarrierForEAWB", null, sqlSession);
   }
   
   @Override
   public List<String> getEmailAddressesForEAWB(String carrierCode) throws CustomException {
      return this.fetchList("getEmailAddressesForEawb", carrierCode, sqlSession);
   }
   
   @Override
   public List<String> getEmailAddressesForNEAWB(String carrierCode) throws CustomException {
      
      return this.fetchList("getEmailAddressesForNEawb", carrierCode, sqlSession);
   }
   

   @Override
   public List<String> getShipemntInformationForACarrierForNAWB()
         throws CustomException {
      return this.fetchList("getShipemntByCarrierForNEAWB", null, sqlSession);
   }

   @Override
   public List<NonAWBLowStockLimitParentModel> getShipemntInformationAwbStockLimit() throws CustomException {
      
      return this.fetchList("getAWBLowStockData", null, sqlSession);
   }

   @Override
   public void revertUnUsedButReservedAWBstoStockAWBs(BigInteger awbStockId) throws CustomException {
     this.updateData("revertUnUsedButReservedAWBstoStockAWBs", awbStockId, sqlSession);
      
   }

   @Override
   public List<String> getEmailAddressesForNEAWBLowStock(String carrierCode) throws CustomException {
      return this.fetchList("getEmailAddressesForNEawbLowStock", carrierCode, sqlSession);
   }


}
