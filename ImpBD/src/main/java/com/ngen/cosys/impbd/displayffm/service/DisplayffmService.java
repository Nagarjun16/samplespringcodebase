/**
 * 
 * DisplayffmService.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 30 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.displayffm.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displayffm.model.DisplayffmByFlightModel;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;

/**
 * This interface takes care of the Displayffm List.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface DisplayffmService {

   /**
    * Method to get shipment information for Displayffm List.
    * 
    * @param requestModel
    * @return DisplayffmByFlightModel
    * @throws CustomException
    */
   List<DisplayffmByFlightModel> search(SearchDisplayffmModel searchDisplayffmModel) throws CustomException;
   
   List<DisplayffmByFlightModel> updateFFMstatus(SearchDisplayffmModel searchDisplayffmModel) throws CustomException;

}
