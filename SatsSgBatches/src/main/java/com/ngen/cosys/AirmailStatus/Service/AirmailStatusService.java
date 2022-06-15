package com.ngen.cosys.AirmailStatus.Service;

import java.util.List;

import com.ngen.cosys.AirmailStatus.Model.AirmailStatusFlightModel;
import com.ngen.cosys.AirmailStatus.Model.AirmailStatusChildModel;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.framework.exception.CustomException;

public interface AirmailStatusService {

	List<AirmailStatusEvent> getAirmailStoredEvents() throws CustomException;

	void updateStatus(AirmailStatusEvent requestModel) throws CustomException;

	AirmailStatusFlightModel getFlightInformationForImport(AirmailStatusChildModel requestModel) throws CustomException;

	AirmailStatusChildModel getContainerInfo(AirmailStatusChildModel brkdwnModel) throws CustomException;

	String getCarrierCodeForDamageEvents(AirmailStatusChildModel brkdwnModel) throws CustomException;

	AirmailStatusEvent getFlightInformationForExport(AirmailStatusEvent value) throws CustomException;

}
