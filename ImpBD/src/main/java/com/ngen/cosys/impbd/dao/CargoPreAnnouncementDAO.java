package com.ngen.cosys.impbd.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.CargPreAnnouncementShcModel;
import com.ngen.cosys.impbd.model.CargoPreAnnouncement;
import com.ngen.cosys.impbd.model.CargoPreAnnouncementBO;
import com.ngen.cosys.impbd.model.FlightDetails;

public interface CargoPreAnnouncementDAO {

   /**
    * @param cargoPreAnnouncementBO
    * @return
    * @throws CustomException
    */
   List<CargoPreAnnouncement> cargoPreAnnouncement(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException;

   /**
    * @param cargoPreAnnouncementBO
    * @return
    * @throws CustomException
    */
   CargoPreAnnouncementBO flightDetails(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException;

   /**
    * @param cargoPreAnnouncementBO
    * @throws CustomException
    */
   void updateCargoPreAnnouncement(List<CargoPreAnnouncement> cargoPreAnnouncementList) throws CustomException;

   /**
    * @param cargoPreAnnouncementBO
    * @throws CustomException
    */
   void insertCargoPreAnnouncement(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

   /**
    * @param cargoPreAnnouncementBO
    * @throws CustomException
    */
  
   void deleteCargoPreAnnouncement(List<CargoPreAnnouncement> cargoPreAnnouncementList) throws CustomException;

   
   /**
    * @param cargoPreAnnouncement
    * @return
    * @throws CustomException
    */
   CargoPreAnnouncement isCargoPreAnnouncementRecordExist(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

  

   /**
    * @param cargoPreAnnouncementBO
    * @throws CustomException
    */
   void cargoPrefinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO)throws CustomException;

   /**
    * @param cargoPreAnnouncementBO
    * @throws CustomException
    */
   void cargoPreunfinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO)throws CustomException;

   /**
    * @param cargoPreAnnouncementBO
    * @return
    * @throws CustomException
    */
   Boolean isFinalizeORunFinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO)throws CustomException;

   /**
    * @param cargoPreAnnouncement
    * @throws CustomException
    */
   void insertCargoPreannouncementRamCheckIn(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

   /**
    * @param list
    * @throws CustomException
    */
   void updateCargoPreannouncementRamCheckIn(List<CargoPreAnnouncement> list) throws CustomException;

   /**
    * @param cargoPreAnnouncement
    * @return
    * @throws CustomException 
    */
   BigInteger isRamcheckinRecordExist(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;
   
   /**
    * 
    * @param cargoPreAnnouncement
    * @return
    * @throws CustomException
    */
   List<String> getPRNShcList(CargoPreAnnouncementBO cargoPreAnnouncement) throws CustomException;
   
   /**
    * 
    * @param cargoPreAnnouncement
    * @return
    * @throws CustomException
    */
   List<String> getPrnGroupshcList(CargoPreAnnouncementBO cargoPreAnnouncement) throws CustomException;

   void insertCargoPreAnnouncementSHC(CargPreAnnouncementShcModel shcModel)throws CustomException;
   
   void insertCargoPreAnnouncementRamCheckinSHC(CargPreAnnouncementShcModel shcModel)throws CustomException;

   void deleteCargoPreAnnouncementShc(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;
   
   void deleteCargoPreAnnouncementRampcheckinShc(CargoPreAnnouncement cargoPreAnnouncement) throws CustomException;

   void mailPrefinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException;

   void mailPreunfinalize(CargoPreAnnouncementBO cargoPreAnnouncementBO) throws CustomException;
   
   void updateBrakBulkStatus(CargoPreAnnouncement cargoPreAnnouncementBO) throws CustomException;
   
   BigInteger isFlightExist(FlightDetails flightDetails) throws CustomException;
  
   CargoPreAnnouncementBO preAnnoncementfinalizeInfo(CargoPreAnnouncementBO cargoPreAnnouncementBO)throws CustomException;

   
	/**
	 * Method to check whether ULD has been checked in OR not
	 * 
	 * @param request
	 * @return boolean - true if checked in OR false
	 * @throws CustomException
	 */
	boolean isULDCheckedIn(CargoPreAnnouncement request) throws CustomException;

}
