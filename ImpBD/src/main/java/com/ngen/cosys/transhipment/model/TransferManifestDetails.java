/**
 * This class represents a model to print TRM list
 */
package com.ngen.cosys.transhipment.model;

import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class TransferManifestDetails {
   
   private String awbNo;
   private String awbDest;
   private String noPackage;
   private String weight;
   private String weightUnitCode;
   private String remarks;
   
}