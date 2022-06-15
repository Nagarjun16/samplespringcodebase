package com.ngen.cosys.impbd.delaystatus.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.delaystatus.constants.BreakDownConstants;
import com.ngen.cosys.impbd.delaystatus.model.DelayStatusSearch;
import com.ngen.cosys.impbd.delaystatus.model.FlightTonnageDifference;
import com.ngen.cosys.impbd.summary.model.BreakDownSummaryModel;
import com.ngen.cosys.impbd.summary.model.Email;

@Repository
public class BreakDownDelayStatusDaoImpl extends BaseDAO implements BreakDownDelayStatusDao {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   @Override
   public List<BreakDownSummaryModel> fetchDelayList(DelayStatusSearch flightData) throws CustomException {
      return this.fetchList(BreakDownConstants.GET_FLIGHTDETAIL.toString(), flightData, sqlSessionTemplate);
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.CLOSE_FLIGHT)
   @Override
   public void closeFlight(DelayStatusSearch flightData) throws CustomException {
	   flightData.setFlightClosed("YES");
	   flightData.setFlightClosedAt(flightData.getModifiedOn());
      updateData(BreakDownConstants.CLOSE_FLIGHT.toString(), flightData, sqlSessionTemplate);
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.REOPEN_FLIGHT)
   @Override
   public void reopenFlight(DelayStatusSearch flightData) throws CustomException {
	   flightData.setFlightClosed("NO");
	   flightData.setFlightReopenedAt(flightData.getModifiedOn());
      updateData(BreakDownConstants.REOEN_FLIGHT.toString(), flightData, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.delaystatus.dao.BreakDownDelayStatusDao#
    * checkInboundULDFinalized(com.ngen.cosys.impbd.delaystatus.model.
    * DelayStatusSearch)
    */
   @Override
   public boolean checkInboundULDFinalized(DelayStatusSearch flightInfo) throws CustomException {
      return this.fetchObject("sqlCheckInboundULDFinalized", flightInfo, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.delaystatus.dao.BreakDownDelayStatusDao#
    * checkRampCheckInComplete(com.ngen.cosys.impbd.delaystatus.model.
    * DelayStatusSearch)
    */
   @Override
   public boolean checkRampCheckInComplete(DelayStatusSearch flightInfo) throws CustomException {
      return this.fetchObject("sqlCheckInboundRampCheckInComplete", flightInfo, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.delaystatus.dao.BreakDownDelayStatusDao#
    * checkFlightComplete(com.ngen.cosys.impbd.delaystatus.model.DelayStatusSearch)
    */
   @Override
   public boolean checkFlightComplete(DelayStatusSearch flightInfo) throws CustomException {
      return this.fetchObject("sqlCheckInboundFlightComplete", flightInfo, sqlSessionTemplate);
   }

   @Override
   public FlightTonnageDifference fetchFlightTonnageWeight(DelayStatusSearch flightData) throws CustomException {
      return this.fetchObject("sqlFetchFlightTonnageDetails", flightData, sqlSessionTemplate);
   }

   @Override
   public List<Email> fetchFlightCloseAdminEmail() throws CustomException {
      return this.fetchList("sqlFetchFlightCompleteAdminEmail", null, sqlSessionTemplate);
   }

}