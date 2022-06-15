package com.ngen.cosys.impbd.model;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies Ltd.
 *
 */
@ApiModel
@Getter
@ToString
@Setter
@NoArgsConstructor
public class RampCheckInPiggybackModel extends BaseBO {
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   
   private BigInteger impRampCheckInId;
   @Valid
   private List<RampCheckInPiggybackList> uldNumberList;
   
}
