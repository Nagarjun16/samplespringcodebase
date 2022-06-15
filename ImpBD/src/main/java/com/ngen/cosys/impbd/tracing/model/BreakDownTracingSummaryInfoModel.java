package com.ngen.cosys.impbd.tracing.model;

import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class BreakDownTracingSummaryInfoModel extends BaseBO{
	
	/**
	 * System generated serial version id 
	 */
	private static final long serialVersionUID = 6962101310754548643L;
    private List<BreakDownTracingUldModel> breakDownTracingUldModel;

}
