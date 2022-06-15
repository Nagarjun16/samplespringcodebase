package com.ngen.cosys.satssg.interfaces.psn.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.psn.model.PsnMessageModel;

public interface PSNDAO {

   void insertPSNInfo(PsnMessageModel psnMessageModel) throws CustomException;

   boolean validatePSNCode(PsnMessageModel psnMessageModel) throws CustomException;

}