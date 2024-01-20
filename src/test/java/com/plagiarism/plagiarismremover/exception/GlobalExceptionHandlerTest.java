package com.plagiarism.plagiarismremover.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = {"classpath:messages.properties"})
class GlobalExceptionHandlerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @Mock
    private ReloadableResourceBundleMessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    
    @Value("${json.invalid.format}")
    private String expectedJsonInvalidFormatErrorMessage;

    
    @Autowired
    private Environment environment;
    
	@Test
    void handleMethodArgumentNotValid() throws Exception {
		String apiUrl = this.getApiBasePath() + this.getRemoveEndpointPath();
        // Simulating a sample MethodArgumentNotValidException
        String jsonContent = "{\"field1\": \"value1\"}";
        MockHttpServletResponse response = this.mockHttpPostRequest(jsonContent, apiUrl);

     // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        // Parse the JSON response content for further assertions
        String jsonResponse = response.getContentAsString();
        JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

        assertEquals(true, rootNode.path("errors").isArray());
        assertNotEquals(0, rootNode.path("errors").size());
        
        for(JsonNode errorFieldNode : rootNode.path("errors")) {
        	assertNotNull(errorFieldNode.path("fieldName").textValue());
        	assertNotNull(errorFieldNode.path("errorMessage").textValue());
        }
    }
	
	@Test
    void handleHttpMessageNotReadableException() throws Exception {
		String apiUrl = this.getApiBasePath() + this.getRemoveEndpointPath();
		// Simulating a sample HttpMessageNotReadableException
        String jsonContent = "{\"field1\": }";
        MockHttpServletResponse response = this.mockHttpPostRequest(jsonContent, apiUrl);
        		
     // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        
        // Parse the JSON response content for further assertions
        String jsonResponse = response.getContentAsString();
        JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

        assertEquals(true, rootNode.path("errors").isArray());
        assertNotEquals(0, rootNode.path("errors").size());
        

        for(JsonNode errorFieldNode : rootNode.path("errors")) {
        	assertNull(errorFieldNode.path("fieldName").textValue());
        	assertNotNull(errorFieldNode.path("errorMessage").textValue());
        	assertEquals(
        		expectedJsonInvalidFormatErrorMessage,
        		errorFieldNode.path("errorMessage").textValue()
        	);
        }
    }

    @Test
    void handleNoResourceFoundException() throws Exception {
        // Simulating a sample NoResourceFoundException
        MockHttpServletResponse response = this.mockHttpGetRequest(this.getNonExistentEndpointPath());
        		
     // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        
        // Parse the JSON response content for further assertions
        String jsonResponse = response.getContentAsString();
        JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

        assertEquals(true, rootNode.path("errors").isArray());
        assertNotEquals(0, rootNode.path("errors").size());
        
        for(JsonNode errorFieldNode : rootNode.path("errors")) {
        	assertNull(errorFieldNode.path("fieldName").textValue());
        	assertNotNull(errorFieldNode.path("errorMessage").textValue());
        }
    }
    
    @Test
    void handleHttpRequestMethodNotSupportedException() throws Exception {
    	String apiUrl = this.getApiBasePath() + this.getRemoveEndpointPath();
    	// Simulating a sample HttpRequestMethodNotSupportedException
        MockHttpServletResponse response = this.mockHttpGetRequest(apiUrl);
        
     // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());
        
        // Parse the JSON response content for further assertions
        String jsonResponse = response.getContentAsString();
        JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

        assertEquals(true, rootNode.path("errors").isArray());
        assertNotEquals(0, rootNode.path("errors").size());
        
        for(JsonNode errorFieldNode : rootNode.path("errors")) {
        	assertNull(errorFieldNode.path("fieldName").textValue());
        	assertNotNull(errorFieldNode.path("errorMessage").textValue());
        }
    }
    
    @Test
    void handleOtherExceptions() throws Exception {
		String apiUrl = this.getApiBasePath() + this.getInternalServerErrorEndpointPath();
        // Simulating a sample Exception
        MockHttpServletResponse response = this.mockHttpGetRequest(apiUrl);
        		
     // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        
        // Parse the JSON response content for further assertions
        String jsonResponse = response.getContentAsString();
        JsonNode rootNode = new ObjectMapper().readTree(jsonResponse);

        assertEquals(true, rootNode.path("errors").isArray());
        assertNotEquals(0, rootNode.path("errors").size());
        
        for(JsonNode errorFieldNode : rootNode.path("errors")) {
        	assertNull(errorFieldNode.path("fieldName").textValue());
        	assertNotNull(errorFieldNode.path("errorMessage").textValue());
        }
    }
    
	private MockHttpServletResponse mockHttpPostRequest(String jsonContent, String apiUrl) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent);
        
        // Performing the request and getting the MvcResult
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        // Extract the response from the MvcResult
        return mvcResult.getResponse();
	}
	
	private MockHttpServletResponse mockHttpGetRequest(String apiUrl) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(apiUrl);

     // Performing the request and getting the MvcResult
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        // Extract the response from the MvcResult
        return mvcResult.getResponse();
	}
	
	private String getApiBasePath() {
		return environment.getProperty("plagiarism-remover.base-path");
	}
	
	private String getRemoveEndpointPath() {
		return environment.getProperty("plagiarism-remover.endpoint.remove");
	}
	
	private String getNonExistentEndpointPath() {
		return "/api/nonexistent-endpoint";
	}
	
	private String getInternalServerErrorEndpointPath() {
		return environment.getProperty("plagiarism-remover.endpoint.internal-server-error");
	}
}
