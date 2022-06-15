/**
 * Repository implementation component which has methods to get information from DB
 */
package com.ngen.cosys.message.resend.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.events.payload.InboundShipmentBreakDownCompleteStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDeliveredStoreEvent;
import com.ngen.cosys.events.payload.InboundShipmentDocumentReleaseStoreEvent;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.context.TenantContext;

@Repository
public class IncomingShipmentsMissingFSUMessageSendDAOImpl extends BaseDAO
		implements IncomingShipmentsMissingFSUMessageSendDAO {

	@Autowired
	@Qualifier(BeanFactoryConstants.SESSION_TEMPLATE)
	private SqlSession sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.message.resend.dao.IncomingShipmentsMissingFSUMessageSendDAO#
	 * getBreakDownCompleteShipments()
	 */
	@Override
	public List<InboundShipmentBreakDownCompleteStoreEvent> getBreakDownCompleteShipments() throws CustomException {
		return this.fetchList("sqlGetIncomingShipmentsMissingForBreakDownComplete", TenantContext.get().getTenantId(),
				sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.message.resend.dao.IncomingShipmentsMissingFSUMessageSendDAO#
	 * getDocumentReleasedShipments()
	 */
	@Override
	public List<InboundShipmentDocumentReleaseStoreEvent> getDocumentReleasedShipments() throws CustomException {
		return this.fetchList("sqlGetIncomingShipmentsMissingForDocumentRelease", TenantContext.get().getTenantId(),
				sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.message.resend.dao.IncomingShipmentsMissingFSUMessageSendDAO#
	 * getDeliveredShipments()
	 */
	@Override
	public List<InboundShipmentDeliveredStoreEvent> getDeliveredShipments() throws CustomException {
		return this.fetchList("sqlGetIncomingShipmentsMissingForDelivered", TenantContext.get().getTenantId(),
				sqlSession);
	}

}