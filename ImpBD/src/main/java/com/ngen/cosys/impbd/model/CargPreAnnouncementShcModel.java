package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.model.SHCModel;
import com.ngen.cosys.validator.annotations.CheckSpecialHandlingCodeConstraint;

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
@Validated
@NgenAudit(entityType = NgenAuditEntityType.ULD, eventName = NgenAuditEventType.CARGO_PREANNOUNCEMENT, repository = NgenAuditEventRepository.ULD)
public class CargPreAnnouncementShcModel extends SHCModel {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   private BigInteger id;
   
   private BigInteger rampShcid;

   @CheckSpecialHandlingCodeConstraint()
   @NgenAuditField(fieldName = NgenAuditFieldNameType.SHC)
   private String preSpecialHandlingCode;

}
