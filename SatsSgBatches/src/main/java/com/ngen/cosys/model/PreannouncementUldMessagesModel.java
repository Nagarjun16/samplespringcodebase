package com.ngen.cosys.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete",
      "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId", "terminal", "loggedInUser" }) 
public class PreannouncementUldMessagesModel extends BaseBO {
   /**
    * system generated serial version id
    */
   private static final long serialVersionUID = 1L;
   /**
   *
   * This field contains Transaction Sequence Number
   */
   @JsonProperty(value = "TRA_REF_NUM") 
   private String transactionSequenceNumber;
   /**
   *
   * This field contains uldType
   */
   private String uldType;
   /**
   *
   * This field contains uldNumber
   */
   private String uldNumber;
   /**
   *
   * This field contains uldCarrier
   */
   private String uldCarrier;
   /**
   *
   * This field contains Container Destination
   */
   private String containerDestination;
   /**
   *
   * This field contains In Carrier
   */
   private String inCarrier;
   /**
   *
   * This field contains In Flight
   */
   private String inFlightNumber;
   /**
   *
   * This field contains In Flight
   */
   private LocalDate inFlightDate;
   /**
   *
   * This field contains Indicative Outing Carrier For Container
   */
   private String indicativeOutgoingCarrierForContainer;
   /**
   *
   * This field contains indicative outing flight number for container
   */
   private String indicativeOutgoingFlightNumberForContainer;
   /**
   *
   * This field contains indicative outing flight date container
   */
   private LocalDate indicativeOutgoingFlightDateForContainer;
   /**
   *
   * This field contains intact indicator for delivery
   */
   private Boolean intactIndicatorForlocalDelivery;
   /**
   *
   * This field contains transit intact indicator
   */
   private Boolean transitIntactIndicator;
   /**
   *
   * This field contains short transit indicator
   */
   private String shortTransitIndicator;
   /**
   *
   * This field contains remarks for container
   */
   private String remarksForContainer;   
}
