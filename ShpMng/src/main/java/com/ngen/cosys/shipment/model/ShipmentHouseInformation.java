package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentHouseInformation extends BaseBO {

	private static final long serialVersionUID = 1L;
	private String houseId;
	@NgenAuditField(fieldName = "hwbNumber")
	private String hwbNumber;
	@NgenAuditField(fieldName = "hwbOrigin")
	private String hwbOrigin;
	@NgenAuditField(fieldName = "hwbDestination")
	private String hwbDestination;
	@NgenAuditField(fieldName = "hwbPieces")
	private BigInteger hwbPieces;
	@NgenAuditField(fieldName = "hwbWeight")
	private BigDecimal hwbWeight;
	@NgenAuditField(fieldName = "hwbChgWeight")
	private String hwbChgWeight;
	@NgenAuditField(fieldName = "hwbNatureOfGoods")
	private String hwbNatureOfGoods;
	@NgenAuditField(fieldName = "hwbSHC")
	private ArrayList<String> hwbSHC;
}
