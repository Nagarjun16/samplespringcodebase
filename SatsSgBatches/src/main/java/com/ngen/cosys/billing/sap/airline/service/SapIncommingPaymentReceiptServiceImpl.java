package com.ngen.cosys.billing.sap.airline.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microsoft.applicationinsights.core.dependencies.google.common.collect.Lists;
import com.ngen.cosys.billing.sap.airline.dao.SapAirlineDAO;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentBankTransfer;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentCASH;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentCheque;
import com.ngen.cosys.framework.exception.CustomException;

@Service
public class SapIncommingPaymentReceiptServiceImpl  implements SapIncommingPaymentReceiptService{
	@Autowired
	SapAirlineDAO sapAirlineDAO;
	@Autowired
	SapAirlineService sapAirlineService;
	String Dataformat="yyyyMMdd";
	String Datatineformat="yyyyMMdd hhmm";
	@Override
	public void createAndSendMessageToSap() throws CustomException {
		
		List<Integer> paymentReceiptIds= new ArrayList<>();
		StringBuffer message=new StringBuffer();
		message.append("DocNum,DocType,DocDate,TaxDate,DueDate,CardCode,BillToAddress,TransferAccount,TransferSum,TDSAccount,TdsAmt,DocCurrency,TransferDate,TransferReference,CounterReference,Series,ControlAccount,U_Dtype,Department,U_LOB\n");
		message.append("DocNum,DocType,DocDate,TaxDate,DueDate,CardCode,BillToAddress,TransferAccount,TransferSum,TDSAccount,TdsAmt,DocCurrency,TransferDate,TransferReference,CounterReference,Series,BpAct,U_Dtype,Department,U_LOB\n");
		List<IncomingPaymentBankTransfer> data=sapAirlineDAO.fetchIncomingPaymentBankTransferDetails();
		for(IncomingPaymentBankTransfer obj:data) {
			message.append(obj.getDocNum()).append(",");
			message.append(obj.getDocType()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getTaxDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDueDate(),Dataformat)).append(",");
			message.append(obj.getCardCode()).append(",");
			message.append(obj.getBillToAddress()).append(",");
			message.append(obj.getTransferAccount()).append(",");
			message.append(obj.getTransferSum()).append(",");
			message.append(obj.getTDSAccount()).append(",");
			message.append(obj.getTdsAmt()).append(",");
			message.append(obj.getDocCurrency()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getTransferDate(),Dataformat)).append(",");
			message.append(obj.getTransferReference()).append(",");
			message.append(obj.getCounterReference()).append(",");
			message.append(obj.getSeries()).append(",");
			message.append(obj.getBpAct()).append(",");
			message.append(obj.getU_Dtype()).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getU_LOB()).append("\n");
			paymentReceiptIds.add(obj.getPaymentReceiptId());
		}
		String status="";
		if(!data.isEmpty()) {
			status=sapAirlineService.sendCosysMessageToSAP("Incoming_Payment_Bank_Transfer.csv", message.toString(),"Payment Bank");			
		}
		if(status.equalsIgnoreCase(SapAirlineService.SENT)) {
			sapAirlineDAO.updateSapBillingStatus(Lists.partition(paymentReceiptIds, 500));
		}
		status="";
		paymentReceiptIds= new ArrayList<>();
		message=new StringBuffer();
		message.append("DocNum,DocType,DocDate,TaxDate,DueDate,CardCode,BillToAddress,CashAccount,CashSum,DocCurrency,CounterReference,Series,ControlAccount,U_Dtype,Department,U_LOB\n");
		message.append("DocNum,DocType,DocDate,TaxDate,DueDate,CardCode,BillToAddress,CashAccount,CashSum,DocCurrency,CounterReference,Series,BpAct,U_Dtype,Department,U_LOB\n");
		List<IncomingPaymentCASH> dataCASH=sapAirlineDAO.fetchIncomingPaymentCASHDetails();
		for(IncomingPaymentCASH obj:dataCASH) {
			message.append(obj.getDocNum()).append(",");
			message.append(obj.getDocType()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getTaxDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDueDate(),Dataformat)).append(",");
			message.append(obj.getCardCode()).append(",");
			message.append(obj.getBillToAddress()).append(",");
			message.append(obj.getCashAccount()).append(",");
			message.append(obj.getCashSum()).append(",");
			message.append(obj.getDocCurrency()).append(",");
			message.append(obj.getCounterReference()).append(",");
			message.append(obj.getSeries()).append(",");
			message.append(obj.getBpAct()).append(",");
			message.append(obj.getU_Dtype()).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getU_LOB()).append("\n");
			paymentReceiptIds.add(obj.getPaymentReceiptId());
		}
		if(!dataCASH.isEmpty()) {
			status=sapAirlineService.sendCosysMessageToSAP("Incoming_Payment_CASH.csv", message.toString(),"Payment CASH");			
		}
		if(status.equalsIgnoreCase(SapAirlineService.SENT)) {
			sapAirlineDAO.updateSapBillingStatus(Lists.partition(paymentReceiptIds, 500));
		}
		status="";
		paymentReceiptIds= new ArrayList<>();
		message=new StringBuffer();
		message.append("DocNum,DocType,DocDate,TaxDate,DueDate,CardCode,BillToAddress,ChequeAccount,ChequeSum,TDSAccount,TdsAmt,DocCurrency,CounterReference,Series,ControlAccount,U_Dtype,Department,U_LOB,CHQNUM\n");
		message.append("DocNum,DocType,DocDate,TaxDate,DueDate,CardCode,BillToAddress,ChequeAccount,ChequeSum,TDSAccount,TdsAmt,DocCurrency,CounterReference,Series,BpAct,U_Dtype,Department,U_LOB,CHQNUM\n");
		List<IncomingPaymentCheque> dataCheque=sapAirlineDAO.fetchIncomingPaymentChequeDetails();
		for(IncomingPaymentCheque obj:dataCheque) {
			message.append(obj.getDocNum()).append(",");
			message.append(obj.getDocType()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getTaxDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDueDate(),Dataformat)).append(",");
			message.append(obj.getCardCode()).append(",");
			message.append(obj.getBillToAddress()).append(",");
			message.append(obj.getChequeAccount()).append(",");
			message.append(obj.getChequeSum()).append(",");
			message.append(obj.getTDSAccount()).append(",");
			message.append(obj.getTdsAmt()).append(",");
			message.append(obj.getDocCurrency()).append(",");
			message.append(obj.getCounterReference()).append(",");
			message.append(obj.getSeries()).append(",");
			message.append(obj.getBpAct()).append(",");
			message.append(obj.getU_Dtype()).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getU_LOB()).append(",");
			message.append(obj.getCHQNUM()).append("\n");
			paymentReceiptIds.add(obj.getPaymentReceiptId());
		}
		if(!dataCheque.isEmpty()) {
			status=sapAirlineService.sendCosysMessageToSAP("Incoming_Payment_Cheque.csv", message.toString(),"Payment Cheque");
		}
		if(status.equalsIgnoreCase(SapAirlineService.SENT)) {
			sapAirlineDAO.updateSapBillingStatus(Lists.partition(paymentReceiptIds, 500));
		}
	}
}
