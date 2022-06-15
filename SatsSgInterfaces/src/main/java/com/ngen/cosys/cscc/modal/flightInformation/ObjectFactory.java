package com.ngen.cosys.cscc.modal.flightInformation;

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

    public static FlightInformation createFlightInformation(){
        FlightInformation flightInformation = new FlightInformation();
        flightInformation.setMessage(createMessage());
        flightInformation.getMessage().setBody(createBody());
        flightInformation.getMessage().setHeader(createHeader());
        flightInformation.getMessage().getBody().setFlights(new ArrayList<>());
        return flightInformation;
    }

    public static FlightsItem createFlightItem(){
        return new FlightsItem();
    }

    public static Message createMessage(){
        return new Message();
    }

}
