package com.ngen.cosys.shipment.exportawbdocument.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.exportawbdocument.dao.ExportAwbDocumentDao;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentSearchModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerAddressInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerInfoModel;

@Service
@Transactional(readOnly = false, rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
public class ExportAwbDocumentServiceImpl implements ExportAwbDocumentService{

	@Autowired
	private ExportAwbDocumentDao exportAwbDocumentdao;
	
	@Override
	public ExportAwbDocumentModel getExportAwbDocument(ExportAwbDocumentSearchModel exportAwbDocumentSearchModel)
			throws CustomException {
		return exportAwbDocumentdao.getExportAwbDocument(exportAwbDocumentSearchModel);
	}

	@Override
	public ExportAwbDocumentModel saveExportAwbDocument(ExportAwbDocumentModel exportAwbDocumentModel)
			throws CustomException {
		this.exportAwbDocumentdao.saveExportAwbDocument(exportAwbDocumentModel);
		return getExportAwbDocument(new ExportAwbDocumentSearchModel(exportAwbDocumentModel.getShipmentType(), 
																	 exportAwbDocumentModel.getShipmentNumber(), 
																	 exportAwbDocumentModel.getShipmentDate(), 
																	 exportAwbDocumentModel.getNonIATA()));
	}

	@Override
	public ExportAwbDocumentModel documentComplete(ExportAwbDocumentModel exportAwbDocumentModel)
			throws CustomException {
		exportAwbDocumentdao.documentComplete(exportAwbDocumentModel);
		return saveExportAwbDocument(exportAwbDocumentModel);
	}

	@Override
	public ExportAwbDocumentModel documentReopen(ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
		exportAwbDocumentdao.documentReopen(exportAwbDocumentModel);
		return getExportAwbDocument(new ExportAwbDocumentSearchModel(exportAwbDocumentModel.getShipmentType(), 
				 exportAwbDocumentModel.getShipmentNumber(), 
				 exportAwbDocumentModel.getShipmentDate(), 
				 exportAwbDocumentModel.getNonIATA()));
	}

	@Override
	public ShipmentMasterCustomerAddressInfoModel getAddressDetails(ShipmentMasterCustomerInfoModel shipmentMasterCustomerInfoModel) throws CustomException {
		ShipmentMasterCustomerAddressInfoModel shipmentMasterCustomerAddressInfoModel = exportAwbDocumentdao.getAddressDetails(shipmentMasterCustomerInfoModel);
		shipmentMasterCustomerAddressInfoModel.setContactInformation(shipmentMasterCustomerInfoModel.getAddress().getContactInformation());
		return shipmentMasterCustomerAddressInfoModel;
	}

}
