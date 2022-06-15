package com.ngen.cosys.ics.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.ContainerExitFromLaneModel;

public interface ContainerExitFromLaneDAO {
   Integer getLaneInformationForContainerExit(ContainerExitFromLaneModel containerExitFromLaneModel)
         throws CustomException;
}