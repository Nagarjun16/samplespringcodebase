package com.ngen.cosys.transhipment.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWB;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBInfo;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBSearch;

public interface ShipmentTransferByAWBDAO {

	TranshipmentTransferManifestByAWBSearch searchList(TranshipmentTransferManifestByAWBSearch search)
			throws CustomException;

	TranshipmentTransferManifestByAWB search(TranshipmentTransferManifestByAWBSearch search) throws CustomException;

	TranshipmentTransferManifestByAWB maintain(TranshipmentTransferManifestByAWB maintain) throws CustomException;

	TranshipmentTransferManifestByAWBSearch cancelAWB(TranshipmentTransferManifestByAWBSearch cancelAWB)
			throws CustomException;

	TranshipmentTransferManifestByAWBSearch finalizeAWB(TranshipmentTransferManifestByAWBSearch finalizeAWB)
			throws CustomException;

	String getTrmCount(TranshipmentTransferManifestByAWB maintain) throws CustomException;

	TranshipmentTransferManifestByAWBInfo getShipmentDetail(TranshipmentTransferManifestByAWBInfo maintain) throws CustomException;

	TranshipmentTransferManifestByAWB mobileMaintain(TranshipmentTransferManifestByAWB search) throws CustomException;
	
	String getCarrierNameBasedOnCarrierCode(String carrierCode) throws CustomException;

	boolean checkTRMAlreadyExists(String trmNumber) throws CustomException;

	void transferDataToFreightOut(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException;

	void transferDataBackToInventory(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException;

	int checkRouting(TranshipmentTransferManifestByAWBInfo awbInfo) throws CustomException; 
}
