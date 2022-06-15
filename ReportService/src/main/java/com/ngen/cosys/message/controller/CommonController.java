/**
 * 
 * CommonController.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22th August, 2018 NIIT -
 */
package com.ngen.cosys.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.message.model.ChatInitMessage;
import com.ngen.cosys.message.model.UserDetails;
import com.ngen.cosys.message.model.UserGroup;
import com.ngen.cosys.message.service.CommonService;

/**
 * 
 * This controller takes care of all requests related to user chat details
 * 
 * @author NIIT Technologies Ltd
 */
@NgenCosysAppInfraAnnotation
public class CommonController {

   @Autowired
   CommonService commonService;

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   /**
    * This Method will return all user details
    * 
    * @return
    * @throws CustomException
    */
   @RequestMapping(method = RequestMethod.POST, value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<UserDetails>> getAllUsers(@RequestBody UserDetails userDetails) throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<UserDetails>> baseResponseForUserDetails = utilitiesModelConfiguration
            .getBaseResponseInstance();
      baseResponseForUserDetails.setData(commonService.getAllUsers(userDetails));
      return baseResponseForUserDetails;
   }

   /**
    * This method will return all groups
    * 
    * @return
    * @throws CustomException
    */
   @RequestMapping(method = RequestMethod.POST, value = "/users/group", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public BaseResponse<List<UserGroup>> getAllGroups() throws CustomException {
      @SuppressWarnings("unchecked")
      BaseResponse<List<UserGroup>> baseResponseForUserGroup = utilitiesModelConfiguration.getBaseResponseInstance();
      baseResponseForUserGroup.setData(commonService.getAllGroups());
      return baseResponseForUserGroup;
   }

   /**
    * This method will return all groups
    * 
    * @return
    * @throws CustomException
    */
   @RequestMapping(method = RequestMethod.POST, value = "/users/test", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
   public ChatInitMessage getTestResponse(@RequestBody ChatInitMessage chatInitMessage) throws CustomException {
      chatInitMessage.setDetail(commonService.getFromAndToChatSummary(chatInitMessage));
      return chatInitMessage;
   }

}
