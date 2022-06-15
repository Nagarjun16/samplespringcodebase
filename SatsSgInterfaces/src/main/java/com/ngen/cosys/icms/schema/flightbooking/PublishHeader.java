package com.ngen.cosys.icms.schema.flightbooking;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class PublishHeader {
	public String messageType;
	public String sourceSystem;
	public Date entityUpdateTime;
	public Date messageCreationTime;
}
