package com.ngen.cosys.service.volumetricscanner.model;


import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateSerializer;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ScannedImage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScannedImage extends BaseBO {

    private static final long serialVersionUID = 1L;

    private BigInteger uploadDocId;
    private String referenceId;
    private String messageReferenceId;
    private String associatedTo;
    private String stage;
    private String document;
    private String remarks;
    private String documentName;
    private String documentSize;
    // Document Type is CLAIM DOCUMENT/CUSTOMS CLEARANCE
    private String documentType;
    // Document Type Description
    private String documentTypeDescription;
    // Document Format is PDF/XLS/PNG
    private String documentFormat;
    //
    private String documentDescription;

    private boolean isDeleted;
    //
    
    private String entityType;
    private String entityKey;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate entityDate;

    private BigInteger flightId; // Nullable

    private String userCode;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime documentTime;

}