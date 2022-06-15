package com.ngen.cosys.ForeignUld.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.ForeignUld.Dao.ForeignUldArrivedOnIncomingFlightDao;
import com.ngen.cosys.ForeignUld.Model.ForeignUldArrivedOnIncomingFlightModel;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class ForeignUldArrivedOnIncomingFlightSreviceImpl implements ForeignUldArrivedOnIncomingFlightSrevice {

   @Autowired
   ForeignUldArrivedOnIncomingFlightDao dao;

   @Override
   public String getReportOnTheBasisOfCarriercode() throws CustomException {

      return dao.getReportOnTheBasisOfCarriercode();
   }

   @Override
   public List<String> getEmailAddresses(String carrierCode) throws CustomException {
      return dao.getEmailAddresses(carrierCode);
   }

   @Override
   public void updateLatestFromDateForForeignULD() throws CustomException {
      dao.updateLatestFromDateForForeignULD();

   }

   @Override
   public ForeignUldArrivedOnIncomingFlightModel getParametersForForeignUldsReport() throws CustomException {
      return dao.getParametersForForeignUldsReport();
   }

}
