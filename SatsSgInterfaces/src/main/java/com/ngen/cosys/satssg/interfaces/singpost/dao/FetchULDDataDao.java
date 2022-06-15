package com.ngen.cosys.satssg.interfaces.singpost.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDDataRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDSuccessResponseModel;

public interface FetchULDDataDao {

   FetchULDSuccessResponseModel fatchUldData(FetchULDDataRequestModel fetchULDDataRequestModel) throws CustomException;

}
