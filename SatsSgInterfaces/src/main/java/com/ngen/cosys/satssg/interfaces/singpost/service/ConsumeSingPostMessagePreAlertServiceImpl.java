/**
 * 
 * ConsumeSingPostMessagePreAlertServiceImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          25 May, 2018 NIIT      -
 */
package com.ngen.cosys.satssg.interfaces.singpost.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;
import com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao;
import com.ngen.cosys.satssg.interfaces.singpost.factory.enums.BookingStatusCode;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;
import com.ngen.cosys.satssg.interfaces.singpost.model.PullMailBagResponseModel;

/**
 * This consumer class takes care of the processing activities for the incoming
 * SINGPOST messages.
 * 
 * @author NIIT Technologies Ltd
 *
 */
// @Service(value = "preAlert")
@Service(value = "consumeSingPostMessageService")
@Primary
public class ConsumeSingPostMessagePreAlertServiceImpl implements ConsumeSingPostMessageService {

	private static final Logger lOGGER = LoggerFactory.getLogger(ConsumeSingPostMessageService.class);

	@Autowired
	private ConsumeSingPostMessageDao consumeSingPostMessageDao;

	@Autowired
	private AirmailStatusEventProducer producer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.service.
	 * ConsumeSingPostMessageService#pullMailBagStatus(com.ngen.cosys.satssg.
	 * interfaces.singpost.model.PullMailBagResponseModel)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public PullMailBagResponseModel pullMailBagStatus(PullMailBagResponseModel pullMailBagResponseModel)
			throws CustomException {
		pullMailBagResponseModel.setMailBag(processPADetail(pullMailBagResponseModel.getMailBag()));
		return pullMailBagResponseModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.service.
	 * ConsumeSingPostMessageService#processPASummary(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public List<MailBagResponseModel> processPASummary(List<MailBagResponseModel> paSummaryMailBags)
			throws CustomException {
		if (lOGGER.isInfoEnabled()) {
			lOGGER.info("Processing Incoming SINGPOST PA Summary message ===>\n", paSummaryMailBags.toString());
		}
		for (MailBagResponseModel bag : paSummaryMailBags) {
			bag.setBagWeight(BigDecimal.ZERO);
			bag.setOrigin(bag.getDispatchID().substring(2, 5));
			bag.setDestination(bag.getDispatchID().substring(8, 11));
			bag.setFlightBoardPoint(bag.getRecpID().substring(2, 5));
			bag.setFlightOffPoint(bag.getRecpID().substring(8, 11));
			consumeSingPostMessageDao.updateMailBagStatus(bag);
		}
		return paSummaryMailBags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.service.
	 * ConsumeSingPostMessageService#processPADetail(java.util.List)
	 */
	@Override
	public List<MailBagResponseModel> processPADetail(List<MailBagResponseModel> paDetailMailBags)
			throws CustomException {
		if (lOGGER.isInfoEnabled()) {
			lOGGER.info("Processing Incoming SINGPOST PA Detail message ===>\n", paDetailMailBags.toString());
		}
		for (MailBagResponseModel bag : paDetailMailBags) {
			if (StringUtils.isEmpty(bag.getCreatedBy())) {
				bag.setCreatedBy("SINGPOST");
			}
			bag.setShipmentNumber(bag.getRecpID().substring(0, 20));
			bag.setOrigin(bag.getRecpID().substring(2, 5));
			bag.setDestination(bag.getDestinationOE());
			if (Optional.ofNullable(bag.getBagWeight()).isPresent()) {
				bag.setBagWeight(bag.getBagWeight().divide(BigDecimal.TEN, 1, RoundingMode.CEILING));
			} else {
				bag.setBagWeight(BigDecimal.ZERO);
			}
			MailBagResponseModel oprFlt = consumeSingPostMessageDao.fetchFlightDetails(bag);
			if (!Optional.ofNullable(oprFlt).isPresent()) {
			   bag.addError("Invalid Flight: " + bag.getFlightNumber() + "/" + bag.getDepartureDateTime(), "",
                     ErrorType.ERROR);
				bag.setIsValidMb(false);
			} else {
				if (bag.getIsValidMb() && !StringUtils.isEmpty(bag.getRecpID())) {
					bag.setFlightBoardPoint(oprFlt.getFlightBoardPoint());
					bag.setFlightOffPoint(bag.getDestinationOE());
					bag.setFlightSegmentId(oprFlt.getFlightSegmentId());

					if (!Optional.ofNullable(bag.getTotalBagCount()).isPresent()) {
						bag.setTotalBagCount(Integer.toString(1));
					}
					bag.setFlightId(oprFlt.getFlightId());
					bag.setBookingStatusCode(BookingStatusCode.UU.toString());

					// MailBagResponseModel bookingID =
					// consumeSingPostMessageDao.getBookingIdForPADetail(bag);
					consumeSingPostMessageDao.updateMailBagStatusForPADetail(bag);
					consumeSingPostMessageDao.updateBagDetails(bag);
				}

			}
			consumeSingPostMessageDao.saveMailBagsForPostalAuthorities(bag);
		}
		boolean isFail = paDetailMailBags.stream().anyMatch(obj -> !obj.getIsValidMb());
		if (isFail) {
			throw new CustomException();
		}
		return paDetailMailBags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.service.
	 * ConsumeSingPostMessageService#processDLV(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public List<MailBagResponseModel> processDLV(List<MailBagResponseModel> dlvMailBags) throws CustomException {
		if (lOGGER.isInfoEnabled()) {
			lOGGER.info("Processing Incoming SINGPOST DLV message ===>\n", dlvMailBags.toString());
		}
		for (MailBagResponseModel bag : dlvMailBags) {
			bag.setFlightBoardPoint(bag.getRecpID().substring(0, 6));
			bag.setFlightOffPoint(bag.getRecpID().substring(6, 12));
			bag.setDestinationCountry(bag.getRecpID().substring(6, 8));
			consumeSingPostMessageDao.dlvMailBags(bag);
			AirmailStatusEvent event = new AirmailStatusEvent();
			event.setSourceTriggerType("SINGPOSTDLV");
			event.setMailBag(bag.getRecpID());
			AirmailStatusEvent getMailBagInfoForSingPostDLV = consumeSingPostMessageDao
					.getMailBagInfoForSingPostDLV(event);
			event.setFlightId(getMailBagInfoForSingPostDLV.getFlightId());
			event.setCarrierCode(getMailBagInfoForSingPostDLV.getCarrierCode());
			event.setNextDestination(getMailBagInfoForSingPostDLV.getNextDestination());
			event.setShipmentId(getMailBagInfoForSingPostDLV.getShipmentId());
			event.setShipmentNumber(getMailBagInfoForSingPostDLV.getShipmentNumber());
			event.setTenantId(TenantContext.getTenantId());
			event.setCreatedBy("SINGPOST");
			event.setCreatedOn(LocalDateTime.now());
			// producer.publish(event);
		}
		return dlvMailBags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.service.
	 * ConsumeSingPostMessageService#processIPSAA(java.util.List)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public List<MailBagResponseModel> processIPSAA(List<MailBagResponseModel> ipsAaMailBags) throws CustomException {
		if (lOGGER.isInfoEnabled()) {
			lOGGER.info("Processing Incoming SINGPOST IPS AA message ===>\n", ipsAaMailBags.toString());
		}
		for (MailBagResponseModel bag : ipsAaMailBags) {
			bag.setFlightBoardPoint(bag.getRecpID().substring(0, 6));
			bag.setFlightOffPoint(bag.getRecpID().substring(6, 12));
			bag.setDestinationCountry(bag.getRecpID().substring(6, 8));
			consumeSingPostMessageDao.insertIpsAA(bag);
		}
		return ipsAaMailBags;
	}
}