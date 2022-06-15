package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
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
public class AssignULD extends BaseBO {
	private static final long serialVersionUID = 1L;
	private Segment segment;
	@Valid
	private ULDIInformationDetails uld;
//	@Valid
	private List<AssignULD> piggyBackULDList;
	private BigInteger assUldTrolleyPiggyBackId;
	private BigInteger assUldTrolleyId;
	private String flightKey;
	private LocalDate flightOriginDate;
	private boolean errorFlag;
	private boolean warningFlag;
	private int uldNumberLength;
	private String flagCRUD="R";
	BigInteger flightId;
	private boolean dlsInd =true;
}
