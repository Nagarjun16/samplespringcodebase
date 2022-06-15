package com.ngen.cosys.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement
@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class OCIModel extends BaseBO {
   
   private static final long serialVersionUID = 1L;
    
   private String countryCode;   
   private String informationIdentifier;   
   private String csrciIdentifier;   
   private String scsrcInformation;      

}