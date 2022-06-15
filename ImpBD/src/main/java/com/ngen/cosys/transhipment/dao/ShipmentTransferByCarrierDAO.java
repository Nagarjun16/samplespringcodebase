package com.ngen.cosys.transhipment.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.transhipment.model.TransferByCarrierSearch;

public interface ShipmentTransferByCarrierDAO {

	TransferByCarrierSearch search(TransferByCarrierSearch search) throws CustomException;

}
