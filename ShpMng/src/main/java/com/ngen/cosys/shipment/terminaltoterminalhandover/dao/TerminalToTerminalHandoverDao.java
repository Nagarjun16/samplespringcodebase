package com.ngen.cosys.shipment.terminaltoterminalhandover.dao;

import org.springframework.stereotype.Repository;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchGroup;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.SearchKey;
import com.ngen.cosys.shipment.terminaltoterminalhandover.model.TerminalPoint;

@Repository
public interface TerminalToTerminalHandoverDao {

	BaseBO getDetailsOfShipmentFromTerminal(SearchKey searchKey) throws CustomException;

	BaseBO transferShipmentToTerminal(SearchGroup transferShipment) throws CustomException;

	BaseBO getDetailsOfShipmentToTerminal(SearchGroup searchingGroup) throws CustomException;

	void updateShipmentPhysicalLocation(TerminalPoint terminalPoint) throws CustomException;

	void updateUldPhysicalLocation(TerminalPoint terminalPoint) throws CustomException;
}
