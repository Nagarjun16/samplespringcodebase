package com.ngen.cosys.application.job;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.application.service.ConsolidatedShipmentInfoService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.CustomsMRSModel;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.scheduler.job.AbstractCronJob;

public class ACESMRSDataPushImportOAL extends AbstractCronJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsolidateFlightDataFromOperativeFlight.class);

	@Autowired
	private ConsolidatedShipmentInfoService consolidatedShipmentInfoService;

	public static String primaryPath = null;

	static RestTemplate restTemplate = null;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		
		super.executeInternal(jobExecutionContext);
		try {
			String url = getMrsSendConnectorURLForFlight();
			restTemplate = CosysApplicationContext.getRestTemplate();

			List<FlightModel> flightData = consolidatedShipmentInfoService.getACESImportOALFlights().stream()
					.filter(obj -> Optional.ofNullable(obj).isPresent()).collect(Collectors.toList());

			for (FlightModel data : flightData) {

				// set your headers
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				CustomsMRSModel customsMRSModel = new CustomsMRSModel();

				customsMRSModel.setOpenMrs(false);
				customsMRSModel.setCustomsFlightId(data.getCustomsFlightId());
				customsMRSModel.setExportOrImport(data.getImportExportIndicator());
				customsMRSModel.setFlightKey(data.getFlightKey());
				customsMRSModel.setFlightDate(data.getFlightDate());
				// set your entity to send
				HttpEntity entity = new HttpEntity(customsMRSModel, headers);

				restTemplate.exchange(url, HttpMethod.POST, entity, CustomsMRSModel.class);

			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}

	private String getMrsSendConnectorURLForFlight() throws CustomException {
		StringBuilder path = new StringBuilder();
		String primarypath = consolidatedShipmentInfoService.getSendmrsUrl();
		path.append(primarypath);
		path.append("/satssgcustoms/api/satssgcustoms/customsmrs/getConsolidatedInfoForBatches");
		return path.toString();
	}
}
