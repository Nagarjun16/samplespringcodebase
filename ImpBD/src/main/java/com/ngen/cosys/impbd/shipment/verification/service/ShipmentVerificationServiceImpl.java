/**
 * This is a class which holds entire functionality for managing of shipment verification
 * 
 * Usage:
 *  	1. Auto-wire this class service interface
 *      2. invoke the createShipmentVerification() method
 *      
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.verification.service;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.verification.dao.ShipmentVerificationDAO;
import com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerificationModel;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentVerificationServiceImpl implements ShipmentVerificationService {

	@Autowired
	private ShipmentVerificationDAO dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.verification.service.
	 * ShipmentVerificationService#selectShipmentVerificationByFlightIdAndShipmentId
	 * (com.ngen.cosys.impbd.shipment.verification.model.ShipmentVerification)
	 */
	@Override
	public ShipmentVerificationModel createShipmentVerification(ShipmentVerificationModel shipmentVerification)
			throws CustomException {
		// Get the verification data if exists
		ShipmentVerificationModel data = dao.get(shipmentVerification);

		Optional<ShipmentVerificationModel> oRecordExists = Optional.ofNullable(data);
		if (oRecordExists.isPresent()
				&& shipmentVerification.getImpShipmentVerificationId().compareTo(BigInteger.ZERO) == 0) {
			// If exists update the shipment
			shipmentVerification.setImpShipmentVerificationId(data.getImpShipmentVerificationId());
			dao.update(shipmentVerification);
			
		} else {
			// If not exists create the shipment
			dao.create(shipmentVerification);
			
		}
		return shipmentVerification;
	}
	@Override
	public void createDgCheckList(ShipmentVerificationModel shipmentVerification) throws CustomException {
		Integer count = dao.updateDgList(shipmentVerification);
		if(count==0) {
			dao.insertDgList(shipmentVerification);
		}
	}

}