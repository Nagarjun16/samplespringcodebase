/**
 * 
 */
package com.ngen.cosys.ics.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
@NoArgsConstructor
/**
 * @author Ashwin.Bantoo
 *
 */
@JacksonXmlRootElement(localName = "operativeFlightsResponse")
public class ICSUpdateOperativeFlightResponseModel {	


	   @JacksonXmlProperty(localName = "status")
	   private String status;

	   @JsonInclude(content = Include.NON_NULL)
	   @JacksonXmlProperty(localName = "errorNumber")
	   private String errorNumber;

	   @JsonInclude(content = Include.NON_NULL)
	   @JacksonXmlProperty(localName = "errorDescription")
	   private String errorDescription;
	}
