package com.ngen.cosys.satssg.interfaces.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.InfeedULDRequestModel;

public interface InfeedUldService {

	Integer infeeduldfromAirside (InfeedULDRequestModel infeedULDfromAirsideRequestModel) throws CustomException;
}
