package com.ngen.cosys.shipment.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This model class ShipmentMaster entity to show user shipment location details
 * and hold details
 * 
 * @author NIIT Technologies Ltd
 * @version 1.0
 */

@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LocationDuplicateCheck extends BaseBO {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flightId;
		result = prime * result + ((shipmentLocation == null) ? 0 : shipmentLocation.hashCode());
		result = prime * result + ((warehouseLocation == null) ? 0 : warehouseLocation.hashCode());
		result = result * result + ((partSuffix == null) ? 0 : partSuffix.hashCode());;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocationDuplicateCheck other = (LocationDuplicateCheck) obj;
		if (flightId != other.flightId)
			return false;
		if (shipmentLocation == null) {
			if (other.shipmentLocation != null)
				return false;
		} else if (!shipmentLocation.equals(other.shipmentLocation))
			return false;
		if (warehouseLocation == null) {
			if (other.warehouseLocation != null)
				return false;
		} else if (!warehouseLocation.equals(other.warehouseLocation))
			return false;
		if (partSuffix == null) {
			if (other.partSuffix != null)
				return false;
		} else if (!partSuffix.equals(other.partSuffix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocationDuplicateCheck [shipmentLocation=" + shipmentLocation + ", warehouseLocation="
				+ warehouseLocation + ", flightId=" + flightId + ", partSuffix=" + partSuffix + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String shipmentLocation;

	private String warehouseLocation;

	private int flightId;
	
	private String  partSuffix;
}
