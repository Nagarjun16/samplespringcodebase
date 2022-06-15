package com.ngen.cosys.EAWBShipmentDiscrepancyReport.Service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.EAWBShipmentDiscrepancyReport.DAO.EAWBShipmentDiscrepancyReportDAO;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.EAWBShipmentDiscrepancyReportParentModel;
import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitParentModel;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class EAWBShipmentDiscrepancyReportServiceImpl implements EAWBShipmentDiscrepancyReportService {

   @Autowired
   EAWBShipmentDiscrepancyReportDAO dao;
   
   @Override
   public List<String> getShipemntInformation() throws CustomException {
      
      return dao.getShipemntInformationForACarrier();
   }
   
   @Override
   public List<String> getEmailAddressesForEAWB(String carrierCode) throws CustomException {
      
      return dao.getEmailAddressesForEAWB(carrierCode);
   }
   
   @Override
   public List<String> getEmailAddressesForNEAWB(String carrierCode) throws CustomException {
      
      return dao.getEmailAddressesForNEAWB(carrierCode);
   }

   

   @Override
   public List<String> getShipemntInformationForNAWB() throws CustomException {

      return dao.getShipemntInformationForACarrierForNAWB();
   }

   @Override
   public List<NonAWBLowStockLimitParentModel> getShipemntInformationAwbStockLimit() throws CustomException {

      return dao.getShipemntInformationAwbStockLimit();
   }

   @Override
   public void revertUnUsedButReservedAWBstoStockAWBs(BigInteger awbStockId) throws CustomException {
      
      dao.revertUnUsedButReservedAWBstoStockAWBs(awbStockId);
      
   }

   @Override
   public List<String> getEmailAddressesForNEAWBLowStock(String carrierCode) throws CustomException {
      return dao.getEmailAddressesForNEAWBLowStock(carrierCode);
   }

}
