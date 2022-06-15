package com.ngen.cosys.shipment.nawb.model;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@Getter
@Setter
@Component
@ToString
@ApiModel
@NoArgsConstructor
public class FlightBookingToList extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
}