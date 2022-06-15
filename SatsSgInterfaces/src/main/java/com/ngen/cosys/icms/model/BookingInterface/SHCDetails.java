package com.ngen.cosys.icms.model.BookingInterface;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

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
@NgenCosysAppAnnotation
@Validated
@NgenAudit(entityFieldName = "Shipment Number", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.SHC_BOOKING, repository = NgenAuditEventRepository.AWB)
public class SHCDetails extends BaseBO{
	private static final long serialVersionUID = 1L;
	private BigInteger snapShotShipmentId; 
	
	@NgenAuditField(fieldName=NgenAuditFieldNameType.SHC)
    private String specialHandlingCode;
	
     private long flightBookingId;
     private long partBookingId;
     private int specialHandlingPriority;
     private BigInteger expBookingDeltaId ;
     private String shipmentNumber;
}
