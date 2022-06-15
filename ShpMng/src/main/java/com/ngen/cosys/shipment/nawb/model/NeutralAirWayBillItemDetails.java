package com.ngen.cosys.shipment.nawb.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter
@Setter
@Component
@ToString
@ApiModel
@NoArgsConstructor
public class NeutralAirWayBillItemDetails extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String noPieces;
    private String grossWeight;
    private String kg;
    private String rateClass;
    private String itemNo;
    private String chargableWeight; 
    private String rateCharges;
    private String total;
	private String otherCharges;
}