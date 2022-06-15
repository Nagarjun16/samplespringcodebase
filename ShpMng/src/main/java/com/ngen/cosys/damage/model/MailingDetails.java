package com.ngen.cosys.damage.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MailingDetails{

	private String cc;
	private String bcc;
	private List<String> to;
	private String replyTo;
	private String subject;
	private String body;
	private String shipmentNumber;
	private String type;
	private String user;
	private String tenantAirportCode;
	private String flight;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate  flightdate;
	private List<UploadedFiles> uploadedFiles;
	private String entityKey;
	private String entityType;
	private String associatedTo;
	private String stage;
	private String subEntityKey;
	@InjectShipmentDate(shipmentNumberField = "shipmentNumber")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate entityDate; 

}
