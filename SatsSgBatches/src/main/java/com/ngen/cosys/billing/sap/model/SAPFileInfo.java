package com.ngen.cosys.billing.sap.model;

import java.util.Date;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
@Component
@ToString
public class SAPFileInfo extends SAPFileRecord {

   private static final long serialVersionUID = 1L;

   private long sapFileInfoId;

   @NotEmpty(message = "g.file.name.required")
   private String fileName;

   @NotEmpty(message = "g.file.date.required")
   private String fileCreationDt;

   private String interfaceType;

   private int totalLineCount;

   private int controlTotalCount;

   private Date processStDt;

   private Date processedOnDt;

   private String processedStatus;

   private String processingInfo;

   private String createdUserCode;

}
