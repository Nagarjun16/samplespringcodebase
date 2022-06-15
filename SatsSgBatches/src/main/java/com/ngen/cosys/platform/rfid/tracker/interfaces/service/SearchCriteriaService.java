package com.ngen.cosys.platform.rfid.tracker.interfaces.service;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchRequest;

@Service
public interface SearchCriteriaService {

	int getConfirmSearchCriteria(SearchRequest request) throws CustomException;

}
