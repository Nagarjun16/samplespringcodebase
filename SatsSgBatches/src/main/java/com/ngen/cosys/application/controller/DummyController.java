//package com.ngen.cosys.application.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.util.CollectionUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
//import com.ngen.cosys.application.dao.ConsolidatedShipmentInfoDAO;
//import com.ngen.cosys.application.dao.ManifestReconcillationStatementDAO;
//import com.ngen.cosys.application.service.ConsolidatedShipmentInfoService;
//import com.ngen.cosys.application.service.ProduceManifestReconcillationStatementMessageService;
//import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
//import com.ngen.cosys.framework.exception.CustomException;
//import com.ngen.cosys.model.FlightModel;
//import com.ngen.cosys.model.ShipmentDataModel;
//import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;
//
//import io.swagger.annotations.ApiOperation;
//
//@NgenCosysAppInfraAnnotation
//public class DummyController {
//
//   @Autowired
//   private ConsolidatedShipmentInfoService consolidatedShipmentInfoService;
//
//   @Autowired
//   private ConsolidatedShipmentInfoDAO consolidatedShipmentInfoDAO;
//
//   @Autowired
//   private ProduceManifestReconcillationStatementMessageService mrsService;
//
//   @Autowired
//   private ManifestReconcillationStatementDAO manifestReconcillationStatementDAO;
//
//   @Autowired
//   ConnectorPublisher publisher;
//
//   @Autowired
//   ProduceManifestReconcillationStatementMessageService produceManifestReconcillationStatementMessageService;
//
//   @Autowired
//   ConnectorLoggerService logger;
//
//   /**
//    * Get ShipmentInfo from cosys
//    * 
//    * @param addMRSModel
//    * @return
//    * @throws CustomException
//    */
//   @ApiOperation("Get ShipmentInfo from cosys")
//   @RequestMapping(value = "/api/satssgcustoms/customsmrs/getCustomMRSShipmentInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//   public void getCustomMRSShipmentInfo() throws CustomException {
//      /*
//       * try { String mrsMessage = null; List<ManifesrReconciliationStatementModel>
//       * flightData = new ArrayList<>(); ManifesrReconciliationStatementModel
//       * shipmentData = new ManifesrReconciliationStatementModel(); flightData =
//       * manifestReconcillationStatementDAO.getFlightsToSendMrs(); if
//       * (!CollectionUtils.isEmpty(flightData)) { for
//       * (ManifesrReconciliationStatementModel flight : flightData) { shipmentData =
//       * manifestReconcillationStatementDAO.getConsolidatedMrsInfo(flight); if (null
//       * != shipmentData) { mrsMessage =
//       * mrsService.buildManifestReconcillationStatementMesaage(shipmentData); } //
//       * Send batch job data to Connector framework String qname = "CCN"; // Object
//       * payload = this.constructJSONObject(mrsMessage.toString()); try { Object
//       * resultPayload = publisher.sendJobDataToConnector(mrsMessage, qname,
//       * MediaType.APPLICATION_JSON, null); // if (Objects.nonNull(resultPayload) &&
//       * resultPayload instanceof Map) { // Map<String, Object> response =
//       * (Map<String, Object>) resultPayload;
//       * 
//       * String systemName = (String) response.get("systemName"); BigInteger
//       * referenceId = BigInteger.valueOf(((Integer)
//       * response.get("referenceId")).longValue());
//       * 
//       * // Update Status this.updateMessageStatus(referenceId , systemName);
//       * System.out.println("systemName " + systemName);
//       * System.out.println("ReferenceId " + referenceId);
//       * 
//       * }
//       * 
//       * if (null != shipmentData && (!shipmentData.getHwbModel().isEmpty())) {
//       * manifestReconcillationStatementDAO.updateMrsSentDateToLinkMrs(shipmentData);
//       * 
//       * } manifestReconcillationStatementDAO.updateMrsSentDateToCustomsFlight(
//       * shipmentData);
//       * manifestReconcillationStatementDAO.insertMrsOutInfo(shipmentData); } catch
//       * (Exception e) {
//       * 
//       * }
//       * 
//       * } } } catch (CustomException e) {
//       * 
//       * } catch (Exception e) {
//       * 
//       * } } try {
//       * 
//       * consolidatedShipmentInfoService.getFlightInfoFromCustoms(); List<FlightModel>
//       * flightData = consolidatedShipmentInfoService.getFlightInfo(); for
//       * (FlightModel flightdetails : flightData) { List<ShipmentDataModel>
//       * shipmentData =
//       * consolidatedShipmentInfoDAO.getShipmentInfoForCustomsSubmittedShipment(
//       * flightdetails); if (!shipmentData.isEmpty()) {
//       * 
//       * consolidatedShipmentInfoService.validateShipmentInfo(shipmentData); }
//       * }}catch(Exception e) {
//       * 
//       * } } private Object constructJSONObject(String flightData) { //Convert object
//       * to JSON string
//       * 
//       * return JacksonUtility.convertObjectToJSONString(flightData); }
//       * 
//       * public void updateMessageStatus(BigInteger referenceId , String systemName) {
//       * OutgoingMessageLog outgoingMessage = new OutgoingMessageLog(); //
//       * outgoingMessage.setOutMessageId(referenceId);
//       * outgoingMessage.setChannelSent("MQ");
//       * outgoingMessage.setInterfaceSystem("CCN");
//       * outgoingMessage.setSenderOriginAddress("SATS");
//       * outgoingMessage.setMessageType("MRS");
//       * outgoingMessage.setSubMessageType("TYB");
//       * outgoingMessage.setCarrierCode(null); outgoingMessage.setFlightNumber(null);
//       * outgoingMessage.setFlightOriginDate(null);
//       * outgoingMessage.setShipmentNumber(null);
//       * outgoingMessage.setShipmentDate(null);
//       * outgoingMessage.setRequestedOn(LocalDateTime.now());
//       * outgoingMessage.setSentOn(LocalDateTime.now());
//       * outgoingMessage.setAcknowledgementReceivedOn(null);
//       * outgoingMessage.setVersionNo(1); outgoingMessage.setSequenceNo(1);
//       * outgoingMessage.setMessageContentEndIndicator(null);
//       * outgoingMessage.setStatus("SENT");
//       * logger.logOutgoingMessage(outgoingMessage); }
//       */
//
//      /*
//       * (non-Javadoc)
//       * 
//       * @see com.ngen.cosys.scheduler.job.AbstractCronJob#executeInternal(org.quartz.
//       * JobExecutionContext)
//       */
//
//      /*
//       * try {
//       * 
//       * (non-Javadoc) GetFlight Data from Cosys
//       * 
//       * List<FlightModel> flightData =
//       * consolidatedShipmentInfoService.getFlightInfo(); for (FlightModel
//       * flightdetails : flightData) { if (null == flightdetails.getFlightType()) {
//       * flightdetails.setFlightType("C"); }
//       * 
//       * (non-Javadoc) Add flight to customs
//       * 
//       * consolidatedShipmentInfoDAO.addCustomsFlight(flightdetails);
//       * 
//       * 
//       * (non-Javadoc) Get shipment data from Cosys for specific flight
//       * 
//       * List<ShipmentDataModel> shipmentData =
//       * consolidatedShipmentInfoDAO.getCustomShipmentInfo(flightdetails); if
//       * (!shipmentData.isEmpty()) {
//       * 
//       * consolidatedShipmentInfoService.validateShipmentInfo(shipmentData); }
//       * }}catch(Exception e) {
//       * 
//       * }
//       * 
//       */
//      /*
//       * try {
//       * 
//       * consolidatedShipmentInfoService.getFlightInfoFromCustoms(); List<FlightModel>
//       * flightData = consolidatedShipmentInfoService.getFlightInfoFromCustoms(); for
//       * (FlightModel flightdetails : flightData) { List<ShipmentDataModel>
//       * shipmentData =
//       * consolidatedShipmentInfoDAO.getShipmentInfoForCustomsSubmittedShipment(
//       * flightdetails); if (!shipmentData.isEmpty()) {
//       * 
//       * consolidatedShipmentInfoService.validateShipmentInfo(shipmentData); }
//       * }}catch(Exception e) {
//       * 
//       * }
//       */
//
//      /*
//       * try {
//       * 
//       * (non-Javadoc) GetFlight Data from Cosys
//       * 
//       * List<FlightModel> flightData =
//       * consolidatedShipmentInfoService.getFlightInfofromOperativeFlight(); for
//       * (FlightModel flightdetails : flightData) { if (null ==
//       * flightdetails.getFlightType()) { flightdetails.setFlightType("C"); }
//       * 
//       * (non-Javadoc) Add flight to customs
//       * 
//       * consolidatedShipmentInfoDAO.addCustomsFlight(flightdetails);
//       * 
//       * } } catch (Exception e) {
//       * 
//       * }
//       */
//
////      try {
////         List<FlightModel> flightData = consolidatedShipmentInfoService.getFlightInfo();
////         for (FlightModel flightdetails : flightData) {
////            if (null == flightdetails.getFlightType()) {
////               flightdetails.setFlightType("C");
////            }
////            List<ShipmentDataModel> customsShipmentsInfo = consolidatedShipmentInfoDAO
////                  .getCustomShipmentInfo(flightdetails);
////            if (CollectionUtils.isEmpty(customsShipmentsInfo)) {
////               for (ShipmentDataModel shipment : customsShipmentsInfo) {
////                  shipment.setCustomsFlightId(flightdetails.getCustomsFlightId());
////                  consolidatedShipmentInfoService.processShipmentInfo(shipment);
////               }
////            }
////         }
////      } catch (Exception e) {
////
////      }
////
////   }
//
//
