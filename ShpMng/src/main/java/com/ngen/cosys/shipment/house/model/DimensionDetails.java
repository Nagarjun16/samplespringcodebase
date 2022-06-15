package com.ngen.cosys.shipment.house.model;


import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class DimensionDetails extends BaseBO {
   /**
    * Default generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger pcs;
   private BigInteger height;
   private BigInteger width;
   private BigInteger length;
   private BigDecimal volume;
   private String unitCode;
   private String volumeCode;
   private BigDecimal convertedVolume;
   private String manualScanReason;
   private String measurementUnitCode;
   private String weightCode;
   
}