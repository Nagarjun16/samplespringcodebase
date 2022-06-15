package com.ngen.cosys.mailbag.overview.information.service;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.airmail.model.MailContainerInfo;
import com.ngen.cosys.airmail.processor.MailbagProcessor;
import com.ngen.cosys.airmail.service.MailContainerInfoService;
import com.ngen.cosys.annotation.service.ShipmentProcessorService;
import com.ngen.cosys.common.validator.MoveableLocationTypeModel;
import com.ngen.cosys.common.validator.MoveableLocationTypesValidator;
import com.ngen.cosys.framework.constant.Action;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mailbag.overview.information.dao.MailbagOverviewInformationDAO;
import com.ngen.cosys.mailbag.overview.information.model.AllStatusOfMailBag;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewDetail;
import com.ngen.cosys.mailbag.overview.information.model.MailbagOverviewSummary;
import com.ngen.cosys.mailbag.overview.information.model.MailbagSearchReq;

/**
 * @author NIIT Technologies Ltd
 *
 */
@Service
@Transactional
public class MailbagOverviewInformationServiceImpl implements MailbagOverviewInformationService {

   private static final String PASS = "Pass";
   private static final String FAIL = "Fail";
   private static final String PENDING = "Pending";
   private static final String CALL_CUSTOMS = "Call Customs";
   private static final String OK = "Ok";

   @Autowired
   private MoveableLocationTypesValidator moveableLocationTypesValidator;
   @Autowired
   private MailbagOverviewInformationDAO mailbagOverviewInfoDao;

   @Autowired
   private MailbagProcessor mailbagProcessor;

   @Autowired
   private ShipmentProcessorService shipmentProcessorService;

   @Autowired
   MailContainerInfoService mailContainerService;

   @Override
   public List<MailbagOverviewSummary> getMailBagDetails(MailbagSearchReq search) throws CustomException {
      List<MailbagOverviewSummary> mailbagList = Collections.emptyList();
      validateByDispatch(search);
      validateByULD(search);
      if (!CollectionUtils.isEmpty(search.getMessageList())) {
         throw new CustomException();
      } else {
         mailbagList = this.mailbagOverviewInfoDao.getMailbagOverviewDetails(search);
         for (MailbagOverviewSummary value : mailbagList) {
            // check xray status
            for (MailbagOverviewDetail bagValue : value.getMailbagDetails()) {
               if (("00").equalsIgnoreCase(bagValue.getXrayResultFlag())
                     || "01".equalsIgnoreCase(bagValue.getXrayResultFlag())
                     || "02".equalsIgnoreCase(bagValue.getXrayResultFlag())) {
                  bagValue.setXrayResultFlag(PENDING);
               }
               if ("03".equalsIgnoreCase(bagValue.getXrayResultFlag())) {
                  bagValue.setXrayResultFlag(PASS);
               }
               if ("04".equalsIgnoreCase(bagValue.getXrayResultFlag())) {
                  bagValue.setXrayResultFlag(FAIL);
               }
            }
            value.setMailbagDetails(
                  (List<MailbagOverviewDetail>) mailbagProcessor.setMailbagParts(value.getMailbagDetails()));
         }
      }
      return mailbagList;
   }

   private void validateByULD(final MailbagSearchReq search) {
      if ("ByULD".equalsIgnoreCase(search.getSearchMode())) {
         if (search.getUldtrolley() == null || search.getUldtrolley() == "") {
            search.addError("TRAC_ASSIGNTGP06", "uldtrolley", ErrorType.ERROR);
         }
         if (search.getFromDate() == null) {
            search.addError("TRAC_ASSIGNTGP06", "fromDate", ErrorType.ERROR);
         }

         if (search.getToDate() == null) {
            search.addError("TRAC_ASSIGNTGP06", "toDate", ErrorType.ERROR);
         }
      }
   }

