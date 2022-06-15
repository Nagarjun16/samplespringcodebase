package com.ngen.cosys.billing.sap.airline.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
public class IncomingPaymentCASH {
	String docNum;
	String docType;
	LocalDateTime docDate;
	LocalDateTime taxDate;
	LocalDateTime dueDate;
	String cardCode;
	String billToAddress;
	String cashAccount;
	String cashSum;
	String docCurrency;
	String counterReference;
	String series;
	String bpAct;
	String u_Dtype;
	String department;
	String u_LOB;
	Integer paymentReceiptId;
}
