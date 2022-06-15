package com.ngen.cosys.satssginterfaces.mss.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@Validated
public class SHCAirmailInterfaceModel extends BaseBO {

	private static final long serialVersionUID = 1L;

	
	private String specialHandlingCode;
	
	//@CheckShipmentNumberConstraint(message="ShipmentNumber can not be blank",mandatory = MandatoryType.Type.REQUIRED)
	private String bhShipmentNumber;

}