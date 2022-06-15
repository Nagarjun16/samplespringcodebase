package com.ngen.cosys.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class CustomerModel extends BaseBO {
   /**
    * Auto generated SerialVersionUID
    */
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentId;
   private BigInteger shipmentCustomerInfoId;
   private BigInteger customShipmentCustomerInfoId;
   private BigInteger customShipmentInfoId;
   private BigInteger appointedAgent;
   private String customerType;
   private String customerName;
   private String streetAddress;
   private String place;
   private String postal;
   private String stateCode;
   private String countryCode;
}
