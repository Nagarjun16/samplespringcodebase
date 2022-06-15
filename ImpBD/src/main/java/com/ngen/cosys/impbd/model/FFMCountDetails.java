package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
public class FFMCountDetails extends BaseBO {

	private static final long serialVersionUID = 1L;

	public String segmentReceived;

	public String segmentRejected;
	
	public String boardPoint;
	
	public String carrierCode;
	
	public String flightDigits;
	
	public LocalDateTime std;
	
	public String messageType;
	
	public String messageStatus;
	
	public BigInteger segmentId;

	List<FFMVersionDetails> ffmVersionDetails;

}
