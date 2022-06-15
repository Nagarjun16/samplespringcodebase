package com.ngen.cosys.EAWBShipmentDiscrepancyReport.Service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.EAWBShipmentDiscrepancyReport.Model.NonAWBLowStockLimitParentModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface EAWBShipmentDiscrepancyReportService {
   
   List<String> getShipemntInformation () throws CustomException;
   
   List<String> getEmailAddressesForEAWB(String carrierCode) throws CustomException;
   
   List<String> getShipemntInformationForNAWB () throws CustomException;
   
   List<NonAWBLowStockLimitParentModel> getShipemntInformationAwbStockLimit () throws CustomException;

   List<String> getEmailAddressesForNEAWB(String carrierCode) throws CustomException;

   void revertUnUsedButReservedAWBstoStockAWBs(BigInteger awbStockId) throws CustomException;
   List<String> getEmailAddressesForNEAWBLowStock(String carrierCode) throws CustomException;

}
