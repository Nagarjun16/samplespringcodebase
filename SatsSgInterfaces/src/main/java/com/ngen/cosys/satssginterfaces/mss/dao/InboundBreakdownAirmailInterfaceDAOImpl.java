package com.ngen.cosys.satssginterfaces.mss.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.enums.InboundBreakDownAirmailInterfaceSqlId;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentHouseAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentInventoryAirmailInterfaceModel;
import com.ngen.cosys.satssginterfaces.mss.model.InboundBreakdownShipmentShcAirmailInterfaceModel;

@Repository
public class InboundBreakdownAirmailInterfaceDAOImpl extends BaseDAO implements InboundBreakdownAirmailInterfaceDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   @Override
   public Integer insertBreakDownULDTrolleyInfo(InboundBreakdownShipmentAirmailInterfaceModel inboundBreakdownShipmentModel)
         throws CustomException {
      return this.insertData(InboundBreakDownAirmailInterfaceSqlId.INSERT_BREAK_DOWN_ULD_TROLLEY_INFO.getQueryId(),
            inboundBreakdownShipmentModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownULDTrolleyInfo(InboundBreakdownShipmentAirmailInterfaceModel inboundBreakdownShipmentModel)
         throws CustomException {
      return this.updateData(InboundBreakDownAirmailInterfaceSqlId.UPDATE_BREAK_DOWN_ULD_TROLLEY_INFO.getQueryId(),
            inboundBreakdownShipmentModel, sqlSessionTemplate);
   }

   @Override
   public InboundBreakdownShipmentAirmailInterfaceModel selectBreakDownULDTrolleyInfo(
         InboundBreakdownShipmentAirmailInterfaceModel inboundBreakdownShipmentModel) throws CustomException {
      return this.fetchObject(InboundBreakDownAirmailInterfaceSqlId.SELECT_BREAK_DOWN_ULD_TROLLEY_INFO.getQueryId(),
            inboundBreakdownShipmentModel, sqlSessionTemplate);
   }
   
   @Override
   public InboundBreakdownShipmentInventoryAirmailInterfaceModel selectInboundBreakdownShipmentInventoryModel(
         InboundBreakdownShipmentInventoryAirmailInterfaceModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.fetchObject(InboundBreakDownAirmailInterfaceSqlId.SELECT_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }
   
   @Override
   public Integer insertBreakDownStorageInfo(
         InboundBreakdownShipmentInventoryAirmailInterfaceModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.insertData(InboundBreakDownAirmailInterfaceSqlId.INSERT_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }
   
   @Override
   public Integer updateBreakDownStorageInfo(
         InboundBreakdownShipmentInventoryAirmailInterfaceModel inboundBreakdownShipmentInventoryModel) throws CustomException {
      return this.updateData(InboundBreakDownAirmailInterfaceSqlId.UPDATE_BREAK_DOWN_STORAGE_INFO.getQueryId(),
            inboundBreakdownShipmentInventoryModel, sqlSessionTemplate);
   }
   
   @Override
   public Boolean checkBreakDownStorageSHCInfo(InboundBreakdownShipmentShcAirmailInterfaceModel inboundBreakdownShipmentShcModel)
         throws CustomException {
      return this.fetchObject(InboundBreakDownAirmailInterfaceSqlId.CHECK_BREAK_DOWN_STORAGE_SHC_INFO.getQueryId(),
            inboundBreakdownShipmentShcModel, sqlSessionTemplate);
   }

   @Override
   public Integer insertBreakDownStorageSHCInfo(InboundBreakdownShipmentShcAirmailInterfaceModel inboundBreakdownShipmentShcModel)
         throws CustomException {
      return this.insertData(InboundBreakDownAirmailInterfaceSqlId.INSERT_BREAK_DOWN_STORAGE_SHC_INFO.getQueryId(),
            inboundBreakdownShipmentShcModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownStorageSHCInfo(InboundBreakdownShipmentShcAirmailInterfaceModel inboundBreakdownShipmentShcModel)
         throws CustomException {
      return this.updateData(InboundBreakDownAirmailInterfaceSqlId.UPDATE_BREAK_DOWN_SHC_INFO.getQueryId(),
            inboundBreakdownShipmentShcModel, sqlSessionTemplate);

   }
   
   @Override
   public Boolean checkBreakDownShipmentHouseModel(
         InboundBreakdownShipmentHouseAirmailInterfaceModel inboundBreakdownShipmentHouseModel) throws CustomException {
      return this.fetchObject(InboundBreakDownAirmailInterfaceSqlId.CHECK_BREAK_DOWN_HOUSE_INFO.getQueryId(),
            inboundBreakdownShipmentHouseModel, sqlSessionTemplate);
   }

   @Override
   public Integer insertBreakDownShipmentHouseModel(
         InboundBreakdownShipmentHouseAirmailInterfaceModel inboundBreakdownShipmentHouseModel) throws CustomException {
      return this.insertData(InboundBreakDownAirmailInterfaceSqlId.INSERT_BREAK_DOWN_HOUSE_INFO.getQueryId(),
            inboundBreakdownShipmentHouseModel, sqlSessionTemplate);
   }

   @Override
   public Integer updateBreakDownShipmentHouseModel(
         InboundBreakdownShipmentHouseAirmailInterfaceModel inboundBreakdownShipmentHouseModel) throws CustomException {
      return this.updateData(InboundBreakDownAirmailInterfaceSqlId.UPDATE_BREAK_DOWN_HOUSE_INFO.getQueryId(),
            inboundBreakdownShipmentHouseModel, sqlSessionTemplate);
   }

}