/**
 * {@link ShipmentNotificationDetail}
 * 
 * @copyright SATS Singapore 2020-21
 * @author NIIT
 */
package com.ngen.cosys.impbd.notification;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Shipment Notification detail
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class ShipmentNotificationDetail extends BaseBO {

   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
   private BigInteger shipmentId;
   private String shipmentNumber;
   private LocalDate shipmentDate;
   private String origin;
   private String destination;
   private BigInteger pieces;
   private BigDecimal weight;
   private String customerName;
   private String customerType;
   private String contactTypeCode;
   private String contactTypeDetail;
   private String tenantId;
   
}
