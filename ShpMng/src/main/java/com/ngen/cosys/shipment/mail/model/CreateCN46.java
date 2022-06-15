/**
 * 
 * CreateCN46.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 14 April, 2018 NIIT -
 */
package com.ngen.cosys.shipment.mail.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAudits;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the class for creating a CN 46 Request.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
@NgenAudits({ @NgenAudit(eventName = NgenAuditEventType.CN46_CREATE, repository = NgenAuditEventRepository.CN46, entityFieldName = "CN46", entityType = NgenAuditEntityType.CN46),
@NgenAudit(eventName = NgenAuditEventType.CN46_UPDATE, repository = NgenAuditEventRepository.CN46, entityFieldName = "CN46", entityType = NgenAuditEntityType.CN46)})
public class CreateCN46 extends BaseBO {
	
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	
	/**
	 * This field contains observations
	 */
	@NgenAuditField(fieldName = "observations")
	
	private String observations;
	
	/**
	 * This field contains dn number count
	 */
	@NgenAuditField(fieldName = "dnNumberCount")
	private int dnNumberCount;
	
	/**
	 * This field contains admin of origin of mails
	 */
	private String adminOfOriginOfMails;
	
	/**
	 * This field contains airport of loading
	 */
	@NgenAuditField(fieldName = "airportOfLoading")
	private String airportOfLoading;
	
	/**
	 * This field contains airport of offloading
	 */
	@NgenAuditField(fieldName = "airportOfOffLoading")
	private String airportOfOffLoading;
	
	/**
	 * This field contains destination office 
	 */
	@NgenAuditField(fieldName = "destinationOffice")
	private String destinationOffice;
	
	/**
	 * This field contains flight id
	 */
	@NgenAuditField(fieldName = "flightId")
	private BigInteger flightId;
	
	/**
	 * This field contains segment id
	 */
	private BigInteger segmentId;
	
	/**
	 * This field contains airmail manifest id
	 */
	private BigInteger airmailManifestId;
	
	/**
	 * This field contains trolley number 
	 */
	@NgenAuditField(fieldName = "trolleyNumber")
	@CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED)
	private String trolleyNumber;
	
	/**
	 * This field contains flight key
	 */
	@NgenAuditField(fieldName = "flightKey")
	private String flightKey;
	
	/**
	 * This field contains flight date
	 */
	@NgenAuditField(fieldName = "flightDate")
	private LocalDate flightDate;
	
	/**
	 * This field contains bulk flag
	 */
	private boolean bulkFlag;
	private boolean bulk;
	@NgenAuditField(fieldName = "outgoingFlightKey")
	private String outgoingFlightKey;
	@NgenAuditField(fieldName = "outgoingFlightDate")
	private LocalDate outgoingFlightDate;
	@NgenAuditField(fieldName = "outgoingFlightId")
	private BigInteger outgoingFlightId;
	
	/**
	 * This field contains list of cn46 details
	 */
	@Valid
	List<CN46Details> cn46Details;

}
