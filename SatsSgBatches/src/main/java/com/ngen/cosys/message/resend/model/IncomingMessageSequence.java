/**
 * {@link IncomingMessageSequence}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Incoming Message Sequence
 * 
 * @author NIIT Technologies Ltd
 */
@NoArgsConstructor
@Getter
@Setter
public class IncomingMessageSequence {

   private boolean typeASMSSM;
   private boolean typeASMSSMFFR;
   private boolean typeASMSSMFFMFFR;
   private boolean typeCMD;
   private boolean typeFFM;
   private boolean typeFFR;
   private boolean typeFWB;
   private boolean typeFHL;
   
}
