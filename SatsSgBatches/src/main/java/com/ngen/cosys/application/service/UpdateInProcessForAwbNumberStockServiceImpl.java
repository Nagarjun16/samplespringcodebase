package com.ngen.cosys.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.UpdateInProcessForAwbNumberStockDao;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class UpdateInProcessForAwbNumberStockServiceImpl implements UpdateInProcessForAwbNumberStockService{
    
	@Autowired
	private UpdateInProcessForAwbNumberStockDao updateInProcessForAwbNumberStockDao; 
	
	@Override
	public void UpdateInProcessForAwbNumberStock() throws CustomException {
		updateInProcessForAwbNumberStockDao.UpdateInProcessForAwbNumberStock();
	}

	
}
