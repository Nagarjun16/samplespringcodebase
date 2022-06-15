package com.ngen.cosys.shipment.model;


import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.ngen.cosys.framework.model.BaseBO;

import groovy.transform.ToString;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel
@XmlRootElement
@Component
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonFlightId extends BaseBO {
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   /**
   * This field contains the Flight Number
   */
   private String flightKey;
	/**
	* The Date
	*/
	private LocalDateTime flightDate;
	
	private String source;
	
	private String destination;
}
