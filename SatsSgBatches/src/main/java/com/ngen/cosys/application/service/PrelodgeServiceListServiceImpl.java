package com.ngen.cosys.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ngen.cosys.application.dao.PrelodgeServiceListDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class PrelodgeServiceListServiceImpl implements PrelodgeServiceListService {
	
	@Autowired
	PrelodgeServiceListDAO prelodgeServiceListDAO;

	@Override
	public void deleteFromServiceList() throws CustomException {
		prelodgeServiceListDAO.deleteFromServiceList();
		
	}

}
