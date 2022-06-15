package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.util.List;

import org.springframework.stereotype.Component;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ApiModel
@Component
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class PrintTag extends TagRequest {

	private long stationId;
	private List<PrintTagInfo> traceableItems;

}
