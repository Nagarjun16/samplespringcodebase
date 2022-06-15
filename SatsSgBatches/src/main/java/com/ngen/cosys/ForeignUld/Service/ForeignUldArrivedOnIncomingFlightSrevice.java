package com.ngen.cosys.ForeignUld.Service;

import java.util.List;

import com.ngen.cosys.ForeignUld.Model.ForeignUldArrivedOnIncomingFlightModel;
import com.ngen.cosys.framework.exception.CustomException;

public interface ForeignUldArrivedOnIncomingFlightSrevice {

   String getReportOnTheBasisOfCarriercode() throws CustomException;

   List<String> getEmailAddresses(String carrierCode) throws CustomException;

   void updateLatestFromDateForForeignULD() throws CustomException;

   ForeignUldArrivedOnIncomingFlightModel getParametersForForeignUldsReport() throws CustomException;

}
