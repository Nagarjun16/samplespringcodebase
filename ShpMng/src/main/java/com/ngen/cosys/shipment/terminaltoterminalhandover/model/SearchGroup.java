package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SearchGroup extends BaseBO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5127906657749669036L;
	private TerminalPoint terminalPoint;
	//private SubscriberDetails subscriberDetails;
	//private ReceipentDetails receipent;
	private List<TerminalPoint> terminalPointList;
}