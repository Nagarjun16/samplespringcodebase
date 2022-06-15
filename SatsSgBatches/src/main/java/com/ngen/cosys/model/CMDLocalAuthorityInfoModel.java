package com.ngen.cosys.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
@ApiModel
@NoArgsConstructor
@Validated
public class CMDLocalAuthorityInfoModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;
   private BigInteger shipmentMasterLocalAuthInfoId;
   private BigInteger customShipmentLocalAuthorityRequirementDetailId;
   private BigInteger customShipmentLocalAuthorityRequirementId;
   private BigInteger ShipmentMasterLocalAuthInfoDtlsId;
   private BigInteger shipmentMasterLocalAuthInfoDtlsId;
   private BigInteger shipmentId;
   private String referenceNumber;
   private String license;
   private String remarks;
   private BigInteger appointedAgent;
}
