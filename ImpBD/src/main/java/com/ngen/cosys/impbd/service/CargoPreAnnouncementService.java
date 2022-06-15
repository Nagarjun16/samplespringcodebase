/**
 * CargoPreAnnouncementService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0         23 January, 2018 NIIT      -
 */
package com.ngen.cosys.impbd.service;

import java.math.BigInteger;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBO;
import com.ngen.cosys.impbd.model.FlightDetails;


public interface CargoPreAnnouncementService {

   /**
    * This class takes care of the responsibilities related to the
    * CargoPreAnnouncement details  Service operations that comes from the Controller.
    * 
    * @author NIIT Technologies Ltd
    *
    */
   
   CargoPreAnnouncementBO cargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO)throws  CustomException;

   
   /**
    * This class takes care of the responsibilities related to the
    * insert CargoPreAnnouncement Service operations that comes from the Controller.
    * 
    * @author NIIT Technologies Ltd
    *
    */

   void insertUpdateCargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException;

   
   /**
    * This class takes care of the responsibilities related to the
    * delete CargoPreAnnouncement Service operations that comes from the Controller.
    * 
    * @author NIIT Technologies Ltd
    *
    */
   void deleteCargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException;

   /**
    * 
    * @param cargoPreAnnouncementBO
    * @throws CustomException
    */
   void finalizeAndunFinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO)throws CustomException;

   /**
    * 
    * @param flightDetails
    * @return
    * @throws CustomException
    */
   BigInteger isFlightExist(FlightDetails flightDetails)throws  CustomException;

   /**
    * 
    * @param preannoucementInfo
    * @throws CustomException
    * update imp_flightEvents  BreakBulk 
    * 
    */
   void updateBreaKBulkIndicator(CargoPreAnnouncement preannoucementInfo) throws CustomException;
   

}
