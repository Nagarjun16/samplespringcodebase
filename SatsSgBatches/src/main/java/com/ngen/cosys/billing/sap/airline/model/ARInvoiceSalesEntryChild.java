package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ARInvoiceSalesEntryChild {
	String parentKey;
	String lineNum;
	String itemCode;
	String quantity;
	String currency;
	String discountPercent;
	String location_Name;
	String taxCode;
	String tx1;
	String tx2;
	String tx3;
	String unitPrice;
	String department;
	String airline;
}
