package com.ngen.cosys.cscc.modal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.ngen.cosys.framework.model.BaseBO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * cr148_CargoInformation
 *
 * @author
 */
@Getter
@Setter
public class FlightInform extends BaseBO implements Serializable {
    private String flightNo;
    private String flightDate;
    private String importExport;
    private String STA;
    private String ETA;
    private String ATA;
    private String flightType;
    private String aircraftType;
    private String bay;
    private String segment;
    private String REG;
    private String STD;
    private String ETD;
    private String ATD;
    private Integer palletTotal;
    private Integer palletCargoOffer;
    private Integer palletCargoUsed;
    private Integer containerTotal;
    private Integer containerCargoOffer;
    private Integer containerCargoUsed;
    private String lastULDTowIn;
    private String BDComplete;
    private String docComplete;
    private String notoc;
    private String manifest;
    private String pouchComplete;
    private String dls;
    private String firstDLS;
    private String releaseTimingULD;
    private int NOTOCEntries;
    private String flightComplete;
    private String FFMReceived;
    private boolean FDL;
    private String XAF1;
    private String XAF2;
    private String XAF3;
}