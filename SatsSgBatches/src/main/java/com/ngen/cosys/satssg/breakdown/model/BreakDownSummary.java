package com.ngen.cosys.satssg.breakdown.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.ngen.cosys.annotation.NgenCosysAppAnnotation;
import com.ngen.cosys.framework.model.BaseBO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BreakDownSummary extends BaseBO {
    private BigInteger flightId;

    private BigInteger impBreakDownSummaryId;

    private BigInteger feedbackForStaff;

    private String breakDownStaffGroup;

    private String reasonForWaive;

    private String reasonForDelay;

    private Boolean liquIdatedDamagesWaived;

    private Boolean liquIdatedDamageApplicable;

    private Boolean approvedLDWaive;

    private String approvedLDWaiveApprovedBy;

    private Date approvedLDWaiveApprovedOn;

    private Boolean approvedLDApplicable;

    private String approvedLDApplicableApprovedBy;

    private Date approvedLDApplicableApprovedOn;
    
    @Valid
    @NgenCosysAppAnnotation
    private List<BreakDownSummaryTonnageHandledModel> tonnageHandlingInfo;
}