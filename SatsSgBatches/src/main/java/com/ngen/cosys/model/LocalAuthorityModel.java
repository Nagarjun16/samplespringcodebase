package com.ngen.cosys.model;

import java.math.BigInteger;
import java.util.List;

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
public class LocalAuthorityModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;
   private BigInteger customShipmentLocalAuthorityRequirementId;
   private BigInteger shipmentMasterLocalAuthInfoId;
   private BigInteger customShipmentInfoId;
   private String deliveryOrderNo;
   private BigInteger shipmentId;
   private String localAuthorityType;
   private String cancellationReasonCode;
   private List<LocalAuthorityDetailModel> localAuthorityDetail;
}
