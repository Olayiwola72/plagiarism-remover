package com.plagiarism.plagiarismremover.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.plagiarism.plagiarismremover.security.DelegatedAuthenticationEntryPoint;
import com.plagiarism.plagiarismremover.security.DelegatedBearerTokenAccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${auth.whitelist.urls}")
    private String[] authWhitelistUrls;
	
	private final RsaKeyProperties rsaKeys;
	
	private final DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint;
	
	private final DelegatedBearerTokenAccessDeniedHandler delegatedBearerTokenAccessDeniedHandler;
	
	public SecurityConfig(RsaKeyProperties rsaKeys, DelegatedAuthenticationEntryPoint delegatedAuthenticationEntryPoint, DelegatedBearerTokenAccessDeniedHandler delegatedBearerTokenAccessDeniedHandler) {
		this.rsaKeys = rsaKeys;
		this.delegatedAuthenticationEntryPoint = delegatedAuthenticationEntryPoint;
		this.delegatedBearerTokenAccessDeniedHandler = delegatedBearerTokenAccessDeniedHandler;
	}
	
	@Bean
	public InMemoryUserDetailsManager user() {
		return new InMemoryUserDetailsManager(
			User.withUsername("ola")
				.password("{noop}password")
				.authorities("read")
				.build()
		);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf((csrf) -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
		                .requestMatchers(authWhitelistUrls).permitAll() // Exclude URLs
		                .anyRequest()	              
		                .authenticated()
		         )
				.oauth2ResourceServer((oauth2) -> oauth2
						.jwt(Customizer.withDefaults())
						.authenticationEntryPoint(this.delegatedAuthenticationEntryPoint)
						.accessDeniedHandler(this.delegatedBearerTokenAccessDeniedHandler)
				)
				.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.httpBasic(basic -> basic.authenticationEntryPoint(this.delegatedAuthenticationEntryPoint))
				.exceptionHandling(Customizer.withDefaults())
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
}



