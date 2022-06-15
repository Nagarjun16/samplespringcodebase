package com.ngen.cosys.impbd.mail.transfermanifest.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class TransferCarrierResponse extends BaseBO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dispatchNumber;
	private String origin;
	private String destination;
	private String transferCarrier;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime flightDate;
	private String flightKey;
    private List<TransferCarrierDetails> transferCarrierDetails;
	private boolean select;
	private BigInteger shipmentId;

}
