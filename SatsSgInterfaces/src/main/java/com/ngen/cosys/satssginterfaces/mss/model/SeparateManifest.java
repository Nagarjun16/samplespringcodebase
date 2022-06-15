package com.ngen.cosys.satssginterfaces.mss.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class SeparateManifest extends BaseBO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Flight flight;
	private List<Long> manifestShipmentInfoIds;

}
