/**
 * {@link IVRSFaxReportParams}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.service.ivrs.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * IVRS Fax Report Params
 * 
 * @author NIIT Technologies Ltd
 *
 */
@NoArgsConstructor
@Getter
@Setter
public class IVRSFaxReportParams {

   private BigInteger shipmentId;
   private String shipmentNumber;
   private String shipmentDate;
   private String origin;
   private String destination;
   private BigInteger pieces;
   private BigDecimal weight;
   private String consigneeName;
   private String customerCode;
   private String customerName;
   private String flightKey;
   private LocalDate arrivedDate;
   
}
