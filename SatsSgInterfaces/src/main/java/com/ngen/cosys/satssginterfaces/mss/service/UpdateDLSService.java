package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;

public interface UpdateDLSService {


   DLS insertUpdateDLS(DLS dlsUldTrolly) throws CustomException;
   
   DLS updateUld(DLS dls) throws CustomException;


}
