package com.ngen.cosys.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.application.dao.ConsolidatedShipmentInfoDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.FlightModel;

@Service
@Transactional
public class ConsolidatedShipmentInfoServiceImpl implements ConsolidatedShipmentInfoService {

   @Autowired
   ConsolidatedShipmentInfoDAO consolidatedShipmentInfoDAO;

   @Override
   public List<FlightModel> getFlightInfo() throws CustomException {
      return consolidatedShipmentInfoDAO.getFlightInfo();

   }


	@Override
	public String getSendmrsUrl() throws CustomException {
		return consolidatedShipmentInfoDAO.getSendmrsUrl();
	}


	@Override
	public List<FlightModel> getACESExportSQFlights() throws CustomException {
		return consolidatedShipmentInfoDAO.getACESExportSQFlights();
	}
	
	@Override
	public List<FlightModel> getACESExportOALFlights() throws CustomException {
		return consolidatedShipmentInfoDAO.getACESExportOALFlights();
	}
	
	@Override
	public List<FlightModel> getACESImportSQFlights() throws CustomException {
		return consolidatedShipmentInfoDAO.getACESImportSQFlights();
	}
	
	@Override
	public List<FlightModel> getACESImportOALFlights() throws CustomException {
		return consolidatedShipmentInfoDAO.getACESImportOALFlights();
	}

}