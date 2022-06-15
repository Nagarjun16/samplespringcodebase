package com.ngen.cosys.satssg.interfaces.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.UldAutoWeightModel;

public interface UpdateUldAutoWeightService {

	Integer updateUldAutoWeight (UldAutoWeightModel uldAutoWeightModel) throws CustomException;

}
