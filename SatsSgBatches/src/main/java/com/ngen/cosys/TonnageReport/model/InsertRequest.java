package com.ngen.cosys.TonnageReport.model;

import java.time.LocalDate;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class InsertRequest extends BaseBO {

	private String document;
	private String documentName;
	private String entityType;
	private String entityKey;
	private String entityDate;
	private LocalDate reportGenDateTime;
	private Integer uploadedDocId;
	private boolean autoPublish;
	private Integer templateID;
}
