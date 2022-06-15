package com.ngen.cosys.AirmailStatus.Service;

import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsRequestType;
import com.ibsplc.icargo.business.mailtracking.defaults.types.standard.SaveMailDetailsResponseType;
import com.ngen.cosys.framework.exception.CustomException;

public interface CamsConnectorConfigService {

	public  SaveMailDetailsResponseType connectorConfig(SaveMailDetailsRequestType req) throws CustomException;
}
