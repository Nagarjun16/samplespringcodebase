/**
 * MaintainServiceProviderDao.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 31 May, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service.provider.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.service.provider.model.ServiceProviderModel;

/**
 * This interface takes care of maintaining Maintain Service Provider list.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MaintainServiceProviderDao {
   /**
    * Method to get Maintain Service Provider information
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> getAllServiceProvider(ServiceProviderModel serviceProviderModel) throws CustomException;
   /**
    * Method to Insert Maintain Service Provider information
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> createServiceProvider(List<ServiceProviderModel> createService) throws CustomException;
   /**
    * Method to Upadet Maintain Service Provider information
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> updateServiceProvider(List<ServiceProviderModel> updateService) throws CustomException;
   /**
    * Method to Delete Maintain Service Provider information
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> deleteServiceProvider(List<ServiceProviderModel> deleteService) throws CustomException;
   /**
    * Method to TerminalId Maintain Service Provider information
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> terminalId(ServiceProviderModel deleteService) throws CustomException;
   /**
    * Method to Edit Maintain Service Provider information
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> editServiceProvider(List<ServiceProviderModel> deleteService) throws CustomException;

}
