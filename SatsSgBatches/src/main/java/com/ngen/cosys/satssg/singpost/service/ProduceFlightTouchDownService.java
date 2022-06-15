package com.ngen.cosys.satssg.singpost.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDownBase;

public interface ProduceFlightTouchDownService {

   FlightTouchDownBase pushFlightTouchDownStatus(Object value) throws CustomException;

}
