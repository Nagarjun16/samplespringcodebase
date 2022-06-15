/**
 * Repository implementation component for business methods which is used by Imp BD Event processors
 * 
 * 1. Customs Submission
 * 2. EAW Shipment - Send Arrival Notification
 * 3. EAP Shipment - Send Arrival Notification
 * 4. General Shipment - Send Arrival Notification
 */
package com.ngen.cosys.impbd.events.dao;

import java.math.BigInteger;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.awb.notification.model.AwbNotificationShipment;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.GeneralShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.InboundShipmentInfoModel;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;
import com.ngen.cosys.impbd.notification.ShipmentNotificationDetail;


@Repository
public  class ImpBdEventDaoImpl extends BaseDAO implements ImpBdEventDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getInboundCustomsShipmentInfo(
	 * com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getInboundCustomsShipmentInfo(SingaporeCustomsDataSyncEvent payload)
			throws CustomException {
		return this.fetchList("sqlGetInboundCustomsShipments", payload, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getEAWShipments(com.ngen.cosys.
	 * impbd.events.payload.EAWShipmentsEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getEAWShipments(EAWShipmentsEvent payload) throws CustomException {
		return this.fetchList("sqlGetInboundEAWShipments", payload, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getEAPShipments(com.ngen.cosys.
	 * impbd.events.payload.EAPShipmentsEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getEAPShipments(EAPShipmentsEvent payload) throws CustomException {
		return this.fetchList("sqlGetInboundEAPShipments", payload, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getGeneralShipments(com.ngen.
	 * cosys.impbd.events.payload.GeneralShipmentsEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getGeneralShipments(GeneralShipmentsEvent payload) throws CustomException {
		return this.fetchList("sqlGetInboundGeneralShipments", payload, sqlSessionTemplate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.dao.ImpBdEventDao#updateNOAForShipment(java.util.
	 * List)
	 */
	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NOA_SENT_TO)
	@Override
	public void updateNOAForShipment(List<InboundShipmentInfoModel> payload) throws CustomException {
		
		this.updateData("sqlUpdateNOASentStatus", payload, sqlSessionTemplate);

	}

   /* (non-Javadoc)
    * @see com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getIVRSEAWShipments(com.ngen.cosys.impbd.notification.ShipmentNotificationDetail)
    */
   @Override
   public List<AwbNotificationShipment> getIVRSEAWShipments(ShipmentNotificationDetail notificationDetail)
         throws CustomException {
      return this.fetchList("sqlGetIVRSInboundEAWShipments", notificationDetail, sqlSessionTemplate);
   }
   
   /* (non-Javadoc)
    * @see com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getIVRSEAPShipments(com.ngen.cosys.impbd.notification.ShipmentNotificationDetail)
    */
   @Override
   public List<AwbNotificationShipment> getIVRSEAPShipments(ShipmentNotificationDetail notificationDetail) throws CustomException {
      return this.fetchList("sqlGetIVRSInboundEAPShipments", notificationDetail, sqlSessionTemplate);
   }
   
   /* (non-Javadoc)
    * @see com.ngen.cosys.impbd.events.dao.ImpBdEventDao#getIVRSGeneralShipments(com.ngen.cosys.impbd.notification.ShipmentNotificationDetail)
    */
   @Override
   public List<AwbNotificationShipment> getIVRSGeneralShipments(ShipmentNotificationDetail notificationDetail)
         throws CustomException {
      return this.fetchList("sqlGetIVRSInboundGeneralShipments", notificationDetail, sqlSessionTemplate);
   }
	
//
//	@Override
//	public String getCarrier(BigInteger flightId) throws CustomException {
//		
//		return this.fetchObject("fetchCarrier", flightId, sqlSessionTemplate);
//	}

	@Override
	public List<String> getEmailAddress(String carrierCode) throws CustomException {
		// TODO Auto-generated method stub
		return this.fetchList("fetchEmails", carrierCode, sqlSessionTemplate);
	}

	@Override
	public InwardReportEvent getCarrier(BigInteger flightId) throws CustomException {
		// TODO Auto-generated method stub
		return this.fetchObject("fetchCarrier", flightId, sqlSessionTemplate);
	}

}