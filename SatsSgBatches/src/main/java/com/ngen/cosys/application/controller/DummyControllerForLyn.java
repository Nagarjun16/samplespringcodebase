package com.ngen.cosys.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.mesage.lyn.service.LynMessageService;
import com.ngen.cosys.message.lyn.model.LynMessage;


@NgenCosysAppInfraAnnotation
public class DummyControllerForLyn {
	@Autowired
	LynMessageService service;
	@PostMapping(value = "/api/batches/lynmessage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	   public List<LynMessage>  performBatchOperation() throws CustomException {
		List<LynMessage> msg=service.getLynMessageDefinition();
		return msg;
 
	}
	
}
