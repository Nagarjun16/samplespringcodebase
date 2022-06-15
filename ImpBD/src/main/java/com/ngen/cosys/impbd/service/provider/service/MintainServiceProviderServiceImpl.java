/**
 * 
 * MintainServiceProviderServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 31 May, 2018 NIIT -
 */
package com.ngen.cosys.impbd.service.provider.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.service.provider.dao.MaintainServiceProviderDao;
import com.ngen.cosys.impbd.service.provider.model.ServiceProviderModel;

/**
 * This class takes care of the responsibilities related to the Mintain Service Provider
 * service operation that comes from the controller.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class MintainServiceProviderServiceImpl implements MaintainServiceProviderService {

   @Autowired
   private MaintainServiceProviderDao serviceProviderDao;

   /**
    * Find Maintain Service Provider Details using Service Code.
    * 
    * @param Service Code
    * @return List<ServiceProviderModel>
    * @throws CustomException
    */
   public List<ServiceProviderModel> getAllServiceProvider(ServiceProviderModel serviceProviderModel) throws CustomException {
      List<ServiceProviderModel> serviceProviderList = serviceProviderDao.getAllServiceProvider(serviceProviderModel);
      return  serviceProviderList;
   }
   /**
    * Insert Maintain Service Provider Details using Service Code.
    * 
    * @param Service Code
    * @return List<ServiceProviderModel>
    * @throws CustomException
    */
   public List<ServiceProviderModel> createServiceProvider(List<ServiceProviderModel> createService) throws CustomException {
      List<ServiceProviderModel> createServiceList = serviceProviderDao.createServiceProvider(createService);
      return  createServiceList;
   }
   /**
    * Update Maintain Service Provider Details using Service Code.
    * 
    * @param Service Code
    * @return List<ServiceProviderModel>
    * @throws CustomException
    */
   public List<ServiceProviderModel> updateServiceProvider(List<ServiceProviderModel> updateService) throws CustomException {
      List<ServiceProviderModel> updateServiceList = serviceProviderDao.updateServiceProvider(updateService);
      return  updateServiceList;
   }
   /**
    * Delete Maintain Service Provider Details using Service Code.
    * 
    * @param Service Code
    * @return List<ServiceProviderModel>
    * @throws CustomException
    */
   public List<ServiceProviderModel> deleteServiceProvider(List<ServiceProviderModel> deleteService) throws CustomException {
      List<ServiceProviderModel> deleteServiceList = serviceProviderDao.deleteServiceProvider(deleteService);
      return  deleteServiceList;
   }
   public List<ServiceProviderModel> editServiceProvider(List<ServiceProviderModel> deleteService) throws CustomException {
      List<ServiceProviderModel> deleteServiceList = serviceProviderDao.editServiceProvider(deleteService);
      return  deleteServiceList;
   }
}
