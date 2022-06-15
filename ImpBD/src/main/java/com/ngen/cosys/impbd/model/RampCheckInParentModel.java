package com.ngen.cosys.impbd.model;

import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class RampCheckInParentModel extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String driverId;
   @Valid
   List<RampCheckInUld> uldInfoList;

}
