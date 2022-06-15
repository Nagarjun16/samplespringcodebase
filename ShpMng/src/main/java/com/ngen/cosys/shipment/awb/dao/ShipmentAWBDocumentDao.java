/**
 * This is a repository interface for Shipment Document on AWB/CBV/UCB
 */
package com.ngen.cosys.shipment.awb.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.routing.RoutingRequestModel;
import com.ngen.cosys.shipment.awb.model.AWB;
import com.ngen.cosys.shipment.awb.model.AwbModelForFWB;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerAddressInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerContactInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterHandlingArea;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityDetails;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterLocalAuthorityInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterRoutingInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterShc;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterShcHandlingGroup;
import com.ngen.cosys.shipment.awb.model.ShipmentOtherChargeInfo;
import com.ngen.cosys.shipment.awb.model.ShipmentRemarksModel;

public interface ShipmentAWBDocumentDao {

   Boolean checkShipmentExists(AWB awbData) throws CustomException;

   Boolean isPartShipment(AWB awbData) throws CustomException;

   Boolean isSVCShipment(AWB awbData) throws CustomException;

   AWB getShipment(AWB awbDetails) throws CustomException;

   AWB getFwbInfo(AWB awbDetails) throws CustomException;

   AWB getShipmentInfoFromImportECC(AWB awbDetails) throws CustomException;

   AWB getShipmentInfoFromBooking(AWB awbDetails) throws CustomException;

   ShipmentRemarksModel get(ShipmentRemarksModel shipmentRemarksModel) throws CustomException;

   BigDecimal fetchExchangeRate(AWB awbDetail) throws CustomException;

   String getCarrierCodeBasedOnAwbPrefix(AWB awbDetails) throws CustomException;

   BigInteger getAppointedAgentId(ShipmentMasterCustomerInfo shipmentMasterCustomerInfo) throws CustomException;

   void createShipment(AWB awbData) throws CustomException;

   void deleteCustomsData(AWB awbData) throws CustomException;

   void updateShipmentType(AWB awbData) throws CustomException;

   void createShipmentMasterCustomerAddressInfo(ShipmentMasterCustomerAddressInfo shipmentMasterCustomerAddressInfo)
         throws CustomException;

   void createShipmentMasterCustomerContactInfo(List<ShipmentMasterCustomerContactInfo> contacts)
         throws CustomException;

   void createShipmentMasterCustomerInfo(ShipmentMasterCustomerInfo shipmentMasterCustomerInfo) throws CustomException;

   void createIvrsCustomerContactInfo(List<ShipmentMasterCustomerContactInfo> contacts) throws CustomException;

   void createShipmentMasterHandlingArea(ShipmentMasterHandlingArea shipmentMasterHandlingArea) throws CustomException;

   void createShipmentMasterRoutingInfo(List<ShipmentMasterRoutingInfo> routing) throws CustomException;

   void createShipmentMasterShc(List<ShipmentMasterShc> shcs) throws CustomException;

   void deleteShipmentMasterShc(AWB shipmentMaster) throws CustomException;

   void createShipmentMasterShcHandlingGroup(List<ShipmentMasterShcHandlingGroup> shcHandlingGroup)
         throws CustomException;

   void createShipmentOtherChargeInfo(ShipmentOtherChargeInfo shipmentOtherChargeInfo) throws CustomException;

   void create(ShipmentRemarksModel shipmentRemarksModel) throws CustomException;

   void createOverseaseConsignee(AWB shipmentMaster) throws CustomException;

   void createShipmentVerification(AWB shipmentMaster) throws CustomException;

   void createIrregulaity(AWB shipmentMaster) throws CustomException;

   void createTracing(AWB shipmentMaster) throws CustomException;

   void updateShipmentDocumentReceivedOn(AWB shipmentMaster) throws CustomException;

   List<BigInteger> getShipmentMasterLocalAuthorityInfo(ShipmentMasterLocalAuthorityInfo shipmentMasterLocalAuthorityInfo)
         throws CustomException;

