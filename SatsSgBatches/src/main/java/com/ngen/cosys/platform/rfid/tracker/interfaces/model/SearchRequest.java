package com.ngen.cosys.platform.rfid.tracker.interfaces.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

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
@Validated
public class SearchRequest extends BaseBO{
   
	private static final long serialVersionUID = 1L;
	
	private String carrierCode;
	private String station;
	private String shc;
	private int rfidApplicable;
}
