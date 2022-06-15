package com.ngen.cosys.satssg.singpost.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.singpost.model.MailBagRequestModel;

public interface ProduceHandoverToDNATADao {

   List<MailBagRequestModel> pushHandoverToDNATAStatus(Object value) throws CustomException;

}
