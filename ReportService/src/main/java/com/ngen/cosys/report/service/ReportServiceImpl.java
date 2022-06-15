/**
 * Report Service Implementation
 */
package com.ngen.cosys.report.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.data.oda.jdbc.IConnectionFactory;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.emitter.csv.CSVRenderOption;
import org.eclipse.core.internal.registry.RegistryProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ngen.cosys.common.ConfigurationConstant;
import com.ngen.cosys.framework.exception.CustomException;
import com.ngen.cosys.multitenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.context.TenantContext;
import com.ngen.cosys.multitenancy.model.TenantConfig;
import com.ngen.cosys.multitenancy.timezone.support.TenantTimeZoneUtility;
import com.ngen.cosys.report.common.ReportConstants;
import com.ngen.cosys.report.common.ReportFormat;
import com.ngen.cosys.report.common.ReportRequestType;
import com.ngen.cosys.report.custom.model.NGCReportRequestCustom;
import com.ngen.cosys.report.dao.LabelDAO;
import com.ngen.cosys.report.data.LabelCache;
import com.ngen.cosys.report.model.ReportLabel;
import com.ngen.cosys.report.model.ReportRequest;

/**
 * Report Service Implementation
 */
@Service("birtReportService")
public class ReportServiceImpl implements ReportService {
	private static Logger LOGGER = LoggerFactory.getLogger(ReportService.class);
	//
	private static final String IMAGE_FOLDER = "/images";
	private static final String REPORTS_RESOLVER_PATTERN = "/reports/*.rptdesign";
	private static final String REPORT_DESIGN_EXTENSION = ".rptdesign";
	private static final String SUPPORTED_IMAGE_FORMATS = "JPG;PNG;";
	private static final String FONTS_CONFIG_FILE = "/config/fontsConfig.xml";

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	@Qualifier(BeanFactoryConstants.DATASOURCE)
	private DataSource dataSource;

	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_DATASOURCE)
	private DataSource dataSourceROI;
	@Autowired
	private LabelDAO labelDAO;

	//
	private IReportEngine birtEngine;
	private Map<String, IReportRunnable> reports = new HashMap<>();

	/**
	 * Post Construct of Report Service
	 * 
	 * @throws BirtException
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	protected void initialize() throws BirtException {
		EngineConfig reportEngineConfig = new EngineConfig();
		URL fontConfigURL = getFontConfigURL();
		RegistryProviderFactory.releaseDefault();
		// Set Report Context
		reportEngineConfig.getAppContext().put(ConfigurationConstant.REPORT_SPRING_CONTEXT, this.applicationContext);
		reportEngineConfig.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
				Thread.currentThread().getContextClassLoader());
		reportEngineConfig.setFontConfig(fontConfigURL);
		// Start
		Platform.startup(reportEngineConfig);
		// Create BIRT Engine
		IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
		this.birtEngine = factory.createReportEngine(reportEngineConfig);
		// Load Reports
		loadReports();
	}

	/**
	 * Destroy
	 */
	@PreDestroy
	protected void destroy() {
		if (this.birtEngine != null) {
			this.birtEngine.destroy();
			Platform.shutdown();
		}
	}

	/**
	 * Get Reports
	 * 
	 * @return Reports
	 */
	public String[] getReports() {
		Object[] reportNames = this.reports.keySet().toArray();
		//
		return Arrays.copyOf(reportNames, reportNames.length, String[].class);
	}

	/**
	 * Gets Font Config URL
	 * 
	 * @return Font Config URL
	 */
	private URL getFontConfigURL() {
		try {
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources(FONTS_CONFIG_FILE);
			//
			if (resources != null && resources.length > 0) {
				return resources[0].getURL();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Load Label Cache
	 * 
	 * @param locale Locale as String
	 */
	private void loadLabelCache(String locale) {
		try {
			if (!LabelCache.getInstance().hasLocaleLabels(locale)) {
				List<ReportLabel> labels = labelDAO.getI18NLabels(locale);
				// Update Label Cache
				LabelCache.getInstance().updateLabels(labels, locale);
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load Report Files to Memory
	 * 
	 * @throws EngineException
	 */
	private void loadReports() throws EngineException {
		try {
			Resource resources[] = new PathMatchingResourcePatternResolver().getResources(REPORTS_RESOLVER_PATTERN);
			// Load All the Reports
			for (Resource resource : resources) {
				LOGGER.info(resource.getFilename());
				//
				String fileName = resource.getFilename();
				// Cache Report Design
				try {
					reports.put(fileName.replace(REPORT_DESIGN_EXTENSION, ""),
							birtEngine.openReportDesign(resource.getInputStream()));
				} catch (Exception e) {
					LOGGER.error("Failed to load Report " + resource.getFilename(), e);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Loading Reports Failed", e);
		}
	}

	/**
	 * @see com.ngen.cosys.report.service.ReportService#createReport(com.ngen.cosys.report.model.ReportRequest,
	 *      javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("unchecked")
	public OutputStream createReport(ReportRequest reportRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException, EngineException, SQLException {
		/*
		 * // boolean transactional = false; if
		 * (!StringUtils.isEmpty(reportRequest.getDataSource())) { transactional =
		 * reportRequest.getDataSource().equalsIgnoreCase("transactional"); } Connection
		 * connection = transactional ? dataSource.getConnection() :
		 * dataSourceROI.getConnection();
		 * 
		 * // Get the report design file and create the IReportRunnable String
		 * reportFilePath = REPORTS_RESOLVER_PATTERN.replace("*",
		 * reportRequest.getReportName());
		 * 
		 * // Get the resource array Resource resources[] = new
		 * PathMatchingResourcePatternResolver().getResources(reportFilePath);
		 * 
		 * if (resources == null) { LOGGER.error("No report found to generate",
		 * reportRequest.getReportName()); }
		 * 
		 * // Open the report design //IReportRunnable report =
		 * birtEngine.openReportDesign(resources[0].getFile().getAbsolutePath());
		 * IReportRunnable report =
		 * birtEngine.openReportDesign(resources[0].getInputStream());
		 * ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		 * IRunAndRenderTask runAndRenderTask =
		 * birtEngine.createRunAndRenderTask(report); ReportRequestType requestType =
		 * reportRequest.getRequestType() != null ? reportRequest.getRequestType() :
		 * ReportRequestType.DOWNLOAD; ReportFormat format = reportRequest.getFormat()
		 * != null ? reportRequest.getFormat() : ReportFormat.PDF; // Update Response
		 * Header response.setContentType(birtEngine.getMIMEType(format.format()));
		 * response.setHeader("Content-Disposition", requestType.requestType() +
		 * ";filename=" + reportRequest.getReportName() + "." + format.fileExtension());
		 * runAndRenderTask.setParameterValues(reportRequest.getParameters());
		 */

		Connection connection = dataSourceROI.getConnection();
		IReportRunnable report = reports.get(reportRequest.getReportName());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		IRunAndRenderTask runAndRenderTask = birtEngine.createRunAndRenderTask(report);
		String localeLanguage = reportRequest.getLocale() == null ? Locale.US.getLanguage()
				: reportRequest.getLocale().substring(0, 2);
		String localeCountry = reportRequest.getLocale() == null ? Locale.US.getCountry()
				: reportRequest.getLocale().substring(3, 5);
		ReportRequestType requestType = reportRequest.getRequestType() != null ? reportRequest.getRequestType()
				: ReportRequestType.DOWNLOAD;
		ReportFormat format = reportRequest.getFormat() != null ? reportRequest.getFormat() : ReportFormat.PDF;
		// Update Response Header
		response.setContentType(birtEngine.getMIMEType(format.format()));
		response.setHeader("Content-Disposition", requestType.requestType() + ";filename="
				+ reportRequest.getReportName() + "." + format.fileExtension());
		runAndRenderTask.setParameterValues(transformParameters(reportRequest));
		runAndRenderTask.setLocale(new Locale(localeLanguage, localeCountry));
		// Label Cache Update
		loadLabelCache(TenantContext.get().getLocale());
// Create Report
		switch (format) {
		case XLS:
			// Create XLS Report
			createXLSReport(outputStream, runAndRenderTask);
			break;
		case CSV:
			// Create CSV Report
			createCSVReport(outputStream, runAndRenderTask);
			break;
		default:
			// Create PDF Report
			createPDFReport(outputStream, runAndRenderTask);
			break;
		}
		//
		birtEngine.createGetParameterDefinitionTask(report);
		//
		try {
			// Update BIRT Viewer HTTP Servlet Request (Not Required as of Now)
			runAndRenderTask.getAppContext().put(EngineConstants.APPCONTEXT_BIRT_VIEWER_HTTPSERVET_REQUEST, request);
			// Set Connection
			if (this.dataSourceROI != null) {
				runAndRenderTask.getAppContext().put(IConnectionFactory.PASS_IN_CONNECTION, connection);
				runAndRenderTask.getAppContext().put(IConnectionFactory.CLOSE_PASS_IN_CONNECTION, Boolean.FALSE);
			}
			// Run the Report Engine for Report
			runAndRenderTask.getRenderOption().setOutputStream(outputStream);
			runAndRenderTask.run();
		} catch (EngineException e) {
			String parameters = !CollectionUtils.isEmpty(reportRequest.getParameters())
					? parameters = convertObjectToJSONString(reportRequest.getParameters())
					: null;
			LOGGER.error("Report Service Error :: Report Name - {}, Format - {}, Parameters - {}, Exception - {}",
					reportRequest.getReportName(), reportRequest.getFormat().format(), parameters, e);
			throw e;
		} finally {
			try {
				runAndRenderTask.close();
				connection.close();
			} catch (Exception e) {
				LOGGER.error("Report Connection Close Error (Swallowed)", e);
			}
		}
		return outputStream;
	}

	/**
	 * Transform Report Parameters
	 * 
	 * @param request Request
	 * @return
	 */
	private Map<String, Object> transformParameters(ReportRequest request) {
		Map<String, Object> paramMap = request.getParameters();
		/*
		try {
			if (Objects.nonNull(paramMap)) {
				Set<Entry<String, Object>> entrySet = paramMap.entrySet();
				Iterator<Entry<String, Object>> entryIter = entrySet.iterator();
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter
						.ofPattern(ReportConstants.REQUEST_DATE_TIME_FORMAT);
				// Convert to Date/Time
				while (entryIter.hasNext()) {
					Entry<String, Object> entry = entryIter.next();
					if (entry.getValue() instanceof String) {
						try {
							// Parse & Verify
							LocalDateTime dateTime = LocalDateTime.parse(entry.getValue().toString(),
									dateTimeFormatter);
							// Convert Only Time is Available
							if (!(dateTime.toLocalTime().getHour() == 0 && dateTime.toLocalTime().getMinute() == 0
									&& dateTime.toLocalTime().getSecond() == 0)) {
								// If Fine, Convert to DB Date/Time
								LocalDateTime dbDateTime = TenantTimeZoneUtility.fromDatabaseToTenantDateTime(dateTime);
								// Update Parameter
								paramMap.put(entry.getKey(), dateTimeFormatter.format(dbDateTime));
							}
						} catch (Exception e) {
						}
					}
				}
			}
		} catch (Exception e) {
		}
		*/

		
		paramMap.put("tenantAirportCode", request.getTenantAirport());
		paramMap.put("tenantCityCode", request.getTenantCity());
		try {
			paramMap.put("tenantCityName", labelDAO.getTenantCityName(request.getTenantCity()));
		} catch (CustomException e) {
			//DO Nothing
		}
		
		LocalDateTime currentDateTime = TenantTimeZoneUtility.now();
		DateTimeFormatter df;
		
		if (Optional.ofNullable(TenantContext.get()).isPresent()) {
			
			TenantConfig tenantConfig = TenantContext.get().getTenantConfig();

			if (Optional.ofNullable(tenantConfig).isPresent()) {
				 df = tenantConfig.getFormat().getDateTimeFormatter();

				paramMap.put("tenantCurrentDateTime", currentDateTime.format(df).toUpperCase());
			}
		}else {
			 df = DateTimeFormatter.ofPattern(ReportConstants.DEFAULT_DATE_TIME_FORMAT);
			 paramMap.put("tenantCurrentDateTime", currentDateTime.format(df).toUpperCase());
		}
		return paramMap;
	}

	/**
	 * Create HTML Report
	 * 
	 * @param byteArrayOutputStream
	 * @param runAndRenderTask      Render Task
	 */
	@SuppressWarnings("unused")
	private void createHTMLReport(OutputStream byteArrayOutputStream, IRunAndRenderTask runAndRenderTask) {
		HTMLRenderOption htmlRenderOptions = new HTMLRenderOption();
		//
		htmlRenderOptions.setImageHandler(new HTMLServerImageHandler());
		htmlRenderOptions.setImageDirectory(servletContext.getRealPath(IMAGE_FOLDER));
		htmlRenderOptions.setSupportedImageFormats(SUPPORTED_IMAGE_FORMATS);
		htmlRenderOptions.setOutputFormat(ReportFormat.HTML.format());
		htmlRenderOptions.setOutputStream(byteArrayOutputStream);
		//
		runAndRenderTask.setRenderOption(htmlRenderOptions);
	}

	/**
	 * Create PDF Report
	 * 
	 * @param byteArrayOutputStream Output Stream
	 * @param runAndRenderTask      Render Task
	 */
	private void createPDFReport(OutputStream byteArrayOutputStream, IRunAndRenderTask runAndRenderTask) {
		PDFRenderOption pdfRenderOption = new PDFRenderOption();
		//
		pdfRenderOption.setOption(IPDFRenderOption.REPAGINATE_FOR_PDF, new Boolean(true));
		pdfRenderOption.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
		pdfRenderOption.setOption(IPDFRenderOption.PDF_TEXT_WRAPPING, true);
		pdfRenderOption.setOption(IPDFRenderOption.PDF_HYPHENATION, true);
		pdfRenderOption.setSupportedImageFormats(SUPPORTED_IMAGE_FORMATS);
		pdfRenderOption.setOutputFormat(ReportFormat.PDF.format());
		pdfRenderOption.setOutputStream(byteArrayOutputStream);
		//
		runAndRenderTask.setRenderOption(pdfRenderOption);
	}

	/**
	 * Create CSV Report
	 * 
	 * @param byteArrayOutputStream OutputStream
	 * @param runAndRenderTask      Render Task
	 */
	private void createCSVReport(OutputStream byteArrayOutputStream, IRunAndRenderTask runAndRenderTask) {
		CSVRenderOption csvRenderOption = new CSVRenderOption();
		//
		csvRenderOption.setSupportedImageFormats(SUPPORTED_IMAGE_FORMATS);
		csvRenderOption.setOutputFormat(ReportFormat.CSV.format());
		csvRenderOption.setOutputStream(byteArrayOutputStream);
		//
		runAndRenderTask.setRenderOption(csvRenderOption);
	}

	/**
	 * Create XLS Report
	 * 
	 * @param byteArrayOutputStream Output Stream
	 * @param runAndRenderTask      Render Task
	 */
	private void createXLSReport(OutputStream byteArrayOutputStream, IRunAndRenderTask runAndRenderTask) {
		EXCELRenderOption xlsRenderOption = new EXCELRenderOption();
		//
		xlsRenderOption.setSupportedImageFormats(SUPPORTED_IMAGE_FORMATS);
		xlsRenderOption.setOutputFormat(ReportFormat.XLS.format());
		xlsRenderOption.setOutputStream(byteArrayOutputStream);
		//
		runAndRenderTask.setRenderOption(xlsRenderOption);
	}

	@Override
	public OutputStream createReportCustom(ReportRequest reportRequest, HttpServletRequest request,
			HttpServletResponse response, NGCReportRequestCustom ngcReportCustom)
			throws IOException, EngineException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see com.ngen.cosys.report.service.ReportService#convertOutputStreamToBase64Text(java.io.OutputStream)
	 * 
	 */
	@Override
	public String convertOutputStreamToBase64Text(OutputStream outputStream) {
		//
		if (outputStream instanceof ByteArrayOutputStream) {
			return Base64.getEncoder().encodeToString(((ByteArrayOutputStream) outputStream).toByteArray());
		}
		return Base64.getEncoder().encodeToString(outputStream.toString().getBytes());
	}

	/**
	 * @param payload
	 * @return
	 */
	private static String convertObjectToJSONString(Object payload) {
		String result = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			result = objectMapper.writeValueAsString(payload);
		} catch (Exception ex) {
		}
		return result;
	}

}
