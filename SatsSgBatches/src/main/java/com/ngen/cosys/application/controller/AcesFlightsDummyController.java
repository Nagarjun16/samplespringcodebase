package com.ngen.cosys.application.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.ngen.cosys.annotation.NgenCosysAppInfraAnnotation;
import com.ngen.cosys.application.dao.ConsolidatedShipmentInfoDAO;
import com.ngen.cosys.application.dao.ManifestReconcillationStatementDAO;
import com.ngen.cosys.application.service.ConsolidatedShipmentInfoService;
import com.ngen.cosys.application.service.ProduceManifestReconcillationStatementMessageService;
import com.ngen.cosys.events.esb.connector.logger.service.ConnectorLoggerService;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.model.CustomsMRSModel;
import com.ngen.cosys.model.FlightModel;
import com.ngen.cosys.multitenancy.support.CosysApplicationContext;
import com.ngen.cosys.scheduler.esb.connector.ConnectorPublisher;

import io.swagger.annotations.ApiOperation;

@NgenCosysAppInfraAnnotation
public class AcesFlightsDummyController {
	@Autowired
	private ConsolidatedShipmentInfoService consolidatedShipmentInfoService;
	@Autowired
	private ConsolidatedShipmentInfoDAO consolidatedShipmentInfoDAO;
	@Autowired
	private ProduceManifestReconcillationStatementMessageService mrsService;
	@Autowired
	private ManifestReconcillationStatementDAO manifestReconcillationStatementDAO;
	@Autowired
	ConnectorPublisher publisher;
	@Autowired
	ProduceManifestReconcillationStatementMessageService produceManifestReconcillationStatementMessageService;
	@Autowired
	ConnectorLoggerService logger;

	public static String primaryPath = "";
	private static final Logger LOGGER = LoggerFactory.getLogger(AcesFlightsDummyController.class);


	static RestTemplate restTemplate = null;

	/**
	 * Get ShipmentInfo from cosys
	 * 
	 * @param addMRSModel
	 * @return
	 * @throws CustomException
	 */
	@ApiOperation("Get Flight from cosys")
	@RequestMapping(value = "/api/satssgcustoms/customsmrs/getFlightInfoForDummyController", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getCustomMRSShipmentInfo() throws CustomException {

		try {
			String url = getMrsSendConnectorURLForFlight();
			restTemplate = CosysApplicationContext.getRestTemplate();

			List<FlightModel> flightData = consolidatedShipmentInfoService.getFlightInfo().stream()
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

						restTemplate.exchange(url, HttpMethod.POST, entity,
								CustomsMRSModel.class);
				
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}

 
	private String getMrsSendConnectorURLForFlight() throws CustomException {
		StringBuilder path =  new StringBuilder();
				primaryPath = "http://localhost:9700";
		path.append(primaryPath);
		path.append("/satssgcustoms/api/satssgcustoms/customsmrs/getConsolidatedInfoForBatches");
		return path.toString();
	}
	
	@ApiOperation("Get Flight from cosys")
	@RequestMapping(value = "/api/satssgcustoms/customsmrs/getSQExportCustomDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getACESExportSQFlights() throws CustomException {

		try {
			String url = getMrsSendConnectorURLForFlight();
			restTemplate = CosysApplicationContext.getRestTemplate();

			List<FlightModel> flightData = consolidatedShipmentInfoService.getACESExportSQFlights().stream()
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

						restTemplate.exchange(url, HttpMethod.POST, entity,
								CustomsMRSModel.class);
				
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}
	@ApiOperation("Get Flight from cosys")
	@RequestMapping(value = "/api/satssgcustoms/customsmrs/getACESExportOALFlights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getACESExportOALFlights() throws CustomException {

		try {
			String url = getMrsSendConnectorURLForFlight();
			restTemplate = CosysApplicationContext.getRestTemplate();

			List<FlightModel> flightData = consolidatedShipmentInfoService.getACESExportOALFlights().stream()
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

						restTemplate.exchange(url, HttpMethod.POST, entity,
								CustomsMRSModel.class);
				
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}
	
	@ApiOperation("Get Flight from cosys")
	@RequestMapping(value = "/api/satssgcustoms/customsmrs/getACESImportSQFlights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getACESImportSQFlights() throws CustomException {

		try {
			String url = getMrsSendConnectorURLForFlight();
			restTemplate = CosysApplicationContext.getRestTemplate();

			List<FlightModel> flightData = consolidatedShipmentInfoService.getACESImportSQFlights().stream()
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

						restTemplate.exchange(url, HttpMethod.POST, entity,
								CustomsMRSModel.class);
				
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}
	
	@ApiOperation("Get Flight from cosys")
	@RequestMapping(value = "/api/satssgcustoms/customsmrs/getACESImportOALFlights", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getACESImportOALFlights() throws CustomException {

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

						restTemplate.exchange(url, HttpMethod.POST, entity,
								CustomsMRSModel.class);
				
			}
		} catch (Exception e) {
			LOGGER.warn(e.getMessage());
		}

	}
}
