package com.ngen.cosys.ics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.CoolPortShipmentInformationDAO;
import com.ngen.cosys.ics.model.CoolPortShipmentRequestModel;
import com.ngen.cosys.ics.model.CoolPortShipmentResponseModel;
import com.ngen.cosys.ics.service.CoolPortShipmentInformationService;

@Service
public class CoolPortShipmentInformationServiceImpl implements CoolPortShipmentInformationService {

   @Autowired
   private CoolPortShipmentInformationDAO dao;

   @Override
   public CoolPortShipmentResponseModel fetchShipmentInformation(CoolPortShipmentRequestModel request) throws CustomException {
      return dao.fetchShipmentInformation(request);
   }

}
