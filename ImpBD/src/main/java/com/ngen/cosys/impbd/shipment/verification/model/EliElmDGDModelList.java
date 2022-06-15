package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

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
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.ELI_ELM_DEATILS, repository = NgenAuditEventRepository.AWB)
public class EliElmDGDModelList extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.SHIPMENTNUMBER)
	private String shipmentNumber;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.PI)
	private String piData;	
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ELIELM)
	private String eliElm;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.FLTTYPE)
	private String flightType;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	private String remark;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CARRCODE)
	private String carrierCode;
	
	private boolean forbiddenFlag;
	
	private String passengerFlag;
	
	private String freighterFlag;
	
	private BigInteger transactionSequenceNo;
}
