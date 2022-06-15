package com.ngen.cosys.transhipment.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.transhipment.validator.group.TransferManifestByAWBMaintain;
import com.ngen.cosys.transhipment.validator.group.TranshipmentCancelForAWB;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class TranshipmentTransferManifestByAWB extends BaseBO {

	/**
	 * System generated serial version id
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message = "data.mandatory.required", groups = TransferManifestByAWBMaintain.class)
	private String carrierCodeFrom;

	@NotNull(message = "data.mandatory.required", groups = TransferManifestByAWBMaintain.class)
	private String carrierCodeTo;

	private String trmNumber;

	private BigInteger transTransferManifestByAwbId;

	private String handlingTerminalCode;

	private String airlineNumber;

	private String issuedBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime issuedDate;

	@NotNull(groups = TranshipmentCancelForAWB.class, message = "g.required.")
	private String cancellationReason;

	private String cancelledBy;

	private Boolean finalizedFlag;

	private String finalizedBy;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime finalizedDate;

	private Boolean printedFlag;

	private String printedBy;

	private LocalDate printedDate;

	private Boolean rePrintedFlag;

	private String rePrintedBy;

	private LocalDate rePrintedDate;

	private String rejectionReason;

	private String handoverTo;

	private String signatureOfReceiver;

	private boolean isError;

	@NgenCosysAppAnnotation
	@Valid
	private List<TranshipmentTransferManifestByAWBInfo> awbInfoList;

	private boolean select;

	private String printerName;

	private BigInteger shipmentId;

	private List<String> freightOutAwb;
	private Boolean allowFreightOutAwb = Boolean.TRUE;

	private String routingErrorPrompt;
	private Boolean allowRoutingErrorPrompt = Boolean.TRUE;

}