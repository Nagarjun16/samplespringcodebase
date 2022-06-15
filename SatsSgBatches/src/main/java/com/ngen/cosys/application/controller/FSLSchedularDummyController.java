package com.ngen.cosys.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.service.FSLMessageJobService;
import com.ngen.cosys.framework.exception.CustomException;

@NgenCosysAppInfraAnnotation
public class FSLSchedularDummyController {
	@Autowired
	FSLMessageJobService service;
	@PostMapping(value = "/api/batches/fslMessage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public void performBatchOperation() throws CustomException {
		service.getFSLMessageDefinition();
	}
}
