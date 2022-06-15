package com.ngen.cosys.satssg.interfaces.singpost.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.InfeedULDRequestModel;


@Repository("InfeedULDfromAirsideDao")
public class InfeedULDDaoImpl extends BaseDAO implements InfeedUldDao{

	@Autowired
	private SqlSession sqlSessionTemplate;
	
	
	@Override
	public Integer infeedUldfromAirside(InfeedULDRequestModel infeedULDfromAirsideRequestModel) throws CustomException {

		return updateData("infeedUldfromAirside", infeedULDfromAirsideRequestModel, sqlSessionTemplate);
	}

}
