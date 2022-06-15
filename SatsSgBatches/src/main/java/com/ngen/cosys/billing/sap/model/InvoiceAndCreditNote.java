package com.ngen.cosys.billing.sap.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

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
public class InvoiceAndCreditNote extends BaseBO {

   private static final long serialVersionUID = 1L;

   private long invoiceCrdtNoteId;

   private long sapFileInfoId;

   /**
    * Invoiced customer account no.
    */
   @NotEmpty(message = "g.customer.account.required")
   private String customerAccountNo;
   /**
    * Invoice / credit note number
    */
   @NotEmpty(message = "g.docs.number.required")
   private String documentNo;

   private String cancelIndicator;
   /**
    * any addtional header text in the invoice / credit node
    */
   private String documentMiscText;

   private List<InvoiceAndCreditNoteEntry> invoiceCreditNoteEntryList = new ArrayList<>();

   /**
    * grand total in the invoice / credit node
    */
   private double grandTotalAmount;

   /**
    * footer text text in the invoice / credit node
    * 
    */
   private String documentMiscFooterText;

   /**
    * document creator name in the invoice / credit node
    */
   @NotEmpty(message = "g.raise.by.required")
   private String raiseBy;

   private String creditTerm;

   private String customerName1;

   private String customerName2;

   private String customerName3;

   private String customerName4;

   private String street2;

   private String street3;

   private String customerStreet;

   private String poBox;

   private String customerCity;

   private String customerPostalCode;

   private String creditNoteTypeIndicator;

}
