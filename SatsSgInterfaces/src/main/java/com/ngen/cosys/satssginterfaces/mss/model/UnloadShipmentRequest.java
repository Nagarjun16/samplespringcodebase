package com.ngen.cosys.satssginterfaces.mss.model;

import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnloadShipmentRequest extends BaseBO{
	
	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = -9124111307711685961L;

	@NgenAuditField(fieldName = "Unload Shipments")
//	@Valid
	private List<UnloadShipment> unloadShipments;
	Flight flight;
	Segment segment;
	private String assUldTrolleyNo;
	private Shipment shipmentDetails;
	private boolean flagError;
	private boolean FromUnload;
	private boolean FromAmendULD;
	private boolean fromUnload;

	private Boolean isTTCase;
	private String user;
}