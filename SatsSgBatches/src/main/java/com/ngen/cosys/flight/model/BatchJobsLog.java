package com.ngen.cosys.flight.model;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BatchJobsLog {
   private long sequenceID;
   private long jobId;
   private long totalRecords;
   private long successRecords;
   private long failureRecords;
   private LocalDateTime startDateTime;
   private LocalDateTime endDateTime;
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("BatchJobsLog[")
      .append("sequenceID=").append(this.sequenceID)
      .append(", jobId=").append(this.jobId)
      .append(", totalRecords=").append(this.totalRecords)
      .append(", successRecords=").append(this.successRecords)
      .append(", failureRecords=").append(this.failureRecords)
      .append(", startDateTime=").append(this.startDateTime.toString())
      .append(", endDateTime=").append(this.endDateTime.toString())
      .append("]");
      return sb.toString();
   }
}