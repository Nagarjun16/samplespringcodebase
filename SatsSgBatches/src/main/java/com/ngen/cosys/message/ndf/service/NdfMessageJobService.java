package com.ngen.cosys.message.ndf.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.ndf.model.NdfMessageModel;

public interface NdfMessageJobService {
	List<NdfMessageModel> getNdfMessageDefinition() throws CustomException;
	
}
