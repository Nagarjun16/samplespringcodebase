package com.ngen.cosys.shipment.terminaltoterminalhandover.model;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Validated
public class TerminalPoint extends BaseBO {
   /**
    * Default serialVersionUID.
    */
   private static final long serialVersionUID = 1L;
   private String toTrml;
   private String fromTrml;

   private TerminalPointDetails terminalPointDetails;
}