package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.STDETDDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckDlsCompleteConstraint;
import com.ngen.cosys.validator.annotations.CheckManifestCompleteConstraint;

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
@CheckManifestCompleteConstraint(flightKey = "flightKey", flightDate = "flightOriginDate", tenantId = "tenantId")
@CheckDlsCompleteConstraint(flightKey = "flightKey", flightDate = "flightOriginDate", tenantId = "tenantId")
public class BuildupLoadShipment extends BaseBO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private String flightKey;
	private BigInteger flightId;
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate flightOriginDate;
	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime std;
	@JsonSerialize(using = STDETDDateTimeSerializer.class)
	private LocalDateTime etd;
	private String assUldTrolleyNo;
	private BigInteger assUldTrolleyId;
	private String contentCode;
	private String heightCode;
	private Integer phcIndicator;
	private String loadType;
	private BigInteger segmentId;
	private boolean errorFlag;
	private boolean mailBagLoading;
	private boolean warningFlag;
	private boolean infoFlag;

	private List<UldShipment> loadedShipmentList;

	//private RuleShipmentExecutionDetails ruleShipmentExecutionDetails;

}
