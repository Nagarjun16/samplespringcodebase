package com.ngen.cosys.TonnageReport.model;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class TonnageDetails extends BaseBO{

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean autoPublish;
	private String carrier;
}
