package com.ngen.cosys.ics.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JacksonXmlRootElement(localName = "preAnnouncementRequest")
public class ICSPreAnnouncementModel {

   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "ULD")
   private List<ULD> uld;
}