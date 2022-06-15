package com.ngen.cosys.shipment.airmail.service;

import java.util.List;

import com.ngen.cosys.shipment.airmail.model.LyingListContainer;
import com.ngen.cosys.shipment.airmail.model.SearchForLyingList;
import com.ngen.cosys.framework.exception.CustomException;

public interface LyingListService {
	
	List<LyingListContainer> getSearchedLyingList(SearchForLyingList param) throws CustomException;
}
