package com.ngen.cosys.mailbag.overview.information.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

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
public class MailbagCorrectionReq extends BaseBO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mailbagNumber;
	
}
