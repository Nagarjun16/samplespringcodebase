package com.ngen.cosys.shipment.house.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@XmlRootElement
@ToString
@ApiModel
@Setter
@Getter
@NoArgsConstructor
@NgenCosysAppAnnotation
public class HouseDimensionModel extends BaseBO{
	 /**
	    * Default generated serial version id
	    */
	   private static final long serialVersionUID = 1L;
	   private BigInteger id;
	   private BigInteger houseId;
	   private BigInteger houseDimensionId;
	   private BigInteger pieces;
	   private BigDecimal weight;
	   @NgenAuditField(fieldName=NgenAuditFieldNameType.DIMENSION)
	   private List<HouseDimensionDetailsModel> dimensionList;
	   private BigInteger totalDimpieces;
	   private Integer densityGroupCode;
	   private String volumeUnitCode;
	   private String unitCode;
	   private BigDecimal volume;
	   @NgenAuditField(fieldName = "Volume")
	   private BigDecimal volumeWeight;
	   private BigDecimal volumetricWeight;
}
