/**
 * 
 * AssignContainerToDestinationService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          9 March, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.mail.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestination;
import com.ngen.cosys.shipment.mail.model.SearchAssignToContainerToDestinationDetails;

/**
 * This interface takes care of the Assign Container To Destination services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface AssignContainerToDestinationService {

   /**
    *  fetches Assign Container to Destination details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination searchAssignContainerDetails(SearchAssignToContainerToDestinationDetails request) throws CustomException;

   /**
    *  saves Assign Container to Destination details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination saveAssignContainerDetails(AssignContainerToDestination request) throws CustomException;

   /**
    *  deletes Assign Container to Destination details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination deleteAssignContainerDetails(
         SearchAssignToContainerToDestinationDetails request) throws CustomException;


}
