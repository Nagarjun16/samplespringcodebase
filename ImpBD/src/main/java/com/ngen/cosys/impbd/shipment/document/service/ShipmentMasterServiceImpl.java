/**
 * This is a class which holds entire functionality for managing of shipment
 * information for shipment.
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the
 * createShipment() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.document.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.CdhDocumentmaster;
import com.ngen.cosys.impbd.shipment.document.dao.ShipmentMasterDAO;
import com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster;
import com.ngen.cosys.impbd.shipment.remarks.model.ShipmentRemarksModel;
import com.ngen.cosys.impbd.shipment.remarks.service.ShipmentRemarksService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentMasterServiceImpl implements ShipmentMasterService {

	@Autowired
	private ShipmentMasterDAO dao;

	@Autowired
	private ShipmentRemarksService shipmentRemarksService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService#
	 * createShipment(com.ngen.cosys.impbd.shipment.document.model.ShipmentMaster)
	 */
	@Override
	public void createShipment(ShipmentMaster shipmentMaster) throws CustomException {

		// Check for existing data
		ShipmentMaster existingShipmentInfo = dao.getShipment(shipmentMaster);
		Optional<ShipmentMaster> oShipmentMaster = Optional.ofNullable(existingShipmentInfo);
		// Check for shipment existence
		if (oShipmentMaster.isPresent() && (oShipmentMaster.get().getShipmentId() != null)) {
			shipmentMaster.setShipmentId(oShipmentMaster.get().getShipmentId());
		}

		// Derive part shipment for a shipment
		Boolean partShipment = dao.isPartShipment(shipmentMaster);
		shipmentMaster.setPartShipment(partShipment);

		// Derive Service Shipment
		Boolean svcShipment = dao.isSVCShipment(shipmentMaster);
		shipmentMaster.setSvc(svcShipment);

		// Add shipment master
		dao.createShipment(shipmentMaster);
		
		// Update shipment exp booking pieces/weight
		//commenting for 13532 and required some more changes 
		//dao.updateExportBookingPieceWieght(shipmentMaster);

		// Add routing info
		this.createRoutingInfo(shipmentMaster);

		// Add SHC
		this.createSHC(shipmentMaster);

		// Remarks
		this.createRemarks(shipmentMaster);
	}

	/**
	 * Method to create routing info
	 * 
	 * @param shipmentMaster
	 * @throws CustomException
	 */
	private void createRoutingInfo(ShipmentMaster shipmentMaster) throws CustomException {
		// Add Routing Info
		if (!CollectionUtils.isEmpty(shipmentMaster.getRouting())) {
			shipmentMaster.getRouting().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
			dao.createShipmentMasterRoutingInfo(shipmentMaster.getRouting());
		}
	}

	/**
	 * Methid to create SHC
	 * 
	 * @param shipmentMaster
	 * @throws CustomException
	 */
	private void createSHC(ShipmentMaster shipmentMaster) throws CustomException {
		// Add SHC
		if (!CollectionUtils.isEmpty(shipmentMaster.getShcs())) {
			shipmentMaster.getShcs().forEach(t -> t.setShipmentId(shipmentMaster.getShipmentId()));
			dao.createShipmentMasterShc(shipmentMaster.getShcs());
		}
	}

	/**
	 * Method to create remarks
	 * 
	 * @param shipmentMaster
	 * @throws CustomException
	 */
	private void createRemarks(ShipmentMaster shipmentMaster) throws CustomException {
		// Remarks
		if (shipmentMaster.getRemarks() != null) {
			for (ShipmentRemarksModel t : shipmentMaster.getRemarks()) {
				t.setShipmentId(shipmentMaster.getShipmentId());
				t.setShipmentType(shipmentMaster.getShipmentType());
				this.shipmentRemarksService.createShipmentRemarks(t);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService#
	 * updateCDHShipmetMasterData(com.ngen.cosys.impbd.model.CdhDocumentmaster)
	 */
	@Override
	public void updateCDHShipmetMasterData(CdhDocumentmaster shipmentVerModel) throws CustomException {
		dao.updateCDHShipmetMasterData(shipmentVerModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.document.service.ShipmentMasterService#
	 * deriveDocumentReceivedDateTime(com.ngen.cosys.impbd.shipment.document.model.
	 * ShipmentMaster)
	 */
	@Override
	public LocalDateTime deriveDocumentReceivedDateTime(ShipmentMaster shipmentMaster) throws CustomException {
		return dao.deriveDocumentReceivedDateTime(shipmentMaster);
	}

}