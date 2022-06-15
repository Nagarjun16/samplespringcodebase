package com.ngen.cosys.satssginterfaces.mss.model;

import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class RequestPreannouncementUldMessagesModel extends BaseBO {

   /**
    * Auto generated serial version id
    */
   private static final long serialVersionUID = 1L;
   /**
    * Date which will be sending as request
    */
   private String flightKey;
   /**
    * Date which will be sending as request
    */
   private LocalDate date;
}