package com.ngen.cosys.service.volumetricscanner.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VolumetricResponse extends BaseBO {

   private static final long serialVersionUID = 1L;
   
   
   private long messageId;
   
}
