package com.ngen.cosys.shipment.terminaltoterminalhandover.service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.common.Temp;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.framework.model.ErrorBO;
import com.ngen.cosys.shipment.terminaltoterminalhandover.dao.TerminalToTerminalHandoverDao;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.ReceipentDetails;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchGroup;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchKey;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SubscriberDetails;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPoint;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPointDetails;

@Service(value = "terminalToTerminalHandoverServiceImpl")
public class TerminalToTerminalHandoverServiceImpl extends Temp<Object, BaseBO>
      implements TerminalToTerminalHandoverService {

   private static final String T2THO01 = "TerminalToTerminalHandOver01";

   private static final String T2THO02 = "TerminalToTerminalHandOver02";

   private static final String T2THO03 = "TerminalToTerminalHandOver03";

   private static final String T2THO04 = "TerminalToTerminalHandOver04";

   private static final String EXCEPTION = "Exception Happened ... ";

   private static final Logger lOgger = LoggerFactory.getLogger(TerminalToTerminalHandoverService.class);

   @Autowired
   private TerminalToTerminalHandoverDao handoverDao;

   private boolean exceptionFlag = false;

   @Override
   public void run() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
         InvocationTargetException, NoSuchMethodException, SecurityException {
      this.request((BaseBO) this.getClass().getMethod(this.getM(), BaseBO.class).invoke(this, this.e));
   }

   @Override
   public BaseBO getDetailsOfShipmentFromTerminal(BaseBO searchKey) throws CustomException {
      setUldAWB((SearchKey) searchKey);
      if (this.exceptionFlag) {
         this.exceptionFlag = false;
         return searchKey;
      }
      BaseBO baseBo = handoverDao.getDetailsOfShipmentFromTerminal(((SearchKey) setUldAWB((SearchKey) searchKey)));
      if (baseBo != null) {
         return baseBo;
      }
      return baseBo;
   }

   private BaseBO setUldAWB(final SearchKey searchKey) {
      if (searchKey.getAwbUldnumber() != null) {
         if (searchKey.getAwbUldnumber().matches("^[0-9]{11}")) {
            searchKey.setAwbnumber(searchKey.getAwbUldnumber());
            searchKey.setUldnumber(null);
         } else {
            searchKey.setUldnumber(searchKey.getAwbUldnumber());
            searchKey.setAwbnumber(null);
         }
      } else {
         searchKey.addError(T2THO04, null, ErrorType.ERROR);
      }
      return searchKey;
   }

   @Override
   public BaseBO transferShipmentToTerminal(BaseBO transferShipment) throws CustomException {
      BaseBO xfrShipment = checkProperty((SearchGroup) transferShipment);
      if (!xfrShipment.getMessageList().isEmpty()) {
         return xfrShipment;
      }

      BaseBO baseBo = handoverDao.transferShipmentToTerminal((SearchGroup) transferShipment);
      if (baseBo != null) {
         updateShipmentOrULDTerminal((SearchGroup) baseBo);
         return baseBo;
      }
      return baseBo;
   }

   private BaseBO updateShipmentOrULDTerminal(SearchGroup transferShipment) {

      transferShipment.getTerminalPointList().forEach(t -> {
         if (t.getTerminalPointDetails().getShipmentId() != null) {
            try {
               handoverDao.updateShipmentPhysicalLocation(t);
            } catch (CustomException e) {
               lOgger.error(EXCEPTION, e);
            }
         }
         if (t.getTerminalPointDetails().getUldId() != null) {
            try {
               handoverDao.updateUldPhysicalLocation(t);
            } catch (CustomException e) {
               lOgger.error(EXCEPTION, e);
            }
         }
      });
      return transferShipment;
   }

   @Override
   public BaseBO getDetailsOfShipmentToTerminal(BaseBO searchingGroup) throws CustomException {
      if (!this.compareDate(searchingGroup)) {
         searchingGroup.addError(T2THO01, null, ErrorType.ERROR);
         return searchingGroup;
      }

      if (!this.compareSenderReceiver(((SearchGroup) searchingGroup).getTerminalPoint())) {
         searchingGroup.addError(T2THO03, null, ErrorType.ERROR);
         return searchingGroup;
      }

      if (ObjectUtils.isEmpty(((SearchGroup) searchingGroup).getTerminalPoint().getFromTrml())) {
         ((SearchGroup) searchingGroup).getTerminalPoint().setFromTrml(null);
      }
      if (ObjectUtils.isEmpty(((SearchGroup) searchingGroup).getTerminalPoint().getToTrml())) {
         ((SearchGroup) searchingGroup).getTerminalPoint().setToTrml(null);
      }

      if (!this.compareTerminal(((SearchGroup) searchingGroup).getTerminalPoint())) {
         searchingGroup.addError(T2THO02, null, ErrorType.ERROR);
         return searchingGroup;
      }

      BaseBO baseBo = handoverDao.getDetailsOfShipmentToTerminal((SearchGroup) searchingGroup);
      if (baseBo != null) {
         return this.setValue((SearchGroup) baseBo);
      }
      return baseBo;
   }

   private BaseBO setValue(SearchGroup baseBo) {
      if (baseBo.getTerminalPointList().isEmpty()) {
         baseBo.addError("TerminalToTerminalHandOver06", null, ErrorType.ERROR);
      } else {
         baseBo.getTerminalPointList().forEach(t -> {
            if (!ObjectUtils.isEmpty(t.getTerminalPointDetails().getHandoverTerminalShp().getFlight())) {

               t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().setOnwardBookingDetails(
                     t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().toString());
               t.getTerminalPointDetails().getHandoverTerminalShp()
                     .setPiecesweight(t.getTerminalPointDetails().getHandoverTerminalShp().getPeicesAndWeight());
               t.getTerminalPointDetails().getHandoverTerminalShp()
                     .setOriginDestination(t.getTerminalPointDetails().getHandoverTerminalShp().getOrgDest());
            }

            // t.getTerminalPointDetails().getHandoverTerminalShp()
            // .setPiecesweight(null
            // + checkNull(null +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getPieces())
            // + checkNull(null +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getWeight()));
            //
            // t.getTerminalPointDetails().getHandoverTerminalShp().setOriginDestination(null
            // + checkNull(null +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getOrigin())
            // + checkNull(null +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getDestination()));
            // t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().setOnwardBookingDetails(null
            // + checkNull(null +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().getNumber())
            //
            // + checkNull(null +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().getDateSTD())
            // // + checkNull(null +
            // //
            // t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().getTime())
            // + checkNull(null
            // +
            // t.getTerminalPointDetails().getHandoverTerminalShp().getFlight().getDestination()));
         });
      }
      return baseBo;
   }

   private Boolean compareDate(BaseBO searchGroup) {
      boolean isComparisonSuccess = true;
      SearchGroup sg = (SearchGroup) searchGroup;
      TerminalPoint tp = sg.getTerminalPoint();
      if (tp != null) {
         TerminalPointDetails terminalPointDetails = tp.getTerminalPointDetails();
         if (terminalPointDetails != null) {
            SubscriberDetails subscriberDetails;
            ReceipentDetails receipentDetails;
            if ((subscriberDetails = terminalPointDetails.getSubscriberDetails()) != null
                  && (receipentDetails = terminalPointDetails.getReceipentDetails()) != null) {
               LocalDateTime fromDate = subscriberDetails.getHandedOverOn();
               LocalDateTime toDate = receipentDetails.getReceivedDate();
               if (fromDate != null && toDate != null) {
                  int comparedValue = fromDate.compareTo(toDate);
                  if (comparedValue > 0) {
                     isComparisonSuccess = false;
                  } /*else if (comparedValue == 0 || comparedValue < 0) {
                     isComparisonSuccess = true;
                  }*/
               }
            }
         }
      }
      return isComparisonSuccess;
   }

   private Boolean compareTerminal(BaseBO terminalPoint) {
      boolean isComparisonSuccess = true;
      TerminalPoint tp = (TerminalPoint) terminalPoint;
      if (tp != null && !(StringUtils.isEmpty(tp.getFromTrml()) || StringUtils.isEmpty(tp.getToTrml()))) {
         isComparisonSuccess = !tp.getFromTrml().equals(tp.getToTrml());
      }
      return isComparisonSuccess;
   }

   private Boolean compareSenderReceiver(BaseBO terminals) {
      boolean isComparisonSuccess = true;
      TerminalPoint tp = (TerminalPoint) terminals;
      if (!ObjectUtils.isEmpty(tp)) {
         TerminalPointDetails terminalPointDetails = tp.getTerminalPointDetails();
         if (ObjectUtils.isEmpty(terminalPointDetails)) {
            SubscriberDetails subscriberDetails = terminalPointDetails.getSubscriberDetails();
            ReceipentDetails receipentDetails = terminalPointDetails.getReceipentDetails();
            if (!ObjectUtils.isEmpty(subscriberDetails) && ObjectUtils.isEmpty(receipentDetails)) {
               isComparisonSuccess = !receipentDetails.getReceivedBy().equals(subscriberDetails.getHandedOverBy());
            }
         }
      }
      return isComparisonSuccess;
   }

   private BaseBO checkProperty(SearchGroup transferShipment) {
      transferShipment.getTerminalPointList().forEach(t -> {
         if (!this.compareTerminal(t)) {
            t.addError(T2THO02, null, ErrorType.ERROR);
            List<ErrorBO> messageList = new ArrayList<>();
            ErrorBO errorBO = new ErrorBO();
            errorBO.addError(T2THO02, null, ErrorType.ERROR);
            messageList.add(errorBO);
            transferShipment.setMessageList(messageList);
         }
         if (!this.compareSenderReceiver(t)) {
            t.addError(T2THO03, null, ErrorType.ERROR);
            List<ErrorBO> messageList = new ArrayList<>();
            ErrorBO errorBO = new ErrorBO();
            errorBO.addError(T2THO03, null, ErrorType.ERROR);
            messageList.add(errorBO);
            transferShipment.setMessageList(messageList);
         }
      });
      return transferShipment;
   }
}