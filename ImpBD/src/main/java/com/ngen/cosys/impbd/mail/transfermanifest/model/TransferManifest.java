package com.ngen.cosys.impbd.mail.transfermanifest.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class TransferManifest extends BaseBO{/**
    * 
    */
   private static final long serialVersionUID = 1L;
   
   private List<TransferCarrierDetails> details;

}
