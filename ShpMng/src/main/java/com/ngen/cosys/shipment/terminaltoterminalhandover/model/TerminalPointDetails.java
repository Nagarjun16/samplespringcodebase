package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TerminalPointDetails extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -613915321713148671L;
	private String purpose;
	private String shpNumber;
	private String loadingDetails;
	private Long shipmentId;
	private Long uldId;
	private ReceipentDetails receipentDetails;
	private SubscriberDetails subscriberDetails;
	private HandoverTerminalShp handoverTerminalShp;
	private List<HandoverTerminalShp> handoverTerminalShpList;
}