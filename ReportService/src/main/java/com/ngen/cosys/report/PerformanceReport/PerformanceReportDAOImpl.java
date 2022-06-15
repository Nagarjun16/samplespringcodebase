package com.ngen.cosys.report.PerformanceReport;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.service.poi.model.PerformanceReportModel;

@Repository
public class PerformanceReportDAOImpl extends BaseDAO implements PerformanceReportDao {

	@Qualifier("sqlSessionTemplate")
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<PerformanceReportModel> getPerofrmanceReport(PerformanceReportModel requestModel)
			throws CustomException {

		return fetchList("getperofrmanceReport", requestModel, sqlSession);
	}

}
