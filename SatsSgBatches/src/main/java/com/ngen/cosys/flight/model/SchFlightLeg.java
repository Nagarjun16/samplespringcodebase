/**
 * SchFlightLeg.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 28 July, 2017 NIIT -
 */
package com.ngen.cosys.flight.model;

import java.math.BigInteger;
import java.time.LocalTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalTimeSerializer;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model Class SchFlightLeg.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)

public class SchFlightLeg extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	private long flightScheduleID;
	private short codLegOrder;
	@NotBlank(message = "g.board.point.not.empty")

	private String brdPt;
	@NotBlank(message = "g.off.point.not.empty")

	private String offPt;

	private boolean domesticLeg;
	@JsonSerialize(using = LocalTimeSerializer.class)

	private LocalTime arrTime;

	@JsonSerialize(using = LocalTimeSerializer.class)

	private LocalTime depTime;

	private byte dayChangeArr;

	private byte dayChangeDep;

	private String aircraftType;
	private boolean handledInSystem;

	// JV01
	@NgenAuditField(fieldName = NgenAuditFieldNameType.BU_BD_OFFICE)
	private String bubdOffice;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.WH_LEVEL)
	private String warehouseLevel;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.RHO)
	private BigInteger rho;

}