package com.ngen.cosys.impbd.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author NIIT Technologies
 *
 */
@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class RampCheckInUldNumber  extends BaseBO{
   /**
    * The default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private BigInteger handOverId;
   private BigInteger impHandOverContainerTrolleyInformationId;
   private String uldNumber;
   private Boolean usedAsTrolley;
}