   private void validateByDispatch(final MailbagSearchReq search) {
      if ("ByDispatch".equalsIgnoreCase(search.getSearchMode())) {
         if (StringUtils.isEmpty(search.getDispatchNumber()) && StringUtils.isEmpty(search.getMailbagNumber())) {
            search.addError("TRAC_ASSIGNTGP06", "dispatchNumber", ErrorType.ERROR);

         }

         if (search.getFromDate() == null) {
            search.addError("TRAC_ASSIGNTGP06", "fromDate", ErrorType.ERROR);
         }

         if (search.getToDate() == null) {
            search.addError("TRAC_ASSIGNTGP06", "toDate", ErrorType.ERROR);
         }
      }
   }

   @Override
   public List<MailbagOverviewDetail> checkForContentCode(List<MailbagOverviewDetail> requestModel)
         throws CustomException {
      List<MailbagOverviewDetail> updateLocationModel = requestModel.stream()
            .filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());

      if (!StringUtils.isEmpty(updateLocationModel.get(0).getStoreLocation())) {
         MailContainerInfo containerInfo = new MailContainerInfo();
         containerInfo.setStoreLocation(updateLocationModel.get(0).getStoreLocation());
         containerInfo = mailContainerService.getMailContainerInfo(containerInfo);
         if (!containerInfo.getMessageList().isEmpty()) {
            updateLocationModel.get(0).addError("maillyinglist.container.usedbycargo", "", ErrorType.ERROR);
            throw new CustomException();
         }
      }

