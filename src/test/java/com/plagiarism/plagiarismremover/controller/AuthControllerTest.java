package com.plagiarism.plagiarismremover.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plagiarism.plagiarismremover.config.MessageSourceConfig;
import com.plagiarism.plagiarismremover.config.SecurityConfig;
import com.plagiarism.plagiarismremover.security.DelegatedAuthenticationEntryPoint;
import com.plagiarism.plagiarismremover.service.TokenService;
import com.plagiarism.plagiarismremover.utils.UtilsTest;

@WebMvcTest({ AuthController.class })
@Import({ SecurityConfig.class, TokenService.class, DelegatedAuthenticationEntryPoint.class,
		MessageSourceConfig.class })
class AuthControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Value("${plagiarism-remover.endpoint.token}")
	private String tokenEndpoint;

	@Value("${plagiarism-remover.base-path}")
    private String basePath;
	
	@Test
	void unauthenticatedRequestShouldReturnUnauthorized401() throws Exception {
		MockHttpServletResponse response = UtilsTest.mockHttpGetRequest(mockMvc, "/");

		// Validate against the MockHttpServletResponse
		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
	}

	@Test
	@WithMockUser
	void authorizedRequestShouldReturnOk() throws Exception {
		MockHttpServletResponse response = UtilsTest.mockHttpPostRequest(mockMvc, basePath + tokenEndpoint);

		// Validate against the MockHttpServletResponse
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	void whenAuthenticatedReturnToken() throws Exception {
		MockHttpServletResponse response = UtilsTest.mockHttpPostRequestWithBasicAuth(mockMvc, basePath + tokenEndpoint);

		// Validate against the MockHttpServletResponse
		assertEquals(HttpStatus.OK.value(), response.getStatus());

		// Parse the JSON response content for further assertions
		String jsonResponse = response.getContentAsString();
		JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

		assertNotNull(rootNode.path("token").textValue());
	}
}
