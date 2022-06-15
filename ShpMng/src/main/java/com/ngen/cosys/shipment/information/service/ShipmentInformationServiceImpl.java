package com.ngen.cosys.shipment.information.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.billing.api.CheckPaymentStatus;
import com.ngen.cosys.billing.api.model.CheckPaymentStatusResponse;
import com.ngen.cosys.events.payload.ShipmentCancellationStoreEvent;
import com.ngen.cosys.events.producer.ShipmentCancellationStoreEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.helper.DomesticInternationalHelper;
import com.ngen.cosys.helper.HAWBHandlingHelper;
import com.ngen.cosys.helper.model.DomesticInternationalHelperRequest;
import com.ngen.cosys.helper.model.HAWBHandlingHelperRequest;
import com.ngen.cosys.multitenancy.feature.model.ApplicationFeatures;
import com.ngen.cosys.multitenancy.feature.support.FeatureUtility;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.shipment.awb.model.AWBPrintRequest;
import com.ngen.cosys.shipment.information.dao.ShipmentInformationDAO;
import com.ngen.cosys.shipment.information.model.DimensionInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentFlightModel;
import com.ngen.cosys.shipment.information.model.ShipmentFreightOutInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoMessageModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoModel;
import com.ngen.cosys.shipment.information.model.ShipmentInfoSearchReq;
import com.ngen.cosys.shipment.model.CdhDocumentmaster;
import com.ngen.cosys.shipment.model.ShipmentMaster;
import com.ngen.cosys.shipment.printer.util.PrinterService;
import com.ngen.cosys.utils.SecurityHashUtils;

