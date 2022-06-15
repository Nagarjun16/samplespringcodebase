package com.ngen.cosys.shipment.airmail.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.shipment.airmail.dao.LyingListDao;
import com.ngen.cosys.shipment.airmail.model.LyingListContainer;
import com.ngen.cosys.shipment.airmail.model.SearchForLyingList;
import com.ngen.cosys.shipment.airmail.service.LyingListService;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class LyingListServiceImpl implements LyingListService {
	@Autowired
	private LyingListDao lyingListDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public List<LyingListContainer> getSearchedLyingList(SearchForLyingList param) throws CustomException {
		List<LyingListContainer> response = lyingListDao.getSearchedLyingList(param);
		return response;
	}
}
