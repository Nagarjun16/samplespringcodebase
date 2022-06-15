package com.ngen.cosys.impbd.shipment.verification.model;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
public class SearchRegulationDetails {

   private int dgRegulationId;
}