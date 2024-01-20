package com.plagiarism.plagiarismremover.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootTest
public class MessageSourceConfigTest {
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	@Autowired
	LocalValidatorFactoryBean bean;
	
	@Autowired
	SessionLocaleResolver localeResolver;
	
	@Test
	public void testMessageSourceBean() {
		assertNotNull(messageSource);
        assertTrue(messageSource.getBasenameSet().contains("classpath:messages"));
	}
	
	@Test
	void testValidatorBean() {
		assertNotNull(bean);
	}
	
	@Test
    public void testLocaleResolverBean() {
        assertNotNull(localeResolver);
    }
}
