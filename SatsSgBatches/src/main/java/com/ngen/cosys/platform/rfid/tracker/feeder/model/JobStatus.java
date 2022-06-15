package com.ngen.cosys.platform.rfid.tracker.feeder.model;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ngen.cosys.framework.model.BaseBO;

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
public class JobStatus extends BaseBO{
	private boolean jobEnabled;
	@JsonFormat(timezone = "Asia/Singapore", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date currentJobRun;
	@JsonFormat(timezone = "Asia/Singapore", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastJobRun;
	private long lastSeqNo;
	private String printTagService;
	private String deleteTagService;
	private String updateTagService;
}
