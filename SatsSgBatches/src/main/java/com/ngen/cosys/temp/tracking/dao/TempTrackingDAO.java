package com.ngen.cosys.temp.tracking.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.temp.tracking.model.TempTrackingRequestModel;

public interface TempTrackingDAO {
   
   public List<TempTrackingRequestModel> getTempTrackingDetails() throws CustomException;
   
   
}
