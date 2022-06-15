package com.ngen.cosys.satssginterfaces.mss.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.DLS;
import com.ngen.cosys.satssginterfaces.mss.model.DLSULD;

public interface UpdateDLSDAO {


   DLS insertUpdateDLS(DLS dls) throws CustomException;


   
   void insertDLS(DLS dls) throws CustomException;
   
   DLS getDLS(DLS dls) throws CustomException;
   
   void insertUld(DLSULD uld) throws CustomException;
   
   void updateUld(DLSULD uld) throws CustomException;
   
   void deleteUldTrollyList(List<DLSULD> uldList) throws CustomException;




}

