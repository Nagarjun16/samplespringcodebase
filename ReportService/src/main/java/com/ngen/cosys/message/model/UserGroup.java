
/**
 * 
 * UserGroup.java
 * 
 * Copyright
 * 
 * <PRE>
 * <IMG SRC = XX></IMG>
 * </PRE>
 *
 * Version Date Author Reason 1.0 22th August, 2018 NIIT -
 */
package com.ngen.cosys.message.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * This model will give details of group user chat details
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class UserGroup extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;

   /**
    * Name of the role
    */
   private String groupName;
   /**
    * Id of the role
    */
   private String groupID;

}
