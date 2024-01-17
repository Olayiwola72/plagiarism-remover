package com.plagiarism.plagiarismremover.exception;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.plagiarism.plagiarismremover.entity.errors.ErrorFields;
import com.plagiarism.plagiarismremover.entity.errors.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final ReloadableResourceBundleMessageSource messageSource;

	public GlobalExceptionHandler(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		BindingResult result = ex.getBindingResult();
		ErrorResponse errorResponse = new ErrorResponse();

		List<FieldError> fieldErrors = result.getFieldErrors();
		if(!fieldErrors.isEmpty()) {
			 for(FieldError fieldError : fieldErrors) {
				 String fieldName = fieldError.getField();
				 String errorCode = fieldError.getCode();

//				 Get the error message from the  template to include the additional parameter
//				 Additional parameter, for example, the constraint (e.g., "fieldName")
				 String errorMessage = messageSource.getMessage(
						 					errorCode, 
						 					new Object[]{fieldName},
						 					LocaleContextHolder.getLocale()
						 			);
				 
				 ErrorFields errorFields = new ErrorFields(errorMessage, fieldName);
				 errorResponse.addErrorFields(errorFields);
			 }
			 
			 return ResponseEntity.badRequest().body(errorResponse);
		}else {
			errorResponse = new ErrorResponse(messageSource.getMessage("json.validation.failed", null, LocaleContextHolder.getLocale()), "");
	        return ResponseEntity.badRequest().body(errorResponse);
		}
    }
	
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable rootCause = getRootCause(ex);
        if (rootCause instanceof JsonParseException) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ""); // Extract the raw "message" attribute
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        	
        ErrorResponse errorResponse = new ErrorResponse(messageSource.getMessage("json.invalid.format", null, LocaleContextHolder.getLocale()), "");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
