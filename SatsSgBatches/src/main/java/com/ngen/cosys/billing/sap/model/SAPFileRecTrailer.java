package com.ngen.cosys.billing.sap.model;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Component
@ToString
public class SAPFileRecTrailer extends SAPFileRecord {

   private static final long serialVersionUID = 1L;

   private int totalLineCount;

}
