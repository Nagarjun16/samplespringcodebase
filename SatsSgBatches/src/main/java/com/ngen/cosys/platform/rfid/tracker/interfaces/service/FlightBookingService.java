package com.ngen.cosys.platform.rfid.tracker.interfaces.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
@Service
public interface FlightBookingService {

	public List<AwbModel> getFlightBookingData(AuthModel auth) throws CustomException;

}
