package com.ngen.cosys.shipment.airmail.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.annotation.PostRequest;
import com.ngen.cosys.framework.config.UtilitiesModelConfiguration;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseResponse;
import com.ngen.cosys.shipment.airmail.model.LyingListContainer;
import com.ngen.cosys.shipment.airmail.model.SearchForLyingList;
import com.ngen.cosys.shipment.airmail.service.LyingListService;

@NgenCosysAppInfraAnnotation(path = "/api/shpmng/")
public class LyingListController {

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(LyingListController.class);

   @Autowired
   private UtilitiesModelConfiguration utilitiesModelConfiguration;

   @Autowired
   private LyingListService lyingListService;

   @PostRequest(value = "/airmail/lyingList/searchLyingList", method = RequestMethod.POST)
   public BaseResponse<Object> searchLyingList(@RequestBody @Valid SearchForLyingList param) throws CustomException {
      BaseResponse<Object> searchlyinglist = utilitiesModelConfiguration.getBaseResponseInstance();
      try {
         List<LyingListContainer> response = lyingListService.getSearchedLyingList(param);
         searchlyinglist.setData(response);
      } catch (CustomException e) {
         searchlyinglist.setData(param);
         lOgger.error(EXCEPTION, e);
      }
      return searchlyinglist;
   }
}