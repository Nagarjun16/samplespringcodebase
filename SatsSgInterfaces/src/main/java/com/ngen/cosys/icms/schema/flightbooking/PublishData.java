package com.ngen.cosys.icms.schema.flightbooking;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class PublishData {
	public int publishID;
	public String entity;
	public BookingDetails bookingDetails;
}
