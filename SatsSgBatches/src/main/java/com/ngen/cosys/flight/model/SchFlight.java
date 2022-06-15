/**
 * SchFlight.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Model Class - SchFlight.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@SuppressWarnings("unused")
@ApiModel
@XmlRootElement
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Scope(scopeName = "prototype")

public class SchFlight extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	private long flightSchedulePeriodID;
	private long flightScheduleID;
	private short codFrqNum;

	private boolean monFlg;

	private boolean tueFlg;

	private boolean wedFlg;

	private boolean thurFlg;

	private boolean friFlg;

	private boolean satFlg;

	private boolean sunFlg;

	private boolean monJntFlg;

	private boolean tueJntFlg;

	private boolean wedJntFlg;

	private boolean thuJntFlg;

	private boolean friJunFlg;

	private boolean satJntFlg;

	private boolean sunJntFlg;

	private boolean ssmFlag;

	private String flightServiceType;

	private String description;

	private String flightType;

	private List<SchFlightLeg> schFlightLegs;

	private List<SchFlightSeg> schFlightSegments;

	private List<ScheduleFlightFact> factList;

	private List<SchFlightJoint> schFlightJointList;
	private String isCreated;
}