package com.ngen.cosys.shipment.nawb.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

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
public class CommodityChargesList extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String rateLineNumber;
	private String numberOfPieces;
	private String weightUnitCode;
	private String grossWeight;
	private String rateClassCode;
	private String commodityItemNo;
	private String chargeableWeight;
	private String rateChargeAmount;
	private String totalChargeAmount;
	private List<String> natureOfGoodsList;
	private List<RateDescriptionOtherInfoForNawb> rateDescriptionOtherInfoForNawb;
}