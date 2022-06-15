/**
 * 
 * FlightAssociatedRemarksBO.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 18 January, 2018 NIIT -
 */
package com.ngen.cosys.shipment.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This model class is the Response for Assign Role Functions.
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */
@ApiModel
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FlightAssociatedRemarksBO extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   /**
    * The FlightId
    */
   private long flightId;
   /**
    * This field contains flight number
    */
   @Length(min = 4, max = 7, message = "g.flight.key.len")
   @Pattern(regexp = "^[A-Z0-9]{4,7}", message = "flight.invalid.flight.no")
   private String flightKey;
   private String shipmentNumber;
   private String remarkType;
   private String shipmentRemarks;
   /**
    * This field contains flight date
    */
   private LocalDateTime flightDate;
   private String flightSource;
   private String flightDestination;
   private String segments;
   /**
    * The Shipment remarks for a given flight
    */
   private List<MaintainRemark> remarks=new ArrayList<MaintainRemark>();

}