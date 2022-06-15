package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class Flight extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9221868189912138372L;
	private String number;
	private String destination;
	private String onwardBookingDetails;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime dateSTD;

	private String time;

	@Override
	public String toString() {

		String str = "";

		if (this.number != null) {
			str += this.number;
		}
		if (this.dateSTD != null) {
			str += " / " + this.dateSTD.format(DateTimeFormatter.ofPattern("ddMMMYYYY")).toString().split("T")[0]
					+ " / " + this.time;
		}
		if (this.destination != null) {
			str += " / " + this.destination;
		}

		return str;
	}

}