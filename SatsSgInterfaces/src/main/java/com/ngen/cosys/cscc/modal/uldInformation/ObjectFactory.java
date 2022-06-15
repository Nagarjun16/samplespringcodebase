package com.ngen.cosys.cscc.modal.uldInformation;

import com.ngen.cosys.cscc.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class ObjectFactory {
    public static Body createBody() {
        return new Body();
    }

    public static Header createHeader() {
        Header header = new Header();
        header.setType("info");
        header.setMsgTime(Utils.formatDateTime(Utils.getTenantLocalDateTime(),
                Utils.DATE_TIME_FORMAT_SECOND));
        header.setUUID(UUID.randomUUID().toString());
        return header;
    }

    public static ULDInformation createULDInformation() {
        ULDInformation uldInformation = new ULDInformation();
        uldInformation.setMessage(createMessage());
        uldInformation.getMessage().setBody(createBody());
        uldInformation.getMessage().setHeader(createHeader());
        uldInformation.getMessage().getBody().setULDs(new ArrayList<>());
        return uldInformation;
    }

    public static ULDsItem createULDsItem() {
        return new ULDsItem();
    }

    public static Message createMessage() {
        return new Message();
    }

}
