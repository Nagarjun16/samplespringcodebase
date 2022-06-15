package com.ngen.cosys.impbd.mail.manifest.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestModel;
import com.ngen.cosys.impbd.mail.manifest.model.InboundMailManifestShipmentInventoryInfoModel;

public interface InboundMailManifestService {

	public InboundMailManifestModel search(InboundMailManifestModel requestModel) throws CustomException;

	public InboundMailManifestModel manifest(InboundMailManifestModel requestModel) throws CustomException;

	public InboundMailManifestModel transferToCN46(InboundMailManifestModel requestModel) throws CustomException;

	public InboundMailManifestModel transferToServiceReport(InboundMailManifestModel requestModel)
			throws CustomException;

	public InboundMailManifestModel documentComplete(InboundMailManifestModel requestModel) throws CustomException;

	public InboundMailManifestModel breakDownComplete(InboundMailManifestModel requestModel) throws CustomException;

	public List<InboundMailManifestShipmentInventoryInfoModel> updateLocation(
			List<InboundMailManifestShipmentInventoryInfoModel> requestModel) throws CustomException;

	void checkTransferToCN46(InboundMailManifestModel requestModel) throws CustomException;

	public List<InboundMailManifestShipmentInventoryInfoModel> checkContainerDestination(
			List<InboundMailManifestShipmentInventoryInfoModel> requestModel) throws CustomException;

	String getStoragelocType(String requestModel) throws CustomException;

}