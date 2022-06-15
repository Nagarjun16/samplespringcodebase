package com.ngen.cosys.shipment.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotNull;
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
public class UpdateHoldNotifyGroup extends BaseBO {
	   /**
	    * @author Raghav request model for Update Hold Notify shipment
	    */
	   private static final long serialVersionUID = 1L;
	   private boolean flagError;
	   @NotNull(message = "awb.hold.notify.group.req")
	   private String holdNotifyGroup;
	   private List<HoldNotifyShipment> shipmentNumber;

}
