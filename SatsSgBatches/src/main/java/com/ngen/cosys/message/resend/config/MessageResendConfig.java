/**
 * {@link MessageResendConfig}
 * 
 * @copyright SATS Singapore 2019-20
 * @author NIIT
 */
package com.ngen.cosys.message.resend.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.ngen.cosys.message.resend.common.InterfaceSystemConstants;
import com.ngen.cosys.message.resend.common.MessageResendConstants;
import com.ngen.cosys.message.resend.enums.MessageResendSQL;
import com.ngen.cosys.multi.tenancy.constants.BeanFactoryConstants;
import com.ngen.cosys.multitenancy.utilities.MultiTenantUtility;

/**
 * Message Resends configuration
 * 
 * @author NIIT Technologies Ltd
 */
@Component
public class MessageResendConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageResendConfig.class);

	@Autowired
	@Qualifier(BeanFactoryConstants.ROI_SESSION_TEMPLATE)
	private SqlSession sqlSession;

	private Integer messageResendRetryLimit = 0;
	private Map<String, OutgoingInterfaceConfig> outgoingInterfaceConfig = new HashMap<>();;
	private Map<String, List<IncomingInterfaceConfig>> incomingInterfaceConfigList = new HashMap<>();
	private Map<String, String> outgoingInterfaceConfigListAisats = new HashMap<>();

	/**
	 * Method to load the interface incoming/outgoing end point configurations by
	 * tenant
	 */
	public void loadIncomingOutgoingInterfaceConfig() {
		LOGGER.warn("Message Resend Configuration initialization - {}");

		// Get tenant from tenant context
		String tenantId = MultiTenantUtility.getTenantIdFromContext();

		messageResendRetryLimit = sqlSession.selectOne(
				MessageResendSQL.SQL_SELECT_MESSAGE_RESEND_RETRY_LIMIT.getQueryId(),
				MessageResendConstants.MESSAGE_RESEND_RETRY_LIMIT);

		// Check if the configuration exists for an tenant then just return it
		if (ObjectUtils.isEmpty(this.outgoingInterfaceConfig.get(tenantId))) {
			// Load outgoing end points and cache it
			OutgoingInterfaceConfig outgoingInterfaceConfigByTenant = sqlSession
					.selectOne(MessageResendSQL.SQL_SELECT_MESSAGE_OUTGOING_ENDPOINT_CONFIG.getQueryId());

			boolean outgoingInterfaceConfigAvailability = Objects.nonNull(outgoingInterfaceConfigByTenant) ? true
					: false;

			// Add the interface list to Map object
			if (outgoingInterfaceConfigAvailability) {
				this.outgoingInterfaceConfig.put(tenantId, outgoingInterfaceConfigByTenant);
			}

			LOGGER.warn("Outgoing Message Resend Interface Config availability - {}",
					outgoingInterfaceConfigAvailability);
		}

		// Check if the configuration exists for an tenant then just return it
		if (CollectionUtils.isEmpty(this.incomingInterfaceConfigList.get(tenantId))) {
			// Load incoming end points and cache it
			List<IncomingInterfaceConfig> incomingInterfaceConfigListByTenant = sqlSession
					.selectList(MessageResendSQL.SQL_SELECT_MESSAGE_INCOMING_ENDPOINT_CONFIG.getQueryId());

			boolean incomingInterfaceConfigAvailability = !CollectionUtils.isEmpty(incomingInterfaceConfigListByTenant)
					? true
					: false;

			// Add the interface list to Map object
			if (incomingInterfaceConfigAvailability) {
				this.incomingInterfaceConfigList.put(tenantId, incomingInterfaceConfigListByTenant);
			}

			LOGGER.warn("Incoming Message Resend Interface Config availability - {}",
					incomingInterfaceConfigAvailability);
		}
	}

	/**
	 * GET Message Resend Retry Limit
	 * 
	 * @return Integer - Retry limits
	 */
	public Integer getMessageResendRetryLimit() {
		return messageResendRetryLimit;
	}

	/**
	 * GET Outgoing Connector config URL's
	 * 
	 * @param medium
	 * @return String - End point of ESB Connector
	 */
	public String connectorConfigURL(String interfaceSystem) {
		if (StringUtils.isEmpty(interfaceSystem)) {
			throw new IllegalArgumentException("Message medium cannot be blank..!");
		}

		// Get tenant from tenant context
		String tenantId = MultiTenantUtility.getTenantIdFromContext();

		OutgoingInterfaceConfig outgoingInterfaceConfigByTenant = this.outgoingInterfaceConfig.get(tenantId);

		if (Objects.isNull(outgoingInterfaceConfigByTenant)) {
			throw new IllegalStateException(
					"Outgoing Interface config instances cannot be null for tenant - " + tenantId);
		}
		if (Objects.equals(InterfaceSystemConstants.SYSTEM_ICS, interfaceSystem)) {
			return outgoingInterfaceConfigByTenant.getConnectorHTTPUrl();
		} else {
			return outgoingInterfaceConfigByTenant.getConnectorMQUrl();
		}
	}

	/**
	 * Incoming Interface Config Details
	 * 
	 * @return List<IncomingInterfaceConfig> - List of interface end points for a
	 *         given tenant
	 */
	public List<IncomingInterfaceConfig> incomingInterfaceConfigDetails() {
		// Get tenant from tenant context
		String tenantId = MultiTenantUtility.getTenantIdFromContext();

		List<IncomingInterfaceConfig> incomingInterfaceConfigListByTenant = this.incomingInterfaceConfigList
				.get(tenantId);
		boolean incomingInterfaceConfigAvailability = !CollectionUtils.isEmpty(incomingInterfaceConfigListByTenant)
				? true
				: false;
		LOGGER.info("Incoming Message Resend Interface Config available for tenant - {} {}", tenantId,
				incomingInterfaceConfigAvailability);
		if (!incomingInterfaceConfigAvailability) {
			throw new IllegalStateException(
					"Incoming Interface config instances cannot be null for tenant - " + tenantId);
		}
		return incomingInterfaceConfigListByTenant;
	}

	/**
	 * Outgoing End Point Config
	 * 
	 * @return
	 */
	private Map<String, String> outgoingEndPointConfigParams() {
		Map<String, String> endPointConfig = new HashMap<>();
		endPointConfig.put(MessageResendConstants.API_MQ_CONFIG_KEY, MessageResendConstants.API_MQ_CONFIG_VALUE);
		endPointConfig.put(MessageResendConstants.API_HTTP_CONFIG_KEY, MessageResendConstants.API_HTTP_CONFIG_VALUE);
		return endPointConfig;
	}

	
	
	
	public void loadIncomingOutgoingInterfaceConfigAisatsOutgoing() {
		LOGGER.warn("Message Resend Configuration initialization - {}");
		
		messageResendRetryLimit = sqlSession.selectOne(
				MessageResendSQL.SQL_SELECT_MESSAGE_RESEND_RETRY_LIMIT.getQueryId(),
				MessageResendConstants.MESSAGE_RESEND_RETRY_LIMIT);

			List<IncomingInterfaceConfig> incomingInterfaceConfigListByTenant = sqlSession
					.selectList(MessageResendSQL.SQL_SELECT_MESSAGE_ENDPOINT_CONFIG_AISATSOUTGOING.getQueryId());

			boolean incomingInterfaceConfigAvailability = !CollectionUtils.isEmpty(incomingInterfaceConfigListByTenant)
					? true
					: false;

			// Add the interface list to Map object
			if (incomingInterfaceConfigAvailability) {
				for(IncomingInterfaceConfig url:incomingInterfaceConfigListByTenant) {
				    outgoingInterfaceConfigListAisats.put(url.getSystemName(), url.getEndPointUrl());
				}
			}

			LOGGER.warn("Incoming Message Resend Interface Config availability - {}",
					incomingInterfaceConfigAvailability);
		}
	


	public String connectorConfigURLAisatsOutgoing(String interfaceSystem) {
		if (StringUtils.isEmpty(interfaceSystem)) {
			throw new IllegalArgumentException("Message medium cannot be blank..!");
		}

		String outgoingInterfaceConfigByTenant = this.outgoingInterfaceConfigListAisats.get(interfaceSystem);

		if (Objects.isNull(outgoingInterfaceConfigByTenant)) {
			throw new IllegalStateException(
					"Outgoing Interface config instances cannot be null for  - " + interfaceSystem);
		}
		
		return outgoingInterfaceConfigByTenant;
		
	}

}
