package com.ngen.cosys.shipment.exportawbdocument.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentSearchModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerAddressInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerInfoModel;

public interface ExportAwbDocumentService {

	ExportAwbDocumentModel getExportAwbDocument(ExportAwbDocumentSearchModel exportAwbDocumentSearchModel) throws CustomException;
	
	ExportAwbDocumentModel saveExportAwbDocument(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException;
	
	ExportAwbDocumentModel documentComplete(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException;
	
	ExportAwbDocumentModel documentReopen(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException;
	
	ShipmentMasterCustomerAddressInfoModel getAddressDetails(ShipmentMasterCustomerInfoModel shipmentMasterCustomerInfoModel) throws CustomException;
}
