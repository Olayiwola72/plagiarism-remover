package com.plagiarism.plagiarismremover.entity.errors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ErrorResponseTest {

	@Test
	void testDefaultConstructor() {
		ErrorResponse errorResponse = new ErrorResponse();
		assertNotNull(errorResponse.getErrors());
		assertEquals(0, errorResponse.getErrors().size());
	}
	
	@Test
	void testMessageConstructor() {
		ErrorResponse errorResponse = new ErrorResponse();
		ErrorField errorField = new ErrorField("errorMessage");
		
		errorResponse.addErrors(errorField);	
		assertEquals(1, errorResponse.getErrors().size());
		
		assertEquals(errorResponse.getErrors().get(0).getErrorMessage(), "errorMessage");
		assertEquals(errorResponse.getErrors().get(0).getFieldName(), null);
	}
	
	@Test
	void testMessageAndFieldNameConstructor() {
		testGetAddErrorFields();
	}
	
	@Test
	void testGetAddErrorFields() {
		ErrorResponse errorResponse = new ErrorResponse();
		ErrorField errorField = new ErrorField("errorMessage","fieldName");
		
		testDefaultConstructor();
		
		errorResponse.addErrors(errorField);	
		assertEquals(1, errorResponse.getErrors().size());
		
		assertEquals(errorResponse.getErrors().get(0).getErrorMessage(), "errorMessage");
		assertEquals(errorResponse.getErrors().get(0).getFieldName(), "fieldName");
	}
}
