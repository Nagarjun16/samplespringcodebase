/**
 * 
 * CN46Controller.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date       Author      Reason
 * v1         14 April, 2017   NIIT      -
 */
package com.ngen.cosys.shipment.mail.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.mail.model.CreateCN46;
import com.ngen.cosys.shipment.mail.service.CN46Service;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class CN46Controller {

   @Autowired
   private UtilitiesModelConfiguration utility;

   @Autowired
   private CN46Service cn46Service;

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @ApiOperation("Getting CN 46 Details")
   @RequestMapping(value = "/api/shipment/mail/searchCN46Details", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<CreateCN46> searchCN46Details(@Valid @RequestBody CreateCN46 request) throws CustomException {
      BaseResponse<CreateCN46> cn = utility.getBaseResponseInstance();
      CreateCN46 cn46 = cn46Service.searchCN46Details(request);
      cn.setData(cn46);
      return cn;
   }

   /**
    * @param request
    * @return
    * @throws CustomException
    */
   @ApiOperation("Creating CN 46 Request")
   @RequestMapping(value = "/api/shipment/mail/cn46Request", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<CreateCN46> insertCN46Request(@Valid @RequestBody CreateCN46 request) throws CustomException {
      BaseResponse<CreateCN46> cn = utility.getBaseResponseInstance();
      CreateCN46 cn46 = cn46Service.insertCN46Request(request);
      cn.setData(cn46);
      return cn;
   }
}