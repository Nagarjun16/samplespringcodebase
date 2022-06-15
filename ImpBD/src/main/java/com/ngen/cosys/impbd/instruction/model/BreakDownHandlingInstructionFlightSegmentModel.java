package com.ngen.cosys.impbd.instruction.model;

import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.model.SegmentModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@NgenCosysAppAnnotation
public class BreakDownHandlingInstructionFlightSegmentModel extends SegmentModel {

   /**
    * System Generated Serial Version id
    */
   private static final long serialVersionUID = -8060304197189691211L;

   @Valid
   private List<BreakDownHandlingInstructionShipmentModel> shipments;

}
