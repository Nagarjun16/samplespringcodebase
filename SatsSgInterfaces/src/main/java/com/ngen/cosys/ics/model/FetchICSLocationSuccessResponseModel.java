package com.ngen.cosys.ics.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@Getter
@Setter
@NoArgsConstructor
@ApiModel
@JacksonXmlRootElement(localName = "fetchPCHSLocationResponse")
public class FetchICSLocationSuccessResponseModel {

   @JacksonXmlProperty(localName = "status")
   public String status;

   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "containerInfo")
   private List<FetchICSLocationContainerInfoModel> containerInfo;

}