/**
 * 
 * MaintainRemark.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          3 Jan, 2018   NIIT      -
 */
package com.ngen.cosys.shipment.model;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This is the Base class for all the AWB Shipment List.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteRemarkBO extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * This field contains Shipment Number
    */
   @NotBlank(message = "g.Shipment.id.empty")
   private ArrayList<Integer> remarkIdList;
   /**
    * This field contains AWB Number
    */
   private String shipmentNumber;
  
}