package com.ngen.cosys.impbd.vctinformation.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.vctinformation.model.VCTInformation;

public interface VctInformationService {
	
	VCTInformation fetch(VCTInformation vctInformationModel)throws CustomException;
	
	VCTInformation save(VCTInformation vctInformationModel)throws CustomException;

}
