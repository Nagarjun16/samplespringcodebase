package com.ngen.cosys.message.ndf.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.ndf.model.NdfMessageModel;

@Repository
public class NdfMessageJobDaoImpl extends BaseDAO implements NdfMessageJobDao {
	private static final String SQL_QUERY_FOR_FETCH_NDF_MESSAGE_DATA = "getTerminalsAndTelexAddressForNDF";
	private static final Object MESSAGE_TYPE = "NDF";
	private static final String GET_COSYS_ADDRESS = "getOriginatorAddressForNDF";
	private static final String GET_PASTDAYS_CONFIG = "getPastDaysTotriggerNDF";
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	@Override
	public List<NdfMessageModel> getNdfMessageDefinition() throws CustomException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("messageType", MESSAGE_TYPE);
		return this.fetchList(SQL_QUERY_FOR_FETCH_NDF_MESSAGE_DATA,queryMap,sqlSession);
	}
	@Override
	public String fetchOriginatorAddress() throws CustomException {
		return this.fetchObject(GET_COSYS_ADDRESS, null, sqlSession);
	}

	@Override
	public BigInteger fetchPreviousDaystoTrigger() throws CustomException {
		return this.fetchObject(GET_PASTDAYS_CONFIG, null, sqlSession);
	}
}
