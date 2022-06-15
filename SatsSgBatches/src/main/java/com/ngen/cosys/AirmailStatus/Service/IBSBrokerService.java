package com.ngen.cosys.AirmailStatus.Service;

import com.ibsplc.icargo.business.admin.accesscontrol.types.standard.LoginRequestType;
import com.ngen.cosys.framework.exception.CustomException;

public interface IBSBrokerService {

   String fetchLoginToken(LoginRequestType loginRequest) throws CustomException;

}
