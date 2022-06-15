/**
 * This is a service component for displaying CPM inbound message
 */
package com.ngen.cosys.impbd.displaycpm.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displaycpm.model.DisplayCpmModel;

public interface DisplayCpmService {

   /**
    * Method to search CPM info
    * 
    * @param displayCpmModel
    * @return DisplayCpmModel
    * @throws CustomException
    */
   DisplayCpmModel search(DisplayCpmModel displayCpmModel) throws CustomException;

}