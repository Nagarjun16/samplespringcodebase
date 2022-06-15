package com.ngen.cosys.mailbag.overview.information.model;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.mailbag.overview.information.validation.group.MailbagValidationGroup;
import com.ngen.cosys.validator.annotations.CheckContainerNumberConstraint;
import com.ngen.cosys.validator.annotations.CheckHousewayBillConstraint;
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
@CheckHousewayBillConstraint(mandatory = MandatoryType.Type.NOTREQUIRED, shipmentTypeField = "shipmentType", hwbNumberField = "mailbagNumber")
public class MailbagSearchReq extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String searchMode;
	@CheckContainerNumberConstraint(groups=MailbagValidationGroup.class, mandatory = "")
	private String uldtrolley;
	@Length(groups=MailbagValidationGroup.class, max = 4, message = "mail.dn.len")
	private String dispatchNumber;
	@Length(groups=MailbagValidationGroup.class, max = 3, message = "mail.org.des.len")
	private String origin;
	@Length(groups=MailbagValidationGroup.class, max = 3, message = "mail.org.des.len")
	private String destination;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String xrayresult;
	private String dispatchSeries;
	@Length(groups=MailbagValidationGroup.class, max = 29, message = "mail.mailbag.no.len")
	private String  mailbagNumber;
	private String shipmentNumber;
	private LocalDate shipmentDate;
	private BigInteger shipmentId;
}
