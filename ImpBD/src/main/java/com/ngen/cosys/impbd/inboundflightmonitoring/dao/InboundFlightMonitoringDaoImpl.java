package com.ngen.cosys.impbd.inboundflightmonitoring.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.enums.InboundFlightMonitoringQueryIds;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringModel;
import com.ngen.cosys.impbd.inboundflightmonitoring.model.InboundFlightMonitoringSerach;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

@Repository
public class InboundFlightMonitoringDaoImpl extends BaseDAO implements InboundFlightMonitoringDao {

   @Autowired
   @Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
   private SqlSessionTemplate sqlSessionTemplateROI;

   @Override
   public List<InboundFlightMonitoringModel> getInBoundFlightMonitoringInformation(
         InboundFlightMonitoringSerach inboundFlightMonitoringSerach) throws CustomException {
      return super.fetchList(InboundFlightMonitoringQueryIds.SQL_GET_Inbound_Flight_Monitoring.getQueryId(),
            inboundFlightMonitoringSerach, sqlSessionTemplateROI);
   }

   @Override
   public InboundFlightMonitoringModel fetchFlightFromTranshipment(
         InboundFlightMonitoringSerach inboundFlightMonitoringSerach) throws CustomException {
      return super.fetchObject("fetchFlightFromTranshipment", inboundFlightMonitoringSerach, sqlSessionTemplateROI);
   }
}