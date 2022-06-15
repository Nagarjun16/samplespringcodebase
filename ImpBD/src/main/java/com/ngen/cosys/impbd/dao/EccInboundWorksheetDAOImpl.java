package com.ngen.cosys.impbd.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.AuthorizeTo;
import com.ngen.cosys.impbd.model.EccInboundResult;
import com.ngen.cosys.impbd.model.EquipmentOperator;
import com.ngen.cosys.impbd.model.SearchInbound;
import com.ngen.cosys.impbd.model.ShipmentList;
import com.ngen.cosys.impbd.model.ShipmentListDetails;
import com.ngen.cosys.impbd.model.SpecialHandlingCode;

/**
 * @author NIIT Technogies
 *
 */
@Repository("EccInboundWorksheetDAO")
public class EccInboundWorksheetDAOImpl extends BaseDAO implements EccInboundWorksheetDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionEcc;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#getWorksheetID(com.ngen.cosys
    * .impbd.model.SearchInbound)
    */
   @Override
   public int getWorksheetID(SearchInbound searchInbound) throws CustomException {
      int worksheetID;
      if (super.fetchObject("fetchWorksheetID", searchInbound, sqlSessionEcc) != null) {
         worksheetID = super.fetchObject("fetchWorksheetID", searchInbound, sqlSessionEcc);
      } else {
         worksheetID = 0;
      }
      return worksheetID;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#getDetailsICIDTC(com.ngen.
    * cosys.impbd.model.EccInboundResult)
    */
   @Override
   public EccInboundResult getDetailsICIDTC(EccInboundResult eccInboundResult) throws CustomException {
      return super.fetchObject("getDetails", eccInboundResult, sqlSessionEcc);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#getShipmentList(com.ngen.
    * cosys.impbd.model.SearchInbound)
    */
   @Override
   public List<ShipmentList> getShipmentList(SearchInbound searchInbound) throws CustomException {
      return super.fetchList("getShip", searchInbound, sqlSessionEcc);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#getShipmentDetails(com.ngen.
    * cosys.impbd.model.ShipmentList)
    */
   @Override
   public List<ShipmentListDetails> getShipmentDetails(ShipmentList shipmentList) throws CustomException {
      return super.fetchList("sqlGetShipmentDetails", shipmentList, sqlSessionEcc);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#getTeamName(com.ngen.cosys.
    * impbd.model.SearchInbound)
    */
   @Override
   public List<String> getTeamName(SearchInbound searchInbound) throws CustomException {
      return super.fetchList("getTeamName", searchInbound, sqlSessionEcc);
   }

   /*
    * (non-Javadoc)s
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#save(com.ngen.cosys.impbd.
    * model.EccInboundResult)
    */
   @Override
   public EccInboundResult save(EccInboundResult eccInboundResult) throws CustomException {
      if (super.fetchObject("fetchWorkingShift", eccInboundResult, sqlSessionEcc) == null) {
         insertData("insertShift", eccInboundResult, sqlSessionEcc);
         int worksheetID = super.fetchObject("searchWorksheetID", eccInboundResult, sqlSessionEcc);
         eccInboundResult.setWorksheetID(worksheetID);
         for (ShipmentList ele : eccInboundResult.getShipmentList()) {
            ele.setWorksheetID(eccInboundResult.getWorksheetID());
            ShipmentList e = validateEO(ele);
            for (ShipmentListDetails details : e.getShipmentListDetails()) {
               details.setWorksheetID(eccInboundResult.getWorksheetID());
               for (EquipmentOperator eqp : details.getEqpOperator()) {
                  if (eqp.isErrorFlag()) {
                     return eccInboundResult;
                  }
               }
            }
            saveByFlight(ele);
         }
      } else if (super.fetchObject("fetchWorkingShift", eccInboundResult, sqlSessionEcc) != null) {
         insertData("updateShift", eccInboundResult, sqlSessionEcc);
         for (ShipmentList ele : eccInboundResult.getShipmentList()) {
            ele.setWorksheetID(eccInboundResult.getWorksheetID());
            ShipmentList e = validateEO(ele);
            for (ShipmentListDetails details : e.getShipmentListDetails()) {
               details.setWorksheetID(eccInboundResult.getWorksheetID());
               for (EquipmentOperator eqp : details.getEqpOperator()) {
                  if (eqp.isErrorFlag()) {
                     return eccInboundResult;
                  }
               }
            }
            saveByFlight(ele);
         }
      }
      return eccInboundResult;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#validateUsers(com.ngen.cosys.
    * impbd.model.EccInboundResult)
    */
   public void validateUsers(EccInboundResult eccInboundResult) throws CustomException {
      if (!CollectionUtils.isEmpty(eccInboundResult.getAuthorizeTo())) {
         for (AuthorizeTo at : eccInboundResult.getAuthorizeTo()) {
            if (StringUtils.isEmpty(super.fetchObject("fetchUser", at, sqlSessionEcc))) {
               throw new CustomException("ECC001", null, ErrorType.ERROR);
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#validateAgent(com.ngen.cosys.
    * impbd.model.EccInboundResult)
    */
   public void validateAgent(EccInboundResult eccInboundResult) throws CustomException {
      for (ShipmentList list : eccInboundResult.getShipmentList()) {
         for (ShipmentListDetails detail : list.getShipmentListDetails()) {
            if (super.fetchObject("fetchCustID", detail, sqlSessionEcc) == null) {
               throw new CustomException("ECC005", null, ErrorType.ERROR);
            }
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#saveByFlight(com.ngen.cosys.
    * impbd.model.ShipmentList)
    */
   private void validateStatus(ShipmentListDetails shipment) throws CustomException {
      if (super.fetchObject("searchShipmentID", shipment, sqlSessionEcc) != null) {
         int shipmentId = super.fetchObject("searchShipmentID", shipment, sqlSessionEcc);
         shipment.setShipmentId(shipmentId);
      }
      int delivered = super.fetchObject("checkDelivered", shipment, sqlSessionEcc);
      int ok = super.fetchObject("checkOK", shipment, sqlSessionEcc);
      int handover = super.fetchObject("checkHANDEDOVER", shipment, sqlSessionEcc);
      if ((shipment.getStatus().equalsIgnoreCase("DELIVERED"))) {
         if (delivered == 0) {
            shipment.addError("checkDel", "status", ErrorType.ERROR);
            shipment.setErrorFlag(true);

         }
      } else if ((shipment.getStatus().equalsIgnoreCase("HANDEDOVER"))) {
         if (handover == 0) {
            shipment.addError("checkHand", "status", ErrorType.ERROR);
            shipment.setErrorFlag(true);
         }
      } else if ((shipment.getStatus().equalsIgnoreCase("OK"))) {
         if (ok == 0) {
            shipment.addError("checkOk", "status", ErrorType.ERROR);
            shipment.setErrorFlag(true);
         }
      }
   }

   /**
    * @param shipmentList
    * @return
    * @throws CustomException
    */
   private ShipmentList saveByFlight(ShipmentList shipmentList) throws CustomException {
      List<EquipmentOperator> tempEo = null;
      for (ShipmentListDetails e : shipmentList.getShipmentListDetails()) {
         validateShc(e);
         e.setFlightKey(shipmentList.getFlightKey());
         e.setFlightDate(shipmentList.getSta().toLocalDate());
         if (e.getStatus() != null && !e.getStatus().isEmpty()) {
            validateStatus(e);
         }
         if (e.isErrorFlag()) {
            return shipmentList;
         }

         if (e.getFlagMaintain().equalsIgnoreCase("U")) {
            if (!e.getFlagCRUD().equalsIgnoreCase("D")) {
               e.setFlightID(shipmentList.getFlightID());
               updateShipmentDetailsByFlight(e);
               deleteData("deleteSHC", e, sqlSessionEcc);
               for (SpecialHandlingCode e1 : e.getShcList()) {
                  e1.setWorksheetShipmentID(e.getWorksheetShipmentID());
                  insertData("insertSHC", e1, sqlSessionEcc);
               }
               if (!"HANDEDOVER".equalsIgnoreCase(e.getStatus())) {
                  deleteData("deleteEo", e, sqlSessionEcc);
                  for (EquipmentOperator e1 : e.getEqpOperator()) {
                     e1.setWorksheetShipmentID(e.getWorksheetShipmentID());
                     insertData("insertEo", e1, sqlSessionEcc);
                  }
               }
            }
         }

         else if (e.getFlagMaintain().equalsIgnoreCase("C")) {
            e.setWorksheetID(shipmentList.getWorksheetID());
            e.setFlightID(shipmentList.getFlightID());
            e.setCreatedOn(LocalDateTime.now());
            e.setCreatedBy(shipmentList.getLoggedInUser());
            int count = checkShipment(e);
            if (count > 0) {
               e.addError("ECC002", "shipmentNumber", ErrorType.ERROR);
            }
            saveShipmentDetailsByFlight(e);
            deleteData("deleteSHC", e, sqlSessionEcc);
            for (SpecialHandlingCode e1 : e.getShcList()) {
               if (e1.getShc() != "") {
                  e1.setWorksheetShipmentID(e.getWorksheetShipmentID());
                  e1.setWorksheetShipmentID(e.getWorksheetShipmentID());
                  e1.setCreatedOn(LocalDateTime.now());
                  e1.setCreatedBy(shipmentList.getLoggedInUser());
                  insertData("insertSHC", e1, sqlSessionEcc);
               }
            }
            deleteData("deleteEo", e, sqlSessionEcc);
            if ((e.getEqpOperator() == null || e.getEqpOperator().isEmpty()) && tempEo != null) {
               e.setEqpOperator(tempEo);
            }
            for (EquipmentOperator e1 : e.getEqpOperator()) {
               e1.setWorksheetShipmentID(e.getWorksheetShipmentID());
               insertData("insertEo", e1, sqlSessionEcc);
            }
            if (e.getEqpOperator() != null && e.getEqpOperator().isEmpty()) {
               tempEo = e.getEqpOperator();
            }
         }
      }
      return shipmentList;
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.impbd.dao.EccInboundWorksheetDAO#deleteByFlight(com.ngen.cosys
    * .impbd.model.ShipmentListDetails)
    */
   @Override
   @Transactional(rollbackFor = Throwable.class)
   public ShipmentListDetails deleteByFlight(ShipmentListDetails shipmentListDetails) throws CustomException {
      deleteData("deleteSHC", shipmentListDetails, sqlSessionEcc);
      deleteData("deleteEo", shipmentListDetails, sqlSessionEcc);
      deleteData("deleteShip", shipmentListDetails, sqlSessionEcc);
      return shipmentListDetails;
   }

   /**
    * @param shipmentListDetails
    * @throws CustomException
    */
   private ShipmentList validateEO(ShipmentList shipmentList) throws CustomException {
      List<EquipmentOperator> eqp = new ArrayList<>();
      List<ShipmentListDetails> details = new ArrayList<>();
      for (ShipmentListDetails shipmentListDetails : shipmentList.getShipmentListDetails()) {
    	  if(!"D".equalsIgnoreCase(shipmentListDetails.getFlagCRUD())) {
    		  for (EquipmentOperator e : shipmentListDetails.getEqpOperator()) {
    	            String userID = super.fetchObject("fetchEO", e.getEo(), sqlSessionEcc);
    	            e.setUserid(userID);
    	            e.setEo(e.getEo());
    	            eqp.add(e);
    	            if (userID == null) {
    	               e.addError("InvalidEO", "eo", ErrorType.ERROR);
    	               e.setErrorFlag(true);
    	            }
    	         }
    	  }
         
         shipmentListDetails.setEqpOperator(eqp);
         details.add(shipmentListDetails);
         eqp = new ArrayList<>();
      }
      shipmentList.setShipmentListDetails(details);
      return shipmentList;
   }

   private void validateShc(ShipmentListDetails shipmentListDetails) throws CustomException {
	   if(!"D".equalsIgnoreCase(shipmentListDetails.getFlagCRUD())) {
		   if (CollectionUtils.isEmpty(shipmentListDetails.getShcList())) {
		         throw new CustomException("ECC007", "shcList", ErrorType.ERROR);
		      }
		      for (SpecialHandlingCode e : shipmentListDetails.getShcList()) {
		         int count = super.fetchObject("fetchShc", e.getShc(), sqlSessionEcc);
		         if (count == 0) {
		            e.addError("shccode", "shc", ErrorType.ERROR);
		            shipmentListDetails.setErrorFlag(true);
		         }
		      }
	   }
      
   }

   @Override
   public int checkShipment(ShipmentListDetails shipmentListDetails) throws CustomException {
      return super.fetchObject("checkShipment", shipmentListDetails, sqlSessionEcc);
   }

   @Override
   public List<String> getEOSummary(SearchInbound searchInbound) throws CustomException {
      return super.fetchList("getEOSummary", searchInbound, sqlSessionEcc);
   }
   
   @Override
   public int checkShipmentDetails(ShipmentListDetails shipmentListDetails) throws CustomException {
      return super.fetchObject("checkShipmentDetails", shipmentListDetails, sqlSessionEcc);
   }
   
   //For new Audit trail implementation to avoid insert multiple records implementing this method
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ECC_SHIPMNT)
   public void saveShipmentDetailsByFlight(ShipmentListDetails shipmentListDetails) throws CustomException {
	   shipmentListDetails.setAgentCode(fetchCustomerCode(shipmentListDetails));
	   insertData("insertShipment", shipmentListDetails, sqlSessionEcc);
   }
   //For new Audit trail implementation to avoid insert multiple records implementing this method
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ECC_SHIPMNT)
   public void updateShipmentDetailsByFlight(ShipmentListDetails shipmentListDetails) throws CustomException {
	   shipmentListDetails.setAgentCode(fetchCustomerCode(shipmentListDetails));
	   updateData("updateShipment", shipmentListDetails, sqlSessionEcc);
   }

	@Override
	public List<ShipmentListDetails> getCustomerIdByAgentCodeOrAgentId(ShipmentListDetails detail) throws CustomException {
		return super.fetchList("getCustomerIdByAgentCodeOrAgentId", detail, sqlSessionEcc);
	}
	   
   
   //Audit trail issue 3728
	
	public String fetchCustomerCode(ShipmentListDetails shipment) throws CustomException{
		return super.fetchObject("fetchCustomerCode", shipment, sqlSessionEcc);
		
	}


}