      MailbagOverviewDetail assignedFlight = mailbagOverviewInfoDao
            .checkUldAssignmentForMailFlight(requestModel.get(0));
      if (Optional.ofNullable(assignedFlight).isPresent()) {
         requestModel.get(0).setFlightKey(assignedFlight.getFlightKey());
         requestModel.get(0).setFlightDate(assignedFlight.getFlightDate());
         return requestModel;
      }
      return requestModel;
   }

   @Override
   public MailbagOverviewSummary checkForMobileContentCode(MailbagOverviewSummary requestModel) throws CustomException {
      if (!CollectionUtils.isEmpty(requestModel.getMailbagDetails())) {
         List<MailbagOverviewDetail> mailBagDetailList = requestModel.getMailbagDetails();
         for (MailbagOverviewDetail mailBag : mailBagDetailList) {
            if (StringUtils.isEmpty(mailBag.getStoreLocation())
                  && StringUtils.isEmpty(mailBag.getWarehouseLocation())) {
               mailBag.addError("exp.buildup.unload.location.required", "", ErrorType.ERROR);

            }
            if (!StringUtils.isEmpty(mailBag.getStoreLocation())) {
               Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
               Matcher matcher = pattern.matcher(mailBag.getStoreLocation());
               boolean isLocationContainsSpecialCharacter = matcher.find();
               if (isLocationContainsSpecialCharacter) {
                  mailBag.addError("data.mailbag.pattern.match", "", ErrorType.ERROR);

               }

               MoveableLocationTypeModel movableLocationModel = new MoveableLocationTypeModel();
               movableLocationModel.setKey(mailBag.getStoreLocation());
               movableLocationModel = moveableLocationTypesValidator.split(movableLocationModel);
               if (!movableLocationModel.getMessageList().isEmpty()) {
                  mailBag.addError(movableLocationModel.getMessageList().get(0).getCode(), "", ErrorType.ERROR);

               }
            }
            if (!mailBag.getMessageList().isEmpty()) {
               throw new CustomException();
            }
         }
         checkForContentCode(requestModel.getMailbagDetails());
      }

      return requestModel;
   }

   @Override
   public List<MailbagOverviewDetail> checkForShcsOtherThanMail(List<MailbagOverviewDetail> requestModel)
         throws CustomException {
      int otherShipments = mailbagOverviewInfoDao.getLoadedSHC(requestModel.get(0).getStoreLocation());
      if (otherShipments > 0) {
         requestModel.get(0).setUldPopup(true);
      }
      return requestModel;
   }

   @Override
   public List<MailbagOverviewDetail> checkForContainerDestination(List<MailbagOverviewDetail> requestModel)
         throws CustomException {
      String containerDestination = mailbagOverviewInfoDao.getContainerDestination(requestModel.get(0));
      if (!StringUtils.isEmpty(requestModel.get(0).getStoreLocation()) && !StringUtils.isEmpty(containerDestination)
            && !containerDestination.equalsIgnoreCase(requestModel.get(0).getNextDestination())) {
         requestModel.get(0).setContainerDestination(containerDestination);
      }
      return requestModel;
   }

   @Override
   public List<MailbagOverviewDetail> updateLocation(List<MailbagOverviewDetail> requestModel) throws CustomException {
      List<MailbagOverviewDetail> updateLocationModel = requestModel.stream()
            .filter(obj -> (Action.UPDATE.toString().equalsIgnoreCase(obj.getFlagCRUD()))).collect(Collectors.toList());
      if (!CollectionUtils.isEmpty(updateLocationModel)) {

         this.mailbagOverviewInfoDao.updateLocation(updateLocationModel);
         if (!StringUtils.isEmpty(updateLocationModel.get(0).getContainerDestination())) {
            updateLocationModel
                  .forEach(obj -> obj.setNextDestination(updateLocationModel.get(0).getContainerDestination()));
            mailbagOverviewInfoDao.updateNextDestination(updateLocationModel);
         }

      }
      return requestModel;

   }

   @Override
   public MailbagOverviewSummary updateMultipleMailbagLocationMobile(MailbagOverviewSummary requestModel)
         throws CustomException {
      updateLocation(requestModel.getMailbagDetails());
      return requestModel;

   }

   @Override
   public Object getMailBagDetail(MailbagSearchReq mailbagSearchReq) throws CustomException {
      mailbagSearchReq.setShipmentNumber(mailbagSearchReq.getMailbagNumber().substring(0, 20));
      mailbagSearchReq.setShipmentDate(shipmentProcessorService.getShipmentDate(mailbagSearchReq.getShipmentNumber()));
      List<MailbagOverviewSummary> listMailBagDetail = mailbagOverviewInfoDao
            .getMailbagOverviewDetailsMobile(mailbagSearchReq);
      MailbagOverviewSummary summaryDamage = mailbagOverviewInfoDao
            .getMailBagOverDamageLineItemDetails(mailbagSearchReq);

      if (CollectionUtils.isEmpty(listMailBagDetail)) {
         mailbagSearchReq.addError("sats.mail.bag.not.acc.yet", null, ErrorType.ERROR);
         throw new CustomException();
      } else {
         if (summaryDamage != null) {
            listMailBagDetail.get(0).getMailbagDetails().get(0).setSeverity(summaryDamage.getSeverity());
            listMailBagDetail.get(0).getMailbagDetails().get(0).setDamageFlag(true);
            listMailBagDetail.get(0).getMailbagDetails().get(0).setNatureOfDamage(summaryDamage.getNatureOfDamage());
         }
         listMailBagDetail.get(0)
               .setShipmentHouseID(listMailBagDetail.get(0).getMailbagDetails().get(0).getShipmentHouseId());
         if (mailbagOverviewInfoDao.getEmbargoFailureDetails(listMailBagDetail.get(0)) != null) {
            listMailBagDetail.get(0).getMailbagDetails().get(0).setEmbargoFlag("Y");
         } else {
            listMailBagDetail.get(0).getMailbagDetails().get(0).setEmbargoFlag("N");
         }
         return listMailBagDetail.get(0).getMailbagDetails().get(0);
      }
   }

   @Override
   @Transactional(rollbackFor = CustomException.class)
   public MailbagOverviewDetail saveUpdateMailBagMobile(MailbagOverviewDetail mailbagOverviewDetail)
         throws CustomException {

      // X-Ray Operations
      performXrayOperation(mailbagOverviewDetail);

      // updating location
      if ((!StringUtils.isEmpty(mailbagOverviewDetail.getStoreLocation()) && !mailbagOverviewDetail.getStoreLocation()
            .equalsIgnoreCase(mailbagOverviewDetail.getExistingShipmentLocation()))
            || (!StringUtils.isEmpty(mailbagOverviewDetail.getWarehouseLocation()) && !mailbagOverviewDetail
                  .getWarehouseLocation().equalsIgnoreCase(mailbagOverviewDetail.getExistingWarehouseLocation()))) {
         mailbagOverviewInfoDao.updateLocationRecord(mailbagOverviewDetail);
      }
      /* update damage */

      performDamageOperations(mailbagOverviewDetail);

      // embargo operations
      performEmbargoOperations(mailbagOverviewDetail);

      return mailbagOverviewDetail;

   }

   private void performXrayOperation(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      if (StringUtils.isEmpty(mailbagOverviewDetail.getXrayResultFlag())) {
         mailbagOverviewInfoDao.deleteXrayIfNoStatus(mailbagOverviewDetail);
      } else {
         if (PASS.equalsIgnoreCase(mailbagOverviewDetail.getXrayResultFlag())) {
            mailbagOverviewDetail.setScStatus(OK);
            mailbagOverviewDetail.setScreenedMethod("03");
         } else if (FAIL.equalsIgnoreCase(mailbagOverviewDetail.getXrayResultFlag())) {
            mailbagOverviewDetail.setScreenedMethod("04");
         } else if (PENDING.equalsIgnoreCase(mailbagOverviewDetail.getXrayResultFlag())) {
            mailbagOverviewDetail.setScreenedMethod("00");
         } else if (StringUtils.isEmpty(mailbagOverviewDetail.getXrayResultFlag())) {
            mailbagOverviewDetail.setScStatus("");
         } else {
            mailbagOverviewDetail.setScStatus(CALL_CUSTOMS);
         }

         int updateResultXray = mailbagOverviewInfoDao.updateMailbagXray(mailbagOverviewDetail);
         if (updateResultXray == 0) {
            mailbagOverviewInfoDao.createMailbagXrayRecord(mailbagOverviewDetail);
         }

      }
   }

   private void performDamageOperations(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      MailbagOverviewDetail detail = mailbagOverviewInfoDao.checkMailBagOverViewMobile(mailbagOverviewDetail);
      if (detail == null) {
         if (mailbagOverviewDetail.getDamageFlag() != null && mailbagOverviewDetail.getDamageFlag()) {
            mailbagOverviewInfoDao.createMailDamage(mailbagOverviewDetail);
            MailbagOverviewDetail details = mailbagOverviewInfoDao.checkMailBagOverViewMobile(mailbagOverviewDetail);
            mailbagOverviewDetail.setDamageInfoId(details.getDamageInfoId());
            mailbagOverviewInfoDao.createMailDamageLineItem(mailbagOverviewDetail);
         }
      } else {
         if (mailbagOverviewDetail.getDamageFlag() != null && mailbagOverviewDetail.getDamageFlag()) {
            mailbagOverviewInfoDao.updateMailDamage(mailbagOverviewDetail);
            mailbagOverviewInfoDao.updateMailDamageLineItem(mailbagOverviewDetail);
         } else if (mailbagOverviewDetail.getDamageFlag() != null && !mailbagOverviewDetail.getDamageFlag()) {
            mailbagOverviewInfoDao.deleteMailDamage(mailbagOverviewDetail);
            mailbagOverviewInfoDao.deleteMailDamageLineItem(mailbagOverviewDetail);
         }
      }
   }

   private void performEmbargoOperations(MailbagOverviewDetail mailbagOverviewDetail) throws CustomException {
      MailbagOverviewDetail embargoDetails = mailbagOverviewInfoDao.checkEmbargoDetailMobile(mailbagOverviewDetail);

      if (embargoDetails == null) {
         if (mailbagOverviewDetail.getEmbargoFlag() != null && mailbagOverviewDetail.getEmbargoFlag().equals("Y")) {
            mailbagOverviewInfoDao.createEmbargoFailure(mailbagOverviewDetail);
         }
      } else {
         if (mailbagOverviewDetail.getEmbargoFlag() != null
               && "N".equalsIgnoreCase(mailbagOverviewDetail.getEmbargoFlag())) {
            mailbagOverviewInfoDao.deleteEmbargoFailure(mailbagOverviewDetail);
         }
      }
   }

   @Override
   public String getCarrierCodeForAMailBag(BigInteger shipmentId) throws CustomException {

      return mailbagOverviewInfoDao.getCarrierCodeForAMailBag(shipmentId);
   }

   @Override
   public AllStatusOfMailBag getAllStatusOfTheMailBag(AllStatusOfMailBag status) throws CustomException {
      return mailbagOverviewInfoDao.getAllStatusOfTheMailBag(status);
   }

}