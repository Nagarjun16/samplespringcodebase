package com.ngen.cosys.platform.rfid.tracker.interfaces.client.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;

@Service
public interface PostHttpUtilitySearchCriteriaService {

	public AuthModel getAuthUserPassword() throws CustomException;

	public List<SearchFilterModel> onPostExecute(String endPoint, AuthModel auth) throws CustomException;

}