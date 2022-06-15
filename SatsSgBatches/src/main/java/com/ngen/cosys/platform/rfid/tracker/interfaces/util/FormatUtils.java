
package com.ngen.cosys.platform.rfid.tracker.interfaces.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.platform.rfid.tracker.interfaces.model.ApiRequestModel;

public class FormatUtils {

	private static final Logger LOG = LoggerFactory.getLogger(FormatUtils.class);

	public static final boolean isEmpty(String aParam) {

		if ((aParam == null) || (aParam.trim().length() == 0)) {
			return true;
		}
		return false;
	}

	public static String handle(Object object, String fileName) throws IOException {
		String jsonOutput = null;
		ObjectMapper objectMapper = new ObjectMapper();
		ApiRequestModel apiRequest = (ApiRequestModel)object;
		try {
			jsonOutput = objectMapper.writeValueAsString(apiRequest);
			//objectMapper.writeValue(new File("D:\\Prithvi\\JSON\\" + fileName + ".json"), apiRequest);
			LOG.debug("JSON response for outbound request", jsonOutput);
		} catch (JsonProcessingException jsonProcessingException) {
			LOG.error("JsonProcessingException while writing response object to json format", jsonProcessingException);
		}
		return jsonOutput;
	}
	
	 
}
