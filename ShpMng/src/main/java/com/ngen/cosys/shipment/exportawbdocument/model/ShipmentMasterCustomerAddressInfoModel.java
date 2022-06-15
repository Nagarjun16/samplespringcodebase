package com.ngen.cosys.shipment.exportawbdocument.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ngen.cosys.audit.NgenAudit;
import com.ngen.cosys.audit.NgenAuditEntityType;
import com.ngen.cosys.audit.NgenAuditEventRepository;
import com.ngen.cosys.audit.NgenAuditEventType;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.shipment.awb.model.ShipmentMasterCustomerContactInfo;
import com.ngen.cosys.shipment.validators.SaveAWBDocument;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NgenAudit(entityFieldName = NgenAuditFieldNameType.SHIPMENTNUMBER, entityType = NgenAuditEntityType.AWB, eventName = NgenAuditEventType.EXPORT_AWB_DOCUMENT, repository = NgenAuditEventRepository.AWB)
public class ShipmentMasterCustomerAddressInfoModel extends BaseBO{
	

    private BigInteger shipmentCustomerAddInfoId;
    
    private BigInteger shipmentCustomerInfoId;

    @NgenAuditField(fieldName = NgenAuditFieldNameType.STREET_ADDRESS)
    @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { SaveAWBDocument.class })
    private String streetAddress;

    @NgenAuditField(fieldName = NgenAuditFieldNameType.PLACE)
    @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { SaveAWBDocument.class })
    @Size(max = 17, message = "data.min.max.length.required", groups = { SaveAWBDocument.class })
    private String place;


    @NgenAuditField(fieldName = NgenAuditFieldNameType.POSTAL)
    @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { SaveAWBDocument.class })
    @Size(max = 10, message = "data.min.max.length.required", groups = { SaveAWBDocument.class })
    private String postal;


    @NgenAuditField(fieldName = NgenAuditFieldNameType.STATE_CODE)
    @Pattern(regexp = "^[A-Za-z0-9-. ]*$", message = "data.invalid.telex.characters", groups = { SaveAWBDocument.class })
    @Size(max = 9, message = "data.min.max.length.required", groups = { SaveAWBDocument.class })
    private String stateCode;

    @NgenAuditField(fieldName = NgenAuditFieldNameType.COUNTRY_CODE)
    @Size(max = 2, message = "data.min.max.length.required", groups = { SaveAWBDocument.class })
    private String countryCode;
    
    @NgenAuditField(fieldName = NgenAuditFieldNameType.CUSTOMER_CONTACT_INFO)
    @Valid
    private List<ShipmentMasterCustomerContactInfoModel> contactInformation;

}
