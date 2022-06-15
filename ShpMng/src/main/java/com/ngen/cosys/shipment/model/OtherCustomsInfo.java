/**
 * 
 * ShipmentFreightWayBillOtherCustomsInfo.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22 January , 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.math.BigInteger;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.MaintainFreightWayBillValidator;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model Class- ShipmentFreightWayBillOtherCustomsInfo
 * 
 * @author NIIT Technologies Ltd
 *
 */
@ApiModel
@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityType = NgenAuditEntityType.AWB, entityFieldName = "awbNumber", eventName = NgenAuditEventType.NAWB_MANAGEMENT, repository = NgenAuditEventRepository.AWB)
public class OtherCustomsInfo extends BaseBO {

   private static final long serialVersionUID = 1L;
   private long shipmentFreightWayBillId;
   private long shipmentFreightWayBillOtherCustomsInfoId;
   private BigInteger neutralAWBId;
   private BigInteger expNeutralAWBOtherCustomsInfoId;
   @Size(max = 3, message = "awb.information.idtn.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "informationIdentifier")
   private String informationIdentifier;
   @Size(max = 2, message = "awb.csrci.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "csrciIdentifier")
   private String csrciIdentifier;
   
   @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { MaintainFreightWayBillValidator.class })
   @Size(max = 35, message = "awb.scrc.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "scrcInformation")
   private String scrcInformation;
   @Size(max = 2, message = "g.country.code.len", groups = { MaintainFreightWayBillValidator.class })
   @NgenAuditField(fieldName = "countryCode")
   private String countryCode;
   private String customerName;
}
