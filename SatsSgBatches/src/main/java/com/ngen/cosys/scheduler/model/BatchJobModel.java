package com.ngen.cosys.scheduler.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class BatchJobModel extends BaseBO {
   /**
    * System generated serial version id
    */
   private static final long serialVersionUID = 1L;

   private BigInteger jobId;

   private String jobName;
   private String jobGroup;
   private String cronExpression;
   private String jobClazz;
   private String jobStatus;

   private Boolean active;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime jobScheduleTime;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime jobNextFireTime;

   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime jobLastFireTime;

   private List<BatchJobDataMapModel> jobDataMap;
   
   private List<String> messages;
   
   @JsonSerialize(using = LocalDateTimeSerializer.class)
   private LocalDateTime datetoUpdate; 
   
   private Integer diffInMins;
   
   private Integer jobNotRunningFlag = 0;
}