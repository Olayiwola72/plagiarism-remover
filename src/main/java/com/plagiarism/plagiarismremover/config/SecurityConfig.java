package com.plagiarism.plagiarismremover.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.plagiarism.plagiarismremover.security.DelegatedAuthenticationEntryPoint;
import com.plagiarism.plagiarismremover.security.DelegatedBearerTokenAccessDeniedHandler;
import com.plagiarism.plagiarismremover.service.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Value("${auth.whitelist.urls}")
    private String[] authWhitelistUrls;
	
	private final RsaKeyProperties rsaKeys;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserService userService;

	private final DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;
	
	private final DelegatedBearerTokenAccessDeniedHandler delegatedBearerTokenAccessDeniedHandler;
	
	public SecurityConfig(RsaKeyProperties rsaKeys, PasswordEncoder passwordEncoder, UserService userService, DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint, DelegatedBearerTokenAccessDeniedHandler delegatedBearerTokenAccessDeniedHandler) {
		this.rsaKeys = rsaKeys;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
		this.delegatedBearerTokenAccessDeniedHandler = delegatedBearerTokenAccessDeniedHandler;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> auth
		                .requestMatchers(authWhitelistUrls).permitAll() // Exclude URLs
		                .anyRequest()	              
		                .authenticated()
					)
				 .csrf(csrf -> csrf
				            .ignoringRequestMatchers(authWhitelistUrls)
				            .disable()
				       )
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable)) // This is for H2 Browser Console Access
				.cors(Customizer.withDefaults())
				.httpBasic(basic -> basic
						.authenticationEntryPoint(this.delegatedAuthenticationEntryPoint)
					)
				.oauth2ResourceServer((oauth2) -> oauth2 // Spring Security built-in support for JWTs using oAuth2 Resource Server.
						.jwt(Customizer.withDefaults())
						.authenticationEntryPoint(this.delegatedAuthenticationEntryPoint)
						.accessDeniedHandler(this.delegatedBearerTokenAccessDeniedHandler)
					)
				.exceptionHandling(Customizer.withDefaults())
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(Customizer.withDefaults())
				.authenticationProvider(this.authenticationProvider())
				.build();
	}
	
	
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
	}
	
	@Bean
	public JwtEncoder jwtEncoder() {
		JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(this.userService);
		authenticationProvider.setPasswordEncoder(this.passwordEncoder);
		return authenticationProvider;
	}
}



