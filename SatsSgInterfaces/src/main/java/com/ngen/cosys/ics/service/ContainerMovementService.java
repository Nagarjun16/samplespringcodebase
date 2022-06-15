package com.ngen.cosys.ics.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ContainerMovementModel;

public interface ContainerMovementService {
   public String getContainerMovementStatus(ContainerMovementModel model) throws CustomException;
}
