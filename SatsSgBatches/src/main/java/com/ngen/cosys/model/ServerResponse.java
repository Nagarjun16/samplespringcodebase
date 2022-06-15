package com.ngen.cosys.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServerResponse {
   private int statusCode;
   private Object data;
}