   boolean deleteShipmentMasterLocalAuthorityDetails(
         ShipmentMasterLocalAuthorityInfo shipmentMasterLocalAuthorityDetails) throws CustomException;

   void insertShipmentMasterLocalAuthorityDetails(ShipmentMasterLocalAuthorityInfo shipmentMasterLocalAuthorityDetails)
         throws CustomException;

   /**
    * Method which allows deletion of Shipment on FWB deletion
    * 
    * @param shipmentMaster
    * @throws CustomException
    */
   void deleteAwbDocumentFromFWB(AWB shipmentMaster) throws CustomException;

   boolean isValidConsignee(ShipmentMasterCustomerInfo consignee) throws CustomException;

   boolean isAppointedAgentRequired(ShipmentMasterCustomerInfo consignee) throws CustomException;

   boolean isConsigneeAppointedAgentValid(ShipmentMasterCustomerInfo consignee) throws CustomException;

   boolean isValidShipper(ShipmentMasterCustomerInfo consignee) throws CustomException;

   AwbModelForFWB getShipment(AwbModelForFWB awbDetails) throws CustomException;

   void createShipment(AwbModelForFWB awbData) throws CustomException;

   AwbModelForFWB getFwb(AWB awbDetails) throws CustomException;

   AWB getAcceptanceInfo(AWB awbDetails) throws CustomException;

   ShipmentMasterCustomerInfo getEmailInfo(ShipmentMasterCustomerInfo awbDetails) throws CustomException;

   ShipmentMasterCustomerInfo getFWBConsigneeInfo(AWB awbDetails) throws CustomException;

   List<ShipmentMasterCustomerInfo> getFWBConsigneeAgentInfo(AWB awbDetails) throws CustomException;

   List<ShipmentMasterCustomerInfo> sqlGetFWBConsigneeAgentInfoOnSelect(ShipmentMasterCustomerInfo awbDetails)
         throws CustomException;

   ShipmentMasterCustomerAddressInfo getAllAppointedAgents(ShipmentMasterCustomerInfo request) throws CustomException;

   BigDecimal getccFee() throws CustomException;

   BigDecimal getMinccFee() throws CustomException;

   /**
    * Get the default customer id for direct consignee
    * 
    * @return BigInteger - IXX customer id
    * @throws CustomException
    */
   BigInteger getDirectConsigneeCustomerId() throws CustomException;

   /**
    * Get the default customer id for direct shipper
    * 
    * @return BigInteger - EXX customer id
    * @throws CustomException
    */
   BigInteger getDirectShipperCustomerId() throws CustomException;

   /**
    * Checks the Appointed Agent Based On Appointed Agent Code
    * 
    * @param awbDetails
    * @return
    * @throws CustomException
    */
   Boolean isValidAppointedAgentCode(ShipmentMasterCustomerInfo consigneeDetails) throws CustomException;

   /**
    * Method to get flight id in case import/transhipment
    * 
    * @param awb
    * @return
    * @throws CustomException
    */
   BigInteger getInboundFlightId(AWB awb) throws CustomException;

   Boolean isShipmentOtherChargeInfoExist(ShipmentOtherChargeInfo shipmentOtherChargeInfo) throws CustomException;

   Integer isShipmentMasterCustomerInfoExist(ShipmentMasterCustomerInfo shipmentMasterCustomerInfo)
         throws CustomException;

   void createShipmentFromFwb(AWB awbDetails) throws CustomException;

   /**
    * Method to fetch the ShipmentCustomer Info Id
    * 
    * @param awbDetails
    * @return
    * @throws CustomException
    */
   String getShipmentCustomerInfoIdForShipper(AWB awbDetails) throws CustomException;

   /**
    * Method to fetch the ShipmentCustomer Info Id
    * 
    * @param awbDetails
    * @return
    * @throws CustomException
    */

   String getShipmentCustomerInfoIdForConsignee(AWB awbDetails) throws CustomException;

