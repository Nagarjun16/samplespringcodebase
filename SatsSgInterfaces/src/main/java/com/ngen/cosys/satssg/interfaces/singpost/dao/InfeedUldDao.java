package com.ngen.cosys.satssg.interfaces.singpost.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.InfeedULDRequestModel;

public interface InfeedUldDao {
	
	Integer infeedUldfromAirside(InfeedULDRequestModel infeedULDfromAirsideRequestModel) throws CustomException;

}
