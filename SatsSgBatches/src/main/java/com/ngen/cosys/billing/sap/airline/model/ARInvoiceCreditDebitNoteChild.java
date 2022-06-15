package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ARInvoiceCreditDebitNoteChild {
	String parentKey;
	String lineNum;
	String itemCode;
	String quantity;
	String currency;
	String discountPercent;
	String location_Name;
	String taxCode;
	String unitPrice;
	String department;
	String Airline;
	String tx1;
	String tx2;
	String tx3;
}
