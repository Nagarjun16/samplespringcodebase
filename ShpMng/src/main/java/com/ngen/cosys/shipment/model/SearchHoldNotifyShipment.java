package com.ngen.cosys.shipment.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ngen.cosys.JsonSerializer.LocalDateTimeSerializer;
import com.ngen.cosys.audit.NgenAuditField;
import com.ngen.cosys.audit.NgenAuditFieldName.NgenAuditFieldNameType;
import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@XmlRootElement
@ToString
@Getter
@Setter
@NoArgsConstructor
public class SearchHoldNotifyShipment extends BaseBO {
	   /**
	    * @author Raghav request model for search Hold Notify shipment
	    */
	   private static final long serialVersionUID = 1L;
	   private String terminalPoint;
	   private String holdNotifyGroup;
	   private String shipmentNumber;
	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime from;
	   @JsonSerialize(using = LocalDateTimeSerializer.class)
	   private LocalDateTime to;
	   private boolean flagError;
	   private boolean utl;
	   private String acknowledge;

}
