package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class IncomingPaymentCheque {
	String docNum;
	String docType;
	LocalDateTime docDate;
	LocalDateTime taxDate;
	LocalDateTime dueDate;
	String cardCode;
	String billToAddress;
	String chequeAccount;
	String chequeSum;
	String tDSAccount;
	String tdsAmt;
	String docCurrency;
	String counterReference;
	String series;
	String bpAct;
	String u_Dtype;
	String department;
	String u_LOB;
	String cHQNUM;
	Integer paymentReceiptId;
}
