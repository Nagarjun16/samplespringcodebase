/**
 * This is a service component implementation for search CPM info
 */
package com.ngen.cosys.impbd.displaycpm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displaycpm.dao.DisplayCpmDAO;
import com.ngen.cosys.impbd.displaycpm.model.DisplayCpmModel;

@Service
public class DisplayCpmServiceImpl implements DisplayCpmService {

   @Autowired
   DisplayCpmDAO dao;

   @Override
   public DisplayCpmModel search(DisplayCpmModel displayCpmModel) throws CustomException {
      // Search
      DisplayCpmModel response = this.dao.search(displayCpmModel);

      // Check for CPM info
      Optional<DisplayCpmModel> oDisplayCpmModel = Optional.ofNullable(response);
      if (!oDisplayCpmModel.isPresent()) {
         throw new CustomException("data.no.cpm.received.for.flight", null, ErrorType.APP);
      }

      // Return the response
      return response;
   }

}