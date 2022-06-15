package com.ngen.cosys.satssginterfaces.mss.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssginterfaces.mss.model.ManifestFlight;

public interface ManifestService {

	ManifestFlight createManifest(ManifestFlight flight) throws CustomException;
	

}
