package com.ngen.cosys.impbd.summary.model;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TracingUserDetailsModel extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private String userType;
   private String userLoginCode;
   private String staffIdNumber;
   private String userShortName;

}