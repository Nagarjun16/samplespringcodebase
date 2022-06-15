package com.ngen.cosys.impbd.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

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
public class Flight extends BaseBO {
   private static final long serialVersionUID = 1L;

   private String flightId;

   private String flightKey;
}