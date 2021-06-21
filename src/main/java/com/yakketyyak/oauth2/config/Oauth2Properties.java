package com.yakketyyak.oauth2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * The Class Oauth2Properties.
 */
@Component
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
@Data
public class Oauth2Properties {

	/** The resource id. */
	private String resourceId;

	/** The secret. */
	private String secret;

	/** The client id. */
	private String clientId;

}
