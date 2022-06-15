/**
 * Label Data Access Object
 * 
 * @copyright SATS Singapore 2019-20
 */
package com.ngen.cosys.report.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.report.model.ReportLabel;

/**
 * Label Data Access Object
 */
@Repository
public class LabelDAOImpl extends BaseDAO implements LabelDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	/**
	 * @see com.ngen.cosys.report.dao.LabelDAO#getI18NLabels(java.lang.String)
	 */
	@Override
	public List<ReportLabel> getI18NLabels(String locale) throws CustomException {
		return super.fetchList("sqlGetReportI18NLabels", locale, sqlSession);
	}

	@Override
	public String getTenantCityName(String cityCode) throws CustomException {
		return this.fetchObject("sqlGetTenantCityName", cityCode, sqlSession);
	}

}
