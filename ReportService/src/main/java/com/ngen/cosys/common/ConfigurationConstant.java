/**
 * Configuration Constant
 */
package com.ngen.cosys.common;

/**
 * Configuration Constant
 */
public final class ConfigurationConstant {
	
	// Report Spring Context Name
	public static final String REPORT_SPRING_CONTEXT = "spring";
	
	// End Point
	public static final String MESSAGE_ENDPOINT = "/message-endpoint";
	// Application Destination Prefix
	public static final String APPLICATION_DESTINATION_PREFIX = "/messages";
	// Message Mapping
	public static final String NOTIFICATION_MESSAGE_MAPPING = "/notification/{tenant}/{fromTo}";
	public static final String CHAT_USER_MESSAGE_MAPPING = "/chat/user/{tenant}/{from}/{to}";
	public static final String CHAT_GROUP_MESSAGE_MAPPING = "/chat/group/{tenant}/{from}/{to}";
	public static final String CHAT_SUMMARY_MESSAGE_MAPPING = "/chat/summary/{tenant}/{from}";
	public static final String CHAT_INIT_MESSAGE_MAPPING = "/chat/init/{tenant}/{from}/{to}";
	public static final String CHAT_UPDATE_UNREADMESSAGES_MESSAGE_MAPPING="/chat/updateunreadmessages/{tenant}/{from}/{to}";
    public static final String CHAT_FLIGHT_GROUP_MESSAGE_MAPPING="/chat/flight/{tenant}/{from}/{to}";
	// Topic
	public static final String NOTIFICATION_TOPIC = "/topic/notification";
	public static final String CHAT_TOPIC = "/topic/chat";
	// Send To
	public static final String NOTIFICATION_SEND_TO = NOTIFICATION_TOPIC + "/{tenant}/{fromTo}";
	//
	public static final String CHAT_SEND_TO_USER_URI = CHAT_TOPIC + "/user";
	public static final String CHAT_SEND_TO_USER = CHAT_SEND_TO_USER_URI + "/{tenant}/{to}";
	//
	public static final String CHAT_SEND_TO_GROUP_URI = CHAT_TOPIC + "/group";
	public static final String CHAT_SEND_TO_GROUP = CHAT_SEND_TO_GROUP_URI + "/{tenant}/{to}";
	//
	public static final String CHAT_SEND_TO_SUMMARY_URI = CHAT_TOPIC + "/summary";
	public static final String CHAT_SEND_TO_SUMMARY = CHAT_SEND_TO_SUMMARY_URI + "/{tenant}/{from}";
	//
	public static final String CHAT_SEND_TO_INIT_URI = CHAT_TOPIC + "/init";
	public static final String CHAT_SEND_TO_INIT = CHAT_SEND_TO_INIT_URI + "/{tenant}/{from}";
	//
    public static final String CHAT_SEND_TO_FLIGHT_GROUP_URI = CHAT_TOPIC + "/flight/";
    public static final String CHAT_SEND_TO_FLIGHT_GROUP = CHAT_SEND_TO_FLIGHT_GROUP_URI + "/{tenant}/{to}";
	
	// Path Variable
	public static final String TENANT_PATH_VARIABLE = "tenant";
	public static final String FROM_PATH_VARIABLE = "from";
	public static final String TO_PATH_VARIABLE = "to";
	public static final String FROM_TO_PATH_VARIABLE = "fromTo";
	public static final String USER="user";
	public static final String FLIGHT="flight";
}
