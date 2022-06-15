package com.ngen.cosys.shipment.inactive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.inactive.dao.InactiveOldCargoDAO;
import com.ngen.cosys.shipment.inactive.model.InactiveSearch;
import com.ngen.cosys.shipment.inactive.model.InactiveSearchList;

@Service
@Transactional
public class InactiveOldCargoServiceImpl implements InactiveOldCargoService {

   @Autowired
   private InactiveOldCargoDAO inactiveOldCargoDao;

   @Override
   public List<InactiveSearchList> serchInactiveList(InactiveSearch searchParams) throws CustomException {

      List<InactiveSearchList> list = inactiveOldCargoDao.getInactiveList(searchParams);

      if (!list.isEmpty())
         for (InactiveSearchList inactiveSearchList : list) {
            if (inactiveSearchList.getAwbPices() != null && inactiveSearchList.getAwbWaight() != null) {
               StringBuilder builder = new StringBuilder();
               String awbPiecesWeight = builder.append(inactiveSearchList.getAwbPices()).append("/")
                     .append(inactiveSearchList.getAwbWaight()).toString();
               inactiveSearchList.setAwbPicesWaight(awbPiecesWeight);
            }
            if (inactiveSearchList.getInventaryPices() != null && inactiveSearchList.getInventoryWaight() != null) {
               StringBuilder builder1 = new StringBuilder();
               String inventoryPiecesWeight = builder1.append(inactiveSearchList.getInventaryPices()).append("/")
                     .append(inactiveSearchList.getInventoryWaight()).toString();
               inactiveSearchList.setInventoryPiecesWaight(inventoryPiecesWeight);
            }
         }
      return list;
   }

   public String moveToFreightOut(InactiveSearch searchParams) throws CustomException {
      // Freight out the shipment
      return inactiveOldCargoDao.moveToFreightOut(searchParams);
   }

   protected boolean checkCarrierCode(InactiveSearch searchParams) throws CustomException {
      return false;
   }

   @Override
   public InactiveSearch defalutingDays(InactiveSearch searchParams) throws CustomException {
      return inactiveOldCargoDao.getDefaultCreationDays(searchParams);
   }
}