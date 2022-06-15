package com.ngen.cosys.AirmailStatus.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.AirmailStatus.DAO.AirmailStatusDAO;
import com.ngen.cosys.AirmailStatus.Model.AirmailStatusFlightModel;
import com.ngen.cosys.AirmailStatus.Model.AirmailStatusChildModel;
import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class AirmailStatusServiceImpl implements AirmailStatusService {

   @Autowired
   AirmailStatusDAO dao;
   
   @Override
   public List<AirmailStatusEvent> getAirmailStoredEvents() throws CustomException {
      return dao.getAirmailStoredEvents();
   }

   @Override
   public void updateStatus(AirmailStatusEvent requestModel) throws CustomException {
      dao.updateStatus(requestModel);
      
   }
   
   @Override
   public AirmailStatusFlightModel getFlightInformationForImport(AirmailStatusChildModel requestModel)
         throws CustomException {
      
      return dao.getFlightInformationForImport(requestModel);
   }

   @Override
   public AirmailStatusChildModel getContainerInfo(AirmailStatusChildModel brkdwnModel) throws CustomException {
	   AirmailStatusChildModel response = dao.getContainerInfo(brkdwnModel);
      return response;
   }

   @Override
   public String getCarrierCodeForDamageEvents(AirmailStatusChildModel brkdwnModel) throws CustomException {
      return dao.getCarrierCodeForDamageEvents(brkdwnModel);
   }

@Override
public AirmailStatusEvent getFlightInformationForExport(AirmailStatusEvent value) throws CustomException {
	return dao.getFlightInformationForExport(value);
}

}
