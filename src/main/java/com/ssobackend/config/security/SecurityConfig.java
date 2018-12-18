package com.ssobackend.config.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ssobackend.domain.service.UserDetailsServiceImpl;
import com.ssobackend.exception.CustomOAuth2ExceptionRenderer;

@Profile("!test")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig {
	@Configuration
	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
		@Autowired
		private CustomLogoutSuccessHandler customLogoutSuccessHandler;
		@Autowired
		private CustomOAuth2ExceptionRenderer customOAuth2ExceptionRenderer;
		@Autowired
		private UserDetailsServiceImpl userService;
//		@Autowired
//		private TenantService tenantService;
		
		@Bean
		protected AuthenticationEntryPoint authenticationEntryPoint() {
			OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
			entryPoint.setExceptionRenderer(customOAuth2ExceptionRenderer);
			return entryPoint;
		}
		
		private PostAuthUserContextInitializerFilter postFilter(){
			return new PostAuthUserContextInitializerFilter(userService);
		};
		

		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
				.antMatchers("/user/requestPasswordResetToken/**").permitAll()
				.antMatchers("/user/resetPasswordByPasswordResetToken/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.logout().logoutUrl("/oauth/logout").logoutSuccessHandler(customLogoutSuccessHandler)
				.and()
				.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize")).disable()
				.headers().frameOptions().disable()
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				.addFilterAfter(postFilter(), BasicAuthenticationFilter.class);
		}

		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.authenticationEntryPoint(authenticationEntryPoint());
		}
	}

	@Configuration
	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
			implements EnvironmentAware {

		private static final String ENV_OAUTH = "authentication.oauth.";
		private static final String PROP_CLIENTIDWEB = "clientidweb";
		private static final String PROP_SECRETWEB = "secretweb";
		private static final String PROP_TOKEN_VALIDITY_SECONDSWEB = "tokenValidityInSecondsweb";

		private static final String PROP_CLIENTIDAPP = "clientidapp";
		private static final String PROP_SECRETAPP = "secretapp";
		private static final String PROP_TOKEN_VALIDITY_SECONDSAPP = "tokenValidityInSecondsapp";

		private RelaxedPropertyResolver propertyResolver;

		@Autowired
		@Qualifier("globalDataSource")
		private DataSource dataSource;
		@Autowired
		private PerSessionAuthenticationKeyGenerator authenticationKeyGenerator;
		
		@Bean
		public TokenStore tokenStore() {
			JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
			jdbcTokenStore.setAuthenticationKeyGenerator(authenticationKeyGenerator);
			
			return jdbcTokenStore;
		}

		@Autowired
		UserDetailsService userDetailsService;

		@Bean
		public TokenEnhancer tokenEnhancer() {
			return new OAuth2TokenEnhancer();
		}

		@Autowired
		BCryptPasswordEncoder bCryptPasswordEncoder;

		@Autowired
		@Qualifier("customAuthenticationManager")
		AuthenticationManager authenticationManager;

		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancer())
					.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		}

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			clients.inMemory().withClient(propertyResolver.getProperty(PROP_CLIENTIDWEB)).scopes("read", "write")
					.authorizedGrantTypes("password", "refresh_token")
					.secret(propertyResolver.getProperty(PROP_SECRETWEB))
					.accessTokenValiditySeconds(
							propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDSWEB, Integer.class, 1800))
					.and().withClient(propertyResolver.getProperty(PROP_CLIENTIDAPP)).scopes("read", "write")
					.authorizedGrantTypes("password", "refresh_token")
					.secret(propertyResolver.getProperty(PROP_SECRETAPP)).accessTokenValiditySeconds(
							propertyResolver.getProperty(PROP_TOKEN_VALIDITY_SECONDSAPP, Integer.class, 1800));
		}
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			security.checkTokenAccess("permitAll()");
		}

		@Override
		public void setEnvironment(Environment environment) {
			this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_OAUTH);
		}
	}

	public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
		@Override
		protected MethodSecurityExpressionHandler createExpressionHandler() {
			return new OAuth2MethodSecurityExpressionHandler();
		}
	}
}