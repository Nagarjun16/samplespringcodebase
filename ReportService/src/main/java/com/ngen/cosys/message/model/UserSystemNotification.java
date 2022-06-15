package com.ngen.cosys.message.model;

import java.math.BigInteger;
import java.time.LocalDateTime;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserSystemNotification extends BaseBO {
   /**
   * 
   */
   private static final long serialVersionUID = 1L;

   private BigInteger userGroupId;

   private BigInteger userSystemNotificationId;

   private String userGroupName;

   private BigInteger toUserId;

   private String message;

   private boolean read;

   private LocalDateTime readDate;

   private boolean important;

   private LocalDateTime messageExprity;

   private LocalDateTime deadline;

}
