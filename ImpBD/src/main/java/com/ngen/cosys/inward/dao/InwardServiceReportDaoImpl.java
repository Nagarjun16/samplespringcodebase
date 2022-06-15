/**
 * This is a implementation class for Inward Service Report
 */
package com.ngen.cosys.inward.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.constants.CRUDTypes;
import com.ngen.cosys.impbd.mail.breakdown.dao.InboundMailBreakDownDAO;

import com.ngen.cosys.inward.constants.ServiceReportQueryIds;
import com.ngen.cosys.inward.model.DamageModel;
import com.ngen.cosys.inward.model.InwardSegmentConcatenate;
import com.ngen.cosys.inward.model.InwardServiceReportModel;
import com.ngen.cosys.inward.model.InwardServiceReportOtherDiscrepancyModel;
import com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel;
import com.ngen.cosys.validator.enums.ShipmentType;

@Repository
public class InwardServiceReportDaoImpl extends BaseDAO implements InwardServiceReportDao {

   @Autowired
   private SqlSessionTemplate sqlSessionTemplate;

   @Autowired
   private InboundMailBreakDownDAO mailBreakdownDao;

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#getSegments(com.ngen.cosys.
    * inward.model.InwardServiceReportModel)
    */
   @Override
   public List<InwardServiceReportModel> getSegments(InwardServiceReportModel requestModel) throws CustomException {
      return this.fetchList(ServiceReportQueryIds.SQL_GET_SEGMENTS_FOR_INWARD_SERVICE_REPORT.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#getInwardServiceReportByAwb(
    * com.ngen.cosys.inward.model.InwardServiceReportModel)
    */
   @Override
   public InwardServiceReportModel getInwardServiceReportByAwb(InwardServiceReportModel requestModel)
         throws CustomException {
      return this.fetchObject(ServiceReportQueryIds.SQL_GET_SERVICE_REPORT_BY_AWB.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#getInwardServiceReportByMail
    * (com.ngen.cosys.inward.model.InwardServiceReportModel)
    */
   @Override
   public InwardServiceReportModel getInwardServiceReportByMail(InwardServiceReportModel requestModel)
         throws CustomException {
      return this.fetchObject(ServiceReportQueryIds.SQL_GET_SERVICE_REPORT_BY_MAIL.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.inward.dao.InwardServiceReportDao#
    * getInwardServiceReportByULDForOtherDiscrepancy(com.ngen.cosys.inward.model.
    * InwardServiceReportModel)
    */
   @Override
   public List<InwardServiceReportOtherDiscrepancyModel> getInwardServiceReportByULDForOtherDiscrepancy(
         InwardServiceReportModel requestModel) throws CustomException {
      return this.fetchList(
            ServiceReportQueryIds.SQL_GET_SERVICE_REPORT_OTHER_DISCREPANCY_FOR_ULD_RAMP_CHECK_IN.getQueryId(),
            requestModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.inward.dao.InwardServiceReportDao#
    * checkServiceReportFinalizedForCargo(com.ngen.cosys.inward.model.
    * InwardServiceReportModel)
    */
   @Override
   public Boolean checkServiceReportFinalizedForCargo(InwardServiceReportModel requestModel) throws CustomException {
      return super.fetchObject(ServiceReportQueryIds.SQL_GET_SERVIE_REPORT_FINALIZED_FOR_CARGO.getQueryId(),
            requestModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.inward.dao.InwardServiceReportDao#
    * checkServiceReportFinalizedForMail(com.ngen.cosys.inward.model.
    * InwardServiceReportModel)
    */
   @Override
   public Boolean checkServiceReportFinalizedForMail(InwardServiceReportModel requestModel) throws CustomException {
      return super.fetchObject(ServiceReportQueryIds.SQL_GET_SERVIE_REPORT_FINALIZED_FOR_MAIL.getQueryId(),
            requestModel, sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.inward.dao.InwardServiceReportDao#
    * finalizeServiceReportForCargo(com.ngen.cosys.inward.model.
    * InwardServiceReportModel)
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT)
   @Override
   public void finalizeServiceReportForCargo(InwardServiceReportModel requestModel) throws CustomException {
      super.updateData(ServiceReportQueryIds.SQL_FINALIZE_SERVIE_REPORT_FOR_CARGO.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#finalizeServiceReportForMail
    * (com.ngen.cosys.inward.model.InwardServiceReportModel)
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT)
   @Override
   public void finalizeServiceReportForMail(InwardServiceReportModel requestModel) throws CustomException {
      super.updateData(ServiceReportQueryIds.SQL_FINALIZE_SERVIE_REPORT_FOR_MAIL.getQueryId(), requestModel,
            sqlSessionTemplate);
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#createFlight(com.ngen.cosys.
    * inward.model.InwardServiceReportModel)
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT)
   @Override
   public void createFlight(InwardServiceReportModel requestModel) throws CustomException {
      // Get the primary key id
      BigInteger serviceReportId = super.fetchObject(
            ServiceReportQueryIds.SQL_GET_SERVICE_REPORT_BY_FLIGHT.getQueryId(), requestModel, sqlSessionTemplate);
      // If exists update the data/insert
      Optional<BigInteger> oServiceReportId = Optional.ofNullable(serviceReportId);
      if (oServiceReportId.isPresent()) {
         requestModel.setId(serviceReportId);
         requestModel.setInwardServiceReportId(serviceReportId);
         super.updateData(ServiceReportQueryIds.SQL_UPDATE_SERVICE_REPORT_BY_FLIGHT.getQueryId(), requestModel,
               sqlSessionTemplate);
      } else {
         super.insertData(ServiceReportQueryIds.SQL_INSERT_SERVICE_REPORT_BY_FLIGHT.getQueryId(), requestModel,
               sqlSessionTemplate);
         requestModel.setId(requestModel.getInwardServiceReportId());
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#createShipmentDiscrepancy(
    * com.ngen.cosys.inward.model.InwardServiceReportShipmentDiscrepancyModel)
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT)
   @Override
   public void createShipmentDiscrepancy(List<InwardServiceReportShipmentDiscrepancyModel> requestModel)
         throws CustomException {
      // Based on the data CRUD status perform either conditional operation
      for (InwardServiceReportShipmentDiscrepancyModel t : requestModel) {
         if (Objects.nonNull(t.getShipmentType())
               && t.getShipmentType().equalsIgnoreCase(ShipmentType.MAIL.toString())) {
            String mailType = t.getShipmentNumber().substring(13, 15);
            mailType = mailBreakdownDao.getMailShipmentType(mailType);
            t.setMailType(mailType);
            t.setIrregularityPieces(BigInteger.valueOf(Math.abs(t.getIrregularityPieces().longValue())));
         }
         //
         if (CRUDTypes.Type.CREATE.equalsIgnoreCase(t.getFlagCRUD())
               || CRUDTypes.Type.UPDATE.equalsIgnoreCase(t.getFlagCRUD())) {
            int updateRecordCount = super.updateData(
                  ServiceReportQueryIds.SQL_UPDATE_SERVICE_REPORT_SHIPMENT_DISCREPANCY.getQueryId(), t,
                  sqlSessionTemplate);
            if (updateRecordCount == 0) {
               super.insertData(ServiceReportQueryIds.SQL_INSERT_SERVICE_REPORT_SHIPMENT_DISCREPANCY.getQueryId(), t,
                     sqlSessionTemplate);
            }
         } else if (CRUDTypes.Type.DELETE.equalsIgnoreCase(t.getFlagCRUD())) {
            super.deleteData(ServiceReportQueryIds.SQL_DELETE_SERVICE_REPORT_SHIPMENT_DISCREPANCY.getQueryId(), t,
                  sqlSessionTemplate);
         }
      }
   }

   /*
    * (non-Javadoc)
    * 
    * @see
    * com.ngen.cosys.inward.dao.InwardServiceReportDao#creatOtherDiscrepancy(com.
    * ngen.cosys.inward.model.InwardServiceReportOtherDiscrepancyModel)
    */
   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT)
   @Override
   public void creatOtherDiscrepancy(List<InwardServiceReportOtherDiscrepancyModel> requestModel)
         throws CustomException {
      // Based on the data CRUD status perform either conditional operation
      for (InwardServiceReportOtherDiscrepancyModel t : requestModel) {
         //
         if (CRUDTypes.Type.CREATE.equalsIgnoreCase(t.getFlagCRUD())
               || CRUDTypes.Type.UPDATE.equalsIgnoreCase(t.getFlagCRUD())) {
            int updateRecordCount = super.updateData(
                  ServiceReportQueryIds.SQL_UPDATE_SERVICE_REPORT_OTHER_DISCREPANCY.getQueryId(), t,
                  sqlSessionTemplate);
            if (updateRecordCount == 0) {
               super.insertData(ServiceReportQueryIds.SQL_INSERT_SERVICE_REPORT_OTHER_DISCREPANCY.getQueryId(), t,
                     sqlSessionTemplate);
            }
         } else if (CRUDTypes.Type.DELETE.equalsIgnoreCase(t.getFlagCRUD())) {
            super.deleteData(ServiceReportQueryIds.SQL_DELETE_SERVICE_REPORT_OTHER_DISCREPANCY.getQueryId(), t,
                  sqlSessionTemplate);
         }
      }
   }

   @Override
   public List<InwardServiceReportModel> fetchDiscrepancy(InwardServiceReportModel fetchinwardService)
         throws CustomException {
      List<InwardServiceReportModel> data = fetchList("sqlInwardDiscrepancyData", fetchinwardService,
            sqlSessionTemplate);
      if (("AWB").equalsIgnoreCase(data.get(0).getServiceReportFor())) {
         InwardServiceReportModel data1 = fetchObject("sqlGetFinalizestatus", fetchinwardService.getFlightId(),
               sqlSessionTemplate);
         data.get(0).setCheckstatus(data1.getCheckstatus());

      }

      for (InwardServiceReportModel val : data) {
         val.setSegmentdesc(super.fetchObject("FLIGHTROUTING", val, sqlSessionTemplate));
         val.setStatus(fetchObject("finalizeinformation", val, sqlSessionTemplate));
         val.setMailstatus(fetchObject("mailfinalizeinformation", val, sqlSessionTemplate));
         val.setDamagestatus(fetchObject("damagestatusinfo", val, sqlSessionTemplate));

      }
      return data;
   }

   @Override
   public InwardServiceReportModel fetchId(InwardServiceReportModel fetchinwardService) throws CustomException {
      return super.fetchObject("sqlFetchId", fetchinwardService, sqlSessionTemplate);
   }

   @Override
   public Integer checkDocumentCompletedOrNot(InwardServiceReportModel requestModel) throws CustomException {
      return super.fetchObject("checkdocumentComplete", requestModel, sqlSessionTemplate);
   }

   @Override
   public Integer checkBreakdownCompletedOrNot(InwardServiceReportModel requestModel) throws CustomException {
      return super.fetchObject("checkbreakdownComplete", requestModel, sqlSessionTemplate);
   }

   @Override
   public Integer checkDuplicateForDoc(InwardServiceReportShipmentDiscrepancyModel requestModel)
         throws CustomException {
      return super.fetchObject("checkduplicateDoc", requestModel, sqlSessionTemplate);
   }

   @Override
   public List<InwardServiceReportModel> fetchboardpoints(InwardServiceReportModel fetchboardpoint)
         throws CustomException {
      return null;
   }

   @Override
   public Integer checkshipirrduplicate(InwardServiceReportShipmentDiscrepancyModel ship) throws CustomException {
      return super.fetchObject("checkshipirr", ship, sqlSessionTemplate);
   }

   @Override
   public String fetchFlightInfo(InwardServiceReportModel requestModel) throws CustomException {
      return fetchObject("finalizeinformation", requestModel, sqlSessionTemplate);
   }

   @Override
   public Integer checkDocumentCompletedOrNotForMail(InwardServiceReportModel requestModel) throws CustomException {
      return fetchObject("documentCompleteForMail", requestModel, sqlSessionTemplate);
   }

   @Override
   public Integer checkBreakdownCompletedOrNotForMail(InwardServiceReportModel requestModel) throws CustomException {
      return fetchObject("checckBreakDownComplete", requestModel, sqlSessionTemplate);
   }

   @Override
   public List<String> fetchEmailAdrressesConfigured(String carrierCode) throws CustomException {
      return fetchList("fetchEmailAddress", carrierCode, sqlSessionTemplate);
   }

   @Override
   public String getCarrierCode(BigInteger flightId) throws CustomException {
      return fetchObject("fetchCarrierCode", flightId, sqlSessionTemplate);
   }
   @Override
   public Integer checkDamageStatus(InwardServiceReportModel requestModel) throws CustomException {
      return fetchObject("fetchDamageCount", requestModel, sqlSessionTemplate);
   }

   @Override
   public List<String> fetchEmailAdrressesDamageConfigured(String carrierCode) throws CustomException {
      return fetchList("fetchEmailAddressDamageCargo", carrierCode, sqlSessionTemplate);
   }

   @NgenAuditAction(entityType = NgenAuditEntityType.FLIGHT, eventName = NgenAuditEventType.INWARD_SERVICE_REPORT)
   @Override
   public void unFinalizeServiceReport(InwardServiceReportModel requestModel) throws CustomException {
      updateData("unFinalizeInwardServiceReport", requestModel, sqlSessionTemplate);
   }

   @Override
   public BigInteger getInwardIdforAwb(InwardSegmentConcatenate segAwb) throws CustomException {
      return fetchObject("fetchReportId", segAwb, sqlSessionTemplate);
   }

   @Override
   public InwardServiceReportModel getRegistrationNumber(InwardServiceReportModel requestModel) throws CustomException {
      return fetchObject("fetchRegistraion", requestModel, sqlSessionTemplate);
   }

   @Override
   public InwardServiceReportModel getDamageInfo(InwardServiceReportModel requestModel) throws CustomException {
      return fetchObject("fetchdamageData", requestModel, sqlSessionTemplate);
   }

   @Override
   public List<DamageModel> getDamageReportData(InwardServiceReportModel data) throws CustomException {
      return fetchList("fetchreportdamageData", data, sqlSessionTemplate);
   }

   @Override
   public InwardServiceReportShipmentDiscrepancyModel getAwbDetails(
         InwardServiceReportShipmentDiscrepancyModel requestModel) throws CustomException {
	   InwardServiceReportShipmentDiscrepancyModel response=new InwardServiceReportShipmentDiscrepancyModel();
	   if(requestModel.getHawbnumber()== null || requestModel.getHawbnumber() == "") {
	   response=this.fetchObject("getShipMasterDetails", requestModel, sqlSessionTemplate);
	   }
	   else {
	   response=this.fetchObject("getShipMasterHouseDetails", requestModel, sqlSessionTemplate);
	   }
	   
      return response;
   }

}