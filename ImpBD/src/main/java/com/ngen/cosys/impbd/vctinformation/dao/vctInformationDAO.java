package com.ngen.cosys.impbd.vctinformation.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.verification.model.DocumentVerificationFlightModel;
import com.ngen.cosys.impbd.vctinformation.model.VCTInformation;
import com.ngen.cosys.impbd.vctinformation.model.VCTShipmentInformationModel;

public interface vctInformationDAO {
	
	VCTInformation getImportExportInfo(VCTInformation requestModel) throws CustomException;
	
	VCTInformation save(VCTInformation requesModel) throws CustomException;

}
