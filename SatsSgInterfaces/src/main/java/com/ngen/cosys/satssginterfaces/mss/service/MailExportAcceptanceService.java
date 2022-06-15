package com.ngen.cosys.satssginterfaces.mss.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptance;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceDetails;
import com.ngen.cosys.satssginterfaces.mss.model.MailExportAcceptanceRequest;

public interface MailExportAcceptanceService {

	/**
	 * @param request
	 * @return
	 * @throws CustomException
	 */
	MailExportAcceptanceRequest insertData(MailExportAcceptanceRequest request) throws CustomException;


   /**
    * @param request
    * @return
    * @throws CustomException
    */
   MailExportAcceptanceRequest getPAFlightMO(MailExportAcceptanceRequest request) throws CustomException;
}
