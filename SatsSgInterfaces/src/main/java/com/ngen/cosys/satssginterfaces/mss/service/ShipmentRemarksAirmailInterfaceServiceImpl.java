/**
 * This is a class which holds entire functionality for managing of remarks information for shipment
 * 
 *  Usage:
 *  	1. Auto-wire this class service interface
 *      2. invoke the createShipmentRemarks() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.satssginterfaces.mss.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.ShipmentRemarksAirmailInterfaceDAO;
import com.ngen.cosys.satssginterfaces.mss.model.ShipmentRemarksAirmailInterfaceModel;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentRemarksAirmailInterfaceServiceImpl implements ShipmentRemarksAirmailInterfaceService {

	@Autowired
	private ShipmentRemarksAirmailInterfaceDAO dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.remarks.service.ShipmentRemarksService#
	 * createShipmentRemarks(com.ngen.cosys.impbd.shipment.remarks.model.
	 * ShipmentRemarksModel)
	 */
	@Override
	public void createShipmentRemarks(ShipmentRemarksAirmailInterfaceModel shipmentRemarksModel) throws CustomException {
		// Check if remarks exists for a given type
	   ShipmentRemarksAirmailInterfaceModel t = dao.get(shipmentRemarksModel);
		// Check for null object
		Optional<ShipmentRemarksAirmailInterfaceModel> o = Optional.ofNullable(t);
		if (!o.isPresent()) {
			// Create a remarks
			this.dao.create(shipmentRemarksModel);
		}
	}

}