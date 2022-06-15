package com.ngen.cosys.scheduler.model;

import java.math.BigInteger;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BatchJobDataMapModel extends BaseBO {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger jobId;
   private BigInteger jobDataMapId;
   private String key;
   private String value;
}