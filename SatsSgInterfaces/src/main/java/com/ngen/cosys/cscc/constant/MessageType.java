package com.ngen.cosys.cscc.constant;

public enum MessageType {
    FLIGHTINFO("CSCC_Flight"),
    KEYSHIPMENTINFO("CSCC_Shipment"),
    PILINFO("CSCC_Pharma"),
    ULDINFO("CSCC_ULD");
    
    private String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageType() {
        return this.messageType;
    }
}
