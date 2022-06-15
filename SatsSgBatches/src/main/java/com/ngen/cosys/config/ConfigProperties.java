package com.ngen.cosys.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Application Properties
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("application")
@RefreshScope
public class ConfigProperties {

	private String camsdefaultUri;
}
