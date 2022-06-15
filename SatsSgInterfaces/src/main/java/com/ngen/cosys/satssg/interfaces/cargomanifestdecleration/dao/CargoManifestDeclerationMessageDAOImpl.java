package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.AttachAndDetatchCargoManifestDeclerationModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CmdLocalAuthorityInfoModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CustomCargoManifestDeclarationMessageModel;

@Repository("cargoManifestDeclerationMessageDAO")
public class CargoManifestDeclerationMessageDAOImpl extends BaseDAO implements CargoManifestDeclerationMessageDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   private SqlSessionTemplate sqlSession;

   @Override
   public BigInteger getAwbNumberCount(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetAwbNumberCount", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public BigInteger getHwbNumberCount(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetHwbNumberCount", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public BigInteger getTotalHwbNumberForAwb(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetTotalHwbNumberForAwb", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public BigInteger getCmdAWBExist(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlCmdAWBExist", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public BigInteger getCmdHAWBExist(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlCmdHAWBExist", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public void deleteCmdInfo(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException {
      deleteData("deleteCmdLarInfo", customCargoManifestDeclarationMessageModel, sqlSession);
      deleteData("deleteCmdInfo", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public BigInteger getEarliestFlightToAttach(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetEarliestFlightToAttachInInterface", customCargoManifestDeclarationMessageModel,
            sqlSession);
   }

   @Override
   public List<AttachAndDetatchCargoManifestDeclerationModel> getCmdToAttachToEarliestFlight(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      return super.fetchList("sqlGetCmdToAttachToEarliestFlight", attachAndDetatchCargoManifestDeclerationModel,
            sqlSession);
   }

   @Override
   public void addToHoldShipmentsInfo(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      int count = updateData("updateToHoldShipmentsInfo", attachAndDetatchCargoManifestDeclerationModel, sqlSession);
      if (count == 0) {
         insertData("addToHoldShipmentsInfo", attachAndDetatchCargoManifestDeclerationModel, sqlSession);
      }
   }

   @Override
   public void linkCmdToMrs(AttachAndDetatchCargoManifestDeclerationModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      Integer count = updateData("updateCmdToMrs", attachAndDetatchCargoManifestDeclerationModel, sqlSession);
      if (count == 0) {
         insertData("linkCmdToMrs", attachAndDetatchCargoManifestDeclerationModel, sqlSession);
      }
   }

   @Override
   public void insertCmdlarInfo(CmdLocalAuthorityInfoModel cmdLocalAuthorityInfoModel) throws CustomException {
      int count = updateData("updateCmdlarInfo", cmdLocalAuthorityInfoModel, sqlSession);
      if (count == 0) {
         insertData("insertCmdlarInfo", cmdLocalAuthorityInfoModel, sqlSession);
      }
   }

   @Override
   public void insertCmdInfo(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException {
      insertData("InsertCmdInfo", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   @NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ACES_CUSTOMS)
   public void insertCmdInfoIntoCustShipInfo(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      updateData("InsertCmdInfoIntoCustomsShipmentInfo", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public void deleteFromOtherFlights(
         AttachAndDetatchCargoManifestDeclerationModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      deleteData("deleteFromOtherFlights", attachAndDetatchCargoManifestDeclerationModel, sqlSession);
   }

   @Override
   public List<CustomCargoManifestDeclarationMessageModel> getCustomFlightIdAndPiecesFromCustShipInfo(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      return super.fetchList("sqlGetCustomFlightIdAndPiecesFromCustShipInfo",
            attachAndDetatchCargoManifestDeclerationModel, sqlSession);
   }

   @Override
   public List<CustomCargoManifestDeclarationMessageModel> getShipmentsToAttachCmds(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      return super.fetchList("sqlGetShipmentsToAttachCmd", attachAndDetatchCargoManifestDeclerationModel, sqlSession);
   }

   @Override
   public List<CustomCargoManifestDeclarationMessageModel> getCustomFlightIdAndPiecesFromLinkMrs(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException {
      return super.fetchList("sqlGetCustomFlightIdAndPiecesFromLinkMrs", attachAndDetatchCargoManifestDeclerationModel,
            sqlSession);
   }

   @Override
   public void updateMrsStatusCode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      updateData("updateMrSStatusCode", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public void updatePartshipmentInfoInCustoms(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      updateData("updatePartshipmentInfoInCustoms", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public CustomCargoManifestDeclarationMessageModel getLatestFlightToValidateLateIndicator(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetLatestFlightToValidateLateIndicator", customCargoManifestDeclarationMessageModel,
            sqlSession);
   }

   @Override
   public String getMrsDestinationAddress() throws CustomException {
      return this.fetchObject("getMrsDestinationAddress", null, sqlSession);
   }

   @Override
   public String getMrsPimaAddress() throws CustomException {
      return this.fetchObject("getMrsPimaAddress", null, sqlSession);
   }

   @Override
   public String getMrsOriginatorAddress() throws CustomException {
      return this.fetchObject("getMrsOriginatorAddress", null, sqlSession);
   }

   public CustomCargoManifestDeclarationMessageModel getShipmentFromCustShipInfo(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetShipmentFromCustShipInfo", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   public BigInteger getShipmentCountfromCustoms(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetShipmentCountfromCustoms", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public void updateCmdDeleteInfoIntoCustomsShipmentInfo(CustomCargoManifestDeclarationMessageModel shipment)
         throws CustomException {
      updateData("InsertCmdDeleteInfoIntoCustomsShipmentInfo", shipment, sqlSession);
   }

   public BigInteger getShipmentCountFromCosys(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("getShipmentCountFromCosys", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   public BigInteger getShipmentCountFromAces(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("getShipmentCountFromAces", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   public BigInteger isValidexemptioncode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("isValidexemptioncode", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public BigInteger getMrsFirstWindowForExport() throws CustomException {
      return this.fetchObject("getMrsFirstWindowForExport", null, sqlSession);
   }

   @Override
   public BigInteger getMrsSecondWindowForExport() throws CustomException {
      return this.fetchObject("getMrsSecondWindowForExport", null, sqlSession);
   }

   @Override
   public BigInteger getMrsFirstWindowForImport() throws CustomException {
      return this.fetchObject("getMrsFirstWindowForImport", null, sqlSession);
   }

   @Override
   public BigInteger getMrsSecondWindowForImport() throws CustomException {
      return this.fetchObject("getMrsSecondWindowForImport", null, sqlSession);
   }

   @Override
   public BigInteger getMrsSentDate(
         CustomCargoManifestDeclarationMessageModel customIncomingCargoManifestDeclarationContent)
         throws CustomException {
      return this.fetchObject("getMrsSentDate", customIncomingCargoManifestDeclarationContent, sqlSession);
   }

   @Override
   public BigInteger getMrsSentDetail(
         CustomCargoManifestDeclarationMessageModel customIncomingCargoManifestDeclarationContent)
         throws CustomException {
      return this.fetchObject("getMrsSentDetail", customIncomingCargoManifestDeclarationContent, sqlSession);
   }

   public void deleteAttachedCmd(CustomCargoManifestDeclarationMessageModel shipment) throws CustomException {
      deleteData("deleteFromOtherLinkMrs", shipment, sqlSession);
   }

   @Override
   public void updatePartshipmentInfoForOtherParts(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      updateData("updatePartshipmentInfoForOtherParts", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   public CustomCargoManifestDeclarationMessageModel getFlightDetails(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("getFlightDetailsDetail", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public LocalDateTime getFlightDateTime(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      return this.fetchObject("sqlGetCustomsFlightDate", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   public void validateCmdtStatus(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException {
      this.updateData("sqlGetPossiblestatusFromInterface", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public void detachCMDfromRemainingParts(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      this.updateData("updatePaShipmentsCmdInfoFromInterface", customCargoManifestDeclarationMessageModel, sqlSession);
   }

   @Override
   public void attachCmdToFirstPart(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException {
      this.updateData("AttachCmdToShipmentFromInterface", customCargoManifestDeclarationMessageModel, sqlSession);
      this.updateData("updateCmdLinkTableFromInterface", customCargoManifestDeclarationMessageModel, sqlSession);
   }

}