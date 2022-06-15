/**
 * Service implementation component for business methods which is used by Imp BD Event processors
 * 
 * 1. Customs Submission
 * 2. EAW Shipment - Send Arrival Notification
 * 3. EAP Shipment - Send Arrival Notification
 * 4. General Shipment - Send Arrival Notification
 */
package com.ngen.cosys.impbd.events.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.events.dao.ImpBdEventDao;
import com.ngen.cosys.impbd.events.payload.EAPShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.EAWShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.GeneralShipmentsEvent;
import com.ngen.cosys.impbd.events.payload.InboundShipmentInfoModel;
import com.ngen.cosys.impbd.events.payload.InwardReportEvent;
import com.ngen.cosys.impbd.events.payload.SingaporeCustomsDataSyncEvent;


@Service
public class ImpBdEventServiceImpl implements ImpBdEventService {

	@Autowired
	private ImpBdEventDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.events.service.ImpBdEventService#
	 * getInboundCustomsShipmentInfo(com.ngen.cosys.impbd.events.payload.
	 * SingaporeCustomsDataSyncEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getInboundCustomsShipmentInfo(SingaporeCustomsDataSyncEvent payload)
			throws CustomException {
		return this.dao.getInboundCustomsShipmentInfo(payload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.service.ImpBdEventService#getEAWShipments(com.
	 * ngen.cosys.impbd.events.payload.EAWShipmentsEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getEAWShipments(EAWShipmentsEvent payload) throws CustomException {
		return this.dao.getEAWShipments(payload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.service.ImpBdEventService#getEAPShipments(com.
	 * ngen.cosys.impbd.events.payload.EAPShipmentsEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getEAPShipments(EAPShipmentsEvent payload) throws CustomException {
		return this.dao.getEAPShipments(payload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.service.ImpBdEventService#getGeneralShipments(com
	 * .ngen.cosys.impbd.events.payload.GeneralShipmentsEvent)
	 */
	@Override
	public List<InboundShipmentInfoModel> getGeneralShipments(GeneralShipmentsEvent payload) throws CustomException {
		return this.dao.getGeneralShipments(payload);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.impbd.events.service.ImpBdEventService#updateNOAForShipment(
	 * java.util.List)
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public void updateNOAForShipment(List<InboundShipmentInfoModel> payload) throws CustomException {
		this.dao.updateNOAForShipment(payload);
	}



	@Override
	public List<String> fetchEmailAdrressesConfigured(String carrierCode) throws CustomException {
		// TODO Auto-generated method stub
		return this.dao.getEmailAddress(carrierCode);
	}

	@Override
	public InwardReportEvent getCarrierCode(BigInteger flightId) throws CustomException {
		// TODO Auto-generated method stub
		 return this.dao.getCarrier(flightId);
	}



}