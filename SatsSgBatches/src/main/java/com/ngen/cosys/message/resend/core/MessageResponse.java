/**
 * {@link MessageResponse}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.core;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Message Response Details
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class MessageResponse {

   private HttpStatus httpStatus;
   private Object body;
   private String errorMessage;
   
}
