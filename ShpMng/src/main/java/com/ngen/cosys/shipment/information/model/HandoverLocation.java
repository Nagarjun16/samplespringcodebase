package com.ngen.cosys.shipment.information.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * To show images for handover location
 *
 */
@ApiModel
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class HandoverLocation {
	private String shipmentLocation;
	private String identityKeyForImage;

}
