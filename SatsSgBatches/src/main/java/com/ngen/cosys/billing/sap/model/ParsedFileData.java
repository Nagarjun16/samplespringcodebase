package com.ngen.cosys.billing.sap.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Component
@ToString
public class ParsedFileData {

   private SAPFileInfo sapFileInfo = new SAPFileInfo();

   private List<InvoiceAndCreditNote> invoicAndCreditNoteDataList = new ArrayList<>();

   private List<MaterialInfo> materialInfoList = new ArrayList<>();

   private List<CustomerInfo> customerInfoList = new ArrayList<>();

   private List<SAPFileErrorInfo> proccessingExceptionList = new ArrayList<>();

   /*
    * Current line number during parse and total lines at the end of the parse
    */
   private int currentLineNo = 0;

   /*
    * currentInvoiceCreditNote define new document line in invoice and credit note
    * document
    */

   private InvoiceAndCreditNote currentInvoiceCreditNote;

   /*
    * currentInvoiceAndCreditNoteEntry define new entry in document
    */

   private InvoiceAndCreditNoteEntry currentInvoiceAndCreditNoteEntry;

   private InvoiceAndCreditNoteEntry currentEntryForNonMaterialType;

}
