/**
 * 
 * NeutralAWBController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.billing.api.Charge;
import com.ngen.cosys.billing.api.enums.ChargeEvents;
import com.ngen.cosys.billing.api.enums.ReferenceType;
import com.ngen.cosys.billing.api.model.BillingShipment;
import com.ngen.cosys.billing.api.model.ChargeResponse;
import com.ngen.cosys.events.payload.SendManuallyCreatedFreightWayBillStoreEvent;
import com.ngen.cosys.events.producer.SendManuallyCreatedFreightWayBillStoreEventProducer;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.processing.engine.rule.fact.FactPayload;
import com.ngen.cosys.processing.engine.rule.fact.FactShipment;
import com.ngen.cosys.processing.engine.rule.triggerpoint.OutboundMessage;
import com.ngen.cosys.rule.engine.util.RuleEngineExecutor;
import com.ngen.cosys.service.util.enums.PrinterType;
import com.ngen.cosys.service.util.enums.ReportRequestType;
import com.ngen.cosys.service.util.model.ReportRequest;
import com.ngen.cosys.shipment.model.AgentInfo;
import com.ngen.cosys.shipment.nawb.enums.RateDescriptionOtherInfoForNawbType;
import com.ngen.cosys.shipment.nawb.model.CommodityChargesList;
import com.ngen.cosys.shipment.nawb.model.NAWBRequestList;
import com.ngen.cosys.shipment.nawb.model.NeutralAWBMaster;
import com.ngen.cosys.shipment.nawb.model.RateDescriptionOtherInfoForNawb;
import com.ngen.cosys.shipment.nawb.model.SIDHeaderDetail;
import com.ngen.cosys.shipment.nawb.model.SearchNAWBRQ;
import com.ngen.cosys.shipment.nawb.model.SearchSIDRQ;
import com.ngen.cosys.shipment.nawb.model.SearchStockRQ;
import com.ngen.cosys.shipment.nawb.model.Stock;
import com.ngen.cosys.shipment.nawb.service.NeutralAWBService;
import com.ngen.cosys.shipment.nawb.validator.NeutralAWBValidatorGroup;
import com.ngen.cosys.shipment.printer.util.PrinterService;
import com.ngen.cosys.shipment.util.ShipmentUtility;
import com.ngen.cosys.util.LoggerUtil;
import com.ngen.cosys.validator.enums.ShipmentType;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This controller takes care of the incoming requests related to Neutral AWB
 * maintenance.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NgenCosysAppInfraAnnotation(path = "/api/shipment/")
public class NeutralAWBController {
   private static final String EXCEPTION = "Exception Happened ... ";
   private static final Logger LOGGER = LoggerFactory.getLogger(NeutralAWBController.class);
   private static final String SID_FETCH_ERROR = "SID_FETCH_ERROR";
   private static final String NAWB_FETCH_ERROR = "NAWB_FETCH_ERROR";
   private static final String NAWB_SAVE_ERROR = "NAWB_SAVE_ERROR";

   @Autowired
   private NeutralAWBService neutralAWBService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   PrinterService printerService;

   @Autowired
   SendManuallyCreatedFreightWayBillStoreEventProducer producer;

   @Autowired
   RuleEngineExecutor ruleExecutor;

   @Autowired
   private ShipmentUtility shipmentUtility;

   @ApiOperation("Search List Of Shipper's Instruction For Dispatch")
   @RequestMapping(value = "nawb/search-sid", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<SIDHeaderDetail>> searchSIDList(
         @ApiParam(value = "sidNumber/shipmentNumber/status/carrierCode/fromDate/toDate/terminal", required = true) @Valid @RequestBody SearchSIDRQ searchSIDRQ)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<SIDHeaderDetail>> sidHeaderDetailResponse = utilitiesModelConfiguration
            .getBaseResponseInstance();
      List<SIDHeaderDetail> sidHeaderDetails = null;
      sidHeaderDetails = neutralAWBService.searchSIDList(searchSIDRQ);
      if (sidHeaderDetails == null) {
         LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchSIDList", Level.DEBUG, searchSIDRQ,
               SID_FETCH_ERROR));
         throw new CustomException(SID_FETCH_ERROR, null, ErrorType.ERROR);
      }
      sidHeaderDetailResponse.setData(sidHeaderDetails);
      sidHeaderDetailResponse.setSuccess(true);
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchSIDList", Level.DEBUG, searchSIDRQ,
            sidHeaderDetails));
      return sidHeaderDetailResponse;
   }

   @ApiOperation("Fetch Agent Info")
   @RequestMapping(value = "nawb/agentInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<AgentInfo> searchSIDList() throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<AgentInfo> agentInfoDetails = utilitiesModelConfiguration.getBaseResponseInstance();
      AgentInfo agentDetails = new AgentInfo();
      agentDetails = neutralAWBService.fetchAgentInfo(agentDetails);

      agentInfoDetails.setData(agentDetails);
      agentInfoDetails.setSuccess(true);
      return agentInfoDetails;
   }

   @ApiOperation("Search Details of SID by HeaderId ")
   @RequestMapping(value = "nawb/search-sid-by-id", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<NeutralAWBMaster> searchSIDDetails(
         @ApiParam(value = "sidHeaderId", required = true) @Valid @RequestBody SearchNAWBRQ searchSIDRQ)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<NeutralAWBMaster> sidHeaderDetailResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      NeutralAWBMaster nawbMasterDetails = null;
      // check for import shipment
      boolean ifImportShipment = neutralAWBService.checkIfImportShipment(searchSIDRQ);
      if (ifImportShipment) {
         throw new CustomException("import.awb.cycl.exst.pls.purg", null, ErrorType.ERROR);
      }
      //
      nawbMasterDetails = neutralAWBService.searchSIDDetails(searchSIDRQ);
      if (nawbMasterDetails == null && Optional.ofNullable(searchSIDRQ.getSidHeaderId()).isPresent()) {
         LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchSIDDetails", Level.DEBUG,
               searchSIDRQ, SID_FETCH_ERROR));
         throw new CustomException(SID_FETCH_ERROR, null, ErrorType.ERROR);
      }
      sidHeaderDetailResponse.setData(nawbMasterDetails);
      sidHeaderDetailResponse.setSuccess(true);
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchSIDDetails", Level.DEBUG, searchSIDRQ,
            nawbMasterDetails));
      return sidHeaderDetailResponse;
   }

   @ApiOperation("Search List Of Unused AWB From Stock")
   @RequestMapping(value = "nawb/search-awb-from-stock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<Stock>> searchAWBFromStockList(
         @ApiParam(value = "carrierCode/stockCategoryCode/stockId", required = true) @Valid @RequestBody SearchStockRQ searchStockRQ)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<Stock>> stockResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      List<Stock> stocks = null;
      stocks = neutralAWBService.searchAWBFromStockList(searchStockRQ);
      if (stocks == null) {
         LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchAWBFromStockList", Level.DEBUG,
               searchStockRQ, "AWB_FETCH_ERROR"));
         throw new CustomException("AWB_FETCH_ERROR", null, ErrorType.ERROR);
      }
      stockResponse.setData(stocks);
      stockResponse.setSuccess(true);
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchAWBFromStockList", Level.DEBUG,
            searchStockRQ, stocks));
      return stockResponse;
   }

   @ApiOperation("Search Details of Neutral AWB by AWBNumber ")
   @RequestMapping(value = "nawb/search-nawb-by-awbnumber", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<NeutralAWBMaster> searchNAWBDetails(
         @ApiParam(value = "AWBNumber", required = true) @Valid @RequestBody SearchNAWBRQ searchNAWBRQ)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<NeutralAWBMaster> nawbDetailResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      NeutralAWBMaster nawbMasterDetails = null;
      nawbMasterDetails = neutralAWBService.searchNAWBDetails(searchNAWBRQ);
      if (nawbMasterDetails == null) {
         LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchSIDDetails", Level.DEBUG,
               searchNAWBRQ, NAWB_FETCH_ERROR));
         throw new CustomException(NAWB_FETCH_ERROR, null, ErrorType.ERROR);
      }
      nawbDetailResponse.setData(nawbMasterDetails);
      nawbDetailResponse.setSuccess(true);
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "searchSIDDetails", Level.DEBUG, searchNAWBRQ,
            nawbMasterDetails));
      return nawbDetailResponse;
   }

   @ApiOperation("Insert Details of Neutral AWB")
   @RequestMapping(value = "nawb/create-nawb", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<NeutralAWBMaster> saveNAWB(
         @ApiParam(value = "AWBNumber", required = true) @Valid @RequestBody NeutralAWBMaster neutralAWBMaster)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<NeutralAWBMaster> nawbDetailResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      NeutralAWBMaster nawbMasterResponse = null;
      nawbMasterResponse = neutralAWBService.saveNAWB(neutralAWBMaster);
      if (nawbMasterResponse == null) {
         LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "saveNAWB", Level.DEBUG, neutralAWBMaster,
               NAWB_SAVE_ERROR));
         throw new CustomException(NAWB_SAVE_ERROR, null, ErrorType.ERROR);
      }
      nawbDetailResponse.setData(nawbMasterResponse);
      nawbDetailResponse.setSuccess(true);
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "saveNAWB", Level.DEBUG, neutralAWBMaster,
            nawbMasterResponse));
      return nawbDetailResponse;
   }

   @ApiOperation("Insert Details of Neutral AWB")
   @RequestMapping(value = "nawb/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<NeutralAWBMaster> saveneutralAWB(
         @ApiParam(value = "AWBNumber", required = true) @Validated(value = NeutralAWBValidatorGroup.class) @RequestBody NeutralAWBMaster neutralAWBMaster)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<NeutralAWBMaster> nawbDetailResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         NeutralAWBMaster nawbMasterResponse = neutralAWBService.saveneutralAWB(neutralAWBMaster);
         sendFWBMessage(nawbMasterResponse);
         nawbDetailResponse.setData(nawbMasterResponse);
         nawbDetailResponse.setSuccess(true);
         LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "saveNAWB", Level.DEBUG, neutralAWBMaster,
               nawbMasterResponse));
      } catch (CustomException e) {
         nawbDetailResponse.setData(neutralAWBMaster);
         LOGGER.error(EXCEPTION, e);
      }
      if (nawbDetailResponse.isSuccess()) {
         shipmentUtility.shipmentBillingUpdateForNAWB(neutralAWBMaster);
      }
      return nawbDetailResponse;
   }

   @ApiOperation("Calculates Charges Due against Shipment")
   @RequestMapping(value = "nawb/checkShipmentCharges", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<ChargeResponse>> checkAWBCharges(@RequestBody NeutralAWBMaster nawb)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<ChargeResponse>> nawbDetailResponse = utilitiesModelConfiguration.getBaseResponseInstance();

      if(StringUtils.isEmpty(nawb.getShipmentNumber())) {
    	 nawb.setShipmentNumber(nawb.getAwbNumber());
      }
      
      Boolean isMailOcs = this.neutralAWBService.checkMalOcsForNawb(nawb);

      if (!ObjectUtils.isEmpty(isMailOcs) && isMailOcs) {
    	  BillingShipment ship = new BillingShipment();
          ship.setReferenceId(nawb.getNeutralAWBId());
          ship.setReferenceType(ReferenceType.NAWB_ID.getReferenceType());
          ship.setAdditionalReferenceId(BigInteger.valueOf(Long.valueOf(nawb.getAwbNumber())));
          ship.setAdditionalReferenceType(ReferenceType.SHIPMENT.getReferenceType());
          ship.setEventType(ChargeEvents.EXP_SHIPMENT_UPDATE);
          ship.setProcessType(ChargeEvents.PROCESS_EXP.getEnum());
          ship.setWeight(nawb.getWeight());
          ship.setReadOnly(true);
          ship.setAcceptanceType("NAWB_SHIPMENT");

          ship.setShipmentType(ShipmentType.AWB.getType());
          List<String> shcGroupList = new ArrayList<>();
          if (Objects.isNull(nawb.getSvc()) || !nawb.getSvc()) {
             ship.setSvc("0");
             ship.setShcGroups(shcGroupList);
          } else {
             ship.setSvc("1");
             shcGroupList.add("SVC");
             ship.setShcGroups(shcGroupList);
          }
          ship.setCustomerCode(nawb.getShipperInfo().getAppointedAgent());
          ship.setCustomerId(nawb.getShipperInfo().getCustomerId());
          ship.setCarrierCode(
                Objects.isNull(nawb.getFlightBooking()) || Objects.isNull(nawb.getFlightBooking().getCarrierCode()) ? null
                      : nawb.getFlightBooking().getCarrierCode());
          ship.setHandlingTerminal(nawb.getTerminal());
          ship.setPieces(BigDecimal.valueOf(nawb.getPieces().doubleValue()));
          ship.setManualPopulateFlag(true);
          ship.setQuantity(BigDecimal.ONE);
          ship.setRcarType(nawb.getShipperInfo().getRcarType());
          ship.setNawbFlag("0");
          List<ChargeResponse> chargeResp = Charge.calculateCharge(ship).getChargeResponse();
          
          nawbDetailResponse.setData(chargeResp);
      }
      
      return nawbDetailResponse;
   }

   @ApiOperation(value = "API method for printing of NAWB")
   @RequestMapping(path = "nawb/printNawb", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public void printNAWB(@RequestBody NAWBRequestList requestModel) throws CustomException {
      DecimalFormat df = new DecimalFormat("#.##");
      ReportRequest report = new ReportRequest();
      report.setRequestType(ReportRequestType.PRINT);
      report.setPrinterType(PrinterType.NAWB);
      report.setQueueName(requestModel.getPrinterName());

      Map<String, Object> parameters = new HashMap<>();
      parameters.put("awbPrefix",
            requestModel.getAwbNumber() == null ? "" : requestModel.getAwbNumber().subSequence(0, 3));
      parameters.put("awbSuffix", requestModel.getAwbNumber() == null ? "" : requestModel.getAwbNumber().substring(3));
      parameters.put("originCode", requestModel.getDepartCode() == null ? "" : requestModel.getDepartCode());
      parameters.put("shipperAccNo", requestModel.getShipperAccNo() == null ? "" : requestModel.getShipperAccNo());
      parameters.put("shipperName", requestModel.getShipperName() == null ? "" : requestModel.getShipperName());
      parameters.put("shipperAdd", requestModel.getShipperAdd() == null ? "" : requestModel.getShipperAdd());
      parameters.put("shipperCountry",
            requestModel.getShipperCountry() == null ? "" : requestModel.getShipperCountry());
      parameters.put("shipperPostalCode",
            requestModel.getShipperPostalCode() == null ? "" : requestModel.getShipperPostalCode());
      parameters.put("shipperContact",
            requestModel.getShipperContactDetail() == null ? "" : requestModel.getShipperContactDetail());
      String issuedByValue = neutralAWBService
            .getAirlineName((requestModel.getAwbNumber().subSequence(0, 3).toString()));
      parameters.put("issuedBy", issuedByValue == null ? "" : issuedByValue);
      parameters.put("consigneeAccNo",
            requestModel.getConsigneeAccNo() == null ? "" : requestModel.getConsigneeAccNo());
      parameters.put("consigneeName", requestModel.getConsigneeName() == null ? "" : requestModel.getConsigneeName());
      parameters.put("consigneeAdd", requestModel.getConsigneeAdd() == null ? "" : requestModel.getConsigneeAdd());
      parameters.put("consigneeCountry",
            requestModel.getConsigneeCountry() == null ? "" : requestModel.getConsigneeCountry());
      parameters.put("consigneePostalCode",
            requestModel.getConsigneePostalCode() == null ? "" : requestModel.getConsigneePostalCode());
      parameters.put("consigneeContact",
            requestModel.getConsigneeContactDetail() == null ? "" : requestModel.getConsigneeContactDetail());
      parameters.put("issueCarrierAgentName",
            requestModel.getIssuingAgentName() == null ? "" : requestModel.getIssuingAgentName());
      parameters.put("issueCarrierAgentCity",
            requestModel.getIssuingAgentPlace() == null ? "" : requestModel.getIssuingAgentPlace());
      parameters.put("agentIATACode", requestModel.getAgentCode() == null ? "" : requestModel.getAentIATACode());
      parameters.put("No", requestModel.getAccountNo() == null ? "" : requestModel.getAccountNo());
      parameters.put("departApt", requestModel.getDestApt() == null ? "" : requestModel.getDestApt());
      parameters.put("refNo", requestModel.getRefNo() == null ? "" : requestModel.getRefNo());

      if (null != requestModel.getRoutingToByList() && 0 < requestModel.getRoutingToByList().size()
            && null != requestModel.getRoutingToByList().get(0)
            && null != requestModel.getRoutingToByList().get(0).getTo()) {
         parameters.put("to", requestModel.getRoutingToByList().get(0).getTo());
      } else {
         parameters.put("to", "");
      }
      if (null != requestModel.getRoutingToByList() && 0 < requestModel.getRoutingToByList().size()
            && null != requestModel.getRoutingToByList().get(0)
            && null != requestModel.getRoutingToByList().get(0).getCarrierCode()) {
         parameters.put("byFirstCarrier", requestModel.getRoutingToByList().get(0).getCarrierCode());
      } else {
         parameters.put("byFirstCarrier", "");
      }

      if (null != requestModel.getRoutingToByList() && 1 < requestModel.getRoutingToByList().size()
            && null != requestModel.getRoutingToByList().get(1)
            && null != requestModel.getRoutingToByList().get(1).getTo()) {
         parameters.put("routeAndDestTo1", requestModel.getRoutingToByList().get(1).getTo());
      } else {
         parameters.put("routeAndDestTo1", "");
      }
      if (null != requestModel.getRoutingToByList() && 1 < requestModel.getRoutingToByList().size()
            && null != requestModel.getRoutingToByList().get(1)
            && null != requestModel.getRoutingToByList().get(1).getCarrierCode()) {
         parameters.put("routeAndDestBy1", requestModel.getRoutingToByList().get(1).getCarrierCode());
      } else {
         parameters.put("routeAndDestBy1", "");
      }

      if (null != requestModel.getRoutingToByList() && 2 < requestModel.getRoutingToByList().size()
            && null != requestModel.getRoutingToByList().get(2)
            && null != requestModel.getRoutingToByList().get(2).getTo()) {
         parameters.put("routeAndDestTo2", requestModel.getRoutingToByList().get(2).getTo());
      } else {
         parameters.put("routeAndDestTo2", "");
      }
      if (null != requestModel.getRoutingToByList() && 2 < requestModel.getRoutingToByList().size()
            && null != requestModel.getRoutingToByList().get(2)
            && null != requestModel.getRoutingToByList().get(2).getCarrierCode()) {
         parameters.put("routeAndDestBy2", requestModel.getRoutingToByList().get(2).getCarrierCode());
      } else {
         parameters.put("routeAndDestBy2", "");
      }

      parameters.put("currencyCode", requestModel.getCurrencies() == null ? "" : requestModel.getCurrencies());
      parameters.put("chgsCode", requestModel.getChgsCode() == null ? "" : requestModel.getChgsCode());

      if (null != requestModel.getChgsCode() && "PP".equals(requestModel.getChgsCode())) {
         parameters.put("wtValPPD", "X");
         parameters.put("othersValPPD", "X");
         parameters.put("wtValCOLL", "");
         parameters.put("othersValCOLL", "");
      } else if (null != requestModel.getChgsCode() && "CC".equals(requestModel.getChgsCode())) {
         parameters.put("wtValCOLL", "X");
         parameters.put("othersValCOLL", "X");
         parameters.put("wtValPPD", "");
         parameters.put("othersValPPD", "");
      } else {
         parameters.put("wtValPPD", "");
         parameters.put("othersValPPD", "");
         parameters.put("wtValCOLL", "");
         parameters.put("othersValCOLL", "");
      }

      parameters.put("declareValueCarriage",
            (requestModel.getCarriage() == null || StringUtils.isEmpty(requestModel.getCarriage())) ? "NVD"
                  : requestModel.getCarriage());
      parameters.put("declareValueCustoms",
            (requestModel.getCustomValue() == null || StringUtils.isEmpty(requestModel.getCustomValue())) ? "NCV"
                  : requestModel.getCustomValue());
      parameters.put("destApt", requestModel.getDestApt() == null ? "" : requestModel.getDestApt());

      // flight to be converted to list
      parameters.put("reqFlight", requestModel.getRequestedFlight() == null ? "" : requestModel.getRequestedFlight());
      parameters.put("reqFlightDate",
            requestModel.getRequestedFlightDate() == null ? "" : requestModel.getRequestedFlightDate());
      parameters.put("amountOfInsu",
            (requestModel.getInsuranceValueDeclaration() == null
                  || StringUtils.isEmpty(requestModel.getInsuranceValueDeclaration())) ? "XXX"
                        : requestModel.getInsuranceValueDeclaration());

      // to be filtered based on ssr and osi on UI
      parameters.put("handlingInfo", requestModel.getHandlingInfo() == null ? "" : requestModel.getHandlingInfo());
      parameters.put("handlingCode", requestModel.getHandlingCode() == null ? "" : requestModel.getHandlingCode());
      parameters.put("handlingSci", requestModel.getSci() == null ? "" : requestModel.getSci());
      parameters.put("otherChargesList", requestModel.getDueChargeAmount());
      parameters.put("accountInfoList", requestModel.getAccountingInfo());
      parameters.put("noOfPiecesSum", requestModel.getTotalNoPieces() == null ? "" : requestModel.getTotalNoPieces());
      parameters.put("grossWeightSum",
            requestModel.getTotalGrossWeight() == null ? "" : requestModel.getTotalGrossWeight());
      parameters.put("totalSum", requestModel.getTotalCost() == null ? "" : requestModel.getTotalCost());
      parameters.put("weightChargePPD", requestModel.getPrepaidWc() == null ? "" : requestModel.getPrepaidWc());
      parameters.put("weightChargeCOL", requestModel.getCollectWc() == null ? "" : requestModel.getCollectWc());

      parameters.put("vlauenChargePPD", requestModel.getPrepaidVc() == null ? "" : requestModel.getPrepaidVc());
      parameters.put("vlauenChargeCOL", requestModel.getCollectVc() == null ? "" : requestModel.getCollectVc());

      parameters.put("taxPPD", requestModel.getPrepaidTax() == null ? "" : requestModel.getPrepaidTax());
      parameters.put("taxCOL", requestModel.getCollectTax() == null ? "" : requestModel.getCollectTax());

      parameters.put("totalOtherChargesDueAgentPPD",
            requestModel.getPrepaidOcA() == null ? "" : requestModel.getPrepaidOcA());
      parameters.put("totalOtherChargesDueAgentCOL",
            requestModel.getCollectOcA() == null ? "" : requestModel.getCollectOcA());
      parameters.put("totalOtherChargesDueCarrierPPD",
            requestModel.getPrepaidOcC() == null ? "" : requestModel.getPrepaidOcC());
      parameters.put("totalOtherChargesDueCarrierCOL",
            requestModel.getCollectOcC() == null ? "" : requestModel.getCollectOcC());

      parameters.put("signature", requestModel.getShipperSign() == null ? "" : requestModel.getShipperSign());
      parameters.put("totalPrepaid", requestModel.getTotalPrepaid() == null ? "" : requestModel.getTotalPrepaid());
      parameters.put("totalCollect", requestModel.getTotalCollect() == null ? "" : requestModel.getTotalCollect());
      parameters.put("currencyConversionRate", requestModel.getCcRate() == null ? "" : requestModel.getCcRate());
      parameters.put("chargesInDestCurrency", requestModel.getCcCharges() == null ? "" : requestModel.getCcCharges());
      parameters.put("executedOnDate", requestModel.getExecutionDate() == null ? "" : requestModel.getExecutionDate());
      parameters.put("executedOnPlace",
            requestModel.getExecutionPlace() == null ? "" : requestModel.getExecutionPlace());

      parameters.put("totalCollectCharges",
            requestModel.getTotalChargers() == null ? "" : requestModel.getTotalChargers());
      parameters.put("chargesAtDest", requestModel.getDestCharges() == null ? "" : requestModel.getDestCharges());

      parameters.put("printQueue", requestModel.getPrintQueue() == null ? "" : requestModel.getPrintQueue());
      parameters.put("signofIssueCarrier", requestModel.getCarriersExecutionAuthorisationSignature() == null ? ""
            : requestModel.getCarriersExecutionAuthorisationSignature());
      /**
       * Set Commodity info in the List
       **/
      List<CommodityChargesList> commodityChargesList = null;
      CommodityChargesList ccl = null;
      List<String> natureOfGoodsList = null;
      int numberOfPiecesTotal = 0;
      float grossWeightTotal = 0.0f;
      float totalChargeAmountTotal = 0.0f;

      if (null != requestModel.getCommodityChargesList() && 0 < requestModel.getCommodityChargesList().size()) {
         commodityChargesList = new ArrayList<CommodityChargesList>();
         for (CommodityChargesList cl : requestModel.getCommodityChargesList()) {
            ccl = new CommodityChargesList();
            ccl.setRateLineNumber(cl.getRateLineNumber());
            ccl.setNumberOfPieces(cl.getNumberOfPieces());
            ccl.setWeightUnitCode(cl.getWeightUnitCode());
            ccl.setGrossWeight(cl.getGrossWeight());
            ccl.setRateClassCode(cl.getRateClassCode());
            ccl.setCommodityItemNo(cl.getCommodityItemNo());
            ccl.setChargeableWeight(cl.getChargeableWeight());
            if (null != cl.getRateChargeAmount() && !cl.getRateChargeAmount().contains(".")) {
               ccl.setRateChargeAmount(cl.getRateChargeAmount().concat(".00"));
            } else {
               ccl.setRateChargeAmount(cl.getRateChargeAmount());
            }
            if (null != cl.getTotalChargeAmount() && !cl.getTotalChargeAmount().contains(".")) {
               ccl.setTotalChargeAmount(cl.getTotalChargeAmount().concat(".00"));
            } else {
               ccl.setTotalChargeAmount(cl.getTotalChargeAmount());
            }

            if (null != cl.getRateDescriptionOtherInfoForNawb() && 0 < cl.getRateDescriptionOtherInfoForNawb().size()) {
               natureOfGoodsList = new ArrayList<String>();
               for (RateDescriptionOtherInfoForNawb goodsType : cl.getRateDescriptionOtherInfoForNawb()) {
                  switch (goodsType.getType()) {
                  case RateDescriptionOtherInfoForNawbType.Type.NC:
                     if (null != goodsType.getNatureOfGoodsDescription()
                           && StringUtils.isNotEmpty(goodsType.getNatureOfGoodsDescription())) {
                        natureOfGoodsList.add(goodsType.getNatureOfGoodsDescription());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.ND:
                     if (null != goodsType.getDimensionHeight()
                           && StringUtils.isNotEmpty(goodsType.getDimensionHeight())) {
                        natureOfGoodsList.add(goodsType.getDimensionHeight());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.NG:
                     if (null != goodsType.getNatureOfGoodsDescription()
                           && StringUtils.isNotEmpty(goodsType.getNatureOfGoodsDescription())) {
                        natureOfGoodsList.add(goodsType.getNatureOfGoodsDescription());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.NH:
                     if (null != goodsType.getHarmonisedCommodityCode()
                           && StringUtils.isNotEmpty(goodsType.getHarmonisedCommodityCode())) {
                        natureOfGoodsList.add(goodsType.getHarmonisedCommodityCode());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.NO:
                     if (null != goodsType.getCountryCode() && StringUtils.isNotEmpty(goodsType.getCountryCode())) {
                        natureOfGoodsList.add(goodsType.getCountryCode());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.NS:
                     if (null != goodsType.getSlacCount() && StringUtils.isNotEmpty(goodsType.getSlacCount())) {
                        natureOfGoodsList.add(goodsType.getSlacCount());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.NU:
                     if (null != goodsType.getUldNumber() && StringUtils.isNotEmpty(goodsType.getUldNumber())) {
                        natureOfGoodsList.add(goodsType.getUldNumber());
                     }
                     break;
                  case RateDescriptionOtherInfoForNawbType.Type.NV:
                     if (null != goodsType.getVolumeAmount() && StringUtils.isNotEmpty(goodsType.getVolumeAmount())) {
                        natureOfGoodsList.add(goodsType.getVolumeAmount());
                     }
                     break;
                  default:
                     break;
                  }
                  ccl.setNatureOfGoodsList(natureOfGoodsList);
               }
            }
            commodityChargesList.add(ccl);
            numberOfPiecesTotal = numberOfPiecesTotal + Integer.valueOf(ccl.getNumberOfPieces());
            grossWeightTotal = grossWeightTotal + Float.valueOf(ccl.getGrossWeight());
            totalChargeAmountTotal = totalChargeAmountTotal + Float.valueOf(ccl.getTotalChargeAmount());
         }
      }
      parameters.put("commodityChargesList", commodityChargesList);
      parameters.put("numberOfPiecesTotal", String.valueOf(numberOfPiecesTotal));
      parameters.put("grossWeightTotal", String.valueOf(df.format(grossWeightTotal)));
      parameters.put("totalChargeAmountTotal", String.valueOf(df.format(totalChargeAmountTotal)));
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "printNAWB", Level.DEBUG,
            "commodityChargesList values", parameters.get("commodityChargesList")));
      report.setParameters(parameters);
      if (null != report.getQueueName()) {
         printerService.printReport(report);
      }
   }

   /**
    * @param requestModel
    * @throws CustomException
    */
   private void sendFWBMessage(NeutralAWBMaster requestModel) throws CustomException {
      SendManuallyCreatedFreightWayBillStoreEvent event = new SendManuallyCreatedFreightWayBillStoreEvent();
      if (!CollectionUtils.isEmpty(requestModel.getRoutingList())) {
         event.setCarrier(requestModel.getRoutingList().get(0).getCarrierCode());
      }
      event.setShipmentNumber(requestModel.getAwbNumber());
      event.setShipmentDate(requestModel.getAwbDate());
      event.setCreatedBy(requestModel.getCreatedBy());
      event.setCreatedOn(LocalDateTime.now());
      event.setLastModifiedBy(requestModel.getModifiedBy());
      event.setLastModifiedOn(LocalDateTime.now());
      // Rule Engine Execution call for ACAS Shipment to trigger FWB Message to CCN
      // Interface
      event.setAcasShipment(identifyACASShipment(requestModel));
      event.setCheckShipmenMstExistanceForNAWB(true);
      //
      producer.publish(event);
   }

   /**
    * @param requestModel
    * @return
    * @throws CustomException
    */
   private boolean identifyACASShipment(NeutralAWBMaster requestModel) throws CustomException {
      // Payload Initialization
      FactPayload factPayload = new FactPayload();
      FactShipment factShipment = new FactShipment();
      factShipment.setShipmentNumber(requestModel.getAwbNumber());
      factShipment.setShipmentDate(requestModel.getAwbDate());
      factPayload.setFactShipment(factShipment);
      // Trigger Point & Operation
      factPayload.setTriggerPoint(OutboundMessage.class);
      factPayload.setTriggerPointOperation(com.ngen.cosys.processing.engine.rule.triggerpoint.operation.FWB.class);
      // Set Audit Details
      factPayload.setCreatedBy(requestModel.getCreatedBy());
      factPayload.setCreatedOn(requestModel.getCreatedOn());
      factPayload.setModifiedBy(requestModel.getModifiedBy());
      factPayload.setModifiedOn(requestModel.getModifiedOn());
      //
      return ruleExecutor.initRuleEngineProcessForACAS(factPayload);
   }

   @ApiOperation("update InProcess Status For selected AwbNumber")
   @RequestMapping(value = "nawb/update-inprocess-status", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<SearchStockRQ> updateInProcessForAwbNumber(
         @ApiParam(value = "carrierCode/stockCategoryCode/stockId", required = true) @Valid @RequestBody SearchStockRQ searchStockRQ)
         throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<SearchStockRQ> stockResponse = utilitiesModelConfiguration.getBaseResponseInstance();
      SearchStockRQ stocks = null;
      stocks = neutralAWBService.updateInProcessForAwbNumber(searchStockRQ);
      stockResponse.setData(stocks);
      stockResponse.setSuccess(true);
      LOGGER.debug(LoggerUtil.getLoggerMessage(this.getClass().getName(), "updateInProcessForAwbNumber", Level.DEBUG,
            searchStockRQ, stocks));
      return stockResponse;
   }

}