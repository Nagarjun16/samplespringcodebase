
/**
 * @author Ashwin.Bantoo
 *
 */

package com.ngen.cosys.ics.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ngen.cosys.framework.model.BaseBO;

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
@JsonIgnoreProperties(value = { "deletedOn", "deletedBy", "deleteFlag", "createdBy", "createdOn", "modifiedBy", "modifiedOn", "flagUpdate", "flagDelete", "flagInsert", "flagSaved", "localDateFormat", "locale", "messageList", "flagCRUD", "tenantId",
      "terminal", "loggedInUser" })


@JacksonXmlRootElement(localName = "operativeFlightsRequest")
public class ICSUpdateOperativeFlightRequestModel extends BaseBO {
	
	   private static final long serialVersionUID = 1L;

	   @JacksonXmlElementWrapper(useWrapping = false)
	   @JacksonXmlProperty(localName = "flight")
	   private List<ICSOperativeFlightModel> operativeFlightList;
	}
