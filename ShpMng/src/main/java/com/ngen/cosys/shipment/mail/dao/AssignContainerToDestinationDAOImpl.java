/**
 * 
 * AssignContainerToDestinationDAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestination;
import com.ngen.cosys.shipment.mail.model.AssignContainerToDestinationDetails;
import com.ngen.cosys.shipment.mail.model.SearchAssignToContainerToDestinationDetails;

/**
 * This class takes care of the Assign Container To Destination services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Repository("assignContainerToDestinationDAO")
public class AssignContainerToDestinationDAOImpl extends BaseDAO implements AssignContainerToDestinationDAO {
   
   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSession sqlSessionShipment;

   @Override
   public AssignContainerToDestination searchAssignContainerDetails(SearchAssignToContainerToDestinationDetails request)
         throws CustomException {
      return super.fetchObject("getDestinationDetails", request, sqlSessionShipment);
   }

   @Override
   public List<AssignContainerToDestinationDetails> searchAssignContainerToDestinationDetails(
         SearchAssignToContainerToDestinationDetails request) throws CustomException {
      return super.fetchList("AssignContainerDetails", request, sqlSessionShipment);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.ASSIGN_CONTAINER)
   public AssignContainerToDestination updateUldMaster(AssignContainerToDestination request) throws CustomException {
      super.updateData("updateUldMaster", request, sqlSessionShipment);
      return request;
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.ASSIGN_CONTAINER)
   public AssignContainerToDestination updateHouseInfo(List<AssignContainerToDestinationDetails> request) throws CustomException {
      AssignContainerToDestination resp=null;
      super.updateData("updateShipmentHouseInfo", request, sqlSessionShipment);
      return resp;
   }

   @Override
   public List<AssignContainerToDestinationDetails> getAssignContainerToDestinationDetails(
         AssignContainerToDestination request) throws CustomException {
      return super.fetchList("getContainerDetails", request, sqlSessionShipment);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.ASSIGN_CONTAINER)
   public AssignContainerToDestination updateEaccHouseInfo(List<AssignContainerToDestinationDetails> details)
         throws CustomException {
      AssignContainerToDestination resp=null;
      super.updateData("updateEaccHouseInfo", details, sqlSessionShipment);
      return resp;
   }

   @Override
   public AssignContainerToDestination deleteAssignContainerToDestinationDetails(
         List<AssignContainerToDestinationDetails> request) throws CustomException {
      AssignContainerToDestination resp=null;
      super.deleteData("deleteAssignContainerToDestinationDetails", request, sqlSessionShipment);
      return resp;
   }

   @Override
   public void updateLocation(SearchAssignToContainerToDestinationDetails request) throws CustomException {
      super.updateData("upadteLocation", request, sqlSessionShipment);
   }

   @Override
   public List<AssignContainerToDestinationDetails> getshipmentInventoryID(
         SearchAssignToContainerToDestinationDetails request) throws CustomException {
      return super.fetchList("getShipmentInventoryId", request, sqlSessionShipment);
   }

   @Override
   public List<AssignContainerToDestinationDetails> getContainerDetails(
         SearchAssignToContainerToDestinationDetails request) throws CustomException {
      return super.fetchList("getACTDestinationDetails", request, sqlSessionShipment);
   }

   @Override
   public void updateEaccLocation(List<AssignContainerToDestinationDetails> data) throws CustomException {
     super.updateData("EhouseInfoLocation", data, sqlSessionShipment);
   }

   @Override
   public void updateHouseInfoDestination(List<AssignContainerToDestinationDetails> data) throws CustomException {
      super.updateData("updateHouseInfoDestination", data, sqlSessionShipment);
   }

   @Override
   public int getAssigneContainerCount(AssignContainerToDestination request) throws CustomException {
      return super.fetchObject("getCount", request, sqlSessionShipment);
   }

   @Override
   public int getAssigneContainersCount(SearchAssignToContainerToDestinationDetails request) throws CustomException {
      // TODO Auto-generated method stub
      return super.fetchObject("getCount", request, sqlSessionShipment);
   }

}
