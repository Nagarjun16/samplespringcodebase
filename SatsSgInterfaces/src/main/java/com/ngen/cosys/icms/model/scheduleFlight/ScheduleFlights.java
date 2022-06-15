package com.ngen.cosys.icms.model.scheduleFlight;
import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleFlights extends BaseBO {

	private static final long serialVersionUID = 1L;
	private String carrierCode;
	private String flightNumber;
	private LocalDate effectiveFromDate;
	private LocalDate effectiveToDate;
	private String frequency;
	private String status;
}
