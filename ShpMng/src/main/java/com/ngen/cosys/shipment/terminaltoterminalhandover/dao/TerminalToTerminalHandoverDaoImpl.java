package com.ngen.cosys.shipment.terminaltoterminalhandover.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.HandoverTerminalShp;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchGroup;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchKey;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPoint;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPointDetails;
import com.ngen.cosys.shipment.terminaltoterminalhandover.queryconfig.QueryConfig;

@Repository
public class TerminalToTerminalHandoverDaoImpl extends BaseDAO implements TerminalToTerminalHandoverDao {

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;

	@Override
	public BaseBO getDetailsOfShipmentFromTerminal(SearchKey searchKey) throws CustomException {
		// TODO Auto-generated method stub
		TerminalPointDetails terminalPointDetails = new TerminalPointDetails();

		List<HandoverTerminalShp> handoverTerminalShpList;

		if (!ObjectUtils.isEmpty(handoverTerminalShpList = super.fetchList(QueryConfig.GET_SHPDETAILSATTERMINAL,
				searchKey, sqlSession))) {
			terminalPointDetails.setHandoverTerminalShpList(handoverTerminalShpList);
			return terminalPointDetails;
		}
		return terminalPointDetails;
	}

	@Override
	public BaseBO transferShipmentToTerminal(SearchGroup transferShipments) throws CustomException {
		// TODO Auto-generated method stub
		List<TerminalPoint> transferShipment = transferShipments.getTerminalPointList();
		if (!ObjectUtils.isEmpty(
				super.insertData(QueryConfig.INSERT_TRANSFERSHIPMENTTOTERMINAL, transferShipment, sqlSession))) {

			return transferShipments;
		}
		return transferShipments;
	}

	@Override
	public BaseBO getDetailsOfShipmentToTerminal(SearchGroup searchingGroup) throws CustomException {
		// TODO Auto-generated method stub

		List<TerminalPoint> terminalPointList = super.fetchList(QueryConfig.GET_DETAILSOFSHIPMENTTOTERMINAL,
				searchingGroup, sqlSession);

		searchingGroup.setTerminalPointList(terminalPointList);
		return searchingGroup;

	}

	@Override
	public void updateShipmentPhysicalLocation(TerminalPoint terminalPoint) throws CustomException {
		super.updateData(QueryConfig.UPDATE_SHIPMENT_LOCATION, terminalPoint, sqlSession);
	}

	@Override
	public void updateUldPhysicalLocation(TerminalPoint terminalPoint) throws CustomException {
		super.updateData(QueryConfig.UPDATE_ULD_LOCATION, terminalPoint, sqlSession);
	}
}
