package com.ngen.cosys.satssg.interfaces.singpost.dao;

import java.util.Map;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.UldAutoWeightModel;

public interface UpdateUldAutoWeightDao {

   Integer updateUldAutoWeight(UldAutoWeightModel uldAutoWeightModel) throws CustomException;

   /**
    * Method to create the ULD Auto Weight request data
    * 
    * @param request
    * @throws CustomException
    */
   void insertUpdateUldAutoWeightRequest(UldAutoWeightModel request) throws CustomException;

   /**
    * Method to log audit event data for master
    * 
    * @param auditMap
    * @throws CustomException
    */
   void logMasterAuditData(Map<String, Object> auditMap) throws CustomException;

}