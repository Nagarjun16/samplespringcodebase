package com.ngen.cosys.platform.rfid.tracker.interfaces.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightsModel;

@Service
public interface FlightsService {

	public List<FlightsModel> getFlightData(AuthModel auth) throws CustomException;

}
