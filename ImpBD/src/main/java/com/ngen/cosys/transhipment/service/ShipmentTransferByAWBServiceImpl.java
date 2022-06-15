package com.ngen.cosys.transhipment.service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.enums.EventTypes;
import com.ngen.cosys.events.payload.TranshipmentTransferManifestFinalizedStoreEvent;
import com.ngen.cosys.events.producer.TranshipmentTransferManifestFinalizedStoreEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.timezone.util.TenantZoneTime;
import com.ngen.cosys.transhipment.dao.ShipmentTransferByAWBDAO;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWB;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBInfo;
import com.ngen.cosys.transhipment.model.TranshipmentTransferManifestByAWBSearch;

@Service
public class ShipmentTransferByAWBServiceImpl implements ShipmentTransferByAWBService {

   private static final String DNATA = "DNATA";

   @Autowired
   ShipmentTransferByAWBDAO dao;

   @Autowired
   TranshipmentTransferManifestFinalizedStoreEventProducer eventProducer;

   @Override
   public TranshipmentTransferManifestByAWBSearch searchList(TranshipmentTransferManifestByAWBSearch search)
         throws CustomException {
      return dao.searchList(search);
   }

   @Override
   public TranshipmentTransferManifestByAWB search(TranshipmentTransferManifestByAWBSearch search)
         throws CustomException {
      return dao.search(search);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public TranshipmentTransferManifestByAWB maintain(TranshipmentTransferManifestByAWB maintain)
			throws CustomException {
		if (!CollectionUtils.isEmpty(maintain.getAwbInfoList())) {
			if (maintain.getAllowRoutingErrorPrompt()) {
				checkForRouting(maintain);
			}else {
				maintain.setRoutingErrorPrompt(null);
			}
			if (StringUtils.isEmpty(maintain.getRoutingErrorPrompt())) {
				maintain = getTRMNumber(maintain);
				return dao.maintain(maintain);
			} else {
				return maintain;
			}
		} else {
			throw new CustomException("impbd.add.shipments", null, ErrorType.ERROR);
		}
	}

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public TranshipmentTransferManifestByAWBSearch cancelAWB(TranshipmentTransferManifestByAWBSearch cancelAWB)
         throws CustomException {
      return dao.cancelAWB(cancelAWB);
   }

   @Override
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
   public TranshipmentTransferManifestByAWBSearch finalizeAWB(TranshipmentTransferManifestByAWBSearch finalizeAWB)
         throws CustomException {
      dao.finalizeAWB(finalizeAWB);
      if (!CollectionUtils.isEmpty(finalizeAWB.getAwbList())) {
          for (TranshipmentTransferManifestByAWB awb : finalizeAWB.getAwbList()) {
             if (!CollectionUtils.isEmpty(awb.getAwbInfoList())) {
                for (TranshipmentTransferManifestByAWBInfo awbInfo : awb.getAwbInfoList()) {
                   if (awb.getFinalizedFlag()) {
                      TranshipmentTransferManifestFinalizedStoreEvent event = new TranshipmentTransferManifestFinalizedStoreEvent();
                      event.setFinalizedAt(LocalDateTime.now());
                      event.setFinalizedBy(finalizeAWB.getCreatedBy());
                      event.setStatus("NEW");
                      event.setTrmNumber(awb.getTrmNumber());
                      event.setShipmentNumber(awbInfo.getShipmentNumber());
                      event.setShipmentDate(awbInfo.getShipmentDate());
                      event.setTransferringCarrier(awb.getCarrierCodeFrom());
                      event.setReceivingCarrier(awb.getCarrierCodeTo());
                      event.setCreatedBy(finalizeAWB.getCreatedBy() + finalizeAWB.getCreatedBy());
                      event.setCreatedOn(LocalDateTime.now());
                      event.setFunction("Shipment Transfer By AWB");
                      event.setEventName(EventTypes.Names.TRANSHIPMENT_TRANSFER_MANIFEST_FINALIZED_EVENT);
                      eventProducer.publish(event);
                      awbInfo.setTrmNumber(awb.getTrmNumber());                    
                   }
                   else
                   {
                	   awbInfo.setTrmNumber(awb.getTrmNumber());
                       dao.transferDataBackToInventory(awbInfo);
                   }
                   if (awbInfo.getInboundFlightHandler().equals(DNATA) && awb.getFinalizedFlag() )
                   {
                	   dao.transferDataToFreightOut(awbInfo);
                   }
                 
                }
             }
          }
       }

      return finalizeAWB;
   }

   @Override
   public TranshipmentTransferManifestByAWB getTRMNumber(TranshipmentTransferManifestByAWB maintain)
         throws CustomException {
      if (StringUtils.isEmpty(maintain.getTrmNumber())
    		  && !StringUtils.isEmpty(maintain.getCarrierCodeFrom())
    		  && !StringUtils.isEmpty(maintain.getCarrierCodeTo())) {
        String dbCount = dao.getTrmCount(maintain);
         maintain.setTrmNumber(maintain.getCarrierCodeFrom() +dbCount);
         if (maintain.getTrmNumber().contains("000000")) {
            maintain.setTrmNumber(maintain.getCarrierCodeFrom() + "000001");
         }
         maintain.setIssuedDate((TenantZoneTime.getZoneDateTime(LocalDateTime.now(), maintain.getTenantId())));
      }
      return maintain;
   }

   @Override
   public TranshipmentTransferManifestByAWBInfo getShipmentDetail(TranshipmentTransferManifestByAWBInfo maintain)
         throws CustomException {
      TranshipmentTransferManifestByAWBInfo result = dao.getShipmentDetail(maintain);
      if (result != null) {
         if (!result.isExportDataFlag()) {
            if (result.getPieces() <= (result.getIrregularityPieces() + result.getInventoryPieces())) {
               return result;
            } else {
               return null;
            }
         } else {
            return result;
         }
      } else {
         return null;
      }
   }

   @Override
   public TranshipmentTransferManifestByAWB mobileSearch(TranshipmentTransferManifestByAWBSearch search)
         throws CustomException {

      TranshipmentTransferManifestByAWB retrunObject = dao.search(search);
      if (!ObjectUtils.isEmpty(retrunObject)) {
         if (!CollectionUtils.isEmpty(retrunObject.getAwbInfoList())) {
            retrunObject.setAwbInfoList(retrunObject.getAwbInfoList().stream()
                  .filter(e -> DNATA.equals(e.getInboundFlightHandler())).collect(Collectors.toList()));
            retrunObject.setHandlingTerminalCode(DNATA);
            if (ObjectUtils.isEmpty(retrunObject.getFinalizedFlag()) || !retrunObject.getFinalizedFlag()) {
               retrunObject.setError(true);
               retrunObject.addError("STBAWB0003", "", ErrorType.ERROR);
               return retrunObject;
            }
            if (!StringUtils.isEmpty(retrunObject.getRejectionReason())) {
               retrunObject.setError(true);
               retrunObject.addError("STBAWB0005", "", ErrorType.ERROR);
               return retrunObject;
            }
            if (!StringUtils.isEmpty(retrunObject.getHandoverTo())) {
               retrunObject.setError(true);
               retrunObject.addError("STBAWB0006", "", ErrorType.ERROR);
               return retrunObject;
            }
            if (CollectionUtils.isEmpty(retrunObject.getAwbInfoList())) {
               retrunObject.setError(true);
               retrunObject.addError("STBAWB0004", "", ErrorType.ERROR);
            }
         } else {
            retrunObject.setError(true);
            retrunObject.addError("STBAWB0001", "", ErrorType.ERROR);
         }
      } else {
         retrunObject = new TranshipmentTransferManifestByAWB();
         retrunObject.addError("STBAWB0002", "", ErrorType.ERROR);
         retrunObject.setError(true);
      }
      return retrunObject;
   }

   @Override
   public TranshipmentTransferManifestByAWB mobileMaintain(TranshipmentTransferManifestByAWB search)
         throws CustomException {
      if (!StringUtils.isEmpty(search.getRejectionReason()) && !StringUtils.isEmpty(search.getHandoverTo())) {
         search.setError(true);
         search.addError("STBAWB0007", "", ErrorType.ERROR);
         return search;
      }
      if (StringUtils.isEmpty(search.getRejectionReason()) && StringUtils.isEmpty(search.getHandoverTo())) {
         search.setError(true);
         search.addError("STBAWB0008", "", ErrorType.ERROR);
         return search;
      }

      return dao.mobileMaintain(search);
   }

   @Override
   public String getCarrierNameBasedOnCarrierCode(String carrierCode) throws CustomException {
      return dao.getCarrierNameBasedOnCarrierCode(carrierCode);
   }

   private TranshipmentTransferManifestByAWB checkForRouting(TranshipmentTransferManifestByAWB awb) throws CustomException {
      StringBuilder collectedShipmentNumbers = new StringBuilder();
      for (TranshipmentTransferManifestByAWBInfo awbInfo : awb.getAwbInfoList()) {
         // code to check for routing before insertion in database
         if (awbInfo.isSelect()) {
            awbInfo.setTransferCarrier(awb.getCarrierCodeFrom());
            awbInfo.setRecievingCarrier(awb.getCarrierCodeTo());
            int checkRoutingExists = dao.checkRouting(awbInfo);
            if (checkRoutingExists == 0) {
               collectedShipmentNumbers.append(" " + awbInfo.getShipmentNumber());
               awb.setRoutingErrorPrompt(awbInfo.getShipmentNumber());
            }
         }
      }
      return awb;
   }

}