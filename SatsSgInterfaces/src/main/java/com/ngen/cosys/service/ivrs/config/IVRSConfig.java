/**
 * {@link IVRSConfig}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.config;

import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.service.ivrs.constants.IVRSConstants;
import com.ngen.cosys.service.ivrs.constants.IVRSQueryConstants;

/**
 * IVRS Config
 * 
 * @author NIIT Technologies Ltd.
 */
@Component
public class IVRSConfig {

   private static final Logger LOGGER = LoggerFactory.getLogger(IVRSConfig.class);
   
   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSession sqlSession;
   
   private IVRSInterfaceConfig interfaceConfig = null;
   
   /**
    * Initialize
    */
   @PostConstruct
   public void initialize() {
      LOGGER.info("IVRS Config initialization - {}");
      interfaceConfig = sqlSession.selectOne(IVRSQueryConstants.SQL_SELECT_IVRS_API_CONFIG);
      boolean interfaceConfigAvailability = Objects.nonNull(interfaceConfig) ? true : false;
      LOGGER.info("IVRS Interface config availability - {}", interfaceConfigAvailability);
   }
   
   /**
    * @param interfaceSystem
    * @return
    */
   public String getInterfaceURL(String interfaceSystem) {
      if (StringUtils.isEmpty(interfaceSystem)) {
         throw new IllegalArgumentException("Interface System cannot be blank..!");
      }
      if (Objects.isNull(interfaceConfig)) {
         throw new IllegalStateException("IVRS Interface config instances cannot be null");
      }
      if (Objects.equals(IVRSConstants.SYSTEM_IVRS, interfaceSystem)) {
         String connectorURL = interfaceConfig.getPerformCallUrl();
         String performCallAPI = connectorURL.replaceAll("\\" + IVRSConstants.RELATIVE_PATH_PATTERN,
               IVRSConstants.API_PERFORM_CALL);
         return performCallAPI;
      } else if (Objects.equals(IVRSConstants.SYSTEM_FAX, interfaceSystem)) {
         return interfaceConfig.getSendFaxUrl();
      } else if (Objects.equals(IVRSConstants.SYSTEM_REPORT, interfaceSystem)) {
         return interfaceConfig.getReportServiceUrl();
      }
      return null;
   }
   
}
