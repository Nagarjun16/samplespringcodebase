package com.ngen.cosys.mailbag.overview.information.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mailbag.overview.information.model.AllStatusOfMailBag;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewDetail;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewSummary;
import com.ngen.cosys.mailbag.overview.information.model.MailbagSearchReq;

/**
 * This is a repository implementation class for Mailbag Info which allows
 * functionalities for fetching entire Mailbag information
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Repository("mailbagOverviewInformationDAO")
public class MailbagOverviewInformationDAOImpl extends BaseDAO implements MailbagOverviewInformationDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSessionTemplate;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.mailbag.overview.information.dao.MailbagInformationDAO#
    * getMailbagOverviewDetails(com.ngen.cosys.mailbag.overview.information.model.
    * MailbagSearchReq)
    */
   @Override
   public List<MailbagOverviewSummary> getMailbagOverviewDetails(MailbagSearchReq search) throws CustomException {
      return this.fetchList("sqlGetMailbagInfo", search, sqlSessionTemplate);
   }

   @Override
   public List<MailbagOverviewSummary> getMailbagOverviewDetailsMobile(MailbagSearchReq search) throws CustomException {
      BigInteger shipmentId = this.fetchObject("sqlGetShipmentdateForMailBag", search, sqlSessionTemplate);
      search.setShipmentId(shipmentId);
      return this.fetchList("sqlGetMailbagInfoMobile", search, sqlSessionTemplate);
   }

   
   @Override
   @Transactional(rollbackFor = CustomException.class)
   public List<MailbagOverviewDetail> updateLocation(List<MailbagOverviewDetail> reqParam) throws CustomException {
      if (!CollectionUtils.isEmpty(reqParam)) {
         for (int i = 0; i <= reqParam.size() - 1; i++) {
            MailbagOverviewDetail updateInvData = reqParam.get(i);
            if (updateInvData.getOutHouseInformationId() != null) {
            	updateOutHouseLocation(reqParam.get(i));
            } else {
               updateInventoryInfo(updateInvData);
            }
         }
      }
      return reqParam;
   }
   
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW)
   public void updateOutHouseLocation(MailbagOverviewDetail updateInvData) throws CustomException{
	   super.updateData("updateOutHouseLocation", updateInvData, sqlSessionTemplate);
   }

   private void updateInventoryInfo(MailbagOverviewDetail updateInvData) throws CustomException {
      BigInteger newInventoryId = fetchObject("getNewInventoryId", updateInvData, sqlSessionTemplate);
      if (updateInvData.getInventoryId() != newInventoryId) {
         // deduct pieces from previous inv
         //updateData("deductExistingInvPiecesWeight", updateInvData, sqlSessionTemplate);
    	 updatedeductExistingInvPiecesWeight(updateInvData);
         deleteData("deleteExistingHouse", updateInvData, sqlSessionTemplate);
         BigInteger existingInventoryPiecesWeight = fetchObject("fetchExistingInventoryPiecesWeight", updateInvData,
               sqlSessionTemplate);
         if (existingInventoryPiecesWeight != null && existingInventoryPiecesWeight.intValue() < 1) { // As per Vikas
            deleteData("removeInventorySHC", updateInvData, sqlSessionTemplate);
            deleteData("removeInventory", updateInvData, sqlSessionTemplate);
         }
      }
      if (!Optional.ofNullable(newInventoryId).isPresent()) {
         insertData("createNewInventory", updateInvData, sqlSessionTemplate);
         insertData("createNewHouseInventory", updateInvData, sqlSessionTemplate);
      } else {
         updateInvData.setInventoryId(newInventoryId);
         //updateData("addNewInventoryPiecesWeight", updateInvData, sqlSessionTemplate);
         updateNewInventoryPiecesWeight(updateInvData);
         insertData("createNewHouseInventory", updateInvData, sqlSessionTemplate);
      }
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW)
   public void updatedeductExistingInvPiecesWeight(MailbagOverviewDetail updateInvData) throws CustomException{
	   super.updateData("deductExistingInvPiecesWeight", updateInvData, sqlSessionTemplate);
   }
   
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW)
   public void updateNewInventoryPiecesWeight(MailbagOverviewDetail updateInvData) throws CustomException{
	   super.updateData("addNewInventoryPiecesWeight", updateInvData, sqlSessionTemplate);
   }
   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW_XRAY)
   public int updateMailbagXray(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.updateData("updateMailbagXray", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW_XRAY)
   public int createMailbagXrayRecord(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.insertData("createMailbagXray", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public MailbagOverviewDetail checkMailBagOverViewMobile(MailbagOverviewDetail mailbagOverviewDetail)
         throws CustomException {
      return super.fetchObject("checkMailBagExist", mailbagOverviewDetail, sqlSessionTemplate);

   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW_MAILDAMAGE)
   public int updateMailDamage(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.insertData("updateMailDamage", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW_MAILDAMAGE)
   public int createMailDamage(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.updateData("createMailDamage", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public int updateLocationRecord(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      List<MailbagOverviewDetail> updateDetails = new ArrayList<>();
      updateDetails.add(mailbagOverviewDetail);
      updateLocation(updateDetails);
      return 0;
   }

   
   @Override
   public int createLocationInfo(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.insertData("createLocationRecord", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW_EMBARGOFAILURE)
   public int updateEmbargoFailure(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.updateData("updateEmbargoRecord", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW_EMBARGOFAILURE)
   public int createEmbargoFailure(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      // TODO Auto-generated method stub
      return super.insertData("createEmbargoRecord", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public String getCarrierCodeForAMailBag(BigInteger mailBagNumber) throws CustomException {
      return super.fetchObject("getCarrierCodeForAMailBag", mailBagNumber, sqlSessionTemplate);
   }

   @Override
   public int deleteMailDamage(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.deleteData("deleteMailBagDamage", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public int createMailDamageLineItem(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.insertData("createMailBagDamageLineItems", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public int updateMailDamageLineItem(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.updateData("updateMailBagDamageLineItems", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public int deleteMailDamageLineItem(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.deleteData("deleteMailBagDamageLineItems", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public int deleteEmbargoFailure(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return super.deleteData("deleteEmabargoRecord", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public MailbagOverviewSummary getMailBagOverDamageLineItemDetails(MailbagSearchReq searchReq)
         throws CustomException {
      return super.fetchObject("getMalBagDamageLineItemDetails", searchReq, sqlSessionTemplate);
   }

   @Override
   public MailbagOverviewSummary getEmbargoFailureDetails(MailbagOverviewSummary summary) throws CustomException {
      return super.fetchObject("getMailEmbargoDetails", summary, sqlSessionTemplate);
   }

   @Override
   public MailbagOverviewDetail checkEmbargoDetailMobile(MailbagOverviewDetail mailbagOverviewDetail)
         throws CustomException {
      return super.fetchObject("checkForEmabrgoMobile", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public String checkContentCode(String updateLocationModel) throws CustomException {

      return super.fetchObject("checkContentCode", updateLocationModel, sqlSessionTemplate);
   }

   @Override
   public int getLoadedSHC(String storeLocation) throws CustomException {
      return super.fetchObject("getLoadedSHC", storeLocation, sqlSessionTemplate);
   }

   @Override
   public MailbagOverviewDetail checkUldAssignmentForMailFlight(MailbagOverviewDetail mailbagOverviewDetail)
         throws CustomException {
      return fetchObject("checkUldAssignmentForMailFlight", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public String getContainerDestination(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      return fetchObject("getContainerDestinationForOverView", mailbagOverviewDetail, sqlSessionTemplate);
   }

   @Override
   public void updateNextDestination(List<MailbagOverviewDetail> updateLocationModel) throws CustomException {
      updateData("updateNextDestinationOfShipmentHouse", updateLocationModel, sqlSessionTemplate);
      updateData("updateNextDestinationOfAcceptanceHouse", updateLocationModel, sqlSessionTemplate);
      updateNextDestinationOfOutHouse(updateLocationModel);
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.MAILBAG_OVERVIEW)
   public void updateNextDestinationOfOutHouse(List<MailbagOverviewDetail> updateLocationModel) throws CustomException{
	   updateData("updateNextDestinationOfOutHouse", updateLocationModel, sqlSessionTemplate);
   }
   
   @Override
   public void deleteXrayIfNoStatus(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      deleteData("deleteXrayIfNoStatus", mailbagOverviewDetail, sqlSessionTemplate);
      
   }

   @Override
   public AllStatusOfMailBag getAllStatusOfTheMailBag(AllStatusOfMailBag status) throws CustomException {
      return fetchObject("getStatusDetailOfMailBag", status, sqlSessionTemplate);
   }

}
