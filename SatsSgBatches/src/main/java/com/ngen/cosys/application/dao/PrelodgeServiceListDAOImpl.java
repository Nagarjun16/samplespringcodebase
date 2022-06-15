package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.application.model.PrelodgeShipmentJobModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("prelodgeServiceListDAO")
public class PrelodgeServiceListDAOImpl extends BaseDAO implements PrelodgeServiceListDAO{
	
	private static final String SQL_DELETE_SERVICE_NUMBER_LIST = "deleteServiceNumberJob";
	private static final String SQL_UPDATE_NULL_SERVICE_PRELODGE="updateServiceNoNullJob";
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public void deleteFromServiceList() throws CustomException {
		List<PrelodgeShipmentJobModel> prelodgeShipmentList = super.fetchList("fetchPrelodgeServiceList", null, sqlSessionTemplate);

		if(!CollectionUtils.isEmpty(prelodgeShipmentList)) {
			super.deleteData(SQL_DELETE_SERVICE_NUMBER_LIST, prelodgeShipmentList, sqlSessionTemplate);
			
			super.updateData(SQL_UPDATE_NULL_SERVICE_PRELODGE, prelodgeShipmentList, sqlSessionTemplate);
		}
		
	}

}
