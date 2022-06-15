package com.ngen.cosys.mailbag.overview.information.service;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mailbag.overview.information.model.AllStatusOfMailBag;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewDetail;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewSummary;
import com.ngen.cosys.mailbag.overview.information.model.MailbagSearchReq;

public interface MailbagOverviewInformationService {

   /**
    * Fetches Mailbag Information.
    * 
    * @param search
    * @return MailbagOverviewDetailList
    * @throws CustomException
    */

   List<MailbagOverviewSummary> getMailBagDetails(MailbagSearchReq search) throws CustomException;

   List<MailbagOverviewDetail> updateLocation(List<MailbagOverviewDetail> requestModel) throws CustomException;

   MailbagOverviewSummary updateMultipleMailbagLocationMobile(MailbagOverviewSummary requestModel)
         throws CustomException;

   Object getMailBagDetail(MailbagSearchReq mailbagCorrectionResponse) throws CustomException;

   MailbagOverviewDetail saveUpdateMailBagMobile(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   String getCarrierCodeForAMailBag(BigInteger shipmentId) throws CustomException;

   List<MailbagOverviewDetail> checkForContentCode(List<MailbagOverviewDetail> requestModel) throws CustomException;

   MailbagOverviewSummary checkForMobileContentCode(MailbagOverviewSummary requestModel) throws CustomException;

   List<MailbagOverviewDetail> checkForShcsOtherThanMail(List<MailbagOverviewDetail> requestModel)
         throws CustomException;

   List<MailbagOverviewDetail> checkForContainerDestination(List<MailbagOverviewDetail> requestModel)
         throws CustomException;
   
   AllStatusOfMailBag getAllStatusOfTheMailBag (AllStatusOfMailBag status) throws CustomException;

}
