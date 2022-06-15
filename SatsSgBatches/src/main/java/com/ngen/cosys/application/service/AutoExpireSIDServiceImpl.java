/**
 * This is a service component implementation for Auto Expire SID
 * 
 * @author NIIT Technologies Pvt. Ltd
 */
package com.ngen.cosys.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.application.dao.AutoExpireSIDDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Service
@Transactional
public class AutoExpireSIDServiceImpl implements AutoExpireSIDService {

   @Autowired
   AutoExpireSIDDAO dao;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.application.service.AutoExpireSIDService#expireSID()
    */
   @Override
   public void expireSID() throws CustomException {
      this.dao.expireSID();
   }

}
