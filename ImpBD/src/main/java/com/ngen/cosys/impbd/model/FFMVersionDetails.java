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
public class FFMVersionDetails extends BaseBO {
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	public BigInteger segmentId;

	public String messageSequence;

	public Integer messageVersion;

	public String messageStatus;

	public Integer messageCopy;
	
	public String messageVersionWithCopy;
}
