package com.ngen.cosys.shipment.terminaltoterminalhandover.controller;

import java.lang.reflect.InvocationTargetException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.command.RequestKey;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchGroup;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchKey;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPointDetails;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class TerminalToTerminalHandoverController {
   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   @Qualifier("responder")
   private ControllerResponder responder;

   @RequestMapping(value = "/tester", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<BaseResponse<BaseBO>> getRequestData(@Valid @RequestBody SearchKey searchKey)
         throws ReflectiveOperationException, IllegalArgumentException, SecurityException {
      BaseResponse<BaseBO> transferShipmentToTerminal = this.utilitiesModelConfiguration.getBaseResponseInstance();
      BaseBO basebo;
      if ((basebo = responder.getResponse(RequestKey.Test, searchKey)) != null) {
         transferShipmentToTerminal.setData(basebo);
         transferShipmentToTerminal.setSuccess(true);
      }
      return new ResponseEntity<>(transferShipmentToTerminal, HttpStatus.OK);
   }

   @ApiOperation("Get Details Of Shipment At Terminal")
   @RequestMapping(value = "/api/shpmng/getDetailsOfShipmentFromTerminal", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public ResponseEntity<BaseResponse<BaseBO>> getDetailsOfShipmentFromTerminal(@Valid @RequestBody SearchKey searchKey)
         throws CustomException, IllegalArgumentException, SecurityException, InstantiationException,
         IllegalAccessException, InvocationTargetException, NoSuchMethodException {
      BaseResponse<BaseBO> getDetailsOfShipmentFromTerminal = this.utilitiesModelConfiguration
            .getBaseResponseInstance();
      BaseBO baseBo;
      if ((baseBo = responder.getResponse(RequestKey.GETDETAILSOFSHIPMENTFROMTERMINAL, (BaseBO) searchKey)) != null) {

         if (baseBo instanceof SearchKey) {
            getDetailsOfShipmentFromTerminal.setSuccess(false);
         } else {
            getDetailsOfShipmentFromTerminal.setSuccess(true);
            if (((TerminalPointDetails) baseBo).getHandoverTerminalShpList() == null) {
               getDetailsOfShipmentFromTerminal.setSuccess(false);
               baseBo.addError("TerminalToTerminalHandOver06", "", ErrorType.ERROR);
            }
         }
         getDetailsOfShipmentFromTerminal.setData(baseBo);
      }

      /*
       * if ((baseBo =
       * terminalHandoverService.getDetailsOfShipmentFromTerminal(searchKey)) != null)
       * { getDetailsOfShipmentFromTerminal.setData(baseBo);
       * getDetailsOfShipmentFromTerminal.setSuccess(true); }
       */

      return new ResponseEntity<>(getDetailsOfShipmentFromTerminal, HttpStatus.OK);
   }

   @ApiOperation("Transfer Shipment At Receiver Terminal")
   @RequestMapping(value = "/api/shpmng/transferShipmentToTerminal", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public ResponseEntity<BaseResponse<BaseBO>> transferShipmentToTerminal(@Valid @RequestBody SearchGroup transferShps)
         throws IllegalArgumentException, SecurityException, ReflectiveOperationException {
      BaseResponse<BaseBO> transferShp = this.utilitiesModelConfiguration.getBaseResponseInstance();
      BaseBO baseBo;
      if ((baseBo = responder.getResponse(RequestKey.TRANSFERSHIPMENTTOTERMINAL, (BaseBO) transferShps)) != null) {
         transferShp.setData(baseBo);
         transferShp.setSuccess(true);
      }
      return new ResponseEntity<>(transferShp, HttpStatus.OK);
   }

   @ApiOperation("Get Details Of Shipment At Receiver Terminal")
   @RequestMapping(value = "/api/shpmng/getDetailsOfShipmentToTerminal", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
   public ResponseEntity<BaseResponse<BaseBO>> getDetailsOfShipmentToTerminal(
         @Valid @RequestBody SearchGroup searchGroup)
         throws IllegalArgumentException, SecurityException, ReflectiveOperationException {
      BaseResponse<BaseBO> searchedList = this.utilitiesModelConfiguration.getBaseResponseInstance();
      BaseBO baseBo;
      if ((baseBo = responder.getResponse(RequestKey.GETDETAILSOFSHIPMENTTOTERMINAL, (BaseBO) searchGroup)) != null) {
         searchedList.setData(baseBo);
         searchedList.setSuccess(true);
      }
      return new ResponseEntity<>(searchedList, HttpStatus.OK);
   }
}