/**
 * DisplayffmDao.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 30 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.displayffm.dao;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displayffm.model.DisplayffmByFlightModel;
import com.ngen.cosys.impbd.displayffm.model.SearchDisplayffmModel;
import com.ngen.cosys.impbd.model.FFMCountDetails;

/**
 * This interface takes care of maintaining Displayffm list.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface DisplayffmDao {

   /**
    * Method to get Displayffm information
    * 
    * @param requestModel
    * @return DisplayffmByFlightModel
    * @throws CustomException
    */
   List<DisplayffmByFlightModel> search(SearchDisplayffmModel searchDisplayffmModel) throws CustomException;
   
   public void updateFFMstatus(SearchDisplayffmModel searchDisplayffmModel) throws CustomException;

   String getStatus(FFMCountDetails ffmCountDetails)throws CustomException;;

}
