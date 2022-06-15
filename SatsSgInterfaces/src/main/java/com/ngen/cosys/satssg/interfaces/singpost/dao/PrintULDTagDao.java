package com.ngen.cosys.satssg.interfaces.singpost.dao;

import java.util.Map;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.ics.model.AutoWeigh;
import com.ngen.cosys.ics.model.AutoWeighDG;
import com.ngen.cosys.satssg.interfaces.singpost.model.PrintULDTagRequestModel;

public interface PrintULDTagDao {

   String getprinterIPAddressAppSystemParams(PrintULDTagRequestModel printULDTagRequestModel) throws CustomException;

   /**
    * Method to store Print ULD Tag information received from ICS
    * 
    * @param request
    * @throws CustomException
    */
   void insertPrintULDTagInfo(PrintULDTagRequestModel request) throws CustomException;
   
   /**
    * Method to log audit event data for master
    * 
    * @param auditMap
    * @throws CustomException
    */
   void logMasterAuditData(Map<String, Object> auditMap) throws CustomException;
   
   /**
    * insert into auto weigh
    * @param request
    * @throws CustomExceptioninsertAutoWeighDG
    */
   void insertAutoWeighInfo(AutoWeigh request) throws CustomException;
   
   /**
    * insert into auto weigh DG
    * @param request
    * @throws CustomException
    */
   void insertAutoWeighDG(AutoWeighDG request) throws CustomException;

}