package com.ngen.cosys.impbd.tracing.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightModel;
import com.ngen.cosys.impbd.tracing.model.BreakDownTracingFlightSegmentModel;

@Repository
public class BreakDownTracingDAOImpl extends BaseDAO implements BreakDownTracingDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

   @Override
   public List<BreakDownTracingFlightModel> fetchTracingList(BreakDownTracingFlightModel flightBreakdownData)
         throws CustomException {
      return fetchList("sqlGetTracingFlightInfo",flightBreakdownData,sqlSession);
   }

   @Override
   public BigInteger getUldCount(BreakDownTracingFlightSegmentModel segmentData) throws CustomException {
      
      return fetchObject("sqlGetULDCount",segmentData,sqlSession);
   }

}