package com.ngen.cosys.platform.rfid.tracker.interfaces.client.service;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;

@Service
public interface PostHttpUtilityService {

	public AuthModel getAuthUserPassword() throws CustomException;

	public void onPostExecute(ApiRequestModel apiRequest, String endPoint, AuthModel auth) throws CustomException;

}