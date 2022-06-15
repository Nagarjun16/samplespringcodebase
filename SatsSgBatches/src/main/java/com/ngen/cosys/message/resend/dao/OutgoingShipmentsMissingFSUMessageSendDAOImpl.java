/**
 * Repository implementation component which has methods to get information from DB
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.events.payload.OutboundShipmentFlightCompletedStoreEvent;
import com.ngen.cosys.events.payload.OutboundShipmentManifestedStoreEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.resend.model.OutgoingShipmentsMissingFSURCSMessageSendModel;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.context.TenantContext;

@Repository
public class OutgoingShipmentsMissingFSUMessageSendDAOImpl extends BaseDAO
		implements OutgoingShipmentsMissingFSUMessageSendDAO {

	@Autowired
	@Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
	private SqlSession sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.message.resend.dao.OutgoingShipmentsMissingFSUMessageSendDAO#
	 * getManifestCompleteShipments()
	 */
	@Override
	public List<OutboundShipmentManifestedStoreEvent> getManifestCompleteShipments() throws CustomException {
		return this.fetchList("sqlGetOutgoingShipmentsMissingForManifestedShipments", TenantContext.get().getTenantId(),
				sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.message.resend.dao.OutgoingShipmentsMissingFSUMessageSendDAO#
	 * getFlightCompletedShipments()
	 */
	@Override
	public List<OutboundShipmentFlightCompletedStoreEvent> getFlightCompletedShipments() throws CustomException {
		return this.fetchList("sqlGetOutgoingShipmentsMissingForFlightComplete", TenantContext.get().getTenantId(),
				sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.message.resend.dao.OutgoingShipmentsMissingFSUMessageSendDAO#
	 * getAcceptanceFinalizeShipmens()
	 */
	@Override
	public List<OutgoingShipmentsMissingFSURCSMessageSendModel> getAcceptanceFinalizeShipmens() throws CustomException {
		return this.fetchList("sqlGetOutgoingShipmentsMissingForAcceptedShipments", TenantContext.get().getTenantId(),
				sqlSession);
	}

}
