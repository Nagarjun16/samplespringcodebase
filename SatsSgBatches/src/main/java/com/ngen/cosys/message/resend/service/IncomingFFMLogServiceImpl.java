/**
 * {@link IncomingFFMLogServiceImpl}
 * 
 * @copyright SATS Singapore 2020-21
 * @author Coforge PTE Ltd
 */
package com.ngen.cosys.message.resend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.dao.IncomingFFMLogDAO;
import com.ngen.cosys.message.resend.model.IncomingFFMLog;

/**
 * Incoming FFM Log Service Implementation
 * 
 * @author Coforge PTE Ltd
 */
@Service
public class IncomingFFMLogServiceImpl implements IncomingFFMLogService {

   private static final Logger LOGGER = LoggerFactory.getLogger(IncomingFFMLogService.class);
   
   @Autowired
   IncomingFFMLogDAO incomingFFMLogDAO;
   
   /**
    * 
    * @see com.ngen.cosys.message.resend.service.IncomingFFMLogService#getFFMHOLDMessages()
    */
   @Override
   public List<IncomingFFMLog> getFFMHOLDMessages() throws CustomException {
      LOGGER.debug("Incoming FFM Log Service HOLD Messages - {}");
      return incomingFFMLogDAO.getFFMHOLDMessages();
   }

   /**
    * @see com.ngen.cosys.message.resend.service.IncomingFFMLogService#updateFFMLogDetails(java.util.List)
    */
   @Override
   public void updateFFMLogDetails(List<IncomingFFMLog> incomingFFMLogs) throws CustomException {
      LOGGER.debug("Incoming FFM Log Service UPDATE Messages - {}");
      try {
    	  incomingFFMLogDAO.updateFFMLogDetails(incomingFFMLogs);
      }
      catch(Exception e)
      {
    	  LOGGER.error("Incoming FFM Log Service Exception in updateFFMLogDetails",e);
      }
      
   }
   
}
