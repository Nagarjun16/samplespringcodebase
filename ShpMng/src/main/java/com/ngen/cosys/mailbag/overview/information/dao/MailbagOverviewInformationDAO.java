package com.ngen.cosys.mailbag.overview.information.dao;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mailbag.overview.information.model.AllStatusOfMailBag;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewDetail;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewSummary;
import com.ngen.cosys.mailbag.overview.information.model.MailbagSearchReq;

public interface MailbagOverviewInformationDAO {

   /**
    * Method to get mailbag information
    * 
    * @param search
    * @return MailbagOverviewDetailList
    * @throws CustomException
    */

   List<MailbagOverviewSummary> getMailbagOverviewDetails(MailbagSearchReq search) throws CustomException;

   List<MailbagOverviewSummary> getMailbagOverviewDetailsMobile(MailbagSearchReq search) throws CustomException;

   List<MailbagOverviewDetail> updateLocation(List<MailbagOverviewDetail> reqParam) throws CustomException;

   int updateMailbagXray(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int createMailbagXrayRecord(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int createMailDamage(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int updateMailDamage(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   MailbagOverviewDetail checkMailBagOverViewMobile(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int deleteMailDamage(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int createMailDamageLineItem(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int updateMailDamageLineItem(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int deleteMailDamageLineItem(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   MailbagOverviewSummary getMailBagOverDamageLineItemDetails(MailbagSearchReq searchReq) throws CustomException;

   int updateLocationRecord(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int createLocationInfo(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int updateEmbargoFailure(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int createEmbargoFailure(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   int deleteEmbargoFailure(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   MailbagOverviewDetail checkEmbargoDetailMobile(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   MailbagOverviewSummary getEmbargoFailureDetails(MailbagOverviewSummary summary) throws CustomException;

   String getCarrierCodeForAMailBag(BigInteger shipmentId) throws CustomException;

   String checkContentCode(String string) throws CustomException;

   int getLoadedSHC(String storeLocation) throws CustomException;

   MailbagOverviewDetail checkUldAssignmentForMailFlight(MailbagOverviewDetail mailbagOverviewDetail)
         throws CustomException;

   String getContainerDestination(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;

   void updateNextDestination(List<MailbagOverviewDetail> updateLocationModel) throws CustomException;

   void deleteXrayIfNoStatus(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException;
   
   AllStatusOfMailBag getAllStatusOfTheMailBag(AllStatusOfMailBag status) throws CustomException;

}
