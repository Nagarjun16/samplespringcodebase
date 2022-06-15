package com.ngen.cosys.impbd.mail.breakdown.model;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.ngen.cosys.framework.model.BaseBO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class UldDestinationRequest extends BaseBO {

	/**
	    * Default serialVersionUID
	    */
	   private static final long serialVersionUID = 1L;

	   private List<String> uldKeys;
	   
	   private List<UldDestinationEntry> destinationEntries; 
}
