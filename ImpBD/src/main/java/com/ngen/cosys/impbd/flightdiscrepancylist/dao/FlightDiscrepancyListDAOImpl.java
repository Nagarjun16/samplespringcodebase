package com.ngen.cosys.impbd.flightdiscrepancylist.dao;

import java.math.BigInteger;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.flightdiscrepancylist.model.FlightDiscrepancyListModel;

/**
 * DAO for Display Flight Discrepancy List
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("flightDiscrepancyListDAO")
public class FlightDiscrepancyListDAOImpl extends BaseDAO implements FlightDiscrepancyListDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.flightdiscrepancylist.dao.FlightDiscrepancyListDAO#fetch
    * (com.ngen.cosys.impbd.flightdiscrepancylist.model.FlightDiscrepancyListModel)
    */
   @Override
   public FlightDiscrepancyListModel fetch(FlightDiscrepancyListModel searchDiscrepancyList) throws CustomException {

      return super.fetchObject("getFlightDiscrepancyList", searchDiscrepancyList, sqlSession);
   }

   @Override
   public Integer checkForFdlStatus(Long flightId) throws CustomException {
      return super.fetchObject("countForfdlSentFlag", flightId, sqlSession);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.flightdiscrepancylist.dao.FlightDiscrepancyListDAO#
    * updateFDLVersion(com.ngen.cosys.impbd.flightdiscrepancylist.model.
    * FlightDiscrepancyListModel)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.FLIGHT_DISCREPANCY)
   public void updateFDLVersion(FlightDiscrepancyListModel requestModel) throws CustomException {
      // Get the version of FDL
      BigInteger version = this.fetchObject("sqlGetImportFlightEventsFDLLastSentVersion", requestModel, sqlSession);

      // Update the version
      requestModel.setVersion(version);
      this.updateData("sqlUpdateImportFlightEventsFDLSent", requestModel, sqlSession);

   }

	@Override
	public FlightDiscrepancyListModel getFDLVersion(FlightDiscrepancyListModel requestModel) throws CustomException {
		
		return super.fetchObject("getFlightDiscrepancyListUpdatedData", requestModel, sqlSession);
	}

}