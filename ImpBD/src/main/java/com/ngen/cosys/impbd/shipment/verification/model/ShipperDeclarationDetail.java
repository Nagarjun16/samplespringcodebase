/**
 * ShipperDeclarationDetail.java
 * 
 * Copyright <PRE><IMG SRC = XX></IMG></PRE>
 *
 * Version      Date            Author      Reason
 * 1.0         06 MAR, 2018    NIIT        -
 */
package com.ngen.cosys.impbd.shipment.verification.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;
import com.ngen.cosys.impbd.shipment.verification.validator.DgDetailsSave;
import com.ngen.cosys.validator.annotations.CheckPieceConstraint;
import com.ngen.cosys.validator.annotations.CheckWeightConstraint;
import com.ngen.cosys.validator.enums.MandatoryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class takes care of the responsibilities related to the DG Declaration
 * screen data
 * 
 * @author NIIT Technologies Ltd
 *
 */

@Component
@ToString
@Getter
@Setter
@NgenCosysAppAnnotation
@NoArgsConstructor
@Validated
public class ShipperDeclarationDetail extends BaseBO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long expDgShipperDeclarationId;

	private Short dgdReferenceNo;

	private Long dgRegulationId;
	private Long copyDgRegulationId;
	//@Size(max = 4, message = "Enter 4 characters in dgSubriskCode1 Field")
	private String dgSubriskCode1;
	//@Size(max = 4, message = "Enter 4 characters in dgSubriskCode2 Field")
	private String dgSubriskCode2;
	//@Size(max = 4, message = "Enter 4 characters in packingGroupCode Field")
	private String packingGroupCode;
	@NotNull(message = "ERROR_ENTER_PIECES")
	//@Size(max = 4, message = "Enter 4 numbers in packagePieces Field")
	@CheckPieceConstraint(groups = { DgDetailsSave.class }, type = MandatoryType.Type.REQUIRED)
	private BigInteger packagePieces;
	@NotNull(message = "ERROR_ENTER_QUANTITY")
	//@Size(max = 8, message = "Enter 8 numbers in packageQuantity Field")
	@CheckWeightConstraint(groups = { DgDetailsSave.class }, mandatory = MandatoryType.Type.REQUIRED)
	private BigDecimal packageQuantity;
	//@Size(max = 250, message = "Enter 250 characters in packingType Field")
	private String packingType;
	//@Size(max = 4, message = "Enter 4 characters in packingInstructions Field")
	@NotEmpty(message = "ERROR_PACKAGING_INSTRUCTION_REQ")
	private String packingInstructions;
	//@Size(max = 3, message = "Enter 3 characters in packingInstructionCategory Field")
	private String packingInstructionCategory;
	//@Size(max = 4, message = "Enter 4 numbers in packageQuantity Field")
	private short transportIndex;
	//@Size(max = 4, message = "Enter 4 numbers in packingDimension1 Field")
	private short packingDimension1;
	//@Size(max = 4, message = "Enter 4 numbers in packingDimension2 Field")
	private short packingDimension2;
	//@Size(max = 4, message = "Enter 4 numbers in packingDimension3 Field")
	private short packingDimension3;
	//@Size(max = 4, message = "Enter 4 numbers in apioNumber Field")
	private short apioNumber;
	//@Size(max = 4, message = "Enter 4 numbers in overPackNumber Field")
	private short overPackNumber;
	//@Size(max = 15, message = "Enter 15 numbers in overPackNumber Field")
	private String authorizationDetail;
	private String properShippingName;
	@NotEmpty(message = "ERROR_ENTER_UNIDNUMBER")
	private String UNIDnumber;
	private String DGClassCode;
	
	private String packingRemarks;

	private List<String> packingGroupCodeList;

	private List<String> packingGroupInstructionList;

	private List<UNIDOverpackDetails> overPackDetails;

}
