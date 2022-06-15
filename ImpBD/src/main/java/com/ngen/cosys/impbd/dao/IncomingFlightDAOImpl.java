package com.ngen.cosys.impbd.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.DisplayIncomigFlightConfigurationTime;
import com.ngen.cosys.impbd.model.IncomingFlightModel;
import com.ngen.cosys.impbd.model.IncomingFlightQuery;

/**
 * DAO for Display Incoming Flight
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("incomingFlightDao")
public class IncomingFlightDAOImpl extends BaseDAO implements IncomingFlightDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	/*
	 * 
	 * Fetch all Incoming Flight with specified date range
	 * 
	 */
	@Override
	public List<IncomingFlightModel> fetch(IncomingFlightQuery incomingFlightModel) throws CustomException {
		List<IncomingFlightModel> finalist = new ArrayList<>();
		if (incomingFlightModel.getCarrierGroup() != null && !incomingFlightModel.getCarrierGroup().isEmpty()) {
			for (String element : incomingFlightModel.getCarrierGroup()) {
				List<IncomingFlightModel> list = null;
				incomingFlightModel.setCarrierGp(element);
				list = super.fetchList("getIncomingFlight", incomingFlightModel, sqlSession);
				finalist.addAll(list);
			}

		} else {
			finalist = super.fetchList("getIncomingFlight", incomingFlightModel, sqlSession);
		}
		return finalist;
	}
	@Override
	public List<String> fetchTelexMessage (IncomingFlightQuery incomingFlightQuery) throws CustomException{
		return super.fetchList("getIncomingTelexMessage", incomingFlightQuery, sqlSession);
		
	}

	@Override
	public DisplayIncomigFlightConfigurationTime fetchTime(DisplayIncomigFlightConfigurationTime incomingFlightModel)
			throws CustomException {
		DisplayIncomigFlightConfigurationTime configuration = new DisplayIncomigFlightConfigurationTime();

		configuration.setFromDate(super.fetchObject("configurableFromTime", incomingFlightModel, sqlSession));
		configuration.setToDate(super.fetchObject("configurableToTime", incomingFlightModel, sqlSession));

		return configuration;
	}

	@Override
	public List<IncomingFlightModel> getMyFlights(DisplayIncomigFlightConfigurationTime incomingFlightModel)
			throws CustomException {

		return super.fetchList("getMyFlightDetails", incomingFlightModel, sqlSession);
	}
}