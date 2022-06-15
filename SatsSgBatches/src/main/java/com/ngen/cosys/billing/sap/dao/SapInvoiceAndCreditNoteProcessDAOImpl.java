package com.ngen.cosys.billing.sap.dao;

import java.util.List;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ngen.cosys.billing.sap.enums.query.SAPInterfaceQuery;
import com.ngen.cosys.billing.sap.model.InvoiceAndCreditNote;
import com.ngen.cosys.billing.sap.model.InvoiceSentByEmail;
import com.ngen.cosys.framework.dao.BaseDAO;
import com.ngen.cosys.framework.exception.CustomException;

@Repository("sapInvoiceAndCreditNoteProcessDao")
public class SapInvoiceAndCreditNoteProcessDAOImpl extends BaseDAO implements SapInvoiceAndCreditNoteProcessDAO {

   @Autowired
   @Qualifier("sqlSessionTemplate")
   public SqlSession sqlSession;

   /*
    * (non-Javadoc)
    * 
    * @see com.ngen.cosys.billing.sap.service.SapInvoiceAndCreditNoteProcessImpl#
    * searchCode
    * (com.ngen.cosys.billing.sap.model.InvoiceAndCreditNote,com.ngen.cosys.billing
    * .sap.model.InvoiceAndCreditNoteEntry)
    */

   @Override
   public InvoiceAndCreditNote saveInvoiceAndCreditNoteDetails(InvoiceAndCreditNote invoiceAndCreditNote)
         throws CustomException {

      super.insertData(SAPInterfaceQuery.INSERT_INVOICE_AND_CREDIT_NOTE_DETAILS.getQueryId(), invoiceAndCreditNote,
            sqlSession);
      
      invoiceAndCreditNote.getInvoiceCreditNoteEntryList()
            .forEach(entry -> entry.setInvoiceCrdtNoteId(invoiceAndCreditNote.getInvoiceCrdtNoteId()));
      
   // setting values for isVoid using cancel indicator - added by arun
      invoiceAndCreditNote.getInvoiceCreditNoteEntryList()
      .forEach(entry -> {
         if(invoiceAndCreditNote.getCancelIndicator()!=null && invoiceAndCreditNote.getCancelIndicator().equalsIgnoreCase("X")) {
            entry.setIsVoid(1);
         }else {
            entry.setIsVoid(0);
         }
      });

      super.insertList(SAPInterfaceQuery.INSERT_INVOICE_AND_CREDIT_NOTE_ENTRY_DETAILS.getQueryId(),
            invoiceAndCreditNote.getInvoiceCreditNoteEntryList(), sqlSession);

      super.updateData(SAPInterfaceQuery.UPDATE_INVOICE_NUMBER.getQueryId(),
            invoiceAndCreditNote.getInvoiceCreditNoteEntryList(), sqlSession);

      return invoiceAndCreditNote;
   }

   @Override
   public List<InvoiceSentByEmail> getInvoiceSentByEmail() throws CustomException {
      return super.fetchList("sqlGetInvoiceSentByEmailDetails", null, sqlSession);
   }

   @Override
   public List<String> fetchEmail(InvoiceSentByEmail sapInvCreditNote) throws CustomException {
      return super.fetchList("getEmailId", sapInvCreditNote, sqlSession);
   }

   @Override
   public void updateESupportDocEmailSent(InvoiceSentByEmail sapInvCreditNote) throws CustomException {
      super.updateData("sqlupdateESupportDocEmailSent", sapInvCreditNote, sqlSession);

   }

@Override
public String getCompanyCode() throws CustomException {
	 return fetchObject(SAPInterfaceQuery.GET_SALE_AND_DISTRIBUTION_COMPANY_CODE.getQueryId(), null, sqlSession);
}
}
