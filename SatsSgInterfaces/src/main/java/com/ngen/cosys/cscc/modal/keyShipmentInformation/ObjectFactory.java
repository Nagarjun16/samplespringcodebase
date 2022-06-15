package com.ngen.cosys.cscc.modal.keyShipmentInformation;


import com.ngen.cosys.cscc.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class ObjectFactory {
    public static Header createHeader() {
        Header header = new Header();
        header.setType("info");
        header.setMsgTime(Utils.getLocalDateTime());
        header.setUUID(UUID.randomUUID().toString());
        return  header;
    }

    public static ShipmentsItem createShipmentItem() {
        return new ShipmentsItem();
    }

    public static BodyItem createBodyItem() {
        BodyItem bodyItem = new BodyItem();
        bodyItem.setShipments(new ArrayList<>());
        return bodyItem;
    }

    public static Message createMessage() {
        return new Message();
    }

    public static KeyShipmentInformation createKeyShipmentInfo() {
        KeyShipmentInformation keyShipmentInformation = new KeyShipmentInformation();
        keyShipmentInformation.setMessage(createMessage());

        keyShipmentInformation.getMessage().setBody(new ArrayList<>());
        keyShipmentInformation.getMessage().setHeader(createHeader());

        return keyShipmentInformation;
    }
}
