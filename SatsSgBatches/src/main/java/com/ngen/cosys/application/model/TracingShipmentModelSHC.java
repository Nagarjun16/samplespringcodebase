package com.ngen.cosys.application.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TracingShipmentModelSHC extends BaseBO{

	private String shcCodes;
	private BigInteger masterSHCId;
	private BigInteger ComTracingShipmentInfoId;
}
