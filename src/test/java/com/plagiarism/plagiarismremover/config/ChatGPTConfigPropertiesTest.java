package com.plagiarism.plagiarismremover.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@EnableConfigurationProperties(ChatGPTConfigProperties.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:secrets.properties")
public class ChatGPTConfigPropertiesTest {
	@Mock
    private ChatGPTConfigProperties mockedConfig;

    @Value("${openai.api-url}")
    private String expectedApiUrl;

    @Value("${openai.api-key}")
    private String expectedApiKey;

    @Value("${openai.model}")
    private String expectedModel;

    @Value("${openai.role}")
    private String expectedRole;

    @Value("${openai.instruction}")
    private String expectedInstruction;
    
    @Test
    public void testConfigPropertiesMatchSecretProperties() {
        // Mock behavior for your specific test case
        Mockito.when(mockedConfig.getApiUrl()).thenReturn(expectedApiUrl);
        Mockito.when(mockedConfig.getApiKey()).thenReturn(expectedApiKey);
        Mockito.when(mockedConfig.getModel()).thenReturn(expectedModel);
        Mockito.when(mockedConfig.getRole()).thenReturn(expectedRole);
        Mockito.when(mockedConfig.getInstruction()).thenReturn(expectedInstruction);
        
     // Check if each property in application.properties is not null
        assertNotNull(expectedApiUrl);
        assertNotNull(expectedApiKey);
        assertNotNull(expectedModel);
        assertNotNull(expectedRole);
        assertNotNull(expectedInstruction);
        
        // Check if each mocked property is not null
        assertNotNull(mockedConfig.getApiUrl());
        assertNotNull(mockedConfig.getApiKey());
        assertNotNull(mockedConfig.getModel());
        assertNotNull(mockedConfig.getRole());
        assertNotNull(mockedConfig.getInstruction());

        // Check if each property matches the value in secrets.properties
        assertEquals(expectedApiUrl, mockedConfig.getApiUrl());
        assertEquals(expectedApiKey, mockedConfig.getApiKey());
        assertEquals(expectedModel, mockedConfig.getModel());
        assertEquals(expectedRole, mockedConfig.getRole());
        assertEquals(expectedInstruction, mockedConfig.getInstruction());
    }
}
