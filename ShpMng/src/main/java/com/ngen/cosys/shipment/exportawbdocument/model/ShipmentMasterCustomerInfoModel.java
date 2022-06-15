package com.ngen.cosys.shipment.exportawbdocument.model;


import java.math.BigInteger;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;
import com.ngen.cosys.validator.annotations.CheckRemarksConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentMasterCustomerInfoModel extends BaseBO{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger shipmentCustomerInfoId;

	private BigInteger shipmentId;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_CODE)
	private String customerCode;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_NAME)
	@Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { SaveAWBDocument.class })
	private String customerName;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.ACCOUNT_NUMBER)
	private String accountNumber;
	
	private Boolean overseasCustomer= false;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.APPOINTED_AGENT_CODE)
	private BigInteger appointedAgent;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CONTACT_EMAIL)
	private String contactEmail;
	
	private String notifyPartyCode;
	
	private String notifyPartyName;
	
	private Boolean authorizedPersonnel= false;

	@NgenAuditField(fieldName = NgenAuditFieldNameType.REMARKS)
	@Length(max = 65, message = "Remarks Can't Be More Than 65 character length", groups = { SaveAWBDocument.class })
	@CheckRemarksConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, groups = {SaveAWBDocument.class })
	private String authorizationRemarks;
    
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_TYPE)
	private String customerType;
	
	@NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_ADDRESS)
    @Valid
    private ShipmentMasterCustomerAddressInfoModel address;
    
    
    //private List<ShipmentMasterCustomerContactInfoModel> fwbContactInformation;
    
    //private List<IVRSNotificationContactInfo> ivrsContactInformation;

}
