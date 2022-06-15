package com.ngen.cosys.satssg.interfaces.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.PrintULDTagRequestModel;

public interface PrintUldTagService {

	String printUldTag(PrintULDTagRequestModel printULDTagRequestModel) throws CustomException;

}
