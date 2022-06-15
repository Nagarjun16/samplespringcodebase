package com.ngen.cosys.satssginterfaces.mss.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.Flight;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestFlight;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestHouse;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSHC;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestSegment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestShipment;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestULD;

/**
 * This interface takes care of the responsibilities related to the Manifest
 * related Database interaction operations 
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ManifestDAO {
	
	ManifestFlight fetchLoadDetails(Flight flight) throws CustomException;
	
	ManifestSegment insertManifest(ManifestSegment segment) throws CustomException;
	
	ManifestULD insertManifestULD(ManifestULD uld) throws CustomException;
	
	ManifestShipment insertManifestShipment (ManifestShipment shipment) throws CustomException;
	
	ManifestSHC insertManifestShipmentSHC (ManifestSHC shc) throws CustomException;
	

	ManifestShipment deleteAllManifestShipmentSHCHouse(ManifestShipment shipment) throws CustomException;
	
    ManifestHouse insertManifestShipmentHouse(ManifestHouse house) throws CustomException;

}
