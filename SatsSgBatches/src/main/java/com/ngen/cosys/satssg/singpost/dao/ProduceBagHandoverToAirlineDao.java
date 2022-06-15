package com.ngen.cosys.satssg.singpost.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;

public interface ProduceBagHandoverToAirlineDao {

   List<MailBagRequestModel> pushBagHandoverToAirlineStatus(Object value) throws CustomException;

}
