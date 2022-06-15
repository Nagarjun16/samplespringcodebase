package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class contains publish meta data details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class PublishMetaData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Valid
	@XmlElementWrapper(name = "operation-flags")
	@XmlElement(name = "node-operation")
	@NotNull(message="node operation cannot be null",groups = xmlValidator.class)
	private List<NodeOperation> nodeLst;

}
