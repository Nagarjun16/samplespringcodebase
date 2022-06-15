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
import com.ngen.cosys.billing.sap.model.MaterialInfo;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("sapMaterialMasterProcessdao")
public class SapMaterialMasterProcessDAOImpl extends BaseDAO implements SapMaterialMasterProcessDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSession;

   private static Logger logger = LoggerFactory.getLogger(SapMaterialMasterProcessDAOImpl.class);

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.billing.sap.service.SapMaterialMasterProcessImpl#searchCode(
    * com.ngen.cosys.billing.sap.model.MaterialInfo)
    */

   @Override
   public List<MaterialInfo> saveSapMaterialMasterInfo(List<MaterialInfo> materialDataList) throws CustomException {
      logger.info("File data is saving in database {}", ' ');
      if (!CollectionUtils.isEmpty(materialDataList)) {

         List<MaterialInfo> insertMaterialInfo = materialDataList.stream().filter(cbni -> Indicator.INSERT.toString().equals(cbni.getIndicator())).collect(Collectors.toList());
         List<MaterialInfo> updateMaterialInfo = materialDataList.stream().filter(cbnu -> Indicator.UPDATE.toString().equals(cbnu.getIndicator())).collect(Collectors.toList());
         List<MaterialInfo> deleteMaterialInfo = materialDataList.stream().filter(cbnd -> Indicator.DELETE.toString().equals(cbnd.getIndicator())).collect(Collectors.toList());

         if (!CollectionUtils.isEmpty(insertMaterialInfo)) {
            super.insertList(SAPInterfaceQuery.INSERT_MATERIAL_MASTER.getQueryId(), insertMaterialInfo, sqlSession);
         }
         if (!CollectionUtils.isEmpty(updateMaterialInfo)) {
            super.insertList(SAPInterfaceQuery.UPDATE_MATERIAL_MASTER.getQueryId(), updateMaterialInfo, sqlSession);
         }
         if (!CollectionUtils.isEmpty(deleteMaterialInfo)) {
            super.insertList(SAPInterfaceQuery.DELETE_MATERIAL_MASTER.getQueryId(), deleteMaterialInfo, sqlSession);
         }
      }

      return materialDataList;
   }
}
