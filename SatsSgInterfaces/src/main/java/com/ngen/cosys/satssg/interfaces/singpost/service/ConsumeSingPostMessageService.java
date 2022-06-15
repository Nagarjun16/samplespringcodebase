/**
 * 
 * ConsumeSingPostMessageService.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          25 May, 2018 NIIT      -
 */
package com.ngen.cosys.satssg.interfaces.singpost.service;

import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;

/**
 * This consumer interface takes care of the processing activities for the
 * incoming SINGPOST messages.
 * 
 * @author NIIT Technologies Ltd
 *
 */
public interface ConsumeSingPostMessageService {

   /**
    * Reference implementation for consuming incoming messages.
    * 
    * @param pullMailBagResponseModel.
    * @return instance of PullMailBagResponseModel.
    * @throws instance
    *            of CustomException.
    */
   PullMailBagResponseModel pullMailBagStatus(PullMailBagResponseModel pullMailBagResponseModel) throws CustomException;

   /**
    * This method processes the consumed PA Summary Message.
    * 
    * @param paSummaryMailBags.
    * @return instance of List<MailBagResponseModel>.
    * @throws instance
    *            of CustomException.
    */
   List<MailBagResponseModel> processPASummary(List<MailBagResponseModel> paSummaryMailBags) throws CustomException;

   /**
    * This method processes the consumed PA Detail Message.
    * 
    * @param paDetailMailBags.
    * @return instance of List<MailBagResponseModel>.
    * @throws instance
    *            of CustomException.
    */
   List<MailBagResponseModel> processPADetail(List<MailBagResponseModel> paDetailMailBags) throws CustomException;

   /**
    * This method processes the consumed DL(DLV - Delivery) Message.
    * 
    * @param dlvMailBags.
    * @return instance of List<MailBagResponseModel>.
    * @throws instance
    *            of CustomException.
    */
   List<MailBagResponseModel> processDLV(List<MailBagResponseModel> dlvMailBags) throws CustomException;

   /**
    * This method processes the consumed IPS-AA Message.
    * 
    * @param ipsAaMailBags.
    * @return instance of List<MailBagResponseModel>.
    * @throws instance
    *            of CustomException.
    */
   List<MailBagResponseModel> processIPSAA(List<MailBagResponseModel> ipsAaMailBags) throws CustomException;
}