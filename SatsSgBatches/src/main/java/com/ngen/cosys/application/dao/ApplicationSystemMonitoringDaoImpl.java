package com.ngen.cosys.application.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository
public class ApplicationSystemMonitoringDaoImpl extends BaseDAO implements ApplicationSystemMonitoringDao{
   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   @Override
   public String fetchEmailsforSystemMonitoring() throws CustomException {
      return fetchObject("fetchEmailsforSystemMonitoring", null, sqlSessionTemplate);
   }

   @Override
   public String fetchEmailsforDlsUldFlightReport() throws CustomException {
      return fetchObject("fetchEmailsforDlsUldFlightReport", null, sqlSessionTemplate);
   }

}
