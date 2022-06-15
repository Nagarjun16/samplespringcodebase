/**
 * ListModel.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date         Author      Reason
 * 1.0          28 July, 2017   NIIT      -
 */
package com.ngen.cosys.flight.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class takes care for Listing Models for Operative Flight.
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
@Scope(scopeName = "prototype")
@JsonIgnoreProperties("deleteList")
public class ListModel extends BaseBO {
	/**
	 * The default serialVersionUID.
	 */
	 
	private static final long serialVersionUID = 1L;

	private List<OperativeFlight> createList;

	private List<OperativeFlight> cancelList;

	private List<OperativeFlight> deleteList;
}