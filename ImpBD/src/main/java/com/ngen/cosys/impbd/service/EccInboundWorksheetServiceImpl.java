package com.ngen.cosys.impbd.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.exception.SystemException;
import com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO;
import com.ngen.cosys.impbd.model.EccInboundResult;
import com.ngen.cosys.impbd.model.EquipmentOperator;
import com.ngen.cosys.impbd.model.SearchInbound;
import com.ngen.cosys.impbd.model.ShipmentList;
import com.ngen.cosys.impbd.model.ShipmentListDetails;

@Service
public class EccInboundWorksheetServiceImpl implements EccInboundWorksheetService {

   @Autowired
   private EccInboundWorksheetDAO eccInboundWorksheetDAO;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.service.EccInboundWorksheetService#search(com.ngen.cosys
    * .impbd.model.SearchInbound)
    */
   @Override
   public EccInboundResult search(SearchInbound searchInbound) throws CustomException {
      EccInboundResult eccInboundResult = new EccInboundResult();

      // Calculate the shift start and end time. Minus a day if shift start time is
      // greater than end time
      LocalDateTime shiftStartTime = LocalDateTime.of(searchInbound.getDate(), searchInbound.getStartsAt());
      LocalDateTime shiftEndTime = LocalDateTime.of(searchInbound.getDate(), searchInbound.getEndsAt());

      if (searchInbound.getStartsAt().compareTo(searchInbound.getEndsAt()) > 0) {
    	  shiftEndTime = LocalDateTime.of(searchInbound.getDate(), searchInbound.getEndsAt()).plusDays(1);
      }

      searchInbound.setShiftStartTime(shiftStartTime);
      searchInbound.setShiftEndTime(shiftEndTime);

      eccInboundResult.setDate(searchInbound.getDate());

      int worksheetID = eccInboundWorksheetDAO.getWorksheetID(searchInbound);
      if (worksheetID != 0) {
         eccInboundResult.setWorksheetID(worksheetID);
         EccInboundResult ecc = eccInboundWorksheetDAO.getDetailsICIDTC(eccInboundResult);
         if (null != ecc) {
            eccInboundResult.setPlannedBy(ecc.getPlannedBy());
            eccInboundResult.setFlightHandledBy(ecc.getFlightHandledBy());
         }

         List<ShipmentList> list = eccInboundWorksheetDAO.getShipmentList(searchInbound);
         for (ShipmentList i : list) {
            i.setAgent(searchInbound.getAgent());
            List<ShipmentListDetails> s = eccInboundWorksheetDAO.getShipmentDetails(i);
            i.setShipmentListDetails(s);
         }
         eccInboundResult.setShipmentList(list);
         List<String> teamName = eccInboundWorksheetDAO.getTeamName(searchInbound);
         eccInboundResult.setTeamName(teamName);
         List<String> eoSummary = eccInboundWorksheetDAO.getEOSummary(searchInbound);
         eccInboundResult.setEoSummary(eoSummary);
         return eccInboundResult;
      } else {
         List<ShipmentList> list = eccInboundWorksheetDAO.getShipmentList(searchInbound);
         for (ShipmentList i : list) {
            i.setAgent(searchInbound.getAgent());
            List<ShipmentListDetails> s = eccInboundWorksheetDAO.getShipmentDetails(i);
            i.setShipmentListDetails(s);
         }
         for (ShipmentList ele : list) {
            for (ShipmentListDetails element : ele.getShipmentListDetails()) {
               int count = eccInboundWorksheetDAO.checkShipment(element);
               if (count == 0) {
                  element.setColour(true);
               }
            }
         }
         eccInboundResult.setShipmentList(list);
         List<String> teamName = eccInboundWorksheetDAO.getTeamName(searchInbound);
         eccInboundResult.setTeamName(teamName);
         List<String> eoSummary = eccInboundWorksheetDAO.getEOSummary(searchInbound);
         eccInboundResult.setEoSummary(eoSummary);
         return eccInboundResult;
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.service.EccInboundWorksheetService#save(com.ngen.cosys.
    * impbd.model.EccInboundResult)
    */
   @Override
   @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = CustomException.class)
   public EccInboundResult save(EccInboundResult eccInboundResult) throws CustomException {
      eccInboundResult.setCreatedBy(eccInboundResult.getLoggedInUser());
      eccInboundResult.setCreatedOn(LocalDateTime.now());
      eccInboundResult.getShipmentList().stream().forEach(e -> {
         e.getShipmentListDetails().stream().forEach(ele -> {
        	 if (!"D".equalsIgnoreCase(ele.getFlagCRUD()) && ele.getAgent()==null) {
        		 ele.addError("g.mandatory", "agent", ErrorType.ERROR);
        	 }
            if ("D".equalsIgnoreCase(ele.getFlagCRUD())) {
               try {
                  eccInboundWorksheetDAO.deleteByFlight(ele);
               } catch (CustomException e1) {
                  throw new SystemException(e1);
               }
            }
         });
      });
      for(ShipmentList shipment:eccInboundResult.getShipmentList()){
    	  for(ShipmentListDetails detail:shipment.getShipmentListDetails()) {
    		  detail.setFlightID(shipment.getFlightID());
    		  if(!"D".equalsIgnoreCase(detail.getFlagCRUD())) {
    			  int duplicatshipment =  eccInboundWorksheetDAO.checkShipmentDetails(detail);
    			  if(duplicatshipment>0) {
    				  detail.addError("ECC002", "shipmentNumber", ErrorType.ERROR);
    			  }
    			  if (!detail.getMessageList().isEmpty()) {
    	               throw new CustomException();
    	            }
    		  }
    	  }
    	  
    	  
      }
		for (ShipmentList shipment : eccInboundResult.getShipmentList()) {
			for (ShipmentListDetails detail : shipment.getShipmentListDetails()) {
				List<ShipmentListDetails> customerDetails = eccInboundWorksheetDAO.getCustomerIdByAgentCodeOrAgentId(detail);
				if(customerDetails != null && !customerDetails.isEmpty()) {
					detail.setAgent(customerDetails.get(0).getCustomerID());
				} 
				
			}
		}
      EccInboundResult result = eccInboundWorksheetDAO.save(eccInboundResult);

      if (!result.getMessageList().isEmpty()) {
         throw new CustomException();
      } else {
         for (ShipmentList err : result.getShipmentList()) {
            if (!err.getMessageList().isEmpty()) {
               throw new CustomException();
            }
         }
         for (ShipmentList err : result.getShipmentList()) {
            for (ShipmentListDetails detail : err.getShipmentListDetails()) {
               if (!detail.getMessageList().isEmpty()) {
                  throw new CustomException();
               }
               for (EquipmentOperator eqp : detail.getEqpOperator()) {
                  if (!eqp.getMessageList().isEmpty()) {
                     throw new CustomException();
                  }
               }
            }
         }
         return result;
      }
   }



}