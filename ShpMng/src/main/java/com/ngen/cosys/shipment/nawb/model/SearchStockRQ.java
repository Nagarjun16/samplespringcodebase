/**
 * 
 * SearchStockRQ.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0          29 January, 2018 NIIT      -
 */
package com.ngen.cosys.shipment.nawb.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
/**
 * This Model Class takes care of the Search Stock requests.
 * 
 * @author NIIT Technologies Ltd
 *
 */
@XmlRootElement
@ApiModel
@Component
@Setter
@Getter
@ToString
@NoArgsConstructor
public class SearchStockRQ extends BaseBO {
	private static final long serialVersionUID = 1L;
	private String stockCategoryCode;
	private String stockId;
	private String carrierCode;
	private String awbStockDetailsId;
}
