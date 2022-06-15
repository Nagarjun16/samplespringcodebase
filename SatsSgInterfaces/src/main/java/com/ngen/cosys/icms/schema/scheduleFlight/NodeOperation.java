package com.ngen.cosys.icms.schema.scheduleFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * Model class contains node operation details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class NodeOperation implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "node-name")
    private String nodeName;
    @XmlElement(name = "operation-flag")
    private String operationFlag;


}
