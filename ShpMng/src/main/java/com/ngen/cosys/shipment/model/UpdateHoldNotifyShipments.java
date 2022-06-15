package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.util.List;

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
public class UpdateHoldNotifyShipments extends BaseBO {
	   /**
	    * @author Raghav request model for Update Hold Notify shipment
	    */
	   private static final long serialVersionUID = 1L;
	   private List<HoldNotifyShipment> shipmentNumber;
	   private List<BigInteger> shipmentInventoryId; 
	   private boolean flagError;	
	   private String unHoldRemarks;

}
