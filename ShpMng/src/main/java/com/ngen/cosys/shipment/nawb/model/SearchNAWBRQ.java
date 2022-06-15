/**
 * 
 * SearchNAWBRQ.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 29 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.nawb.model;

import java.math.BigInteger;
import java.time.LocalDate;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.annotation.InjectShipmentDate;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This Model Class takes care of the Search NAWB requests.
 * 
 * @author NIIT Technologies Ltd
 *
 */

@XmlRootElement
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class SearchNAWBRQ extends BaseBO {

   private static final long serialVersionUID = 1L;

   private BigInteger sidHeaderId;
   private String awbNumber;
   private String neutralAWBId;
   private String neutralAWBCustomerInfoId;
   private String neutralAWBCustomerAddressInfoId;

   @InjectShipmentDate(shipmentNumberField = "awbNumber")
   @JsonSerialize(using = LocalDateSerializer.class)
   private LocalDate awbDate;

}