package com.ngen.cosys.billing.sap.model;

import java.math.BigInteger;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Component
@ToString
public class InvoiceSentByEmail {

	private static final long serialVersionUID = 1L;
	 
	public String finSysInvoiceNumber;

	public BigInteger customerId;

	public int eSupportDocEmailSent;
	
	public String billEntryId;
}
