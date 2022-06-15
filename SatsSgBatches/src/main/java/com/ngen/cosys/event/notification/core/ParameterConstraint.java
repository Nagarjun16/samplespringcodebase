/**
 * ParameterConstraint.java
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.event.notification.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class is used for Notification Parameter Constraint
 * 
 * @author NIIT Technologies Ltd
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ParameterConstraint {

   // Notification Constraint field name
   private String constraint;
   // Constraints values of decision fields
   private String values;
   // Decision matcher value
   private boolean match;
   
}
