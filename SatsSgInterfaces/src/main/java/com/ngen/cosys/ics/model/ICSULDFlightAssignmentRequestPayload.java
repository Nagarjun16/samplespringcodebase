package com.ngen.cosys.ics.model;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@ApiModel
@ToString
@Getter
@Setter
@NoArgsConstructor

public class ICSULDFlightAssignmentRequestPayload extends BaseBO {

   private static final long serialVersionUID = 1L;

   private LocalDateTime assignedAt;
   private String assignedBy;
   private String createdBy;
   private LocalDateTime createdOn;
   private Integer eventOutboundULDAssignedToFlightStoreId;
   private Integer flightId;
   private String lastModifiedBy;
   private LocalDateTime lastModifiedOn;
   private String status;
   private String uldNumber;
}