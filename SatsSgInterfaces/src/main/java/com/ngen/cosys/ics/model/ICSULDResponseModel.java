package com.ngen.cosys.ics.model;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class ICSULDResponseModel {

   private String uldId;
   private String cargoTerminal;
   private String containerId;
   private Boolean damageStatus;
}