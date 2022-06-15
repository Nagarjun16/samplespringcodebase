package com.ngen.cosys.ics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.dao.ContainerExitFromLaneDAO;
import com.ngen.cosys.ics.enums.ResponseStatus;
import com.ngen.cosys.ics.model.ContainerExitFromLaneModel;
import com.ngen.cosys.ics.service.ContainerExitFromLaneService;

@Service
public class ContainerExitFromLaneServiceImpl implements ContainerExitFromLaneService {

   @Autowired
   private ContainerExitFromLaneDAO laneDAO;

   @Override
   public String getLaneInformationForContainerExit(ContainerExitFromLaneModel containerExitFromLaneModel) throws CustomException {
      Integer exitFormCount = laneDAO.getLaneInformationForContainerExit(containerExitFromLaneModel);
      if (exitFormCount > 0) {
         return ResponseStatus.SUCCESS;
      } else {
         return ResponseStatus.FAIL;
      }
   }

}
