package com.ngen.cosys.satssg.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.PushMailBagRequestModel;


public interface ProduceBagHandoverToAirlineService {

   PushMailBagRequestModel pushBagHandoverToAirlineStatus(Object value) throws CustomException;

}
