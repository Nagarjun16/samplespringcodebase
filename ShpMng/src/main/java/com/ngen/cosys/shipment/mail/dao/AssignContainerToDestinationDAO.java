/**
 * 
 * AssignContainerToDestinationDAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestination;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestinationDetails;
import com.ngen.cosys.shipment.mail.model.SearchAssignToContainerToDestinationDetails;

/**
 * This interface takes care of the Assign Container To Destination services. 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface AssignContainerToDestinationDAO {

   /**
    * Fetches Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination searchAssignContainerDetails(SearchAssignToContainerToDestinationDetails request) throws CustomException;

   /**
    * Fetches Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<AssignContainerToDestinationDetails> searchAssignContainerToDestinationDetails(
         SearchAssignToContainerToDestinationDetails request) throws CustomException;

   /**
    * updates Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination updateUldMaster(AssignContainerToDestination request)throws CustomException;

   /**
    * updates Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination updateHouseInfo(List<AssignContainerToDestinationDetails> details)throws CustomException;

   /**
    * fetches Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<AssignContainerToDestinationDetails> getAssignContainerToDestinationDetails(
         AssignContainerToDestination request) throws CustomException;

   /**
    * updates Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination updateEaccHouseInfo(List<AssignContainerToDestinationDetails> details) throws CustomException;

   /**
    * deletes Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   AssignContainerToDestination deleteAssignContainerToDestinationDetails(
         List<AssignContainerToDestinationDetails> details) throws CustomException;

   /**
    * updates Assign Container To Destination details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   void updateLocation(SearchAssignToContainerToDestinationDetails request)throws CustomException;

   /**
    * fetches shipmentInventory ID
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<AssignContainerToDestinationDetails> getshipmentInventoryID(
         SearchAssignToContainerToDestinationDetails request)throws CustomException;
   
   /**
    * fetches container details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<AssignContainerToDestinationDetails> getContainerDetails(SearchAssignToContainerToDestinationDetails request)throws CustomException;

   /**
    * updates destination details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   void updateEaccLocation(List<AssignContainerToDestinationDetails> data) throws CustomException;
   
   /**
    * updates destination details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   void updateHouseInfoDestination(List<AssignContainerToDestinationDetails> data)throws CustomException;
   
   /**
    * returns assigned container count
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   int getAssigneContainerCount(AssignContainerToDestination request)throws CustomException;

   int getAssigneContainersCount(SearchAssignToContainerToDestinationDetails request)throws CustomException;


}
