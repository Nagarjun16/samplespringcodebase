/**
 * 
 * TransferManifestServiceImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v1 14 April, 2017 NIIT -
 */
package com.ngen.cosys.impbd.mail.transfermanifest.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.events.payload.AirmailStatusEventParentModel;
import com.ngen.cosys.events.producer.AirmailStatusEventProducer;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.impbd.mail.transfermanifest.dao.TransferManifestDAO;
import com.ngen.cosys.impbd.mail.transfermanifest.model.SearchTransferManifestDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierDetails;
import com.ngen.cosys.impbd.mail.transfermanifest.model.TransferCarrierResponse;

/**
 * This class takes care of the Transfer Manifest services.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Service
public class TransferManifestServiceImpl implements TransferManifestService {

	@Autowired
	TransferManifestDAO transferManifestDao;

	@Autowired
	AirmailStatusEventProducer producer;

	private String form = "transferManifestForm";

	@Override
	public List<TransferCarrierResponse> fetchTransferManifestDetails(SearchTransferManifestDetails search)
			throws CustomException {

		if (search.getIncomingCarrier().equals(search.getTransferCarrier())) {
			throw new CustomException("airmail.transfer.manifest.error", form, ErrorType.ERROR);
		}

		return transferManifestDao.fetchTransferManifestDetails(search);
	}

	@Override
	@Transactional(rollbackFor = CustomException.class)
	public List<TransferCarrierDetails> saveTransferManifestDetails(List<TransferCarrierDetails> request)
			throws CustomException {
		if (!CollectionUtils.isEmpty(request)) {
			List<TransferCarrierDetails> distinctElements = request.stream()
					.filter(distinctByKey(p -> p.getShipmentId())).collect(Collectors.toList());
			transferManifestDao.updateLyingListOfShipments(distinctElements);
			for (TransferCarrierDetails mailInfo : request) {
				TransferCarrierDetails getTrm = genetareTrm(mailInfo);
				mailInfo.setTrmNumber(getTrm.getTrmNumber());
				mailInfo.setIssuedDate(getTrm.getIssuedDate());
				transferManifestDao.insertTransferManifestByMail(mailInfo);
				mailInfo.setTransTransferManifestByAwbId(mailInfo.getTransTransferManifestByAwbId());
				transferManifestDao.insertTransferManifestInfoByMail(mailInfo);
			}

		}

		return request;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	private TransferCarrierDetails genetareTrm(TransferCarrierDetails e) throws CustomException {
		String dbCount = transferManifestDao.getTrmCount(e);
		StringBuilder s2 = new StringBuilder("");
		int count = 6 - dbCount.length();
		while (s2.length() < count) {
			s2.append("0");
		}
		s2.append(dbCount);
		e.setTrmNumber(e.getIncomingCarrier() + s2.toString());
		e.setIssuedDate(LocalDateTime.now());
		return e;
	}

}
