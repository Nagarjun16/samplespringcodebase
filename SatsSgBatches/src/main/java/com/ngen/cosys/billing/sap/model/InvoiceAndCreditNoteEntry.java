package com.ngen.cosys.billing.sap.model;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
public class InvoiceAndCreditNoteEntry extends BaseBO {

   private static final long serialVersionUID = 1L;

   private long invoiceCrdtNoteId;

   private long invoiceCrdtNoteEntryId;

   private String finSysInvoiceNo;

   private String cosysBillingNumber;

   @NotEmpty(message = "g.material.code.required")
   private String materialCode;

   @NotEmpty(message = "g.material.desc.required")
   private String materialDescription;

   private double quantity;

   @NotEmpty(message = "g.unit.measure.required")
   private String unitOfMeasure;

   private double rate;

   private double amount;

   private String materialMiscTxt;

   private double totalQuantity;

   private double totalRate;

   private double totalAmount;

   private String creditNoteTypeIndicator;

   private int lineSequenceNumber;

   private int billingNumber;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime invoiceDate;
   
   private int isVoid;

}
