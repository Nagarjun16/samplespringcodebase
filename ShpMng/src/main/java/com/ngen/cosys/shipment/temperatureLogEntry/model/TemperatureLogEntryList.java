package com.ngen.cosys.shipment.temperatureLogEntry.model;


import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@NoArgsConstructor
public class TemperatureLogEntryList extends BaseBO{

	private static final long serialVersionUID = 1L;
	private List<TemperatureLogEntry> temperatureLogEntryList1;
	
}
