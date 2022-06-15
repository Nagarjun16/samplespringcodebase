package com.ngen.cosys.shipment.nawb.model;


import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.nawb.enums.RateDescriptionOtherInfoForNawbType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@Getter
@Setter
@Component
@ToString
@ApiModel
@NoArgsConstructor
public class RateDescriptionOtherInfoForNawb extends BaseBO {
	private static final long serialVersionUID = 1L;
	
	/**
	* Commodity Properties & Desc
	*	NC - Consolidation
	*	ND - Dimension
	*	NG - Nature of Goods
	*	NH - Harmonised Code
	*	NO - Country Of Origin of Goods
	*	NS - Shippers Load and Count
	*	NU - ULD
	*	NV - Volume
	* **/

	
	private String type;
	
	// NC - Consolidation 	NG - Nature of Goods
	private String natureOfGoodsDescription;
	
	// ND - Dimension
	private String measurementUnitCode;
	private String dimensionLength;
	private String dimensionWIdth;
	private String dimensionHeight;
	private String numberOfPieces;
	
	// NH - Harmonised Code
	private String harmonisedCommodityCode;
	
	// NO - Country Of Origin of Goods
	private String countryCode;
	
	// NS - Shippers Load and Count
	private String slacCount;
	
	// NU - ULD
	private String uldNumber;
	
	// NV - Volume
	private String volumeUnitCode;
	private String volumeAmount;
	
}