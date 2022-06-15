package com.ngen.cosys.billing.sap.airline.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngen.cosys.billing.sap.airline.dao.SapAirlineDAO;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceSalesEntryChild;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceSalesEntryMaster;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.holder.TenantContextHolder;
import com.ngen.cosys.multitenancy.context.TenantContext;
@Service
public class SapInvoiceSalesEntryServiceImpl  implements SapInvoiceSalesEntryService{
	@Autowired
	SapAirlineDAO sapAirlineDAO;
	@Autowired
	SapAirlineService sapAirlineService;
	@Autowired
	TenantContextHolder tenantContextHolder;
	String Dataformat="yyyyMMdd";
	String Datatimeformat="yyyyMMdd hhmm";
	@Override
	public void createAndSendMessageToSap() throws CustomException {
		
		StringBuffer message=new StringBuffer();
		List<List<Integer>> idsSet=sapAirlineDAO.fetchARInvoiceSalesEntryIds();
		message.append("DocNum,DocDate,DocDueDate,TaxDate,CardCode,DocCur,TransactionType,Comments,Series,SlpCode,Department,ShipToCode,DiscPrcnt,PayToCode,CtlAccount,U_Dtype,U_ACKNO,U_ACKDATE,U_QRCODE,U_IRN,U_SIGNEDINVOICE,U_BPTYPE\n");
		message.append("DocNum,DocDate,DocDueDate,TaxDate,CardCode,DocCur,TransactionType,Comments,Series,SlpCode,Department,ShipToCode,DiscPrcnt,PayToCode,CtlAccount,Dtype,U_ACKNO,U_ACKDATE,U_QRCODE,U_IRN,U_SIGNEDINVOICE,U_BPTYPE\n");
		List<ARInvoiceSalesEntryMaster> data=sapAirlineDAO.fetchARInvoiceSalesEntryMasterDetails(idsSet);
		String slpCode=sapAirlineDAO.getSAPSpecialCode();
		for(ARInvoiceSalesEntryMaster obj:data) {
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
			message.append(obj.getU_Dtype()).append(",");
			message.append("`"+obj.getU_ACKNO()+"`").append(",");
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
			status1=sapAirlineService.sendCosysMessageToSAP("SOINV_Documents1.csv", message.toString(),"SOINV HDR");		
		}
		
		message=new StringBuffer();
		message.append("ParentKey,LineNum,ItemCode,Quantity,Currency,DiscountPercent,Location_Name,TaxCode,UnitPrice,Department,Airline\n");
		message.append("DocNum,LineNum,ItemCode,Quantity,Currency,DiscPrcnt,LocCode,TaxCode,PriceBefDi,Department,Airline\n");
		List<ARInvoiceSalesEntryChild> dataChild=sapAirlineDAO.fetchARInvoiceSalesEntryChildDetails(idsSet);
		HashMap<String,Integer> linecount=new HashMap<String,Integer>();
		for(ARInvoiceSalesEntryChild obj:dataChild) {
			if(obj.getTx1()!=null) {
				obj.setTaxCode(obj.getTx1());
			}else if(obj.getTx1()!=null) {
				obj.setTaxCode(obj.getTx2());
			}else {
				obj.setTaxCode(obj.getTx3());
			}
			if(linecount.containsKey(obj.getParentKey())) {
				linecount.put(obj.getParentKey(),linecount.get(obj.getParentKey())+1);
			}else {
				linecount.put(obj.getParentKey(),1);
			}
			if(obj.getTaxCode()==null) {
				obj.setTaxCode("NA");
			}
			message.append(obj.getParentKey()==null?"":obj.getParentKey()).append(",");
			message.append(linecount.get(obj.getParentKey())).append(",");
			message.append(obj.getItemCode()==null?"":obj.getItemCode()).append(",");
			message.append(obj.getQuantity()==null?"":obj.getQuantity()).append(",");
			message.append(obj.getCurrency()==null?"":obj.getCurrency()).append(",");
			message.append(obj.getDiscountPercent()==null?"":obj.getDiscountPercent()).append(",");
			message.append(TenantContext.get().getTenantConfig().getAirportCode()).append(",");
			message.append(obj.getTaxCode()==null?"":obj.getTaxCode()).append(",");
			message.append(obj.getUnitPrice()==null?"":obj.getUnitPrice()).append(",");
			message.append(obj.getDepartment()==null?"":obj.getDepartment()).append(",");
			message.append(obj.getAirline()==null?"":obj.getAirline()).append("\n");
		}
		String status2="";
		if(!dataChild.isEmpty()) {
			status2=sapAirlineService.sendCosysMessageToSAP("SINV1_Document_Lines1.csv", message.toString(),"SOINV DTL");			
		}
		if(status1.equalsIgnoreCase(SapAirlineService.SENT) && status2.equalsIgnoreCase(SapAirlineService.SENT)) {
			sapAirlineDAO.updateSapBillingStatus(idsSet);
		}
	}
}
