package com.yakketyyak.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import lombok.RequiredArgsConstructor;

/**
 * The Class ResourceServerConfig.
 */
@Configuration
@EnableResourceServer
@SuppressWarnings("deprecation")
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	/** The oauth 2 properties. */
	private final Oauth2Properties oauth2Properties;

	private final ResourceServerTokenServices tokenService;

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable()
				.authorizeRequests().antMatchers(HttpMethod.POST, "/users").permitAll().anyRequest().authenticated()
				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}

	/**
	 * Configure.
	 *
	 * @param resources the resources
	 */
	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) {
		resources.resourceId(this.oauth2Properties.getResourceId()).tokenServices(tokenService).stateless(true);
	}

}
