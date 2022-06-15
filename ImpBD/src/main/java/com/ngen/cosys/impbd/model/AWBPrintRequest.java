package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckShipmentNumberConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@ToString
@Setter
@Getter
@NoArgsConstructor
public class AWBPrintRequest extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   /**
    * AWB number
    */
   @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
   private String awbNumber;
   private List<String> awbNumbers;
   /**
    * Tells printed copy or a original document
    */
   private boolean photoCopy;

   private boolean documentReceivedFlag;

   private String photoCopyValue;

   private String documentReceivedValue;
   /**
    * Flight Number
    */
   private String flight;
   /**
    * Flight Departure time
    */
   private LocalDate flightDate;

   private List<Flight> flightDetails = new ArrayList<>();

   private String flightKeyId;
   private String printerName;
   private String printerForAT;
   private String flightOffPoint;
   private String destination;
   private String carrierCode;
   private BigInteger shipmentId;
   private String fltKey;
   
   private int firstFourDigitsAfterPrefix;

}
