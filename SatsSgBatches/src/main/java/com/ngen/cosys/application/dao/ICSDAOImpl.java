package com.ngen.cosys.application.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.ICSOperativeFlightRequestModel;
import com.ngen.cosys.model.OperativeFlightModel;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository
public class ICSDAOImpl extends BaseDAO implements ICSDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public ICSOperativeFlightRequestModel getOperativeFlightDetails() throws CustomException {
		List<OperativeFlightModel> operativeFlightList = fetchList("fetchOperativeFlightDetails", MultiTenantUtility.getAirportCityMap(""), sqlSession);
		ICSOperativeFlightRequestModel responseModel = new ICSOperativeFlightRequestModel();
		responseModel.setOperativeFlightList(operativeFlightList);
		return responseModel;
	}

	@Override
	public ICSOperativeFlightRequestModel getAdhocPushFlightDetails() throws CustomException {
		String systemParamDate = this.fetchObject("fetchSystemParamDateTime", null, sqlSession);
 		List<OperativeFlightModel> operativeFlightList = fetchList("fetchOperativeFlightDetailsAdochPush",
				MultiTenantUtility.getAirportCityMap(systemParamDate), sqlSession);
		ICSOperativeFlightRequestModel responseModel = new ICSOperativeFlightRequestModel();
		responseModel.setOperativeFlightList(operativeFlightList);
		return responseModel;
	}

	@Override
	public void updateSystenParamCreatedDateTime(LocalDateTime currentTime) throws CustomException {
		this.updateData("updateSystemParamCreatedDateTime", currentTime, sqlSession);

	}

}