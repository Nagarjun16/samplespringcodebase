package com.ngen.cosys.satssginterfaces.mss.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class UnloadShipmentsInfo {

	private List<UnloadShipment> unloadShipments;
	
}