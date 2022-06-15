/**
 * 
 * MaintainServiceProviderService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date             Author      Reason
 * 1.0          31 May, 2018 NIIT      -
 */
package com.ngen.cosys.impbd.service.provider.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.service.provider.model.ServiceProviderModel;

/**
 * This interface takes care of the Maintain Service Provider  List.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface MaintainServiceProviderService {
   /**
    * Method to get Maintain Service Provider Details using Service Code.
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> getAllServiceProvider(ServiceProviderModel serviceProviderModel)throws CustomException;
   /**
    * Method to insert Maintain Service Provider Details using Service Code.
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> createServiceProvider(List<ServiceProviderModel> createService)throws CustomException;
   /**
    * Method to update Maintain Service Provider Details using Service Code.
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomException
    */
   List<ServiceProviderModel> updateServiceProvider(List<ServiceProviderModel> updateService)throws CustomException;
   /**
    * Method to Delete Maintain Service Provider Details using Service Code.
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomExceptiondeleteServiceProvider
    */
   List<ServiceProviderModel> deleteServiceProvider(List<ServiceProviderModel> deleteService)throws CustomException;
   /**
    * Method to Edit Maintain Service Provider Details using Service Code.
    * 
    * @param requestModel
    * @return ServiceProviderModel
    * @throws CustomExceptiondeleteServiceProvider
    */
   List<ServiceProviderModel> editServiceProvider(List<ServiceProviderModel> deleteService)throws CustomException;
}
