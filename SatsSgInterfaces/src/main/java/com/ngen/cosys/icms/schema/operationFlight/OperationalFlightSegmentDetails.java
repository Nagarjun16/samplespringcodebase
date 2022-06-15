package com.ngen.cosys.icms.schema.operationFlight;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.ngen.cosys.icms.schema.scheduleFlight.TotalCapacity;
import com.ngen.cosys.icms.validation.xmlValidator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Model class contains operational flight segment details
 */
@XmlAccessorType(XmlAccessType.FIELD)
@ToString
@Setter
@Getter
@Valid
public class OperationalFlightSegmentDetails implements Serializable {
 
    private static final long serialVersionUID = 1L;
    @NotBlank(message="Segment origin cannot be blank",groups = xmlValidator.class)
	@NotNull(message="Segment origin cannot be null",groups = xmlValidator.class)
    @Size(min=3,max = 3, message = "Segment origin length must be 3 characters",groups = xmlValidator.class)
    private String segmentOrigin;
    
    @NotBlank(message="Segment destination cannot be blank",groups = xmlValidator.class)
	@NotNull(message="Segment destination cannot be null",groups = xmlValidator.class)
    @Size(min=3,max = 3, message = "Segment destination length must be 3 characters",groups = xmlValidator.class)
	private String segmentDestination;
    
    @NotBlank(message="Segment status cannot be blank",groups = xmlValidator.class)
	@NotNull(message="Segment status cannot be null",groups = xmlValidator.class)
    @Size(min=1,max = 6, message = "Segment status size should not exceed 6 characters",groups = xmlValidator.class)
	private String segmentStatus;
	private String departureTime;
	private String arrivalTime;
	private TotalCapacity guaranteeCapacityDetails;
	private TotalCapacity capacityLimitDetails;
	private TotalCapacity totalCapacity;
	

}
