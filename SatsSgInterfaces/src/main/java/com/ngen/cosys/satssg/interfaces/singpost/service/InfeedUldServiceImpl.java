package com.ngen.cosys.satssg.interfaces.singpost.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.dao.InfeedUldDao;
import com.ngen.cosys.satssg.interfaces.singpost.model.InfeedULDRequestModel;

@Service
public class InfeedUldServiceImpl implements InfeedUldService {

   private static final Logger LOGGER = LoggerFactory.getLogger(InfeedUldServiceImpl.class);

   @Autowired
   private InfeedUldDao infeedULDfromAirsideDao;

   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
   public Integer infeeduldfromAirside(InfeedULDRequestModel infeedULDfromAirsideRequestModel) throws CustomException {
      LOGGER.info(this.getClass().getName() + " Request Model : " + infeedULDfromAirsideRequestModel);
      //1. Update ULD Master

      //2. Update Shipment Inventory
      Integer updteCount = infeedULDfromAirsideDao.infeedUldfromAirside(infeedULDfromAirsideRequestModel);
      return updteCount;
   }

}
