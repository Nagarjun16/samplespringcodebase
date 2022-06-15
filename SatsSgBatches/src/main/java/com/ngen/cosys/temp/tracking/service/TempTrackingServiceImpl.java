package com.ngen.cosys.temp.tracking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.temp.tracking.dao.TempTrackingDAO;
import com.ngen.cosys.temp.tracking.model.TempTrackingRequestModel;

@Service
public class TempTrackingServiceImpl implements TempTrackingService {

   @Autowired
   private TempTrackingDAO tempTrackingDAO;

   @Override
   public List<TempTrackingRequestModel> getTempTrackingRequest() throws CustomException {
      return tempTrackingDAO.getTempTrackingDetails();

   }

}
