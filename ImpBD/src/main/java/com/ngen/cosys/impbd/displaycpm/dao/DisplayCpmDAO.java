/**
 * This is a repository interface for displaying CPM info
 */
package com.ngen.cosys.impbd.displaycpm.dao;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.displaycpm.model.DisplayCpmModel;

public interface DisplayCpmDAO {

   /**
    * Method to search CPM info
    * 
    * @param displayCpmModel
    * @return DisplayCpmModel
    * @throws CustomException
    */
   DisplayCpmModel search(DisplayCpmModel displayCpmModel) throws CustomException;

}