package com.ngen.cosys.ics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ContainerMovementDAO;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ContainerMovementModel;
import com.ngen.cosys.ics.service.ContainerMovementService;

@Service
public class ContainerMovementServiceImpl implements ContainerMovementService {

   @Autowired
   private ContainerMovementDAO containerMovementDAO;

   @Override
   public String getContainerMovementStatus(ContainerMovementModel model) throws CustomException {

      Integer movementStatus = containerMovementDAO.getContainerMovementStatus(model);
      if (movementStatus > 0) {
         return ResponseStatus.SUCCESS;
      } else {
         return ResponseStatus.FAIL;
      }
   }

}
