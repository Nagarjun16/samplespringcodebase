/**
 * This is a class which holds entire functionality for managing of irregularity
 * information based on document/found pieces for shipment.
 * 
 * Usage: 1. Auto-wire this class service interface 2. invoke the
 * createIrregularity() method
 * 
 * @author NIIT Technologies Pvt Ltd
 * @version 1.0
 * @since 23/03/2018
 */
package com.ngen.cosys.impbd.shipment.irregularity.service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.events.enums.EventStatus;
import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.producer.InboundShipmentBreakDownCompleteStoreEventProducer;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.shipment.irregularity.dao.ShipmentIrregularityDAO;
import com.ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class ShipmentIrregularityServiceImpl implements ShipmentIrregularityService {

   @Autowired
   private ShipmentIrregularityDAO dao;
   
   @Autowired
   private InboundShipmentBreakDownCompleteStoreEventProducer producer;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.irregularity.service.
    * ShipmentIrregularityService#createIrregularity(com.ngen.cosys.impbd.shipment.
    * irregularity.model.ShipmentIrregularityModel)
    */
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ADD_MAINTAIN_SHIPMENT_IRREGULARITY)
   public void createIrregularity(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException {
      // Get the existing irregularity if any otherwise insert a new irregularity
      ShipmentIrregularityModel t = dao.get(shipmentIrregularityModel);
      Optional<ShipmentIrregularityModel> o = Optional.ofNullable(t);
      if (!o.isPresent()) {
         // create instance of Random class
         Random rand = new Random();

         // Generate random integers in range 0 to 999
         int transactionSequenceNumber = rand.nextInt(1000);
         shipmentIrregularityModel.setTransactionSequenceNumber(BigInteger.valueOf(transactionSequenceNumber));
         dao.insert(shipmentIrregularityModel);
      }

   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.impbd.shipment.irregularity.service.
    * ShipmentIrregularityService#closeIrregularity(com.ngen.cosys.impbd.shipment.
    * irregularity.model.ShipmentIrregularityModel)
    */
   @Override
   public void closeIrregularity(ShipmentIrregularityModel shipmentIrregularityModel) throws CustomException {
      
	   dao.delete(shipmentIrregularityModel);
      
        //Calling RCF/NFD messages event  only for import
	   if(MultiTenantUtility.isTenantCityOrAirport(shipmentIrregularityModel.getDestination())) {
			InboundShipmentBreakDownCompleteStoreEvent event = new InboundShipmentBreakDownCompleteStoreEvent();
          event.setShipmentId(shipmentIrregularityModel.getShipmentId());
          event.setFlightId(shipmentIrregularityModel.getFlightId());
          event.setPieces(shipmentIrregularityModel.getPiece());
          event.setWeight(shipmentIrregularityModel.getWeight());
          event.setStatus(EventStatus.NEW.getStatus());
          event.setCompletedAt(LocalDateTime.now());
          event.setCompletedBy(shipmentIrregularityModel.getCreatedBy());
          event.setCreatedOn(LocalDateTime.now());
          event.setCreatedBy(shipmentIrregularityModel.getCreatedBy());
          event.setFunction("Deleting irregurlarty");
          event.setEventName("OnDeletingIrregularity");
          producer.publish(event);
		}
   }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.impbd.shipment.irregularity.service.
	 * ShipmentIrregularityService#validatePieceInfoForMorethanOneHouseShipment(com.
	 * ngen.cosys.impbd.shipment.irregularity.model.ShipmentIrregularityModel)
	 */
	@Override
	public Boolean validatePieceInfoForMorethanOneHouseShipment(ShipmentIrregularityModel shipmentIrregularityModel)
			throws CustomException {
		return this.dao.validatePieceInfoForMorethanOneHouseShipment(shipmentIrregularityModel);
	}

}