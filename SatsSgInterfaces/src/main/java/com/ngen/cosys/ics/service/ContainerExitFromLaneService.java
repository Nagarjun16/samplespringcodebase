package com.ngen.cosys.ics.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ContainerExitFromLaneModel;

public interface ContainerExitFromLaneService {

   public String getLaneInformationForContainerExit(ContainerExitFromLaneModel containerExitFromLaneModel) throws CustomException;

}
