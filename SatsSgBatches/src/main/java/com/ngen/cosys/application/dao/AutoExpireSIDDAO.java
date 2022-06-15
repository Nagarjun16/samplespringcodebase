package com.ngen.cosys.application.dao;

import com.ngen.cosys.framework.exception.CustomException;

public interface AutoExpireSIDDAO {

   /**
    * Method which auto expires SID requests which has not been yet issued
    * 
    * @throws CustomException
    */
   void expireSID() throws CustomException;

}
