package com.ngen.cosys.impbd.model;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ApiModel
@Component
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeTo extends BaseBO {

   /**
    * serialVersionUID
    */
   private static final long serialVersionUID = 1L;
   private String authorize;
   private String userID;
   private String user;
   private int worksheetID;
}