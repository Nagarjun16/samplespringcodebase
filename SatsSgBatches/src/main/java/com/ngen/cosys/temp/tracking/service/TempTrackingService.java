package com.ngen.cosys.temp.tracking.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.temp.tracking.model.TempTrackingRequestModel;

public interface TempTrackingService {

   public List<TempTrackingRequestModel> getTempTrackingRequest() throws CustomException;

}
