package com.ngen.cosys.ics.model;

import org.hibernate.validator.constraints.NotBlank;
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
@JacksonXmlRootElement(localName = "ShipmentInfoRequest")
public class CoolPortShipmentRequestModel {

   @JacksonXmlProperty(localName = "User")
   @NotBlank(message = "user.name.cant.be.blank")
   private String user;

   @JacksonXmlProperty(localName = "Password")
   @NotBlank(message = "password.cant.be.blank")
   private String password;

   @JacksonXmlProperty(localName = "SystemName")
   private String systemName;

   @JacksonXmlProperty(localName = "Purpose")
   private String purpose;

   @NotBlank(message = "awb.cant.be.blank")
   // @CheckShipmentNumberConstraint(mandatory = MandatoryType.Type.REQUIRED)
   @JacksonXmlProperty(localName = "AWBNumber")
   private String awbNumber;

   @JacksonXmlElementWrapper(useWrapping = false)
   @JacksonXmlProperty(localName = "TimeRange")
   private TimeRange timeRange;

}
