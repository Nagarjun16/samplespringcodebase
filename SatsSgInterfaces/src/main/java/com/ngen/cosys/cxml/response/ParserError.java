package com.ngen.cosys.cxml.response;

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This object holds errors which are being foreseen during message parsing
 * 
 * @author NIIT
 *
 */

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ParserError implements Serializable {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;
   // Running sequence if required to persist
   private BigInteger sequence;
   // If any error code for application processing
   private String code;
   // Error detail
   private String description;
   // Actual message for which this error is reported
   private String message;

   public ParserError(String description, String message) {
      this.description = description;
      this.message = message;
   }

   public ParserError(String code, String description, String message) {
      this.code = code;
      this.description = description;
      this.message = message;
   }
}