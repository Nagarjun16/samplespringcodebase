package com.ngen.cosys.shipment.terminaltoterminalhandover.service;

import org.springframework.stereotype.Service;

import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.framework.model.BaseBO;

@Service
public interface TerminalToTerminalHandoverService {



	BaseBO getDetailsOfShipmentFromTerminal(BaseBO searchKey) throws CustomException;

	BaseBO transferShipmentToTerminal(BaseBO transferShipment) throws CustomException;

	BaseBO getDetailsOfShipmentToTerminal(BaseBO searchingGroup) throws CustomException;

}
