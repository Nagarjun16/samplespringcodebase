/**
 * 
 * TransferManifestDAO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 14 April, 2018 NIIT -
 */
package com.ngen.cosys.impbd.mail.transfermanifest.dao;


import java.math.BigInteger;
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
public interface TransferManifestDAO {
   
   /**
    * Fetches Transfer Manifest details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   List<TransferCarrierResponse> fetchTransferManifestDetails(SearchTransferManifestDetails search) throws CustomException;
  
   /**
    * returns TRM count
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   String getTrmCount(TransferCarrierDetails e) throws CustomException;
   
   /**
    * Saves Transfer Manifest details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   void insertTransferManifestByMail(TransferCarrierDetails mailInfo) throws CustomException;
  
   /**
    * saves Transfer Manifest details.
    * 
    * @param request
    * @return
    * @throws CustomException
    */
   void insertTransferManifestInfoByMail(TransferCarrierDetails mailInfo) throws CustomException;

   void updateLyingListOfShipments(List<TransferCarrierDetails> shipmentIds) throws CustomException;

}
