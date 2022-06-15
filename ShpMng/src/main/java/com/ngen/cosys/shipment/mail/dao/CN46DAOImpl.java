/**
 * 
 * CN46DAOImpl.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason v0.3 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.constant.ErrorType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.mail.model.CN46Details;
import com.ngen.cosys.shipment.mail.model.CreateCN46;

/**
 * This class interacts with CN 46 tables.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 *
 */
@Repository("cn46DAO")
public class CN46DAOImpl extends BaseDAO implements CN46DAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSessionShipment;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.dao.CN46DAO#getFlightId(com.ngen.cosys.shipment.
	 * mail.model.CreateCN46)
	 */
	@Override
	public CreateCN46 getFlightId(CreateCN46 request) throws CustomException {
		CreateCN46 cn = new CreateCN46();
		cn.setFlightId(super.fetchObject("getFlightId", request, sqlSessionShipment));
		return cn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.dao.CN46DAO#searchCN46Details(com.ngen.cosys.
	 * shipment.mail.model.CreateCN46)
	 */
	@Override
	public CreateCN46 searchCN46Details(CreateCN46 request) throws CustomException {
		CreateCN46 cn = new CreateCN46();
		cn = super.fetchObject("searchCN46Details", request, sqlSessionShipment);
		return cn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.dao.CN46DAO#insertCN46Request(com.ngen.cosys.
	 * shipment.mail.model.CreateCN46)
	 */
	@Override
//	@NgenAuditAction(entityType = NgenAuditEntityType.CN46, eventName = NgenAuditEventType.CN46_CREATE)
	public CreateCN46 insertCN46Request(CreateCN46 request) throws CustomException {
		BigInteger outGoingFlightId = null;
		if (!StringUtils.isEmpty(request.getOutgoingFlightKey())
				&& Optional.ofNullable(request.getFlightDate()).isPresent()) {
			outGoingFlightId = fetchObject("getOutgoingFlightIdForCn46", request, sqlSessionShipment);
			if (!Optional.ofNullable(outGoingFlightId).isPresent()) {
				throw new CustomException("invalid.export.flight", "", ErrorType.ERROR);
			}
		}
		request.setOutgoingFlightId(outGoingFlightId);
		int update = super.updateData("updateAirmailManifest", request, sqlSessionShipment);
		if (update == 0) {
			super.insertData("insertCN46", request, sqlSessionShipment);
		}
		return request;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.dao.CN46DAO#insertCN46RequestDetails(java.util.
	 * List)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.CN46_CREATE)
	public CreateCN46 insertCN46RequestDetails(List<CN46Details> request) throws CustomException {
		CreateCN46 cn = new CreateCN46();
		for (CN46Details e : request) {
			int count = super.fetchObject("checkDnNumber", e, sqlSessionShipment);
			if (count > 0) {
				cn.setDnNumberCount(count);
			}
		}
		if (cn.getDnNumberCount() > 0) {
			throw new CustomException("CN46_05", "cn46Form", ErrorType.ERROR);
		} else {
			super.insertList("insertCN46Details", request, sqlSessionShipment);
		}
		return cn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.dao.CN46DAO#updateCN46Request(com.ngen.cosys.
	 * shipment.mail.model.CreateCN46)
	 */
//	@NgenAuditAction(entityType = NgenAuditEntityType.CN46, eventName = NgenAuditEventType.CN46_UPDATE)
	@Override
	public CreateCN46 updateCN46Request(CreateCN46 request) throws CustomException {
		CreateCN46 cn = new CreateCN46();
		super.updateData("updateAirmailManifest", request, sqlSessionShipment);
		return cn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.mail.dao.CN46DAO#updateCN46RequestDetails(java.util.
	 * List)
	 */
	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.CN46_CREATE)
	public CreateCN46 updateCN46RequestDetails(List<CN46Details> request) throws CustomException {
		CreateCN46 cn = new CreateCN46();
		super.updateData("updateAirmailManifestShipment", request, sqlSessionShipment);
		return cn;
	}

	@Override
	@NgenAuditAction(entityType = NgenAuditEntityType.MAILBAG, eventName = NgenAuditEventType.CN46_CREATE)
	public void deleteManifestShipments(CN46Details deleteValue) throws CustomException {
		deleteData("deleteAirmailManifestShipments", deleteValue, sqlSessionShipment);

	}

	@Override
	public int checkDNExistance(CreateCN46 requestModel) throws CustomException {

		return fetchObject("checkDNExistance", requestModel, sqlSessionShipment);
	}

	@Override
	public void deleteManifest(CreateCN46 requestModel) throws CustomException {
		deleteData("deleteManifestForAFlight", requestModel, sqlSessionShipment);
	}
	
	@Override
	public String getSegmentName(CreateCN46 requestModel) throws CustomException {
		return fetchObject("getSegmentName", requestModel, sqlSessionShipment);
	}
}
