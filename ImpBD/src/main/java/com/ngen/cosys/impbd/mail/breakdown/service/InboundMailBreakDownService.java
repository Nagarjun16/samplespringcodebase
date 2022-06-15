package com.ngen.cosys.impbd.mail.breakdown.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownModel;
import com.ngen.cosys.impbd.mail.breakdown.model.InboundMailBreakDownShipmentModel;

public interface InboundMailBreakDownService {

   InboundMailBreakDownModel search(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownModel searchMailBreakDown(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownModel breakDown(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownShipmentModel splitMailBagNumber(InboundMailBreakDownShipmentModel requestModel);

   InboundMailBreakDownModel checkMailBag(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownModel searchMailBags(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownModel splitMailBag(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownModel mailbreakDown(InboundMailBreakDownModel requestModel) throws CustomException;

   InboundMailBreakDownModel updateContainerDestination(InboundMailBreakDownModel requestModel)throws CustomException;

   InboundMailBreakDownModel checkContainerDestinationForBreakDown(InboundMailBreakDownModel requestModel) throws CustomException;
}