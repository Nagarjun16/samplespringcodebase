/**
 * 
 * ConsumeSingPostMessageDaoImpl.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          25 May, 2018 NIIT      -
 */

package com.ngen.cosys.satssg.interfaces.singpost.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.events.payload.AirmailStatusEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.satssg.interfaces.singpost.model.MailBagResponseModel;

@Repository("consumeSingPostMessageDao")
public class ConsumeSingPostMessageDaoImpl extends BaseDAO implements ConsumeSingPostMessageDao {

	@Autowired
	@Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
	private SqlSession sqlSessionTemplate;

	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
	private SqlSession sqlSessionROI;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao#
	 * pullMailBagStatus(com.ngen.cosys.satssg.interfaces.singpost.model.
	 * MailBagResponseModel)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel insertMailBagStatus(MailBagResponseModel mailBagResponseModel) throws CustomException {
		if (StringUtils.isEmpty(mailBagResponseModel.getOrigin())) {
			mailBagResponseModel.setOrigin("SIN");
		}
		super.insertData("insertMailBagStatus", mailBagResponseModel, sqlSessionTemplate);
		return mailBagResponseModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao#
	 * fetchFlightDetails(com.ngen.cosys.satssg.interfaces.singpost.model.
	 * MailBagResponseModel)
	 */
	@Override
	public MailBagResponseModel fetchFlightDetails(MailBagResponseModel bag) throws CustomException {
		return fetchObject("fetchFlightDetails", bag, sqlSessionROI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao#
	 * validateMailBag(com.ngen.cosys.satssg.interfaces.singpost.model.
	 * MailBagResponseModel)
	 */
	@Override
	public MailBagResponseModel validateMailBag(MailBagResponseModel bag) throws CustomException {
		return super.fetchObject("validateMailBag", bag, sqlSessionTemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel insertBagDetails(MailBagResponseModel bag) throws CustomException {
		super.insertData("insertBagDetails", bag, sqlSessionTemplate);
		return bag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel insertBagDetailsShc(MailBagResponseModel bag) throws CustomException {
		super.insertData("insertBagDetailsShc", bag, sqlSessionTemplate);
		return bag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao#
	 * updateMailBagStatus(com.ngen.cosys.satssg.interfaces.singpost.model.
	 * MailBagResponseModel)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel updateMailBagStatus(MailBagResponseModel bag) throws CustomException {
		if (super.updateData("updateMailBagStatus", bag, sqlSessionTemplate) == 0) {
			if (StringUtils.isEmpty(bag.getOrigin())) {
				bag.setOrigin(bag.getTenantAirport());
			}
			super.insertData("insertMailBagStatus", bag, sqlSessionTemplate);
		}
		return bag;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.satssg.interfaces.singpost.dao.ConsumeSingPostMessageDao#
	 * updateBagDetails(com.ngen.cosys.satssg.interfaces.singpost.model.
	 * MailBagResponseModel)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel updateBagDetails(MailBagResponseModel bag) throws CustomException {
		if (super.updateData("updateBagDetails", bag, sqlSessionTemplate) == 0) {
			super.insertData("insertBagDetails", bag, sqlSessionTemplate);
			super.insertData("insertBagDetailsShc", bag, sqlSessionTemplate);
		}
		return bag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel insertIpsAA(MailBagResponseModel bag) throws CustomException {
		super.insertData("insertIpsAA", bag, sqlSessionTemplate);
		return bag;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel dlvMailBags(MailBagResponseModel bag) throws CustomException {
		super.updateData("updatedlvMailBags", bag, sqlSessionTemplate);
		return bag;
	}

	@Override
	public MailBagResponseModel getBookingIdForPADetail(MailBagResponseModel requestModel) throws CustomException {
		return fetchObject("validateMailBagForDetail", requestModel, sqlSessionTemplate);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel updateMailBagStatusForPADetail(MailBagResponseModel requestModel)
			throws CustomException {
		if (updateData("updateMailBagStatusForPADetail", requestModel, sqlSessionTemplate) == 0) {
			if (StringUtils.isEmpty(requestModel.getOrigin())) {
				requestModel.setOrigin(requestModel.getTenantAirport());
			}
			insertData("insertMailBagStatusForPADetail", requestModel, sqlSessionTemplate);
		} else {
			requestModel.setBookingId(fetchObject("validateMailBagForDetail", requestModel, sqlSessionTemplate));
		}
		return requestModel;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public MailBagResponseModel insertMailBagStatusForPADetail(MailBagResponseModel requestModel)
			throws CustomException {
		insertData("insertMailBagStatusForPADetail", requestModel, sqlSessionTemplate);
		return requestModel;
	}

	@Override
	public AirmailStatusEvent getMailBagInfoForSingPostDLV(AirmailStatusEvent event) throws CustomException {
		return fetchObject("getMailBagInfoForSingPostDLV", event, sqlSessionTemplate);
	}

	@Override
	public void saveMailBagsForPostalAuthorities(MailBagResponseModel bag) throws CustomException {
       if (bag.getIsValidMb()) {
          bag.setValidFlight(true);
       } else {
          Integer validFlight = fetchObject("sqlCheckValidFlightForPostalAUthorities", bag, sqlSessionTemplate);
          if (!ObjectUtils.isEmpty(validFlight) && validFlight > 0) {
             bag.setValidFlight(true);
             bag.setIsValidMb(true);
          }
       }
       insertData("saveMailBagsForPostalAuthorities", bag, sqlSessionTemplate);
		
	}
}