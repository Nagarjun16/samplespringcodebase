package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor 
public class CargoPreAnnouncementBulkShipment extends BaseBO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6605358056611865211L;
	
	private String shipmentType;
	private String handleingArea;
	private BigInteger totalShipments;
	

}
