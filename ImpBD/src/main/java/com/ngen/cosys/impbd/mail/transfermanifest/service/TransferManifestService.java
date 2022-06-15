/**
 * 
 * TransferManifestService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          9 March, 2018   NIIT      -
 */
package com.ngen.cosys.impbd.mail.transfermanifest.service;

import java.util.List;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.transfermanifest.model.SearchTransferManifestDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierResponse;

/**
 * This interface takes care of the Transfer Manifest services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface TransferManifestService {

   /**
    *  fetches Transfer Manifest details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<TransferCarrierResponse> fetchTransferManifestDetails(SearchTransferManifestDetails search) throws CustomException;


   /**
    *  saves transfer manifest details
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<TransferCarrierDetails> saveTransferManifestDetails(List<TransferCarrierDetails> request) throws CustomException;

}
