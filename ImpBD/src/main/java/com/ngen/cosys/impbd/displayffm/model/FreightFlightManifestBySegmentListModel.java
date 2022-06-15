package com.ngen.cosys.impbd.displayffm.model;

import java.math.BigInteger;
import java.util.List;

import com.ngen.cosys.framework.model.BaseBO;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@Setter
@Getter
@NoArgsConstructor
public class FreightFlightManifestBySegmentListModel extends BaseBO {

	/**
	 * default
	 */

	private static final long serialVersionUID = 1L;
	private BigInteger flightSegmentId;
	private String messageStatus;
	private String flightBoardPoint;
	private String createdTimeStamp;
	private BigInteger segmentCopy;
	private Boolean isFFMProcessed;
	private BigInteger flightSegmentOrder;
	private List<FreightFlightManifestBySegmentModel> segments;

}
