package com.ngen.cosys.impbd.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DisplayIncomigFlightConfigurationTime extends BaseBO {



	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;

	private Long toDate;

	private Long fromDate;

}
