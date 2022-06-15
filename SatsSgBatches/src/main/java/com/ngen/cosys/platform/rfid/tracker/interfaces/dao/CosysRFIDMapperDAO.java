package com.ngen.cosys.platform.rfid.tracker.interfaces.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AuthModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.AwbModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.FlightsModel;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.SearchFilterModel;

public interface CosysRFIDMapperDAO {

	public List<FlightsModel> getFlightData(SearchFilterModel SearchFilterModel) throws CustomException;

	public List<AwbModel> getImportFFMCarrShcData(SearchFilterModel SearchFilterModel) throws CustomException;

	public List<AwbModel> getImportFFMShcData(SearchFilterModel SearchFilterModel) throws CustomException;

	public List<AwbModel> getFlightBookingCarrShcData(SearchFilterModel SearchFilterModel) throws CustomException;

	public List<AwbModel> getFlightBookingShcData(SearchFilterModel SearchFilterModel) throws CustomException;

	public List<AwbModel> getFlightManifestedCarrShcData(SearchFilterModel SearchFilterModel) throws CustomException;

	public List<AwbModel> getFlightManifestedShcData(SearchFilterModel SearchFilterModel) throws CustomException;

	public AuthModel getAuthUserPassword() throws CustomException;

}