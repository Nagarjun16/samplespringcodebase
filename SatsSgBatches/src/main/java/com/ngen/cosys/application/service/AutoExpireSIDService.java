/**
 * This is a service component for Auto Expire SID
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.service;

import com.ngen.cosys.framework.exception.CustomException;

public interface AutoExpireSIDService {

   /**
    * Method which auto expires PO requests which has not been yet issued
    * 
    * @throws CustomException
    */
   void expireSID() throws CustomException;

}