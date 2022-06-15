package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.ngen.cosys.icms.schema.scheduleFlight.NodeOperation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class contains operational flag details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
public class OperationFlag implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "node-operation")
    List<NodeOperation> nodeLst;
	

}
