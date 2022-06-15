package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ICSUpdateOperativeFlightRequestModel;


public interface ICSUpdateOperativeFlightDAO {

	 public ICSUpdateOperativeFlightRequestModel getUpdatedOperativeFlightDetails() throws CustomException;
	
}
