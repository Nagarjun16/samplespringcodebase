package com.ngen.cosys.satssg.interfaces.singpost.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.dao.FetchULDDataDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDDataRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDSuccessResponseModel;

@Service
public class FatchUldDataServiceImpl implements FetchUldDataService {

   @Autowired
   private FetchULDDataDao fetchUldDataDao;

   @Override
   public FetchULDSuccessResponseModel fetchUldData(FetchULDDataRequestModel fetchULDDataRequestModel) throws CustomException {
      return fetchUldDataDao.fatchUldData(fetchULDDataRequestModel);
   }
}
