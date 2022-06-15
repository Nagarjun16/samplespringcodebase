package com.ngen.cosys.impbd.mail.document.model;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEntityValue3;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.impbd.mail.validator.group.InboundMailDocumentValidationGroup;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.FlightType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@CheckValidFlightConstraint(type = FlightType.Type.IMPORT, number = "flightNumber", originDate = "flightDate", groups = InboundMailDocumentValidationGroup.class)
@NgenCosysAppAnnotation
@NgenAudit(eventName = NgenAuditEventType.IMPORT_MAIL_CAPTURE_DOC, repository = NgenAuditEventRepository.MAILBAG, entityType = NgenAuditEntityType.MAILBAG)
public class InboundMailDocumentModel extends FlightModel {

   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   @NotBlank( message="FlightKey is mandatory" , groups=InboundMailDocumentValidationGroup.class)
   @NgenAuditField(fieldName = "Flight Key")
   private String flightKey;
   @NgenAuditField(fieldName = "Mail Bag")
   private InboundMailDocumentShipmentModel mailBag;
   @NgenAuditField(fieldName = "Mail Bag Detail")
   private List<InboundMailDocumentShipmentModel> mailsBags;

}