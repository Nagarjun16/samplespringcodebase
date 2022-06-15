package com.ngen.cosys.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.application.dao.ApplicationSystemMonitoringDao;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class ApplicationSystemMonitoringServiceImpl implements ApplicationSystemMonitoringService{
   @Autowired
   private ApplicationSystemMonitoringDao dao;

   @Override
   public String fetchEmailsforSystemMonitoring() throws CustomException {
      return dao.fetchEmailsforSystemMonitoring();
   }
   
   @Override
   public String fetchEmailsforDlsUldFlightReport() throws CustomException {
      return dao.fetchEmailsforDlsUldFlightReport();
   }
   
   
}
