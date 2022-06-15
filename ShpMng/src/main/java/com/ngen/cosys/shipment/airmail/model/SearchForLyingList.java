package com.ngen.cosys.shipment.airmail.model;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.validator.annotations.CheckAirportCodeConstraint;
import com.ngen.cosys.validator.annotations.CheckULDTypeConstraint;
import com.ngen.cosys.validator.annotations.CheckValidFlightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@Component
@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchForLyingList extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Carrier
	 */
	@NotNull(message="g.mandatory")
	private String carriercode;
	
	/**
	 * Destination
	 */
	@NotNull(message="g.mandatory")
	@CheckAirportCodeConstraint(mandatory=MandatoryType.Type.NOTREQUIRED)
	private String destination;
	
	/**
	 * Uld/Trolley
	 */
	@NotNull(message = "g.mandatory")
	private String uldOrTrolley;

}

