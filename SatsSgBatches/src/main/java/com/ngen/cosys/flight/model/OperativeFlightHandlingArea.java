package com.ngen.cosys.flight.model;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
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
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OperativeFlightHandlingArea extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;

   private long fltHandlingAreaId;

   /**
    * flightId of operating flight.
    */
   private long flightId;

   private String terminalCode;
   private String flightKey;
}