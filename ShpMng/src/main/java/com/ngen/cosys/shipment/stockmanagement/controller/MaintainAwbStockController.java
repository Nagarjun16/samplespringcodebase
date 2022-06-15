/**
 * 
 * MaintainAwbStockController.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          30 January, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.stockmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.stockmanagement.model.StockDetail;
import com.ngen.cosys.shipment.stockmanagement.model.StockSummary;
import com.ngen.cosys.shipment.stockmanagement.service.AwbStockService;
import com.ngen.cosys.shipment.validatorgroup.StockManagementValidatorGroup;

import io.swagger.annotations.ApiOperation;

/**
 * This class is controller for AWB Stock Management.
 * 
 * @author NIIT Technologies Ltd.
 *
 */
@NgenCosysAppInfraAnnotation
public class MaintainAwbStockController {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private AwbStockService awbStockService;
   @NgenAuditAction( entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_STOCK_MANAGEMENT)
   @ApiOperation("Create AWB Stock Detail and Summary")
   @RequestMapping(value = "/api/stockmanagment/neutralawb/add/awbstock", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<StockDetail> createNeutralAWBStockManagement(
         @Validated(StockManagementValidatorGroup.class) @RequestBody StockDetail stockDetail) throws CustomException {
      BaseResponse<StockDetail> stockDetailRes = utility.getBaseResponseInstance();
      stockDetail = awbStockService.addAWBStockHeader(stockDetail);
      if (!stockDetail.getMessageList().isEmpty()) {
         stockDetailRes.setMessageList(stockDetail.getMessageList());
      } else {
         stockDetail = awbStockService.addAWBStockSummaryDetail(stockDetail);
         // stockDetailRes.setData(awbStockService.getAWBStockDetailHeader(stockDetail));
         stockDetailRes.setData(stockDetail);
      }
      return stockDetailRes;
   }
   @NgenAuditAction( entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_STOCK_MANAGEMENT)
   @ApiOperation("Neutral AWB Stock Management Detail")
   @RequestMapping(value = "/api/stockmanagment/neutralawb/select/awbstockdetail", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<StockDetail> selectNeutralAWBStockManagement(@Valid @RequestBody StockDetail stockDetail)
         throws CustomException {
      BaseResponse<StockDetail> stockDetailRes = utility.getBaseResponseInstance();
      stockDetailRes.setData(awbStockService.getAWBStockDetailHeader(stockDetail));
      return stockDetailRes;
   }
   	
   @NgenAuditAction( entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_STOCK_MANAGEMENT)
   @ApiOperation("Change AWB Low Stock Limit")
   @RequestMapping(value = "/api/stockmanagment/neutralawb/update/lowstocklimit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<StockDetail> updateAWBLowStockLimit(@Valid @RequestBody StockDetail stockDetail)
         throws CustomException {
      BaseResponse<StockDetail> stockDetailRes = utility.getBaseResponseInstance();
      awbStockService.updateAWBLowStockLimit(stockDetail);
      stockDetailRes.setData(awbStockService.getAWBStockDetailHeader(stockDetail));
      return stockDetailRes;
   }
   
   @NgenAuditAction( entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_STOCK_MANAGEMENT)
   @ApiOperation("Neutral AWB Stock Summary")
   @RequestMapping(value = "/api/stockmanagment/neutralawb/select/list/awbstocksummary", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<StockSummary>> selectNeutralAWBStockSummaryList(@Valid @RequestBody StockDetail stockDetail)
         throws CustomException {
      BaseResponse<List<StockSummary>> stockSummaryRes = utility.getBaseResponseInstance();
      stockSummaryRes.setData(awbStockService.getAWBStockSummaryDetail(stockDetail));
      return stockSummaryRes;
   }

   @ApiOperation("Neutral AWB Stock Status")
   @RequestMapping(value = "/api/stockmanagment/neutralawb/select/awbstocksummarystatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<StockDetail> selectNeutralAWBStockSummaryStatus(@Valid @RequestBody StockDetail stockDetail)
         throws CustomException {
      BaseResponse<StockDetail> stockDetailRes = utility.getBaseResponseInstance();
      stockDetailRes.setData(awbStockService.getAWBStockDetailHeader(stockDetail));
      return stockDetailRes;
   }
   
   @NgenAuditAction( entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.NAWB_STOCK_MANAGEMENT)
   @ApiOperation("Fetch Low Stock Limit")
   @RequestMapping(value = "/api/stockmanagment/neutralawb/fetch/lowStockLimit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<StockDetail> fetchLowStockLimit(@Valid @RequestBody StockDetail stockDetail)
         throws CustomException {
      BaseResponse<StockDetail> stockDetailRes = utility.getBaseResponseInstance();
      stockDetailRes.setData(awbStockService.fetchLowStockLimit(stockDetail));
      return stockDetailRes;
   }
   
   @ApiOperation("Mark as Deleted")
   @NgenAuditAction( entityType = NgenAuditEntityType.MASTER, eventName = NgenAuditEventType.NAWB_STOCK_SUMMARY)
   @RequestMapping(value = "/api/stockmanagment/neutralawb/update/markDelete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<StockDetail> markDelete(@Valid @RequestBody StockDetail stockDetail)
         throws CustomException {
      BaseResponse<StockDetail> stockSummaryRes = utility.getBaseResponseInstance();
      stockSummaryRes.setData(awbStockService.markDelete(stockDetail));
      return stockSummaryRes;
   }
}