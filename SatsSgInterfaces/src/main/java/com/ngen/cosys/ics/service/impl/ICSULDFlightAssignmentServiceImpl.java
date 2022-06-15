package com.ngen.cosys.ics.service.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ICSULDFlightAssignmentDAO;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentRequestPayload;
import com.ngen.cosys.ics.model.ICSULDFlightAssignmentResponsePayload;
import com.ngen.cosys.ics.service.ICSULDFlightAssignmentService;

@Service
public class ICSULDFlightAssignmentServiceImpl implements ICSULDFlightAssignmentService {

   @Autowired
   private ICSULDFlightAssignmentDAO dao;

   ICSULDFlightAssignmentRequestPayload requestModel = null;

   @Override
   public ICSULDFlightAssignmentResponsePayload prepareMessage(Map<String, Object> data) throws CustomException {

      requestModel = new ICSULDFlightAssignmentRequestPayload();

      for (Entry<String, Object> element : data.entrySet()) {
         if (element.getKey().equalsIgnoreCase("flightId")) {
            requestModel.setFlightId((Integer) element.getValue());
         }
         if (element.getKey().equalsIgnoreCase("eventOutboundULDAssignedToFlightStoreId")) {
            requestModel.setEventOutboundULDAssignedToFlightStoreId((Integer) element.getValue());
         }
         if (element.getKey().equalsIgnoreCase("uldNumber")) {
            requestModel.setUldNumber((String) element.getValue());
         }
      }
      return dao.prepareMessage(requestModel);
   }

}