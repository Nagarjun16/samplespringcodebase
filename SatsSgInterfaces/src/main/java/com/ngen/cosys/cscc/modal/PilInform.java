package com.ngen.cosys.cscc.modal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * cr148_CargoInformation
 *
 * @author
 */
@Data
public class PilInform implements Serializable {
    private String awbNo;
    private List<String> flightNumberIN;
    private String flightNumberINs;
    private String towInTime;
    private String intoColdRoomTime;
    private List<String> uLDNumIN;
    private String uldNumIns;
    private boolean PHC;
    private boolean transshipment;
    private List<String> flightNumOut;
    private String flightNumOuts;
    private List<String> uldNumOut;
    private String uldNumOuts;
    private String outOfColdRoomTime;
    private String rampReleaseTime;
    private String XAF1;
    private String XAF2;
    private String XAF3;
}