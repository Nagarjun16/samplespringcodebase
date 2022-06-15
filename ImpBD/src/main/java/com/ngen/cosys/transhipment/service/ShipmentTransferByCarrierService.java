package com.ngen.cosys.transhipment.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.transhipment.model.TransferByCarrierSearch;

public interface ShipmentTransferByCarrierService {

	TransferByCarrierSearch search(TransferByCarrierSearch search) throws CustomException;

}
