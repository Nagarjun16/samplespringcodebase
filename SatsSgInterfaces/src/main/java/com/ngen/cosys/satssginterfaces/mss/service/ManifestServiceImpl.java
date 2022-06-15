package com.ngen.cosys.satssginterfaces.mss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.dao.ManifestDAO;
import com.ngen.cosys.satssginterfaces.mss.dao.MssDAO;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestFlight;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSHC;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSegment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestShipment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestULD;

/**
 * This implementation of ManifestService
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@Service
@Transactional
public class ManifestServiceImpl implements ManifestService {

	@Autowired
	private ManifestDAO manifestDAO;
	@Autowired
	private MssDAO mssdao;




	@Override
	@Transactional(readOnly = false, rollbackFor = Throwable.class)
	public ManifestFlight createManifest(ManifestFlight manifestFlight) throws CustomException {
		for (ManifestSegment segment : manifestFlight.getSegment()) {
			segment.setType("Main");
			segment.setVersionNo(1);
			insertManifest(segment);
		}
		return manifestFlight;
	}

	private void insertManifest(ManifestSegment segment) throws CustomException {
		//select manifestid if present bye bye else insert
	  // if(mssdao.checkManifestedIdexistance(segment) == 0) {
	      manifestDAO.insertManifest(segment);
	        for (ManifestULD uld : segment.getUlds()) {
	            uld.setManifestId(segment.getManifestId());
	            manifestDAO.insertManifestULD(uld);
	            for (ManifestShipment shipment : uld.getShipment()) {
	                shipment.setManifestId(segment.getManifestId());
	                shipment.setManifestUldId(uld.getManifestUldId());
	                manifestDAO.insertManifestShipment(shipment);
	                manifestDAO.deleteAllManifestShipmentSHCHouse(shipment);
	                for (ManifestSHC shc : shipment.getShcList()) {
	                    shc.setShipmentInfoId(shipment.getManifestShipmentInfoId());
	                    manifestDAO.insertManifestShipmentSHC(shc);
	                }
	                for (ManifestHouse house : shipment.getHouseList()) {
	                    house.setShipmentInfoId(shipment.getManifestShipmentInfoId());
	                    manifestDAO.insertManifestShipmentHouse(house);
	                }
	            }
	        }
	  // }
	    
	}


}
