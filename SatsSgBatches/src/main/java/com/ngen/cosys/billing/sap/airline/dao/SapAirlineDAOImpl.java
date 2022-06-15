package com.ngen.cosys.billing.sap.airline.dao;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.microsoft.applicationinsights.core.dependencies.google.common.collect.Lists;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceCreditDebitNoteChild;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceCreditDebitNoteMaster;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceSalesEntryChild;
import com.ngen.cosys.billing.sap.airline.model.ARInvoiceSalesEntryMaster;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentBankTransfer;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentCASH;
import com.ngen.cosys.billing.sap.airline.model.IncomingPaymentCheque;
import com.ngen.cosys.billing.sap.airline.model.SapCSVRequest;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;

@Repository("sapAirlineDAO")
public class SapAirlineDAOImpl extends BaseDAO implements SapAirlineDAO {
	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
	private SqlSession sqlSessionROI;

	@Override
	public String getSAPSpecialCode() throws CustomException {
		return fetchObject("getSAPSpecialCode", null, sqlSessionROI);
	}
	@Override
	public LocalDateTime getSAPDelayHour() throws CustomException {
		String delayedHourString=fetchObject("getSAPDelayHour", null, sqlSessionROI);
		LocalDateTime delayDate=LocalDateTime.now();
		Long delayedHours=0l;
		delayedHours=Long.parseLong(delayedHourString==null?"0":delayedHourString);
		delayDate=delayDate.minusHours(delayedHours);
		return delayDate;
	}
	@Override
	public List<List<Integer>> fetchARInvoiceSalesEntryIds() throws CustomException {
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		List<Integer> ids = fetchList("fetchInvoiceSalesEntryMasterIds", req, sqlSessionROI);
		return Lists.partition(ids, 500);
	}
	@Override
	public List<List<Integer>> fetchCreditNoteIds() throws CustomException {
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		List<Integer> ids = fetchList("fetchCreditNoteIds", req, sqlSessionROI);
		return Lists.partition(ids, 500);
	}
	@Override
	public List<List<Integer>> fetchDebitNoteIds() throws CustomException {
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		List<Integer> ids = fetchList("fetchDebitNoteIds", req, sqlSessionROI);
		return Lists.partition(ids, 500);
	}
	@Override
	public void updateSapBillingStatus(List<List<Integer>> idsSet) throws CustomException {
	for (List<Integer> idsSubArray : idsSet) {
				updateData("sqlSetInvoiceSalesEntryStatus",idsSubArray.toString().replace("[", "(").replace("]", ")"), sqlSessionROI);
	}
	}
	

	@Override
	public List<ARInvoiceSalesEntryMaster> fetchARInvoiceSalesEntryMasterDetails(List<List<Integer>> idsSet)
			throws CustomException {
		List<ARInvoiceSalesEntryMaster> data = new ArrayList<ARInvoiceSalesEntryMaster>();
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		for (List<Integer> idsSubArray : idsSet) {
			req.setIds(idsSubArray);
			data.addAll(fetchList("fetchInvoiceSalesEntryMaster",
					req, sqlSessionROI));
		}
		return data;
	}

	@Override
	public List<ARInvoiceSalesEntryChild> fetchARInvoiceSalesEntryChildDetails(List<List<Integer>> idsSet)
			throws CustomException {
		List<ARInvoiceSalesEntryChild> data = new ArrayList<ARInvoiceSalesEntryChild>();
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		for (List<Integer> idsSubArray : idsSet) {
			req.setIds(idsSubArray);
			data.addAll(fetchList("fetchInvoiceSalesEntryChild",req, sqlSessionROI));
		}
		return data;
	}



	@Override
	public List<ARInvoiceCreditDebitNoteMaster> fetchARInvoiceCreditNoteMasterDetails(List<List<Integer>> idsSet) throws CustomException {
		List<ARInvoiceCreditDebitNoteMaster> data = new ArrayList<ARInvoiceCreditDebitNoteMaster>();
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		for (List<Integer> idsSubArray : idsSet) {
			req.setIds(idsSubArray);
			data.addAll(fetchList("fetchCreditNoteMaster",req, sqlSessionROI));
		}
		return data;
	}
	@Override
	public List<ARInvoiceCreditDebitNoteMaster> fetchARInvoiceDebitNoteMasterDetails(List<List<Integer>> idsSet) throws CustomException {
		List<ARInvoiceCreditDebitNoteMaster> data = new ArrayList<ARInvoiceCreditDebitNoteMaster>();
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		for (List<Integer> idsSubArray : idsSet) {
			req.setIds(idsSubArray);
			data.addAll(fetchList("fetchDebitNoteMaster",req, sqlSessionROI));
		}
		return data;
	}
	@Override
	public List<ARInvoiceCreditDebitNoteChild> fetchARInvoiceDebitNoteChildDetails(List<List<Integer>> idsSet) throws CustomException {
		List<ARInvoiceCreditDebitNoteChild> data = new ArrayList<ARInvoiceCreditDebitNoteChild>();
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		for (List<Integer> idsSubArray : idsSet) {
			req.setIds(idsSubArray);
			data.addAll(fetchList("fetchCreditDebitNoteChild",req, sqlSessionROI));
		}
		return data;
	}

	@Override
	public List<IncomingPaymentBankTransfer> fetchIncomingPaymentBankTransferDetails() throws CustomException {
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		return fetchList("fetchIncomingPaymentBankTransfer",req,sqlSessionROI);
	}

	@Override
	public List<IncomingPaymentCASH> fetchIncomingPaymentCASHDetails() throws CustomException {
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		return fetchList("fetchIncomingPaymentCASH",req,sqlSessionROI);
		}

	@Override
	public List<IncomingPaymentCheque> fetchIncomingPaymentChequeDetails() throws CustomException {
		
		SapCSVRequest req= new SapCSVRequest();
		req.setDelaytime(getSAPDelayHour());
		return fetchList("fetchIncomingPaymentCheque",req,sqlSessionROI);
	}

}
