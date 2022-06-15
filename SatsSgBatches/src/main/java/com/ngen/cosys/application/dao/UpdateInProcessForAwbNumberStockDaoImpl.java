package com.ngen.cosys.application.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository()
public class UpdateInProcessForAwbNumberStockDaoImpl extends BaseDAO implements UpdateInProcessForAwbNumberStockDao {
    
	@Autowired
	@Qualifier("sqlSessionTemplate")
	SqlSession sqlSessionShipment;
	
	@Override
	public void UpdateInProcessForAwbNumberStock() throws CustomException {
		this.updateData("UpdateInProcessForAwbNumberStock", "", sqlSessionShipment);  
	}

}
