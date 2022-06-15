package com.ngen.cosys.billing.sap.airline.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.billing.sap.airline.dao.SapAirlineDAO;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceCreditDebitNoteChild;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceCreditDebitNoteMaster;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.context.TenantContext;

@Service
public class SapInvoiceCreditDebitNoteServiceImpl implements SapInvoiceCreditDebitNoteService{
	@Autowired
	SapAirlineDAO sapAirlineDAO;
	@Autowired
	SapAirlineService sapAirlineService;
	String Dataformat="yyyyMMdd";
	String Datatimeformat="yyyyMMdd hhmm";
	@Override
	public void createAndSendDebitNoteMessageToSap() throws CustomException {
		
		StringBuffer message=new StringBuffer();
		List<List<Integer>> idsSet=sapAirlineDAO.fetchDebitNoteIds();
		message.append("DocNum,DocDate,DocDueDate,TaxDate,CardCode,DocCur,TransactionType,Comments,Series,SlpCode,Department,ShipToCode,DiscPrcnt,PayToCode,CtlAccount,U_Dtype,U_ACKNO,U_ACKDATE,U_QRCODE,U_IRN,U_SIGNEDINVOICE,U_BPTYPE\n");
		message.append("DocNum,DocDate,DocDueDate,TaxDate,CardCode,DocCur,TransactionType,Comments,Series,SlpCode,Department,ShipToCode,DiscPrcnt,PayToCode,CtlAccount,Dtype,U_ACKNO,U_ACKDATE,U_QRCODE,U_IRN,U_SIGNEDINVOICE,U_BPTYPE\n");
		String slpCode=sapAirlineDAO.getSAPSpecialCode();
		List<ARInvoiceCreditDebitNoteMaster> data=sapAirlineDAO.fetchARInvoiceDebitNoteMasterDetails(idsSet);
		for(ARInvoiceCreditDebitNoteMaster obj:data) {
			message.append(obj.getDocNum()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDueDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getTaxDate(),Dataformat)).append(",");
			message.append(obj.getCardCode()).append(",");
			message.append(obj.getDocCur()).append(",");
			message.append(obj.getTransactionType()).append(",");
			message.append(obj.getComments()).append(",");
			message.append(TenantContext.get().getTenantConfig().getAirportCode()).append(",");
			message.append(slpCode).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getShipToCode()).append(",");
			message.append(obj.getDiscPrcnt()).append(",");
			message.append(obj.getPayToCode()).append(",");
			message.append(obj.getCtlAccount()).append(",");
			message.append(obj.getDtype()).append(",");
			String uAckNo=obj.getU_ACKNO()==null?"":"`"+obj.getU_ACKNO()+"`";
			message.append(uAckNo).append(",");
			String uAckdate=sapAirlineService.getStringFromLocalDate(obj.getU_ACKDATE(),Datatimeformat);
			uAckdate=uAckdate!=null?uAckdate+"00,":",";
			message.append(uAckdate);
			message.append(obj.getU_QRCODE()==null?"":obj.getU_QRCODE()).append(",");
			message.append(obj.getU_IRN()==null?"":obj.getU_IRN()).append(",");
			message.append(obj.getU_SIGNEDINVOICE()==null?"":obj.getU_SIGNEDINVOICE()).append(",");
			message.append(obj.getU_BPTYPE()).append("\n");
		}
		String status1="";
		if(!data.isEmpty()) {
		 status1=sapAirlineService.sendCosysMessageToSAP("OINV_Documents1.csv", message.toString(),"OINV HDR");			
		}
		
		message=new StringBuffer();
		message.append("ParentKey,LineNum,ItemCode,Quantity,Currency,DiscountPercent,Location_Name,TaxCode,UnitPrice,Department,Airline\n");
		message.append("DocNum,LineNum,ItemCode,Quantity,Currency,DiscPrcnt,LocCode,TaxCode,PriceBefDi,Department,Airline\n");
		List<ARInvoiceCreditDebitNoteChild> dataChild=sapAirlineDAO.fetchARInvoiceDebitNoteChildDetails(idsSet);
		HashMap<String,Integer> linecount=new HashMap<String,Integer>();
		for(ARInvoiceCreditDebitNoteChild obj:dataChild) {
			if(obj.getTx1()!=null) {
				obj.setTaxCode(obj.getTx1());
			}else if(obj.getTx1()!=null) {
				obj.setTaxCode(obj.getTx2());
			}else {
				obj.setTaxCode(obj.getTx3());
			}
			if(obj.getTaxCode()==null) {
				obj.setTaxCode("NA");
			}
			if(linecount.containsKey(obj.getParentKey())) {
				linecount.put(obj.getParentKey(),linecount.get(obj.getParentKey())+1);
			}else {
				linecount.put(obj.getParentKey(),1);
			}
			message.append(obj.getParentKey()).append(",");
			message.append(linecount.get(obj.getParentKey())).append(",");
			message.append(obj.getItemCode()).append(",");
			message.append(obj.getQuantity()).append(",");
			message.append(obj.getCurrency()).append(",");
			message.append(obj.getDiscountPercent()).append(",");
			message.append(obj.getLocation_Name()).append(",");
			message.append(obj.getTaxCode()).append(",");
			message.append(obj.getUnitPrice()).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getAirline()).append("\n");
		}
		String status2="";
		if(!dataChild.isEmpty()) {
		status2=sapAirlineService.sendCosysMessageToSAP("INV1_Document_Lines1.csv", message.toString(),"INV1 DLT");
		}
		if(status1.equalsIgnoreCase(SapAirlineService.SENT) && status2.equalsIgnoreCase(SapAirlineService.SENT)) {
			sapAirlineDAO.updateSapBillingStatus(idsSet);
		}
	}
	@Override
	public void createAndSendCreditNoteMessageToSap() throws CustomException {
		
		StringBuffer message=new StringBuffer();
		List<List<Integer>> idsSet=sapAirlineDAO.fetchCreditNoteIds();
		message.append("DocNum,DocDate,DocDueDate,TaxDate,CardCode,DocCur,Transaction Type,Comments,Series,SlpCode,Department,ShipToCode,DiscPrcnt,PayToCode,CtlAccount,Dtype,Original Ref. No.,Original Ref. Date,U_ACKNO,U_ACKDATE,U_QRCODE,U_IRN,U_SIGNEDINVOICE,U_BPTYPE\n");
		message.append("DocNum,DocDate,DocDueDate,TaxDate,CardCode,DocCur,Transaction Type,Comments,Series,SlpCode,Department,ShipToCode,DiscPrcnt,PayToCode,CtlAccount,Dtype,Original Ref. No.,Original Ref. Date,U_ACKNO,U_ACKDATE,U_QRCODE,U_IRN,U_SIGNEDINVOICE,U_BPTYPE\n");
		String slpCode=sapAirlineDAO.getSAPSpecialCode();
		List<ARInvoiceCreditDebitNoteMaster> data=sapAirlineDAO.fetchARInvoiceCreditNoteMasterDetails(idsSet);
		for(ARInvoiceCreditDebitNoteMaster obj:data) {
			message.append(obj.getDocNum()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getDocDueDate(),Dataformat)).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getTaxDate(),Dataformat)).append(",");
			message.append(obj.getCardCode()).append(",");
			message.append(obj.getDocCur()).append(",");
			message.append(obj.getTransactionType()).append(",");
			message.append(obj.getComments()).append(",");
			message.append(TenantContext.get().getTenantConfig().getAirportCode()).append(",");
			message.append(slpCode).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getShipToCode()).append(",");
			message.append(obj.getDiscPrcnt()).append(",");
			message.append(obj.getPayToCode()).append(",");
			message.append(obj.getCtlAccount()).append(",");
			message.append(obj.getDtype()).append(",");
			message.append(obj.getOriginalRefNo()).append(",");
			message.append(sapAirlineService.getStringFromLocalDate(obj.getOriginalRefDate(),Dataformat)).append(",");
			message.append("`"+obj.getU_ACKNO()).append("`,");
			String uAckdate=sapAirlineService.getStringFromLocalDate(obj.getU_ACKDATE(),Datatimeformat);
			uAckdate=uAckdate!=null?uAckdate+"00,":uAckdate+",";
			message.append(uAckdate);
			message.append(obj.getU_QRCODE()).append(",");
			message.append(obj.getU_IRN()).append(",");
			message.append(obj.getU_SIGNEDINVOICE()).append(",");
			message.append(obj.getU_BPTYPE()).append("\n");	
		}
		String status1="";
		if(!data.isEmpty()) {
			status1=sapAirlineService.sendCosysMessageToSAP("ORIN_Documents1.csv", message.toString(),"ORIN HDR");			
		}
		
		message=new StringBuffer();
		message.append("ParentKey,LineNum,ItemCode,Quantity,Currency,DiscountPercent,Location Name,TaxCode,UnitPrice,Department,AIRLINE\n");
		message.append("DocNum,LineNum,ItemCode,Quantity,Currency,DiscPrcnt,LocCode,TaxCode,PriceBefDi,Department,AIRLINE\n");
		List<ARInvoiceCreditDebitNoteChild> dataChild=sapAirlineDAO.fetchARInvoiceDebitNoteChildDetails(idsSet);
		HashMap<String,Integer> linecount=new HashMap<String,Integer>();
		for(ARInvoiceCreditDebitNoteChild obj:dataChild) {
			if(obj.getTx1()!=null) {
				obj.setTaxCode(obj.getTx1());
			}else if(obj.getTx1()!=null) {
				obj.setTaxCode(obj.getTx2());
			}else {
				obj.setTaxCode(obj.getTx3());
			}
			
			if(obj.getTaxCode()==null) {
				obj.setTaxCode("NA");
			}
			if(linecount.containsKey(obj.getParentKey())) {
				linecount.put(obj.getParentKey(),linecount.get(obj.getParentKey())+1);
			}else {
				linecount.put(obj.getParentKey(),1);
			}
			message.append(obj.getParentKey()).append(",");
			message.append(linecount.get(obj.getParentKey())).append(",");
			message.append(obj.getItemCode()).append(",");
			message.append(obj.getQuantity()).append(",");
			message.append(obj.getCurrency()).append(",");
			message.append(obj.getDiscountPercent()).append(",");
			message.append(obj.getLocation_Name()).append(",");
			message.append(obj.getTaxCode()).append(",");
			message.append(obj.getUnitPrice()).append(",");
			message.append(obj.getDepartment()).append(",");
			message.append(obj.getAirline()).append("\n");
		}
		String status2="";
		if(!dataChild.isEmpty()) {
			status2=sapAirlineService.sendCosysMessageToSAP("RIN1_Document_Lines1.csv", message.toString(),"RIN DLT");
		}
		if(status1.equalsIgnoreCase(SapAirlineService.SENT) && status2.equalsIgnoreCase(SapAirlineService.SENT)) {
			sapAirlineDAO.updateSapBillingStatus(idsSet);
		}
	}
}
