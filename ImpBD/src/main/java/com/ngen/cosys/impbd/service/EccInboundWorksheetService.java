package com.ngen.cosys.impbd.service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.model.EccInboundResult;
import com.ngen.cosys.impbd.model.SearchInbound;

/**
 * @author rumani.5.jain
 *
 */
public interface EccInboundWorksheetService {

   /**
    * @param searchInbound
    * @return EccInboundResult
    * @throws CustomException
    */
   EccInboundResult search(SearchInbound searchInbound) throws CustomException;

   /**
    * @param eccInboundResult
    * @return EccInboundResult
    * @throws CustomException
    */
   EccInboundResult save(EccInboundResult eccInboundResult) throws CustomException;

}