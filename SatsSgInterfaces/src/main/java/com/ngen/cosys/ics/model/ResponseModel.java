package com.ngen.cosys.ics.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ResponseModel {
   private String status;
   private String errorNumber;
   private String errorDescription;

}
