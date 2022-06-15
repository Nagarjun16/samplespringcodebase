package com.ngen.cosys.satssginterfaces.mss.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

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
public class DLSUldtrolleySHCinfo  extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private BigInteger dlsUldTrolleyId;// DLSId
   private String specialHandlingCode;
   private Long dlsSHCId;// DLSSHCId
}