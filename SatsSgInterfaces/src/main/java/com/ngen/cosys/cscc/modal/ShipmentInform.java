package com.ngen.cosys.cscc.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * cr148_CargoInformation
 * @author 
 */
@Data
public class ShipmentInform implements Serializable {
    private String flightNo;
    private String flightDate;
    private String importExport;
    private String AWBNo;
    private List<String> importULDS;
    private String importULDSString;
    private String SHC;
    private String NOG;
    private String origin;
    private String destination;
    private int pcs;
    private Object weight;
    private boolean bookingChanges;
    private boolean transshipment;
    private List<String> flightNoIn;
    private String flightNoIns;
    private List<String> flightNoOut;
    private String flightNoOuts;
    private String acceptanceTime;
    private String buildUpTime;
    private String AWBReceivedTime;
    private String AWBScannedTime;
    private List<String> loadedInfo;
    private String loadedInfos;
    private List<String> locationInfo;
    private String locationInfos;
    private boolean screenFlag;
    private String screenedBy;
    private String screenedAt;
    private String offloadFinalizationTiming;
    private String rampReceivedTiming;
    private String XAF1;
    private String XAF2;
    private String XAF3;
}