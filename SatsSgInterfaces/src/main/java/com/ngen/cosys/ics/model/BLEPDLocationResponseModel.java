package com.ngen.cosys.ics.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@JacksonXmlRootElement(localName = "PDLocationResponse")
public class BLEPDLocationResponseModel {

   @JacksonXmlElementWrapper(localName = "PDLocation")
   @JacksonXmlProperty(localName = "LocationInfo")
   private List<BLEPD> locationInfo;



}
