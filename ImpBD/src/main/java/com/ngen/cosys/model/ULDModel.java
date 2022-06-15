package com.ngen.cosys.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import com.ngen.cosys.validators.ArrivalManifestValidationGroup;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@NgenAudit(entityFieldName = "uldNumber", entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.BREAKDOWN_SUMMARY, repository = NgenAuditEventRepository.FLIGHT)
public class ULDModel extends BaseBO {

   private static final long serialVersionUID = 1L;

   private String uldType;
   private String uldSerialNumber;
   private String uldOwnerCode;
   @NgenAuditField(fieldName = "uldNumber")
   @CheckContainerNumberConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {
         ArrivalManifestValidationGroup.class })
   private String uldNumber;
   private String uldLoadingIndicator;

   
   private String uldRemarks;

   private int volumeAvailableCode;

}