package com.ngen.cosys.platform.rfid.tracker.interfaces.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightsModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;

@Repository
public class CosysRFIDMapperDAOImpl extends BaseDAO implements CosysRFIDMapperDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<FlightsModel> getFlightData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getFlightsData", searchFilterModel, sqlSession);
	}

	@Override
	public List<AwbModel> getImportFFMCarrShcData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getImportFFMCarrShcData", searchFilterModel, sqlSession);
	}

	@Override
	public List<AwbModel> getImportFFMShcData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getImportFFMShcData", searchFilterModel, sqlSession);
	}

	@Override
	public List<AwbModel> getFlightBookingCarrShcData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getFlightBookingCarrShcData", searchFilterModel, sqlSession);
	}

	@Override
	public List<AwbModel> getFlightBookingShcData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getFlightBookingShcData", searchFilterModel, sqlSession);
	}

	@Override
	public List<AwbModel> getFlightManifestedCarrShcData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getFlightManifestedCarrShcData", searchFilterModel, sqlSession);
	}

	@Override
	public List<AwbModel> getFlightManifestedShcData(SearchFilterModel searchFilterModel) throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchList("getFlightManifestedShcData", searchFilterModel, sqlSession);
	}

	@Override
	public AuthModel getAuthUserPassword() throws CustomException {
		// TODO Auto-generated method stub
		return super.fetchObject("getAuthUserPassword", "", sqlSession);
	}

}