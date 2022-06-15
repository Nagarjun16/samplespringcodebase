package com.ngen.cosys.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.message.ndf.model.NdfMessageModel;
import com.ngen.cosys.message.ndf.service.NdfMessageJobService;

@NgenCosysAppInfraAnnotation
public class DummyControllerForNdf {
	
	@Autowired
	private NdfMessageJobService service;
	
	@PostMapping(value = "/api/batches/ndfMessage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public List<NdfMessageModel>  performBatchOperation() throws CustomException {
		List<NdfMessageModel> msg=service.getNdfMessageDefinition();
		return msg;

	}
}
