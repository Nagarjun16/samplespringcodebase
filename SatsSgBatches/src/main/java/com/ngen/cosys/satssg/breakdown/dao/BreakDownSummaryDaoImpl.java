package com.ngen.cosys.satssg.breakdown.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummary;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryModel;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryProvider;
import com.ngen.cosys.satssg.breakdown.model.BreakDownSummaryTonnageHandledModel;
import com.ngen.cosys.satssg.breakdown.model.FlightDetails;

@Repository
public class BreakDownSummaryDaoImpl extends BaseDAO implements BreakDownSummaryDao  {
	
	   @Autowired
	   @Qualifier("sqlSessionTemplate")
	   private SqlSessionTemplate sqlSession;

	@Override
	public List<FlightDetails> getFlightDetails() throws CustomException {
		
		return fetchList("sqlGetFlightDetails",null,sqlSession);
	}

	@Override
	public BreakDownSummaryModel getSummaryDetails(FlightDetails flightData) throws CustomException {
		
		return fetchObject("sqlGetBreakdownULDSummary",flightData,sqlSession);
	}

	@Override
	public Integer createSummary(BreakDownSummary summaryData) throws CustomException {
		
		return insertData("insertBreakDownSummary",summaryData,sqlSession);
	}

	@Override
	public Integer createTonnageSummary(BreakDownSummaryTonnageHandledModel tonnageData) throws CustomException {
		
		return insertData("insertBreakDownTonnageSummaryInfo",tonnageData,sqlSession);
	}

	@Override
	public Integer updateFlightComplete(FlightDetails flightData) throws CustomException {
		
		return updateData("updateFlightComplete",flightData,sqlSession);
	}

	@Override
	public Boolean checkSummaryExists(BreakDownSummary summaryData) throws CustomException {
		return fetchObject("sqlCheckSummaryExists",summaryData,sqlSession);
	}

	@Override
	public Integer updateBreakDownSummary(BreakDownSummary breakDownSummary) throws CustomException {
		return updateData("updateBreakDownSummary",breakDownSummary,sqlSession);
	}

	@Override
	public BreakDownSummaryProvider getServiceContractorDetails(FlightDetails flightData) throws CustomException {
		return fetchObject("sqlGetBDServiceProvider",flightData,sqlSession);
	}

}
