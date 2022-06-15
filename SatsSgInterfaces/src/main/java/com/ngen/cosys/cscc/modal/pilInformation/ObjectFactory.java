package com.ngen.cosys.cscc.modal.pilInformation;
import com.ngen.cosys.cscc.utils.Utils;

import java.util.ArrayList;
import java.util.UUID;

public class ObjectFactory {
    public static Body createBody(){
        return new Body();
    }
    public static Header createHeader(){
        Header header = new Header();
        header.setType("info");
        header.setMsgTime(Utils.getLocalDateTime());
        header.setUUID(UUID.randomUUID().toString());
        return  header;
    }

    public static PilInformation createPilInformation(){
        PilInformation pilInformation = new PilInformation();
        pilInformation.setMessage(createMessage());
        pilInformation.getMessage().setBody(createBody());
        pilInformation.getMessage().setHeader(createHeader());
        pilInformation.getMessage().getBody().setPILs(new ArrayList<>());
        return pilInformation;
    }

    public static PILsItem createPilItem(){
        return new PILsItem();
    }
    public static Message createMessage(){
        return new Message();
    }

}
