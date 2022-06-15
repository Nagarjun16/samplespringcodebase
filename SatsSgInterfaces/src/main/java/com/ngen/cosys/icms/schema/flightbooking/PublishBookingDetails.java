package com.ngen.cosys.icms.schema.flightbooking;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;




import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
@ToString
@Component
public class PublishBookingDetails {
	private PublishHeader publishHeader;
	private PublishData publishData;
}
