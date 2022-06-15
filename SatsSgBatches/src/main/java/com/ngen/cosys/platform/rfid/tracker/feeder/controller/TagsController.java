package com.ngen.cosys.platform.rfid.tracker.feeder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.platform.rfid.tracker.feeder.service.FeederService;

@NgenCosysAppInfraAnnotation(path = "/tag")
public class TagsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TagsController.class);

	@Autowired
	FeederService feederService;

	@RequestMapping(value = "/print", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void printTags() throws CustomException {
		feederService.pushTrackingFeeds();
		LOGGER.info("Exting from printTags : TagsController ");
	}

}
