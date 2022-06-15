package com.ngen.cosys.impbd.vctinformation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.vctinformation.dao.vctInformationDAO;
import com.ngen.cosys.impbd.vctinformation.model.VCTInformation;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class VctInformationServiceImpl implements VctInformationService {
	
	@Autowired
	private vctInformationDAO vctInformationDAO;
	

	@Override
	public VCTInformation fetch(VCTInformation vctInformationModel) throws CustomException {
		
		return vctInformationDAO.getImportExportInfo(vctInformationModel);
	}

	@Override
	public VCTInformation save(VCTInformation vctInformationModel) throws CustomException {
		
		return vctInformationDAO.save(vctInformationModel);
	}

}