/**
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional
public class ShipmentInformationServiceImpl implements ShipmentInformationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentInformationServiceImpl.class);

	@Autowired
	private HAWBHandlingHelper hawbHandlingHelper;
	
	@Autowired
	private DomesticInternationalHelper domesticInternationalHelper;
	
   @Autowired
   private ShipmentInformationDAO shipmentInformationDAO;

   @Autowired
   private ShipmentProcessorService shipmentProcessorService;

   @Autowired
   private PrinterService printerService;

   @Autowired
   private CheckPaymentStatus checkPaymentStatus;

   @Autowired
   private ShipmentCancellationStoreEventProducer producer;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * getShipmentInfo(com.ngen.cosys.shipment.information.model.
    * ShipmentInfoSearchReq)
    */
   @Override
   public ShipmentInfoModel getShipmentInfo(ShipmentInfoSearchReq search) throws CustomException {

      // Fetch shipment date provided if it is empty
      if (ObjectUtils.isEmpty(search.getShipmentDate())) {
         LocalDate shipmentDate = this.shipmentProcessorService.getShipmentDate(search.getShipmentNumber());
         search.setShipmentDate(shipmentDate);
      }

      ShipmentInfoModel responseModel = this.shipmentInformationDAO.getShipmentInfo(search);
      
      DomesticInternationalHelperRequest domesticInternationalHelperRequest = new DomesticInternationalHelperRequest();
		domesticInternationalHelperRequest.setOrigin(responseModel.getOrigin());
		domesticInternationalHelperRequest.setDestination(responseModel.getDestination());
		responseModel
				.setIndicatorDomIntl(domesticInternationalHelper.getDOMINTHandling(domesticInternationalHelperRequest));
		 // If Domestic & Feature is Disabled handledBy cannot be changed
		if(responseModel.getIndicatorDomIntl()!=null && responseModel.getIndicatorDomIntl().equalsIgnoreCase("DOM") 
				&& !FeatureUtility.isFeatureEnabled(ApplicationFeatures.Gen.DomesticHAWBHandling.class)) {
			responseModel.setHandlingChangeFlag(false);
		}
    /* Optional<ShipmentInfoModel> o = Optional.ofNullable(responseModel);
      if (!o.isPresent()) {
         throw new CustomException("NORECORD", "shipmentNumber", ErrorType.ERROR);
      }

      // While performing purge for an shipment make sure to check payment status
		if (!Objects.isNull(responseModel.getShipmentId())) {
			CheckPaymentStatusResponse checkStatusResponse = checkPaymentStatus
					.getCheckPaymentStatusByShipmentId(responseModel.getShipmentId().longValue());
			responseModel.setPaymentStatus(checkStatusResponse.getPaymentStatus());
		} else {
			responseModel.setPaymentStatus("ChargeNotCreated");
		}*/
      return responseModel;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * cancelShipment(com.ngen.cosys.shipment.information.model.ShipmentInfoModel)
    */
   @Override
   public ShipmentInfoModel cancelShipment(ShipmentInfoModel search) throws CustomException {
      // Get the shipments for which booking event needs to be published
      List<ShipmentCancellationStoreEvent> event = this.shipmentInformationDAO.getCancelShipmentForCancel(search);

      // Perform cancellation shipment and remove all associated information system
      ShipmentInfoModel responseModel = new ShipmentInfoModel();
      BigInteger count = this.shipmentInformationDAO.checkShipmentLoadedOrNot(search);
      if (count.intValue() > 0) {
         responseModel.addError("awb.loaded.shp.cant.cnl", "", ErrorType.ERROR);
         throw new CustomException();
      }
      this.shipmentInformationDAO.cancel(search);
      responseModel = this.shipmentInformationDAO.cancelShipment(search);
      responseModel.setCancelCharge(true);

      // Publish the event for booking info
      if (!CollectionUtils.isEmpty(event)) {
         event.forEach(flightBooking -> producer.publish(flightBooking));
      }
      return responseModel;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * printAWBBarcode(com.ngen.cosys.shipment.awb.model.AWBPrintRequest)
    */
   @Override
   public void printAWBBarcode(AWBPrintRequest awbPrintRequest) throws CustomException {
      ReportRequest report = new ReportRequest();
      report.setRequestType(ReportRequestType.PRINT);
      report.setPrinterType(PrinterType.AWB_BARCODE);
      report.setQueueName(awbPrintRequest.getPrinterName());

      Map<String, Object> parameters = new HashMap<>();
      parameters.put("awbNumBarCode", awbPrintRequest.getAwbNumber());
      parameters.put("awbNumTextCode", awbPrintRequest.getAwbNumber());

      report.setParameters(parameters);
      // Print AWB Barcode
      printerService.printReport(report);

      // If document is Accepted for Export or if flight booking is available for
      // Import/Transhipment
      // (insert into Cdh_DocumentMaster Only if, shipmentId is not exist in
      // Cdh_DocumentMaster)
      if (awbPrintRequest.getShipmentId() != null) {
         if (shipmentInformationDAO.checkShipmentIsAddedOrNot(awbPrintRequest) == 0) {
            CdhDocumentmaster modeldata = new CdhDocumentmaster();
            modeldata.setCopyno(0);
            modeldata.setCarriercode(awbPrintRequest.getCarrierCode());
            modeldata.setDestination(awbPrintRequest.getDestination());
            modeldata.setFlightoffpoint(awbPrintRequest.getDestination());
            modeldata.setShipmentid(awbPrintRequest.getShipmentId());

            // Get location Id
            modeldata.setCdhpigeonholelocationid(shipmentInformationDAO.getPigeonHoleLocationId(modeldata));

            // Insert/update Data To Cdh_DocumentMaster
            shipmentInformationDAO.updateCDHShipmetMasterData(modeldata);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * fetchMessages(com.ngen.cosys.shipment.information.model.ShipmentInfoModel)
    */
   @Override
   public List<ShipmentInfoMessageModel> fetchMessages(ShipmentInfoModel requestModel) throws CustomException {
      return this.shipmentInformationDAO.fetchMessages(requestModel);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * purgeShipment(com.ngen.cosys.shipment.information.model.ShipmentInfoModel)
    */
   @Override
   public ShipmentInfoModel purgeShipment(ShipmentInfoModel search) throws CustomException {
      String pwd = search.getPassword();
      String pwdToMatch = this.shipmentInformationDAO.getUserPwd(search);
      BigInteger checkIfAnyPurgeIsInProgress = this.shipmentInformationDAO.checkIfAnyPurgeIsInProgress(search);
      if (!Objects.isNull(checkIfAnyPurgeIsInProgress) && checkIfAnyPurgeIsInProgress.compareTo(BigInteger.ZERO) > 0) {
         search.addError("shipment.purg.in.progress", null, ErrorType.ERROR);
         throw new CustomException();
      }
      ShipmentFlightModel piecesInfoForPurge = this.shipmentInformationDAO.getPiecesInfoForPurge(search);
      ShipmentFlightModel piecesInfoForPurgeIsEmpty = new ShipmentFlightModel();

      if (pwdToMatch.equalsIgnoreCase(SecurityHashUtils.getWSPassword(pwd))) {
         if (ObjectUtils.isEmpty(piecesInfoForPurge)) {
            piecesInfoForPurgeIsEmpty.setReadyForPurge(false);
            piecesInfoForPurgeIsEmpty.addError("awb.booked.cant.purge", "", ErrorType.ERROR);
            throw new CustomException(piecesInfoForPurgeIsEmpty.getMessageList());
         } else {
            switch (search.getProcess()) {
            case "IMPORT":
               checkIfReadyToPurge(piecesInfoForPurge);
               if (piecesInfoForPurge.isReadyForPurge()) {
                  setArchiveData(piecesInfoForPurge, search);
                  for (ShipmentFlightModel flightDetail : search.getIncomingFlightDetails()) {
                     setArchiveDataForImportFlight(piecesInfoForPurge, flightDetail);
                     this.shipmentInformationDAO.insertIntoArchivalShipmentFlight(piecesInfoForPurge);
                  }
               }
               break;
            case "EXPORT":
               checkIfReadyToPurge(piecesInfoForPurge);
               if (piecesInfoForPurge.isReadyForPurge()) {
                  setArchiveData(piecesInfoForPurge, search);
                  for (ShipmentFreightOutInfoModel flightDetail : search.getFreightOutDetails()) {
                     setArchiveDataForExportFrtFlight(piecesInfoForPurge, flightDetail);
                     this.shipmentInformationDAO.insertIntoArchivalShipmentFlight(piecesInfoForPurge);
                  }
               }
               break;
            default:
               checkIfReadyToPurge(piecesInfoForPurge);
               List<ShipmentFlightModel> bookingFFRFlightInfo = this.shipmentInformationDAO
                     .fetchBookingFFRFlightInfo(search);
               if (piecesInfoForPurge.isReadyForPurge()) {
                  setArchiveData(piecesInfoForPurge, search);
                  if (!ObjectUtils.isEmpty(search.getIncomingFlightDetails())) {
                     for (ShipmentFlightModel flightDetail : search.getIncomingFlightDetails()) {
                        setArchiveDataForImportFlight(piecesInfoForPurge, flightDetail);
                        this.shipmentInformationDAO.insertIntoArchivalShipmentFlight(piecesInfoForPurge);
                     }
                  }
                  if (!ObjectUtils.isEmpty(search.getOutbooundFlightDetails())) {
                     for (ShipmentFlightModel flightDetail : search.getOutbooundFlightDetails()) {
                        setArchiveDataForExportFlight(piecesInfoForPurge, flightDetail);
                        this.shipmentInformationDAO.insertIntoArchivalShipmentFlight(piecesInfoForPurge);
                     }
                  }
                  if (!ObjectUtils.isEmpty(bookingFFRFlightInfo)) {
                     for (ShipmentFlightModel flightDetail : bookingFFRFlightInfo) {
                        flightDetail.setShipmentId(search.getShipmentId());
                        flightDetail.setShipmentNumber(search.getShipmentNumber());
                        flightDetail.setShipmentDate(search.getShipmentDate());
                        flightDetail.setReasonForPurge(search.getReasonForPurge());
                        flightDetail.setArchivedBy(search.getUserLoginCode());
                        if (!Objects.isNull(flightDetail.getCarrierCode())
                              && !Objects.isNull(flightDetail.getFlightNumber())) {
                           flightDetail
                                 .setFlightKey(flightDetail.getCarrierCode().concat(flightDetail.getFlightNumber()));
                        }
                        this.shipmentInformationDAO.insertIntoArchivalShipmentFlight(flightDetail);
                     }
                  }
               }
               break;
            }
         }
      } else {
         search.setStausForCancel("PWD");
      }
      if (ObjectUtils.isEmpty(piecesInfoForPurge)) {
         search.setMessageList(piecesInfoForPurgeIsEmpty.getMessageList());
         search.setReadyForPurge(piecesInfoForPurgeIsEmpty.isReadyForPurge());
      } else {
         search.setMessageList(piecesInfoForPurge.getMessageList());
         search.setReadyForPurge(piecesInfoForPurge.isReadyForPurge());
      }
      return search;
   }

   private ShipmentFlightModel setArchiveDataForExportFlight(ShipmentFlightModel piecesInfoForPurge,
         ShipmentFlightModel flightDetail) {
      piecesInfoForPurge.setFlightId(flightDetail.getFlightId());
      piecesInfoForPurge.setCarrierCode(flightDetail.getCarrierCode());
      piecesInfoForPurge.setFlightNumber(flightDetail.getFlightNumber());
      piecesInfoForPurge.setFlightKey(flightDetail.getBookingFlightKey());
      piecesInfoForPurge.setFlightDate(flightDetail.getBookingSta().toLocalDate());
      piecesInfoForPurge.setFlightOriginDate(flightDetail.getFlightOriginDate());
      return piecesInfoForPurge;
   }

   private ShipmentFlightModel setArchiveDataForImportFlight(ShipmentFlightModel piecesInfoForPurge,
         ShipmentFlightModel flightDetail) {
      LocalDateTime tenantDateTime = TenantTimeZoneUtility.now();
      piecesInfoForPurge.setForcePurgeTime(tenantDateTime);
      piecesInfoForPurge.setFlightId(flightDetail.getFlightId());
      piecesInfoForPurge.setCarrierCode(flightDetail.getCarrierCode());
      piecesInfoForPurge.setFlightNumber(flightDetail.getFlightNumber());
      piecesInfoForPurge.setFlightKey(flightDetail.getFlightDetailsKey());
      piecesInfoForPurge.setFlightDate(flightDetail.getFlightDate());
      piecesInfoForPurge.setFlightOriginDate(flightDetail.getFlightOriginDate());
      return piecesInfoForPurge;
   }

   private ShipmentFlightModel setArchiveDataForExportFrtFlight(ShipmentFlightModel piecesInfoForPurge,
         ShipmentFreightOutInfoModel flightDetail) {
      LocalDateTime tenantDateTime = TenantTimeZoneUtility.now();
      piecesInfoForPurge.setForcePurgeTime(tenantDateTime);
      piecesInfoForPurge.setFlightId(flightDetail.getFlightId());
      piecesInfoForPurge.setCarrierCode(flightDetail.getCarrierCode());
      piecesInfoForPurge.setFlightNumber(flightDetail.getFlightNumber());
      piecesInfoForPurge.setFlightKey(flightDetail.getFlightKey());
      piecesInfoForPurge.setFlightDate(flightDetail.getFreightOutflightDate().toLocalDate());
      piecesInfoForPurge.setFlightOriginDate(flightDetail.getFlightOriginDate());
      return piecesInfoForPurge;
   }

   private ShipmentFlightModel setArchiveData(ShipmentFlightModel piecesInfoForPurge, ShipmentInfoModel search) {
      piecesInfoForPurge.setShipmentId(search.getShipmentId());
      piecesInfoForPurge.setShipmentNumber(search.getShipmentNumber());
      piecesInfoForPurge.setShipmentDate(search.getShipmentDate());
      piecesInfoForPurge.setReasonForPurge(search.getReasonForPurge());
      piecesInfoForPurge.setArchivedBy(search.getUserLoginCode());
      return piecesInfoForPurge;
   }

   private ShipmentFlightModel checkIfReadyToPurge(ShipmentFlightModel piecesInfoForPurge) throws CustomException {
      piecesInfoForPurge.setReadyForPurge(false);
      if (!ObjectUtils.isEmpty(piecesInfoForPurge)) {
         // Check for Failed Cases
         // check if Shipment not complete i.e i. AWB Pcs <> Frt Out Pcs + Inventory Pcs
         BigInteger sumOfinvFrtPcs = piecesInfoForPurge.getInventoryPieces()
               .add(piecesInfoForPurge.getFreightOutPieces()
                     .add(piecesInfoForPurge.getMissingAWBPieces().add(piecesInfoForPurge.getMissingCargoPieces())));
         if (!sumOfinvFrtPcs.equals(piecesInfoForPurge.getAwbPiece())) {
            piecesInfoForPurge.addError("shp.not.complt.pls.chk", "", ErrorType.ERROR);
            throw new CustomException(piecesInfoForPurge.getMessageList());
         } else {
            // Check if Any inventory is not freight out
            if ((!piecesInfoForPurge.getMissingAWBPieces().equals(piecesInfoForPurge.getAwbPiece())
                  || (!piecesInfoForPurge.getMissingCargoPieces().equals(piecesInfoForPurge.getAwbPiece())))) {
               if (!piecesInfoForPurge.getInventoryPieces().equals(BigInteger.ZERO)) {
                  piecesInfoForPurge.addError("shp.not.complt.frt.out.chk", "", ErrorType.ERROR);
                  throw new CustomException(piecesInfoForPurge.getMessageList());
               }
            }

         }
         // Check for Failed Cases
         // Check fo Succes Case 1-if i. AWB Pcs = Frt Out Pcs + 0 Inventory Pcs
         if (piecesInfoForPurge.getInventoryPieces().equals(BigInteger.ZERO)
               && piecesInfoForPurge.getFreightOutPieces().equals(piecesInfoForPurge.getAwbPiece())) {
            piecesInfoForPurge.setReadyForPurge(true);
         }
         // 2-if 0 Inventory + 0 Freight out + MSCA or MSAW
         else if (piecesInfoForPurge.getInventoryPieces().equals(BigInteger.ZERO)
               && piecesInfoForPurge.getFreightOutPieces().equals(BigInteger.ZERO)) {
            if ((piecesInfoForPurge.getMissingCargoPieces().equals(piecesInfoForPurge.getAwbPiece()))
                  || (piecesInfoForPurge.getMissingAWBPieces().equals(piecesInfoForPurge.getAwbPiece()))) {
               piecesInfoForPurge.setReadyForPurge(true);
            }
         }
         // 3-if AWB Pcs = Freight out pcs + MSCA pcs as balance
         else if (piecesInfoForPurge.getInventoryPieces().equals(BigInteger.ZERO) && piecesInfoForPurge.getAwbPiece()
               .equals(piecesInfoForPurge.getFreightOutPieces().add(piecesInfoForPurge.getMissingCargoPieces()))) {
            piecesInfoForPurge.setReadyForPurge(true);
         } else {
            piecesInfoForPurge.setReadyForPurge(false);
         }
         // Check for Success Case
      }
      return piecesInfoForPurge;
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * executeProcedureToMerge()
    */
   @Override
   public void executeProcedureToMerge() throws CustomException {
      this.shipmentInformationDAO.executeProcedureToMerge();
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * getUrlForClearingShipmentDateCache()
    */
   @Override
   public String getUrlForClearingShipmentDateCache() throws CustomException {
      return this.shipmentInformationDAO.getUrlForClearingShipmentDateCache();
   }
   
   /*	
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.shipment.information.service.ShipmentInformationService#
    * isHandledByHouse()
    */
	@Override
	public boolean isHandledByHouse(ShipmentMaster shipmentMaster) throws CustomException {
		HAWBHandlingHelperRequest hAWBHandlingHelperRequest = new HAWBHandlingHelperRequest();
		hAWBHandlingHelperRequest.setShipmentNumber(shipmentMaster.getShipmentNumber());
		hAWBHandlingHelperRequest.setShipmentDate(shipmentMaster.getShipmentDate());
		return hawbHandlingHelper.isHandledByHouse(hAWBHandlingHelperRequest);
	}



	/**
	 * This method is use for getting shipment dimension List
	 */
	@Override
	public DimensionInfoModel getDimensionInfo(@Valid ShipmentInfoSearchReq search) throws CustomException {
		LOGGER.info("Method start getDimensionInfo");
		DimensionInfoModel dimensionInfoModel=new DimensionInfoModel();
		DimensionInfoModel dimBookingdetails= shipmentInformationDAO.getBookingId(search.getShipmentNumber());
	    dimensionInfoModel.setWeightUnitCode(dimBookingdetails.getWeightUnitCode());
		dimensionInfoModel.setBookingId(dimBookingdetails.getBookingId());
		dimensionInfoModel.setShipmentNumber(search.getShipmentNumber());
	    List<DimensionInfoModel> dimensionList=shipmentInformationDAO.getDimensionInfo(dimBookingdetails.getBookingId());
	    if(dimensionList!=null && !dimensionList.isEmpty()) {
		    dimensionList.forEach(p->p.setBookingId(dimBookingdetails.getBookingId()));
		    dimensionList.forEach(p->p.setShipmentNumber(search.getShipmentNumber()));
	    }
	    dimensionInfoModel.setDimensionList(dimensionList);
	    LOGGER.info("Method End getDimensionInfo, dimensionList : "+dimensionList);
	    return dimensionInfoModel;
	}
	
	/**
	 * This method is use for saving/updating/deleting shipment dimension Info
	 */
	@Override
	public DimensionInfoModel saveShipmentDimensions(DimensionInfoModel dimensionInfoModel) throws CustomException {
		LOGGER.info("Method start saveShipmentDimensions");
		int count=1;
		DimensionInfoModel dimensionModel=new DimensionInfoModel();
		try {
		List<DimensionInfoModel> dimensionList=dimensionInfoModel.getDimensionList();
		DimensionInfoModel dimBookingdetails= shipmentInformationDAO.getBookingId(dimensionInfoModel.getShipmentNumber());
		List<Integer> dimIds = shipmentInformationDAO.getDimensionsId(dimBookingdetails.getBookingId());
		LOGGER.info("dimension Ids : "+dimIds);
		if (!CollectionUtils.isEmpty(dimIds)) {
			for (Integer dimId : dimIds) {
				shipmentInformationDAO.deleteShipemntDimensions(dimId);
			}
		}
		for (DimensionInfoModel dim : dimensionList) {
			dim.setBookingId(dimBookingdetails.getBookingId());
			dim.setTransactionSequenceNo(count);
			dim.setShipmentDate(dimensionInfoModel.getShipmentDate());
			dim.setShipmentNumber(dimensionInfoModel.getShipmentNumber());
			dim.setUserType("MANUAL");
			count++;
		}
		shipmentInformationDAO.saveShipmentDimensions(dimensionList);
		dimensionModel.setDimensionList(dimensionList);
		}catch(Exception exception ) {
			 LOGGER.error("Exception while saving Shipment Dimension", exception);
		}
		LOGGER.info("Method end saveShipmentDimensions");
		return dimensionModel;
	}
}