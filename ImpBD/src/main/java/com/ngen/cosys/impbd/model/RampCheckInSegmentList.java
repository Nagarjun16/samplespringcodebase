package com.ngen.cosys.impbd.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RampCheckInSegmentList extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String boardPoint;

	private String offPoint;
}
