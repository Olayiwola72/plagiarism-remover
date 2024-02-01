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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plagiarism.plagiarismremover.entity.User;
import com.plagiarism.plagiarismremover.service.UserService;
import com.plagiarism.plagiarismremover.utils.UtilsTest;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = {"classpath:messages.properties"})
class GlobalExceptionHandlerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
    private UserService userService;

    @Mock
    private ReloadableResourceBundleMessageSource messageSource;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    
    @Value("${json.invalid.format}")
    private String expectedJsonInvalidFormatErrorMessage;

    @Value("${plagiarism-remover.base-path}")
    private String basePath;
    
    @Value("${plagiarism-remover.endpoint.token}")
    private String tokenEndpoint;
    
    @Value("${plagiarism-remover.endpoint.remove}")
    private String removeEndpoint;
    
    @Value("${plagiarism-remover.endpoint.internal-server-error}")
    private String internalServerErrorEndPoint;
    
 // User for tests
//    private User testUser;

//    @BeforeAll
//    static void setUpOnce() {
//        // Create a test user before each test
//        testUser = new User();
//        testUser.setUsername("username");
//        testUser.setPassword("password");
//        testUser.setRoles("USER");
//
//        userService.create(testUser);
//    }
    
//    @BeforeAll
//    void setUpOnce() {
//        // Create the test user once for all tests
//        testUser = new User();
//        testUser.setUsername("username");
//        testUser.setPassword("password");
//        testUser.setRoles("USER");
//    }
    
    @Test
    void handleAuthenticationException() throws Exception {
		String token = "invalid token";
		String apiUrl = basePath + removeEndpoint;
		
        // Simulating a sample AuthenticationException
        String jsonContent = "{\"message\": \"value\"}";
        MockHttpServletResponse response = UtilsTest.mockHttpPostRequestWithBearerToken(mockMvc, apiUrl, token, jsonContent);

        // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

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
    void handleAccessDeniedException() throws Exception {
    	User testUser = new  User();
    	testUser.setUsername("username");
    	testUser.setPassword("password");
		testUser.setRoles("USER");
		
		userService.create(testUser);
		
    	String apiUrl = basePath + tokenEndpoint;
		
        // Simulating a sample AccessDeniedException
        MockHttpServletResponse response = UtilsTest.mockHttpPostRequestWithBasicAuthCredentials(mockMvc, apiUrl, testUser.getUsername(), "password");

        // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

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
    void handleUsernameNotFoundAndBadCredentialsException() throws Exception {
		String apiUrl = basePath + tokenEndpoint;
		
        // Simulating a sample UsernameNotFoundException, BadCredentialsException
        MockHttpServletResponse response = UtilsTest.mockHttpPostRequestWithBasicAuthBadCredentials(mockMvc, apiUrl);

        // Validate against the MockHttpServletResponse
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

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
    void handleMethodArgumentNotValid() throws Exception {
		String token = UtilsTest.getToken(mockMvc, basePath + tokenEndpoint);
		String apiUrl = basePath + removeEndpoint;
		
        // Simulating a sample MethodArgumentNotValidException
        String jsonContent = "{\"field1\": \"value1\"}";
        MockHttpServletResponse response = UtilsTest.mockHttpPostRequestWithBearerToken(mockMvc, apiUrl, token, jsonContent);

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
		String token = UtilsTest.getToken(mockMvc, basePath + tokenEndpoint);
		String apiUrl = basePath + removeEndpoint;
		
		// Simulating a sample HttpMessageNotReadableException
        String jsonContent = "{\"field1\": }";
        MockHttpServletResponse response = UtilsTest.mockHttpPostRequestWithBearerToken(mockMvc, apiUrl, token, jsonContent);
        		
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
    	String token = UtilsTest.getToken(mockMvc, basePath + tokenEndpoint);
    	
        // Simulating a sample NoResourceFoundException
    	MockHttpServletResponse response = UtilsTest.mockHttpGetRequestWithBearerToken(mockMvc, UtilsTest.getNonExistentEndpointPath(), token);
	
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
    	String token = UtilsTest.getToken(mockMvc, basePath + tokenEndpoint);
    	String apiUrl = basePath + removeEndpoint;
    	
    	// Simulating a sample HttpRequestMethodNotSupportedException
        MockHttpServletResponse response = UtilsTest.mockHttpGetRequestWithBearerToken(mockMvc, apiUrl, token);
    	   
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
    	String token = UtilsTest.getToken(mockMvc, basePath + tokenEndpoint);
		String apiUrl = basePath + internalServerErrorEndPoint;
		
        // Simulating a sample Exception
        MockHttpServletResponse response = UtilsTest.mockHttpGetRequestWithBearerToken(mockMvc, apiUrl, token);
        		
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
}
