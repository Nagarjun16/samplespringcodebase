package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.AssignULD;

public interface AssignULDService {

	AssignULD assignUld(AssignULD uld)throws CustomException;

	AssignULD getTareWeight(AssignULD uld)throws CustomException;


	AssignULD insertInventory(AssignULD uld)throws CustomException;


	
	public Integer isUldLoaded(AssignULD uld) throws CustomException;
}
