package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ContainerMovementModel;

public interface ContainerMovementDAO {
   Integer getContainerMovementStatus(ContainerMovementModel model) throws CustomException;
}