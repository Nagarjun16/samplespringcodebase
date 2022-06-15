package com.ngen.cosys.message.lyn.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LynMessage extends BaseBO {

	private LocalDateTime timeToTrigger;
	private String dayToTrigger1;
	private String dayToTrigger2;
	private String dayToTrigger3;
	private String dayToTrigger4;
	private String dayToTrigger5;
	private String dayToTrigger6;
	private String dayToTrigger7;
	private BigInteger interfaceMessageDefinitionByCustomerId;
	private String messageType;
	private List<LynMessageCustomerDetails> lynMessageCustomerDetails;
	private String channelType;
	private String messageFormat;
}
