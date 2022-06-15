package com.ngen.cosys.platform.rfid.tracker.feeder.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RequestResponseInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		LOGGER.error("===========================request begin================================================");
		LOGGER.error("URI         : {}" + request.getURI());
		LOGGER.error("Method      : {}" + request.getMethod());
		LOGGER.error("Headers     : {}" + request.getHeaders());
		LOGGER.error("Request body: {}" + new String(body, "UTF-8"));
		LOGGER.error("==========================request end================================================");

	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
		String line = bufferedReader.readLine();
		while (line != null) {
			inputStringBuilder.append(line);
			inputStringBuilder.append('\n');
			line = bufferedReader.readLine();
		}
		LOGGER.error("============================response begin==========================================");
		LOGGER.error("Status code  : {}" + response.getStatusCode());
		LOGGER.error("Status text  : {}" + response.getStatusText());
		LOGGER.error("Headers      : {}" + response.getHeaders());
		LOGGER.error("Response body: {}" + inputStringBuilder.toString());
		LOGGER.error("=======================response end=================================================");
	}

}
