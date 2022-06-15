package com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.manifestreconcillationstatement.model.ManifesrReconciliationStatementModel;

public interface ManifestReconcillationMessageDAO {

   void addAckInfo(ManifesrReconciliationStatementModel addCustomMRSShipmentInfo) throws CustomException;

   Boolean validateCarrier(String carrierCode) throws CustomException;

}
