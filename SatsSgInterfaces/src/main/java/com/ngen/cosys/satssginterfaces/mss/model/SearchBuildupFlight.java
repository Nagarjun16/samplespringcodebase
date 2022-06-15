package com.ngen.cosys.satssginterfaces.mss.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchBuildupFlight extends Flight {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	//private List<ShipmentToBeLoaded> toBeLoadedList;
	private List<LoadedShipment> loadedUldList;
	private boolean error;
}
