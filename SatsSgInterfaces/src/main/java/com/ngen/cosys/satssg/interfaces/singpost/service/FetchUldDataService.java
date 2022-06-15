package com.ngen.cosys.satssg.interfaces.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDDataRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDSuccessResponseModel;

public interface FetchUldDataService {

   FetchULDSuccessResponseModel fetchUldData(FetchULDDataRequestModel fetchULDDataRequestModel) throws CustomException;
}
