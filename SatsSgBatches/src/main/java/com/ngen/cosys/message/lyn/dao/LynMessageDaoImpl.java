package com.ngen.cosys.message.lyn.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.lyn.model.LynMessage;

@Repository
public class LynMessageDaoImpl extends BaseDAO implements LynMessageDao {

	private final String GET_LYN_MESSAGE_DETAILS = "getLynmessageDataForBatch";
	private final String MESSAGE_TYPE = "LYN";
	private final String MESSAGE_TYPE_FSL = "FSL";
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<LynMessage> getLynMessageDefinition() throws CustomException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("messageType", MESSAGE_TYPE);
		return this.fetchList(GET_LYN_MESSAGE_DETAILS, queryMap, sqlSession);
	}

	@Override
	public List<LynMessage> getFSLMessageDefinition() throws CustomException {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("messageType", MESSAGE_TYPE_FSL);
		return this.fetchList(GET_LYN_MESSAGE_DETAILS, queryMap, sqlSession);
	}

}
