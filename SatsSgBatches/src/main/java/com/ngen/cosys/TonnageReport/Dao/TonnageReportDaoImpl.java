package com.ngen.cosys.TonnageReport.Dao;


import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.TonnageReport.model.InsertRequest;
import com.ngen.cosys.TonnageReport.model.TonnageReportRequest;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;


@Repository("TonnageReportDao")
public class TonnageReportDaoImpl extends BaseDAO implements TonnageReportDao  {

	   @Autowired
	   @Qualifier("sqlSessionTemplate")
	   private SqlSessionTemplate sqlSession;

	@Override
	public void savereportdata(InsertRequest insertdata) throws CustomException {
		int templateId = super.fetchObject("fetchTemplateId", insertdata,sqlSession);
		insertdata.setTemplateID(templateId);
		insertData("insertreportdata", insertdata,sqlSession);
		insertData("insertreportdatawithreportname", insertdata,sqlSession);
		
	}

	@Override
	public List<String> searchcarrriervalues(TonnageReportRequest tonnageReportRequest) throws CustomException {
		return super.fetchList("fetchlistofcarriers", tonnageReportRequest, sqlSession);
	}

	@Override
	public String searchentityvaluecombination(InsertRequest insertdata) throws CustomException {
		return super.fetchObject("fetch_entitytype_entitykeyvalue", insertdata, sqlSession);
	}

	@Override
	public Integer searchdatacount(InsertRequest insertdata) throws CustomException {
		return super.fetchObject("fetch_datacount", insertdata, sqlSession);
	}

	@Override
	public void updatereportdata(InsertRequest insertdata) throws CustomException {
		int templateId = super.fetchObject("fetchTemplateId", insertdata,sqlSession);
		insertdata.setTemplateID(templateId);
		updateData("updatereportdata", insertdata,sqlSession);
		updateData("updatereportdatawithreportname", insertdata,sqlSession);
		
	}
	
	
	/*@Override
	public List<String> searchautopublishvalues(InsertRequest insertdata) throws CustomException {
		return super.fetchList("fetchlistofautopublish", insertdata, sqlSession);
	}*/


}
