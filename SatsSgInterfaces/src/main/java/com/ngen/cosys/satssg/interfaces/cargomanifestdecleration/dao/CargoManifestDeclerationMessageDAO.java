package com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.dao;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.AttachAndDetatchCargoManifestDeclerationModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CmdLocalAuthorityInfoModel;
import com.ngen.cosys.satssg.interfaces.cargomanifestdecleration.model.CustomCargoManifestDeclarationMessageModel;

public interface CargoManifestDeclerationMessageDAO {

   BigInteger getAwbNumberCount(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   BigInteger getHwbNumberCount(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   BigInteger getTotalHwbNumberForAwb(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   BigInteger getCmdHAWBExist(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   BigInteger getCmdAWBExist(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   void deleteCmdInfo(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   BigInteger getEarliestFlightToAttach(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   List<AttachAndDetatchCargoManifestDeclerationModel> getCmdToAttachToEarliestFlight(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   void linkCmdToMrs(AttachAndDetatchCargoManifestDeclerationModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   void deleteFromOtherFlights(
         AttachAndDetatchCargoManifestDeclerationModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   List<CustomCargoManifestDeclarationMessageModel> getCustomFlightIdAndPiecesFromCustShipInfo(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   List<CustomCargoManifestDeclarationMessageModel> getCustomFlightIdAndPiecesFromLinkMrs(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   void updateMrsStatusCode(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   void insertCmdInfo(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   void addToHoldShipmentsInfo(CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   void updatePartshipmentInfoInCustoms(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   void insertCmdlarInfo(CmdLocalAuthorityInfoModel cmdLocalAuthorityInfoModel) throws CustomException;

   CustomCargoManifestDeclarationMessageModel getLatestFlightToValidateLateIndicator(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   String getMrsDestinationAddress() throws CustomException;

   String getMrsPimaAddress() throws CustomException;

   String getMrsOriginatorAddress() throws CustomException;

   void insertCmdInfoIntoCustShipInfo(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   CustomCargoManifestDeclarationMessageModel getShipmentFromCustShipInfo(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   void updateCmdDeleteInfoIntoCustomsShipmentInfo(CustomCargoManifestDeclarationMessageModel shipment)
         throws CustomException;

   BigInteger getShipmentCountFromCosys(CustomCargoManifestDeclarationMessageModel shipment) throws CustomException;

   BigInteger getShipmentCountFromAces(CustomCargoManifestDeclarationMessageModel shipment) throws CustomException;

   BigInteger isValidexemptioncode(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   BigInteger getMrsFirstWindowForExport() throws CustomException;

   BigInteger getMrsSecondWindowForExport() throws CustomException;

   BigInteger getMrsFirstWindowForImport() throws CustomException;

   BigInteger getMrsSecondWindowForImport() throws CustomException;

   BigInteger getMrsSentDate(CustomCargoManifestDeclarationMessageModel customIncomingCargoManifestDeclarationContent)
         throws CustomException;

   BigInteger getMrsSentDetail(CustomCargoManifestDeclarationMessageModel customIncomingCargoManifestDeclarationContent)
         throws CustomException;

   List<CustomCargoManifestDeclarationMessageModel> getShipmentsToAttachCmds(
         CustomCargoManifestDeclarationMessageModel attachAndDetatchCargoManifestDeclerationModel)
         throws CustomException;

   void updatePartshipmentInfoForOtherParts(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   LocalDateTime getFlightDateTime(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   void validateCmdtStatus(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

   void detachCMDfromRemainingParts(
         CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel) throws CustomException;

   void attachCmdToFirstPart(CustomCargoManifestDeclarationMessageModel customCargoManifestDeclarationMessageModel)
         throws CustomException;

}