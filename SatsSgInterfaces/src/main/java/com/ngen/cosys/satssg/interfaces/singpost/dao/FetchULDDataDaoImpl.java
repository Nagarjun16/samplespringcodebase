package com.ngen.cosys.satssg.interfaces.singpost.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.common.validator.LoadingMoveableLocationValidator;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDDataRequestModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.FetchULDSuccessResponseModel;

@Repository("FetchULDDataDao")
public class FetchULDDataDaoImpl extends BaseDAO implements FetchULDDataDao {

	@Autowired
	private SqlSession sqlSessionTemplate;

	@Autowired
	LoadingMoveableLocationValidator loadingmoveablelocationvalidator;

	@Override
	public FetchULDSuccessResponseModel fatchUldData(FetchULDDataRequestModel fetchULDDataRequestModel)
			throws CustomException {

		MoveableLocationTypeModel requestModel = new MoveableLocationTypeModel();
		requestModel.setKey(fetchULDDataRequestModel.getContainerId());
		MoveableLocationTypeModel moveableLocationTypeModel = loadingmoveablelocationvalidator.split(requestModel);
		if (moveableLocationTypeModel != null) {
			fetchULDDataRequestModel.setUldKey(moveableLocationTypeModel.getPart1());
			fetchULDDataRequestModel.setUldNumber(moveableLocationTypeModel.getPart2());
			fetchULDDataRequestModel.setCarrierCode(moveableLocationTypeModel.getPart3());
		}
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("tenantAirport", requestModel.getTenantAirport());
		paramMap.put("containerId", fetchULDDataRequestModel.getContainerId());
		paramMap.put("uldKey", fetchULDDataRequestModel.getUldKey());
		paramMap.put("carrierCode", fetchULDDataRequestModel.getCarrierCode());
		FetchULDSuccessResponseModel responseModel = fetchObject("fetchULDData", paramMap,
				sqlSessionTemplate);
		//set nogRemarks to the response
		String nogRemarks = fetchObject("getLoadedULDUTPShcICS", fetchULDDataRequestModel.getContainerId(),
				sqlSessionTemplate);
		responseModel.setNogRemarks(nogRemarks);
		return responseModel;

	}

}
