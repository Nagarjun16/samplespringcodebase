package com.ngen.cosys.application.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Repository("consolidatedShipmentInfoDAO")
public class ConsolidatedShipmentInfoDAOImpl extends BaseDAO implements ConsolidatedShipmentInfoDAO {

   /**
    * Default generated serial version id
    */
   private static final long serialVersionUID = 1590919053656791952L;

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   @Override
   public List<FlightModel> getFlightInfo() throws CustomException {
      return super.fetchList("getFlightsToSendMrs", null, sqlSessionTemplate);
   }


	@Override
	public String getSendmrsUrl() throws CustomException {
		String data = fetchObject("mrsConfigUrl", null, sqlSessionTemplate);
		return data;
	}
	@Override
	public List<FlightModel> getACESExportSQFlights() throws CustomException {
		return super.fetchList("getACESExportSQFlightsToSendMrs", MultiTenantUtility.getAirportCityMap(""), sqlSessionTemplate);
	}
	
	@Override
	public List<FlightModel> getACESExportOALFlights() throws CustomException {
		return super.fetchList("getACESExportOALFlightsToSendMrs", MultiTenantUtility.getAirportCityMap(""), sqlSessionTemplate);
	}
	
	@Override
	public List<FlightModel> getACESImportSQFlights() throws CustomException {
		return super.fetchList("getACESImportSQFlightsToSendMrs", MultiTenantUtility.getAirportCityMap(""), sqlSessionTemplate);
	}
	
	@Override
	public List<FlightModel> getACESImportOALFlights() throws CustomException {
		return super.fetchList("getACESImportOALFlightsToSendMrs", MultiTenantUtility.getAirportCityMap(""), sqlSessionTemplate);
	}
	
}