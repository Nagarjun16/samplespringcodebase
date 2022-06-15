package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.icms.schema.scheduleFlight.PublishMetaData;
import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains publish operational entity details
 */
@XmlRootElement(name = "publishEntity")
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class PublishOperationEntity implements Serializable {
 
    private static final long serialVersionUID = 1L;
    private String publishID;
	@NotNull(message="Entity cannot be null",groups = xmlValidator.class)
    @Size(min = 0, max = 17, message = "Entity values should not exceed 17 characters",groups = xmlValidator.class)
    private String entity;
    @Valid
    @NotNull(message="Publish metadata cannot be null",groups = xmlValidator.class)
    private PublishMetaData publishMetaData;
    @Valid
    private PublishOperationData publishData;

}
