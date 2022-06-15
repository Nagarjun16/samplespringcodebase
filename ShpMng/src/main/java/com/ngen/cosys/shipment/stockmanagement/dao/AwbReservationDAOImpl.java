package com.ngen.cosys.shipment.stockmanagement.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.audit.NgenAuditAction;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservation;
import com.ngen.cosys.shipment.stockmanagement.model.AwbReservationSearch;

@Repository
public class AwbReservationDAOImpl extends BaseDAO implements AwbReservationDAO {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.stockmanagement.dao.AwbReservationDAO#
	 * searchNextAwbNumber(com.ngen.cosys.shipment.stockmanagement.model.
	 * AwbReservationSearch)
	 */
	@Override
	public AwbReservationSearch searchNextAwbNumber(AwbReservationSearch search) throws CustomException {
		return fetchObject("searchNextAwbNumberForReservation", search, sqlSession);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ngen.cosys.shipment.stockmanagement.dao.AwbReservationDAO#save(com.ngen.
	 * cosys.shipment.stockmanagement.model.AwbReservation)
	 */
	@Override
	public AwbReservation save(AwbReservation search) throws CustomException {
		int checkExistsance = fetchObject("checkawbReservationExistance", search, sqlSession);
		if (checkExistsance > 0) {
			updateAwbReservation(search);
		} else {
			insertAwbReservation(search);
		}
		return search;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ngen.cosys.shipment.stockmanagement.dao.AwbReservationDAO#
	 * fetchAwbReservationDetails(com.ngen.cosys.shipment.stockmanagement.model.
	 * AwbReservationSearch)
	 */
	@Override
	public List<AwbReservation> fetchAwbReservationDetails(AwbReservationSearch search) throws CustomException {
		return fetchList("getAwbReservationDetails", search, sqlSession);
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_RESERVATION)
	public void updateAwbReservation(AwbReservation search) throws CustomException {
		updateData("updateAwbReservation", search, sqlSession);
	}

	public void insertAwbReservation(AwbReservation search) throws CustomException {
		int count = updateData("updateStockDetailsForReservation", search, sqlSession);
		if (count == 1) {
			doInsertAwbReservation(search);
		}
	}

	@NgenAuditAction(entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.AWB_RESERVATION)
	public void doInsertAwbReservation(AwbReservation search) throws CustomException {
		insertData("insertAwbReservationDetails", search, sqlSession);
	}

}