package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class ARInvoiceSalesEntryMaster {
	String DocNum;
	LocalDateTime DocDate;
	LocalDateTime DocDueDate;
	LocalDateTime TaxDate;
	String CardCode;
	String DocCur;
	String TransactionType;
	String Comments;
	String Series;
	String SlpCode;
	String Department;
	String ShipToCode;
	String DiscPrcnt;
	String PayToCode;
	String CtlAccount;
	String U_Dtype;
	String U_ACKNO;
	LocalDateTime U_ACKDATE;
	String U_QRCODE;
	String U_IRN;
	String U_SIGNEDINVOICE;
	String U_BPTYPE;
}
