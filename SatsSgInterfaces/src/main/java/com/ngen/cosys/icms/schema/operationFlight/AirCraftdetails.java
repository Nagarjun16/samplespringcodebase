package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class contains aircraft details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class AirCraftdetails implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message="Aircraft type code cannot be blank",groups = xmlValidator.class)
	@Size(max=6,message="Aircraft type code must not exceed 6 characters",groups = xmlValidator.class)
	private String airCraftTypeCode;
	private String airCraftTypeVersion;
	@Size(min=1,max=1,message="Service type must be 1 character",groups = xmlValidator.class)
	private String serviceType;

}
