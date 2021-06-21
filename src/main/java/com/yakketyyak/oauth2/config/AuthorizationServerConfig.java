package com.yakketyyak.oauth2.config;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import lombok.RequiredArgsConstructor;

/**
 * The Class AuthorizationServerConfig.
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
@Transactional
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	/** The authentication manager. */
	private final AuthenticationManager authenticationManager;

	/** The user details service. */
	private final UserDetailsService userDetailsService;

	/** The oauth 2 properties. */
	private final Oauth2Properties oauth2Properties;

	private final DataSource dataSource;

	/**
	 * Token store.
	 *
	 * @return the token store
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	/**
	 * Configure.
	 *
	 * @param endpoints the endpoints
	 * @throws Exception the exception
	 */
	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(this.tokenStore()).approvalStoreDisabled();
		endpoints.authenticationManager(this.authenticationManager);
		endpoints.userDetailsService(this.userDetailsService);
		endpoints.accessTokenConverter(this.accessTokenConverter());
	}

	/**
	 * Configure.
	 *
	 * @param clients the clients
	 * @throws Exception the exception
	 */
	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(this.dataSource);

	}

	/**
	 * Configure.
	 *
	 * @param oauthServer the oauth server
	 */
	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	/**
	 * Token services.
	 *
	 * @return the default token services
	 */
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(this.tokenStore());
		defaultTokenServices.setSupportRefreshToken(false);
		defaultTokenServices.setReuseRefreshToken(false);
		defaultTokenServices.setAuthenticationManager(this.authenticationManager);
		return defaultTokenServices;
	}

	/**
	 * Jwt access token converter.
	 *
	 * @return the jwt access token converter
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(this.oauth2Properties.getSecret());
		return converter;
	}

}
