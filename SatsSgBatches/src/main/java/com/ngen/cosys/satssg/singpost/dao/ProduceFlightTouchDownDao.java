package com.ngen.cosys.satssg.singpost.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.FlightTouchDown;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;

public interface ProduceFlightTouchDownDao {

   List<FlightTouchDown> pushFlightTouchDownStatus(Object value) throws CustomException;

}
