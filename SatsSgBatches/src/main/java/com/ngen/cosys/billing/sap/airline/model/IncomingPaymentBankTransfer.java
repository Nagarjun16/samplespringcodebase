package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class IncomingPaymentBankTransfer {
	String docNum;
	String docType;
	LocalDateTime docDate;
	LocalDateTime taxDate;
	LocalDateTime dueDate;
	String cardCode;
	String billToAddress;
	String transferAccount;
	String transferSum;
	String tDSAccount;
	String tdsAmt;
	String docCurrency;
	LocalDateTime transferDate;
	String transferReference;
	String counterReference;
	String series;
	String bpAct;
	String u_Dtype;
	String department;
	String u_LOB;
	Integer paymentReceiptId;
}
