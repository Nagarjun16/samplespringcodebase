package com.ngen.cosys.mailbag.overview.information.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Component
@Setter
@Getter
@NoArgsConstructor
public class MailbagOverviewSummary extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dns;
	private String dispatchNumber;
	private BigInteger shipmentId;
	private String originOE;
	private String destinationOE;
	private String nextDestination;
	private BigInteger shpPieces;
	private BigDecimal shpWeight;
	private StringBuilder storeLocations;
	private StringBuilder warehouseLocations;
	private StringBuilder manifestedUldTrolleys;
	private String remarks;
	private String status;
	private String outingStatus;
	private String details;
	private String acceptedBy;
//	private String mailbagNumber;
	
	private List<MailbagOverviewDetail> mailbagDetails;
	
	private String natureOfDamage;
		
	private String severity;
	
    private Boolean embargoFlag;
    
	private Boolean damageFlag;
	
	private BigInteger shipmentHouseID;

}
