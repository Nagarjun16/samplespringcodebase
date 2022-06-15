package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ARInvoiceCreditDebitNoteMaster {
	String docNum;
	LocalDateTime docDate;
	LocalDateTime docDueDate;
	LocalDateTime taxDate;
	String cardCode;
	String docCur;
	String transactionType;
	String comments;
	String series;
	String slpCode;
	String department;
	String shipToCode;
	String discPrcnt;
	String payToCode;
	String ctlAccount;
	String dtype;
	String u_ACKNO;
	LocalDateTime u_ACKDATE;
	String u_QRCODE;
	String u_IRN;
	String u_SIGNEDINVOICE;
	String u_BPTYPE;
	String originalRefNo;
	LocalDateTime originalRefDate;
	
}
