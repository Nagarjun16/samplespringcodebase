package com.ngen.cosys.shipment.exportawbdocument.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentSearchModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerAddressInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerInfoModel;

public interface ExportAwbDocumentDao {
	
	ExportAwbDocumentModel getExportAwbDocument(ExportAwbDocumentSearchModel exportAwbDocumentSearchModel) throws CustomException;
	
	void saveExportAwbDocument(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException;
	
	void documentComplete(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException;
	
	void documentReopen(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException;
	
	ShipmentMasterCustomerAddressInfoModel getAddressDetails(ShipmentMasterCustomerInfoModel shipmentMasterCustomerInfoModel) throws CustomException;
}
