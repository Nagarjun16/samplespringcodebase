/**
 * 
 * UserDetails.java
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
 * This model will give details of the user user chat details
 * 
 * @author NIIT Technologies Ltd
 */
@Getter
@Setter
@Component
@ToString
@NoArgsConstructor
public class UserDetails extends BaseBO {

   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   /**
    * short name of the user
    */
   private String userShortname;
   /**
    * login code of the user
    */
   private String userLoginCode;
   /**
    * encoded String
    */
   private String userProfilePicture;

}
