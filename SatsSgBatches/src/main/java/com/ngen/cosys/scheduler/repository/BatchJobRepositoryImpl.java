package com.ngen.cosys.scheduler.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.scheduler.model.BatchJobModel;

@Repository
public class BatchJobRepositoryImpl extends BaseDAO implements BatchJobRepository {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<BatchJobModel> getJobs() throws CustomException {
		return this.fetchList("sqlGetBatchJob", null, sqlSessionTemplate);
	}
	
	@Override
	public void reinitiateMessages(BatchJobModel requestModel) throws CustomException {
		this.updateData("sqlUpdateMessageStatus", requestModel, sqlSessionTemplate);
		this.updateData("sqlUpdateMessageCreatedDateTime", requestModel, sqlSessionTemplate);
	}
	
	@Override
	public void cleanUpJob(BatchJobModel requestModel) throws CustomException {
		 this.deleteData("sqlDeleteFiredTriggers", requestModel, sqlSessionTemplate);
		 this.updateData("sqlUpdateTriggerState", requestModel, sqlSessionTemplate);
	}

}