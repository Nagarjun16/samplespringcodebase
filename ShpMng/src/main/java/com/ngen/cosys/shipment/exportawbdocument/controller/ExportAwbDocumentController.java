package com.ngen.cosys.shipment.exportawbdocument.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ExportAwbDocumentSearchModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerAddressInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.model.ShipmentMasterCustomerInfoModel;
import com.ngen.cosys.shipment.exportawbdocument.service.ExportAwbDocumentService;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class ExportAwbDocumentController {

	@Autowired
	private UtilitiesModelConfiguration utilitiesModelConfiguration;
	
	@Autowired
	private ExportAwbDocumentService exportAwbDocumentService;
	
	
	@ApiOperation("API operation for retreiving Export AWB Document Information")
    @RequestMapping(value = "/api/shpmng/getExportAwbDocument", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ExportAwbDocumentModel> getExportAwbDocument(@RequestBody ExportAwbDocumentSearchModel exportAwbDocumentSearchModel) throws CustomException {
       BaseResponse<ExportAwbDocumentModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
       ExportAwbDocumentModel exportAwbDocumentModel = exportAwbDocumentService.getExportAwbDocument(exportAwbDocumentSearchModel);
       if(!Optional.ofNullable(exportAwbDocumentModel).isPresent()) {
    	   exportAwbDocumentModel=new ExportAwbDocumentModel();
       }
       response.setData(exportAwbDocumentModel);
       return response;
    }
	
	
	@ApiOperation("API operation for saving Export AWB Document Information")
    @RequestMapping(value = "/api/shpmng/saveExportAwbDocument", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ExportAwbDocumentModel> saveExportAwbDocument(@RequestBody @Valid ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
       BaseResponse<ExportAwbDocumentModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
       response.setData(exportAwbDocumentService.saveExportAwbDocument(exportAwbDocumentModel));
       return response;
    }
	
	
	@ApiOperation("API operation for Updating Document Complete Information")
    @RequestMapping(value = "/api/shpmng/documentcomplete", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ExportAwbDocumentModel> documentComplete(@RequestBody @Valid ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
       BaseResponse<ExportAwbDocumentModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
       response.setData(exportAwbDocumentService.documentComplete(exportAwbDocumentModel));
       return response;
    }
	
	@ApiOperation("API operation for Updating Document Reopening Information")
    @RequestMapping(value = "/api/shpmng/documentreopen", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ExportAwbDocumentModel> documentReOpen(@RequestBody @Valid ExportAwbDocumentModel exportAwbDocumentModel) throws CustomException {
       BaseResponse<ExportAwbDocumentModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
       response.setData(exportAwbDocumentService.documentReopen(exportAwbDocumentModel));
       return response;
    }
	
	
	@ApiOperation("API operation Getting Customer Address Details")
    @RequestMapping(value = "/api/shpmng/addressdetails", method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<ShipmentMasterCustomerAddressInfoModel> getAddressDetails(@RequestBody ShipmentMasterCustomerInfoModel shipmentMasterCustomerInfoModel) throws CustomException {
       BaseResponse<ShipmentMasterCustomerAddressInfoModel> response = utilitiesModelConfiguration.getBaseResponseInstance();
       response.setData(exportAwbDocumentService.getAddressDetails(shipmentMasterCustomerInfoModel));
       return response;
    }
}
