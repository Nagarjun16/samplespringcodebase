package com.ngen.cosys.damage.model;

import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = "Entity Key", entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.CAPTURE_DAMAGE, repository = NgenAuditEventRepository.AWB)
public class FileUploadModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   @NgenAuditField(fieldName = "Files List")
   List<FileUpload> filesList;
   
   private String entityType;
   
   @Valid
   @NgenAuditField(fieldName = "Entity Key")
   //@CheckShipmentNumberConstraint(mandatory = "required",shipmentTypeField="entityType",shipmentNumberField="entityKey")
   private String entityKey;
   private String referenceId;
   
   @NgenAuditField(fieldName = "Email To")
   private String emailTo;
   
}