   /**
    * Method to fetch Shipment Customer Add Info Id
    * 
    * @param shipmentCustomerInfoId
    * @return
    * @throws CustomException
    */
   String getShipmentCustomerAddInfoId(String shipmentCustomerInfoId) throws CustomException;

   /**
    * Method to delete shipper customer contact information based on shipment
    * Customer Add Info Id
    * 
    * @param shipmentCustomerAddInfoId
    * @return
    * @throws CustomException
    */
   int deleteShipmentMasterCustomerContactInfo(String shipmentCustomerAddInfoId) throws CustomException;

   /**
    * Method to delete shipper customer Address Information based on shipment
    * Customer Info Id
    * 
    * @param shipmentCustomerInfoId
    * @return
    * @throws CustomException
    */
   int deleteShipmentMasterCustomerAddressInfo(String shipmentCustomerInfoId) throws CustomException;

   /**
    * Method to delete Customer Information For Shipper
    * 
    * @param awbDetails
    * @return
    * @throws CustomException
    */
   int deleteShipmentMasterCustomerInfoForShipper(AWB awbDetails) throws CustomException;

   /**
    * Method to delete Customer Information For Consignee
    * 
    * @param awbDetails
    * @return
    * @throws CustomException
    */
   int deleteShipmentMasterCustomerInfoForConsignee(AWB awbDetails) throws CustomException;

   /**
    * Method to Check Valid Currency
    * 
    * @param requestModel
    * @return
    * @throws CustomException
    */
   Boolean checkValidCurrency(ShipmentOtherChargeInfo requestModel) throws CustomException;

   /**
    * Method to update origin/destination to other sources
    * 
    * @param awbDetails
    * @throws CustomException
    */
   void updateOriginDestinationToOtherSources(AWB awbDetails) throws CustomException;

   BigInteger isShipmentLoaded(AWB awb) throws CustomException;

   BigInteger isPoGeneratedForShipment(AWB awb) throws CustomException;

   String getShipmentType(AWB awb) throws CustomException;

   /**
    * 
    * @param model
    * @return
    * @throws CustomException
    */
   List<ShipmentMasterRoutingInfo> getRoutingInfo(RoutingRequestModel model) throws CustomException;

   /**
    * @param awb
    * @return
    * @throws CustomException
    */
   BigInteger getStatusUpdateEventPieces(AWB awb) throws CustomException;

   /**
    * 
    * @param awb
    * @return
    * @throws CustomException
    */
   AWB getBreakDownAndFoundPieces(AWB awb) throws CustomException;

   /**
    * 
    * @param awb
    * @throws CustomException
    *            delete imp_shipmentVerification for the flight if no breakdown
    *            st0rage info exist
    */
   void deleteImpShipmentVerification(AWB awb) throws CustomException;

   /**
    * Method to validate whether this is an valid exemption code
    * 
    * @param requestModel
    * @return boolean - true if it is an valid exemption code otherwise false
    * @throws CustomException
    */
   boolean isValidExemptionCode(ShipmentMasterLocalAuthorityDetails requestModel) throws CustomException;
   
   /**
    * 
    * @param awb
    * @return last modified datetime
    * @throws CustomException
    */
   AWB getLastModifiedInfo(AWB awb) throws CustomException;

   Boolean checkDocumentAcceptance(AWB requestModel) throws CustomException;
   
   AWB getDeliveredPieces(AWB data) throws CustomException;
   
   /**
    * Method to Delete All SHCs comes under COU SHCHandlingGroupCode
    * 
    * @param awbData
    * @return void
    * @throws CustomException
    */
   void deleteCOUShc(AWB awbData) throws CustomException;
   
   //Update Import Shipment details to Bial
   Integer isFlightCompleted(BigInteger flightId) throws CustomException;
   String  getMessageType(Map<String, BigInteger> map) throws CustomException;
   String getClearingAgentName(String agentCode) throws CustomException;
   Integer isInternationalShipment(BigInteger shipmentId) throws CustomException;
}