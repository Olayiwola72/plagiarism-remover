package com.plagiarism.plagiarismremover.exception;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.plagiarism.plagiarismremover.entity.errors.ErrorField;
import com.plagiarism.plagiarismremover.entity.errors.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final ReloadableResourceBundleMessageSource messageSource;

	public GlobalExceptionHandler(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;		
	}
	
	@ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
	
	@ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
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
				 
				 ErrorField errorField = new ErrorField(errorMessage, fieldName);
				 errorResponse.addErrors(errorField);
			 }
			 
			 return ResponseEntity.badRequest().body(errorResponse);
		}else {
			errorResponse = new ErrorResponse(messageSource.getMessage("json.validation.failed", null, LocaleContextHolder.getLocale()));
	        return ResponseEntity.badRequest().body(errorResponse);
		}
    }
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(messageSource.getMessage("json.invalid.format", null, LocaleContextHolder.getLocale()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
	
	
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()); 
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()); 
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
	}
	
	@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleOtherExceptions(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage()); 
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
