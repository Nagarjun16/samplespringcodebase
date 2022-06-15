package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngen.cosys.framework.model.ErrorBO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@ToString
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackerFeedResponse{
//	private long statusCode;
//	private String message;
	
	private List<Object> data;
	private List<ErrorBO> messageList = new ArrayList<>();
	private boolean success;
	private boolean confirmMessage;
}
